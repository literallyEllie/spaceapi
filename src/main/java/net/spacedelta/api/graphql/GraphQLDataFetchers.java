package net.spacedelta.api.graphql;

import graphql.kickstart.tools.GraphQLQueryResolver;
import graphql.schema.DataFetcher;
import net.spacedelta.api.mongo.MongoManager;
import net.spacedelta.api.stats.StatsRepository;
import net.spacedelta.api.stats.AbstractStat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class GraphQLDataFetchers implements GraphQLQueryResolver {

    private MongoManager mongoManager;

    private final StatsRepository statsRepository = new StatsRepository();

    private final AtomicInteger requests = new AtomicInteger(0);

    /* Stats */

    public AbstractStat stat(String key) {
        return statsRepository.getStat(key);
    }

    public List<AbstractStat> stats(String[] keys) {
        return statsRepository.getStats(keys);
    }

    public DataFetcher<AbstractStat> getStatByKey() {
        return fetchingEnvironment -> {
            requests.getAndIncrement();

            final String statId = fetchingEnvironment.getArgument("key");
            return stat(statId);
        };
    }

    public DataFetcher<List<AbstractStat>> getStatsByKey() {
        return fetchingEnvironment -> {
            requests.getAndIncrement();

            final String[] statIds = fetchingEnvironment.getArgument("keys");
            return stats(statIds);
        };
    }

    public AtomicInteger getRequests() {
        return requests;
    }

}
