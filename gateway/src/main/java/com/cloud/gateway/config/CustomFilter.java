package com.cloud.gateway.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class CustomFilter extends AbstractGatewayFilterFactory<CustomFilter.Config> {
    public CustomFilter(){
        super(Config.class);
    }
    public static class Config{
        // Put the configuration properties
    }
    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {      //chain 반환(응답) 시켜주는 객체
            //exchange -> request : pre 필터
            //exchange -> response : post 필터
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            //커스텀 pre 필터
            log.info("커스텀 pre filter : request uri -> {}", request.getId());
            log.info("커스텀 pre filter : request uri -> {}", request.getURI());
            log.info("커스텀 pre filter : request uri -> {}", request.getPath());


            //post 필터  생성               then (필터가 적용된다음에 다음값을 전하겠다.) 그러면서
            return chain.filter(exchange).then(Mono.fromRunnable(() ->{
                log.info("커스텀 post filter : response code => {}", response.getStatusCode());
            }));
        });
    }
}
