package net.spacedelta.api.stats.impl;

import graphql.com.google.common.collect.Lists;
import net.spacedelta.api.stats.AbstractStat;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class OverviewStatisticSet extends AbstractStat {

    private final List<AbstractStat> INCLUDE = Lists.newArrayList(
            new StatisticUniquePlayers()
    );

    public OverviewStatisticSet(long refreshRate) {
        super("overview", TimeUnit.MINUTES.toMillis(10));
    }

    @Override
    public List<AbstractStat> getValue() {
        return INCLUDE;
    }

    @Override
    public void setValue(Object object) {
    }

    @Override
    public void refresh() {
        INCLUDE.forEach(AbstractStat::refresh);
    }

}
