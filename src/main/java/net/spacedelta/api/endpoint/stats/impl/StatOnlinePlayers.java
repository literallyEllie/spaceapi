package net.spacedelta.api.endpoint.stats.impl;

import net.spacedelta.api.endpoint.stats.StatsRepository;
import net.spacedelta.api.endpoint.stats.api.NumberStat;

/**
 * Online player provider which counts the online network players using the database
 */
public class StatOnlinePlayers extends NumberStat {

    @Override
    public void refresh(StatsRepository repository) {
        setValue(repository.getMongoManager()
                .getCollection("SERVER_PLAYERS")
                .countDocuments());
    }

}
