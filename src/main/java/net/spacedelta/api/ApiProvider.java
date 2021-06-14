package net.spacedelta.api;

import com.google.common.io.Resources;
import graphql.GraphQL;
import graphql.kickstart.tools.SchemaParser;
import graphql.schema.GraphQLSchema;
import kotlin.text.Charsets;
import net.spacedelta.api.endpoint.stats.StatQueryResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URL;

/**
 * The provider of the service
 * <p>
 * Loads the schema and registers the appropriate resolvers.
 */
@Service
public class ApiProvider {

    private GraphQL graphQL;

    @Autowired
    private StatQueryResolver statQueryResolver;

    @Bean
    public GraphQL graphQL() {
        return graphQL;
    }

    @PostConstruct
    public void init() throws IOException {
        URL url = Resources.getResource("schema.graphqls");
        String sdl = Resources.toString(url, Charsets.UTF_8);
        GraphQLSchema graphQLSchema = buildSchema(sdl);

        this.graphQL = GraphQL.newGraphQL(graphQLSchema)
                .build();
    }

    private GraphQLSchema buildSchema(String sdl) {
        return SchemaParser.newParser()
                .resolvers(statQueryResolver)
                .schemaString(sdl)
                .build()
                .makeExecutableSchema();
    }

}
