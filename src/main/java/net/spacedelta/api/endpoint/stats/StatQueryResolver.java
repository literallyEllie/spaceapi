package net.spacedelta.api.endpoint.stats;

import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

/**
 * Resolver for GraphQL schema requests
 */
@Component
public class StatQueryResolver implements GraphQLQueryResolver {

    @Autowired
    private StatsRepository repository;

    /**
     * Default (and only) endpoint for stat accessing
     *
     * @return repository
     */
    @PreAuthorize("hasRole('USER')")
    public StatsRepository stat() {
        return repository;
    }

}
