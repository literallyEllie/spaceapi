package net.spacedelta.api.stats.impl;

import net.spacedelta.api.stats.AbstractStat;

import java.util.concurrent.TimeUnit;

public class StatisticUniquePlayers extends AbstractStat {

    private int amount;

    public StatisticUniquePlayers() {
        super("unique_players", TimeUnit.MINUTES.toMillis(10));
    }

    @Override
    public void refresh() {
        // todo
    }

    @Override
    public Object getValue() {
        return amount;
    }

    @Override
    public void setValue(Object object) {
        this.amount = (Integer) object;
    }

}
