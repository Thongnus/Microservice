package com.example.api.Service.Serviceimpl;

import com.example.api.Entity.Role;
import com.example.api.Entity.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static java.nio.charset.StandardCharsets.UTF_8;

@Component
@Slf4j
public class JwtService {
    private final String secret = "my-very-strong-secret-keyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy";
    private final SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(UTF_8));
    private final SecretKey secretKeyrefesh = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private final long accessTokenExpiration = 60_0000000; // 1 phút
    private final long refreshTokenExpiration = 300_000000; // 5 phút

 //tao token

public  String generatetoken(User user){
    Map<String,Object>  map = new HashMap<>();
    map.put("roles",user.getRoles().stream().map(Role::getName).collect(Collectors.toList()));
    return Jwts.builder()
            .setClaims(map)
            .setSubject(user.getUsername())
            .setIssuedAt(new Date())
            .setExpiration(new Date((new Date()).getTime()+ accessTokenExpiration))
            .signWith(secretKey)
            .compact();
}

    // setclaims để đặt các thông tin muốn đưa vào jwt , ví dụ thêm email chẳng hạn
    public  String generaterefeshtoken( User user){
        return Jwts.builder()

                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime()+ refreshTokenExpiration))
                .signWith(secretKey)
                .compact();
    }
//trich USername from token

public String extractUsernamefromToken(String token){
  return   Jwts.parserBuilder()
          .setSigningKey(secretKey)
          .build()
          .parseClaimsJws(token)
          .getBody()
          .getSubject();


}
public String extractUsernamefromRefeshToken(String token){
    return Jwts.parserBuilder()
            .setSigningKey(secretKeyrefesh)
            .build()
            .parseClaimsJws(token)
            .getBody()
            .getSubject();

}

    public Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();


    }
    public long getExpirationTime(){
        return accessTokenExpiration;
    }



    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parse(authToken);
            return !isTokenExpired(authToken);
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }
    public boolean validateJwtTokenrefesh(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKeyrefesh).build().parse(authToken);
            if(isTokenExpiredrefesh(authToken))
            {
                log.error("JWT token is expired");
                return false;
            }
               return true;
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }
    public boolean isTokenExpired(String token) {
        Date expiration = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        return expiration.before(new Date());}
    public boolean isTokenExpiredrefesh(String token) {
        Date expiration = Jwts.parserBuilder()
                .setSigningKey(secretKeyrefesh)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        return expiration.before(new Date());}
}
