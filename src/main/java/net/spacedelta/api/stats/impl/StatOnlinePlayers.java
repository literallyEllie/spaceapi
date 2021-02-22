package net.spacedelta.api.stats.impl;

import net.spacedelta.api.stats.IntegerStat;
import net.spacedelta.api.stats.StatsRepository;

/**
 * Online player provider which counts the online network players using the database
 */
public class StatOnlinePlayers extends IntegerStat {

    @Override
    public void refresh(StatsRepository repository) {
        setValue(repository.getMongoManager()
                .getCollection("SERVER_PLAYERS")
                .countDocuments());
    }

}
