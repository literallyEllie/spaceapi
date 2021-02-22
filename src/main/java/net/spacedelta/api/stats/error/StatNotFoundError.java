package net.spacedelta.api.stats.error;

import graphql.GraphQLException;

public class StatNotFoundError extends GraphQLException {

    public StatNotFoundError(String query) {
        super(query + " is not a stat");
    }
}
