package application.middleware;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ErrorHandlingFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            chain.doFilter(request, response);
        } catch (Exception e) {
            handleException((HttpServletResponse) response, e);
        }
    }
    private void handleException(HttpServletResponse httpResponse, Exception e) throws IOException {
        int statusCode;
        String errorMessage;
        statusCode = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
        errorMessage = "Помилка на сервері: " + e.getMessage();
        httpResponse.setStatus(statusCode);
        httpResponse.getWriter().write(errorMessage);
    }
}
