package net.spacedelta.api.error;

import graphql.ErrorClassification;
import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.GraphQLException;
import graphql.kickstart.execution.error.GraphQLErrorHandler;
import graphql.kickstart.spring.error.ThrowableGraphQLError;
import graphql.language.SourceLocation;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ApiExceptionHandler implements GraphQLErrorHandler {

    @ExceptionHandler(GraphQLException.class)
    public ThrowableGraphQLError handle(GraphQLException e) {
        return new ThrowableGraphQLError(e);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ThrowableGraphQLError handle(AccessDeniedException e) {
        return new ThrowableGraphQLError(e, HttpStatus.FORBIDDEN.getReasonPhrase());
    }

    @ExceptionHandler(RuntimeException.class)
    public ThrowableGraphQLError handle(RuntimeException e) {
        return new ThrowableGraphQLError(e, "Internal Server Error");
    }

    @Override
    public List<GraphQLError> processErrors(List<GraphQLError> errors) {
        return errors.stream()
                .map(graphQLError -> {
                    if (graphQLError.getErrorType() == ErrorType.ValidationError) {
                        return new GraphQLError() {
                            @Override
                            public String getMessage() {
                                return "No such schema field at" + graphQLError.getMessage().split("@")[1];
                            }

                            @Override
                            public List<SourceLocation> getLocations() {
                                return null;
                            }

                            @Override
                            public ErrorClassification getErrorType() {
                                return graphQLError.getErrorType();
                            }
                        };
                    }

                    return graphQLError;
                })
                .collect(Collectors.toList());
    }

}
