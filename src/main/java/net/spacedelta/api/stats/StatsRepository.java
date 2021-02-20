package net.spacedelta.api.stats;

import com.google.common.collect.Lists;

import java.util.List;

public class StatsRepository {

    private final List<AbstractStat> statisticRegistry;
    private final StatsUpdaterThread updaterThread;

    public StatsRepository() {
        statisticRegistry = Lists.newArrayList();
        this.updaterThread = new StatsUpdaterThread(this);
    }

    public List<AbstractStat> getStatisticRegistry() {
        return statisticRegistry;
    }

    public AbstractStat getStat(String id) {
        return statisticRegistry.stream()
                .filter(abstractStat -> abstractStat.getKey().equals(id))
                .findFirst()
                .orElse(null);
    }

}
