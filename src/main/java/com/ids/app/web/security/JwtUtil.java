package com.ids.app.web.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.ids.app.domain.service.UserDtoService;
import com.ids.app.persistence.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
public class JwtUtil {
    private final Log LOGGER = LogFactory.getLog(JwtUtil.class);
    private static final String SECRET_KEY = "*56384abc123";
    private static Algorithm ALGORITHM = Algorithm.HMAC256(SECRET_KEY);
    private static final long ACCESS_TOKEN_EXPIRATION = 60000; // 15 minutos 900000
    private static final long REFRESH_TOKEN_EXPIRATION = 1800000; // 30 minutos
    private static final long RECOVERY_TOKEN_EXPIRATION = 900000; // 15 minutos
    private static UserDtoService userDtoService;
    public JwtUtil(UserDtoService userDtoService){
        this.userDtoService = userDtoService;
    }
    public String generateToken(long id){
        LOGGER.info("Generating Token...");
        return JWT.create()
                .withSubject(""+id)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION))
                .sign(ALGORITHM);
    }
    public String generateRefreshToken(long id){
        LOGGER.info("Generating Refresh Token...");
        return JWT.create()
                .withSubject(""+id)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION))
                .sign(ALGORITHM);
    }
    public String generateRecoveryToken(long id){
        return JWT.create()
                .withSubject(""+id)
                //.withIssuer("webapp")
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + RECOVERY_TOKEN_EXPIRATION))
                .sign(ALGORITHM);
    }
    public boolean validateToken(String token){
        try {
            LOGGER.info("validating Token...");
            JWT.require(ALGORITHM)
                    .build()
                    .verify(token);
            return true;
        }catch (JWTVerificationException ex){
            LOGGER.error("validate token error "+ex.getMessage());
            return false;
        }
    }
    public String resolveToken(HttpServletRequest request){
        LOGGER.info("resolve Token...");
        String bearerToken = request.getHeader("Authorization");
        if(bearerToken != null && bearerToken.startsWith("Bearer")){
            return bearerToken.substring(7);
        }
        return null;
    }
    public String extractUsername(String token){
        LOGGER.info("extracting user name...");
        String id = JWT.require(ALGORITHM)
                .build()
                .verify(token)
                .getSubject();
        LOGGER.info("user id "+id);
        LOGGER.info("converting id...");
        String email = userDtoService.getById(Long.parseLong(id)).get().getUserEmail();
        LOGGER.info("email "+email);
        return email;
    }
    public boolean isTokenExpired(String token){
        return getClaims(token).getExpiration().before(new Date());
    }

    public Claims getClaims(String token){
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }
}
