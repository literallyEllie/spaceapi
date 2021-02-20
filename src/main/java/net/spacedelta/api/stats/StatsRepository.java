package net.spacedelta.api.stats;

import com.google.common.collect.Lists;
import net.spacedelta.api.stats.impl.StatisticOnlinePlayers;
import net.spacedelta.api.stats.impl.StatisticUniquePlayers;

import java.util.List;

public class StatsRepository {

    private final List<AbstractStat> statisticRegistry;
    private final StatsUpdaterThread updaterThread;

    public StatsRepository() {
        statisticRegistry = Lists.newArrayList(
                new StatisticUniquePlayers(),
                new StatisticOnlinePlayers()
        );
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

    public List<AbstractStat> getStats(String[] ids) {
        List<AbstractStat> stats = Lists.newArrayList();
        for (String id : ids) {
            final AbstractStat stat = getStat(id);
            if (stat != null) {
                stats.add(stat);
            }
        }

        return stats;
    }

}
