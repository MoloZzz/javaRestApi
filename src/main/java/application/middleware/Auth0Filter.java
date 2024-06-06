package application.middleware;

import application.config.Auth0Config;
import application.service.AuthService;
import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.JwkProviderBuilder;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;


public class Auth0Filter implements Filter {
    private JwkProvider provider;
    private String audience;
    private String issuer;
    private AuthService authService;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Set your Auth0 domain
        String domain = Auth0Config.DOMAIN;
        this.provider = new JwkProviderBuilder(domain)
                .cached(10, 24, TimeUnit.HOURS)
                .rateLimited(10, 1, TimeUnit.MINUTES)
                .build();

        this.audience = Auth0Config.CLIENT_ID;
        this.authService = new AuthService();
        this.issuer = "https://" + domain + "/";
    }
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String path = httpRequest.getRequestURI();
        if (path.startsWith("/api/register") || path.startsWith("/api/login")) {
            chain.doFilter(request, response);
            return;
        }
        String token = httpRequest.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        if (token != null && authService.validateToken(token) != null) {
            chain.doFilter(request, response);
        } else {
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}



