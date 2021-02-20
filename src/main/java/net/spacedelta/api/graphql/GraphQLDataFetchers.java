package net.spacedelta.api.graphql;

import graphql.kickstart.tools.GraphQLQueryResolver;
import graphql.schema.DataFetcher;
import net.spacedelta.api.stats.StatsRepository;
import net.spacedelta.api.stats.AbstractStat;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GraphQLDataFetchers implements GraphQLQueryResolver {

    private final StatsRepository statsRepository = new StatsRepository();

    public AbstractStat stat(String key) {
        return statsRepository.getStat(key);
    }

    public List<AbstractStat> stats(String[] keys) {
        return statsRepository.getStats(keys);
    }

    public DataFetcher<AbstractStat> getStatByKey() {
        return fetchingEnvironment -> {
            final String statId = fetchingEnvironment.getArgument("key");
            return stat(statId);
        };
    }

    public DataFetcher<List<AbstractStat>> getStatsByKey() {
        return fetchingEnvironment -> {
            final String[] statIds = fetchingEnvironment.getArgument("keys");
            return stats(statIds);
        };
    }

}
