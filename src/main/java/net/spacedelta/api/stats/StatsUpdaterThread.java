package net.spacedelta.api.stats;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class StatsUpdaterThread implements Runnable {

    private final Logger logger = LoggerFactory.getLogger("Stats-Updater");

    private final StatsRepository repository;

    private final ScheduledFuture<?> taskRunner;
    private final Executor updaterExecutor;

    /**
     * Updater thread for refreshing stats and keeping them not stale
     * with respect to their refresh rate
     *
     * @param repository repository instance
     */
    public StatsUpdaterThread(StatsRepository repository) {
        this.repository = repository;

        // delay 1 minute to allow for mongo to init
        taskRunner = Executors.newSingleThreadScheduledExecutor().scheduleWithFixedDelay(
                this,
                1,
                10,
                TimeUnit.MINUTES
        );

        updaterExecutor = Executors.newCachedThreadPool();
    }

    /**
     * Terminate the updater thread
     */
    public void terminate() {
        taskRunner.cancel(true);
    }

    @Override
    public void run() {
        repository.getStatisticRegistry().stream()
                .filter(statistic -> System.currentTimeMillis() - statistic.getLastRefresh() > statistic.getRefreshRate())
                .forEach(statistic -> {
                    logger.info("Updating " + statistic.getKey());

                    updaterExecutor.execute(() -> statistic.refresh(repository));
                    statistic.setLastRefresh(System.currentTimeMillis());
                });
    }

}
