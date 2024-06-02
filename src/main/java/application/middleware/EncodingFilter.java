package application.middleware;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/*")
public class EncodingFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request instanceof HttpServletRequest) {
            ((HttpServletRequest) request).setCharacterEncoding("UTF-8");
        }
        if (response instanceof HttpServletResponse) {
            ((HttpServletResponse) response).setCharacterEncoding("UTF-8");
            ((HttpServletResponse) response).setContentType("application/json; charset=UTF-8");
        }
        chain.doFilter(request, response);
    }
}

