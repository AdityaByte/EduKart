package com.edukart.gateway.config;

import com.nimbusds.jwt.JWTParser;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.server.ServerWebExchange;

@Configuration
public class FirebasePayloadForwardingFilter {

    @Bean
    public GlobalFilter globalFilter() {
        return (exchange, chain) -> {
            String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            if (token != null && token.startsWith("Bearer ")) {
                try {
                    String jwt = token.substring(7);
                    var parsed = JWTParser.parse(jwt);
                    var claims = parsed.getJWTClaimsSet();

                    String uid = claims.getSubject();
                    String email =(String) claims.getClaim("email");
                    String name = (String) claims.getClaim("name");

                    ServerWebExchange mutated = exchange
                            .mutate()
                            .request(builder -> builder
                                    .header("X-Auth-UID", uid != null ? uid : "")
                                    .header("X-Auth-Email", email != null ? email : "")
                                    .header("X-Auth-Name", name != null ? name : "")
                            )
                            .build();

                    return chain.filter(mutated);
                } catch (Exception e) {
                    return chain
                            .filter(exchange);
                }
            }
            return chain.filter(exchange);
        };
    }

}
