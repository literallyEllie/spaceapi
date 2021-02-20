package net.spacedelta.api.graphql;

import graphql.schema.DataFetcher;
import net.spacedelta.api.stats.StatsRepository;
import net.spacedelta.api.stats.AbstractStat;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Component
public class GraphQLDataFetchers {

    private final StatsRepository statsRepository = new StatsRepository();

    @GetMapping(value = "/stat/{id}")
    public AbstractStat getStat(@PathVariable String id) {
        return statsRepository.getStat(id);
    }

    public DataFetcher<AbstractStat> getStatById() {
        return fetchingEnvironment -> {
            final String statId = fetchingEnvironment.getArgument("id");
            return getStat(statId);
        };
    }

}
