package net.spacedelta.api.endpoint.stats.impl;

import net.spacedelta.api.endpoint.stats.StatsRepository;
import net.spacedelta.api.endpoint.stats.api.NumberStat;

/**
 * Unique player provider which counts the unique network players using the database.
 */
public class StatUniquePlayers extends NumberStat {

    @Override
    public void refresh(StatsRepository repository) {
        setValue(repository.getMongoManager()
                .getCollection("ACCOUNTS")
                .countDocuments());
    }

}
