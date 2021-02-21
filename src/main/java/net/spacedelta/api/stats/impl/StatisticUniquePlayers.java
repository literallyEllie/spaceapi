package net.spacedelta.api.stats.impl;

import net.spacedelta.api.stats.IntegerStat;

public class StatisticUniquePlayers extends IntegerStat {

    public StatisticUniquePlayers() {
        super("online_players");
    }

    @Override
    public void refresh() {
        // todo
    }

}
