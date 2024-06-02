package application.middleware;

import application.utils.Logging.StatusLoggingHttpServletResponseWrapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebFilter("/*")
public class LoggingFilter implements Filter {
    private static final Logger logger = LogManager.getLogger(LoggingFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        StatusLoggingHttpServletResponseWrapper wrappedResponse = new StatusLoggingHttpServletResponseWrapper((HttpServletResponse) response);
        long startTime = System.currentTimeMillis();

        try {
            // Log the request details
            logger.info("Request: " + httpRequest.getMethod() + " " + httpRequest.getRequestURI());
            chain.doFilter(request, wrappedResponse);
        } catch (Exception e) {
            // Log any exceptions that occur
            logger.error("Error processing request", e);
            throw e;
        } finally {
            long duration = System.currentTimeMillis() - startTime;
            // Log the response details
            logger.info("Response: " + httpRequest.getMethod() + " " + httpRequest.getRequestURI() + " completed in " + duration + "ms with status " + wrappedResponse.getStatus());
        }
    }
}
