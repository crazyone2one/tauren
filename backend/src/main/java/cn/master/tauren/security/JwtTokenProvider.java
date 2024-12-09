package cn.master.tauren.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

/**
 * @author Created by 11's papa on 11/29/2024
 **/
@Component
public class JwtTokenProvider {

    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;
    @Value("${application.security.jwt.secret-key}")
    private String secret;
    //SecretKey key = Jwts.SIG.HS256.key().build();

    private SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(UserDetails userDetails) {
        String username = userDetails.getUsername();
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + jwtExpiration);
        return Jwts.builder()
                .subject(username)
                .issuedAt(currentDate)
                .expiration(expireDate)
                .signWith(getKey(), Jwts.SIG.HS256)
                .compact();
    }

    public String getUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        String username = getUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }

    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parser().verifyWith(getKey()).build().parseSignedClaims(token).getPayload();
        } catch (SecurityException | MalformedJwtException e) {
            throw new AuthenticationCredentialsNotFoundException("JWT was expired or incorrect");
        } catch (ExpiredJwtException e) {
            throw new AuthenticationCredentialsNotFoundException("Expired JWT token.");
        } catch (UnsupportedJwtException e) {
            throw new AuthenticationCredentialsNotFoundException("Unsupported JWT token.");
        } catch (IllegalArgumentException e) {
            throw new AuthenticationCredentialsNotFoundException("JWT token compact of handler are invalid.");
        }
    }


}
