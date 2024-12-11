package com.example.servicegateway.Filter;

import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class AuthorizationFilterFactory   extends AbstractGatewayFilterFactory<AuthorizationFilterFactory.Config> implements Ordered
{

    @Autowired
    JwtUtils utils;

    public AuthorizationFilterFactory() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                log.error("jwt not validate....!");
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            String token = authHeader.substring(7);
            ArrayList<String> roles= (ArrayList<String>) utils.extractAllClaims(token).get("roles");

            if (!roles.contains(config.getRequiredRole())) {
                log.error("Not permit with role:"+roles);
                exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                return exchange.getResponse().setComplete();
            }

              if(!utils.validateJwtToken(token)) {
                  log.error("JWT validate");
                  exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                  return exchange.getResponse().setComplete();
              }

            return chain.filter(exchange);
        };
    }

    @Override
    public int getOrder() {
        return -1;
    }
   // neu muốn author từng api con trong service thì sử dụng Map <String,String > , bao gồm path và roles

    public static class Config {
        private String requiredRole;

        // Getters and Setters
        public String getRequiredRole() {
            return requiredRole;
        }

        public void setRequiredRole(String requiredRole) {
            this.requiredRole = requiredRole;
        }
    }
}
