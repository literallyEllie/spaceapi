package net.spacedelta.api.stats;

import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

@Component
public class StatQueryResolver implements GraphQLQueryResolver {

    @Autowired
    private StatsRepository repository;

    @PreDestroy
    public void preDestroy() {
        repository.shutdown();
    }

    @PreAuthorize("hasRole('USER')")
    public StatsRepository stat() {
        return repository;
    }

}
