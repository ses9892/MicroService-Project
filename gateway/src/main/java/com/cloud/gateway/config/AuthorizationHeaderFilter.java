package com.cloud.gateway.config;

import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {
    @Autowired
    Environment env;
    public AuthorizationHeaderFilter(){ super(Config.class); }
    public AuthorizationHeaderFilter(Environment env) { this.env = env; }

    public static class Config{ }
    // login 요청 ->  Token -> users( with token ) -> Header(Include Token) -> API GateWay -> Token Search ->
    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();
            //가지고온 헤더들중! 인증에관한 헤더가 존재하지않으면?
            if(!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)){
                return onError(exchange,"no Authorization Header", HttpStatus.UNAUTHORIZED);
                //에러를 반환
            }
            log.info(env.getProperty("token.secret"));
            //반환값은 List형태여서 0번째 index를 찾는다.
            String authorization = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
            String jwt = authorization.replace("Bearer","");
            if(!isJwtValid(jwt)){
                return onError(exchange,"JWT token is not valid",HttpStatus.UNAUTHORIZED);
            }

            return chain.filter(exchange);
        });
    }

    private boolean isJwtValid(String jwt) {
        boolean retrunValue = true;
        String subject = null;

        try{
        subject= Jwts.parser().setSigningKey(env.getProperty("token.secret"))
                .parseClaimsJws(jwt).getBody().getSubject();
        }catch (Exception e){
            retrunValue=false;
        }
        // .parseClaimsJws(jwt) 복구할 대상
        if(subject==null || subject.isEmpty()){
            retrunValue =false;
        }

        return retrunValue;
    }

    //헤더에 인증에 관련된 키가 없을시 status 코드와 에러메세지를 반환할 메소드
    private Mono<Void> onError(ServerWebExchange exchange, String errmeg, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        log.error(errmeg);
        return response.setComplete();
    }



}

