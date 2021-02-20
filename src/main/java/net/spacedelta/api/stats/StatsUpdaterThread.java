package net.spacedelta.api.stats;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StatsUpdaterThread extends Thread {

    private final Logger logger = LoggerFactory.getLogger("Stats-Updater");

    private final StatsRepository repository;

    public StatsUpdaterThread(StatsRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run() {
        while (true) {

            repository.getStatisticRegistry().stream()
                    .filter(statistic -> System.currentTimeMillis() - statistic.getLastRefresh() > statistic.getRefreshRate())
                    .forEach(statistic -> {

                        statistic.setLastRefresh(System.currentTimeMillis());
                    });

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                logger.error("interruption checking stats", e);
            }
        }
    }

}
