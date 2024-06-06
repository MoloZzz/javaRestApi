package application.service;

import application.config.Auth0Config;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Date;

public class AuthService {
    private static final String SECRET = Auth0Config.CLIENT_SECRET;
    private static final long EXPIRATION_TIME = 864_000_000;

    public String login(String username, String password) throws Exception {
        Algorithm algorithm = Algorithm.HMAC256(SECRET);
        return JWT.create()
                .withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(algorithm);
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            JWT.require(algorithm)
                    .build()
                    .verify(token);
            return JWT.decode(token).getSubject();
        } catch (Exception e) {
            return null;
        }
    }

}
