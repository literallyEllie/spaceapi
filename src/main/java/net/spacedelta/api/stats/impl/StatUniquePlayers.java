package net.spacedelta.api.stats.impl;

import net.spacedelta.api.stats.IntegerStat;
import net.spacedelta.api.stats.StatsRepository;

/**
 * Unique player provider which counts the unique network players using the database
 */
public class StatUniquePlayers extends IntegerStat {

    @Override
    public void refresh(StatsRepository repository) {
        setValue(repository.getMongoManager()
                .getCollection("ACCOUNTS")
                .countDocuments());
    }

}
