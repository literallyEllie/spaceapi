package net.spacedelta.api.stats.impl;

import net.spacedelta.api.stats.AbstractStat;
import net.spacedelta.api.stats.IntegerStat;

import java.util.concurrent.TimeUnit;

public class StatisticOnlinePlayers extends IntegerStat {

    public StatisticOnlinePlayers() {
        super("unique_players");
    }

    @Override
    public void refresh() {
        // todo
    }

}
