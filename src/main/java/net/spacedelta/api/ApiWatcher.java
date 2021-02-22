package net.spacedelta.api;

import graphql.kickstart.servlet.core.GraphQLServletListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Watches and counts requests and logs them
 */
@Component
public class ApiWatcher implements GraphQLServletListener {

    private final Logger logger = LoggerFactory.getLogger("Api-Watcher");
    private final AtomicInteger requests = new AtomicInteger();

    @Override
    public RequestCallback onRequest(HttpServletRequest request, HttpServletResponse response) {
        int reqId = requests.incrementAndGet();

        logger.info("(#" + reqId + ") Received " + request.getMethod() + " request from " + request.getRemoteAddr() + ":"
                + request.getRemotePort());

        long start = System.currentTimeMillis();

        return new RequestCallback() {
            @Override
            public void onSuccess(HttpServletRequest request, HttpServletResponse response) {
            }

            @Override
            public void onError(HttpServletRequest request, HttpServletResponse response, Throwable throwable) {
            }

            @Override
            public void onFinally(HttpServletRequest request, HttpServletResponse response) {
                logger.info("(#" + reqId + ") Processed request in " + (System.currentTimeMillis() - start) + "ms " +
                        "(" + response.getStatus() + ")");
            }
        };
    }

}
