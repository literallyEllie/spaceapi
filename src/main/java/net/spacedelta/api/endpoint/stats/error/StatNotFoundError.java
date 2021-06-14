package net.spacedelta.api.endpoint.stats.error;

import graphql.GraphQLException;

public class StatNotFoundError extends GraphQLException {

    /**
     * Exception thrown when a specified query does not exist
     *
     * @param query what was not found
     */
    public StatNotFoundError(String query) {
        super(query + " is not a stat");
    }

}
