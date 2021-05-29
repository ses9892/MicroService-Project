package com.cloud.gateway.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class LoggingFilter extends AbstractGatewayFilterFactory<LoggingFilter.Config> {
    public LoggingFilter() {
        super(Config.class);
    }
    @Data
    public static class Config{
        private String baseMessage;
        private boolean preLogger;
        private boolean postLogger;
    }
    @Override
    public GatewayFilter apply(LoggingFilter.Config config) {
        GatewayFilter filter = new OrderedGatewayFilter((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            log.info("Logging Filter base meg :{}",config.getBaseMessage());
            if(config.isPreLogger()){
                log.info("Logging pre Filter : request id : {}",request.getId());
                log.info("Logging pre Filter : 요청받은 URI : {}",request.getPath());
            }
            return chain.filter(exchange).then(Mono.fromRunnable(()->{
                if(config.isPostLogger()){
                    log.info("Logging post Filter: response code : {}",response.getStatusCode());
                }
        }));
      }, Ordered.LOWEST_PRECEDENCE);   //Ordered.HIGHEST_PRECEDENCE 실행순서 가장먼져
                                        //LOWEST_PRECEDENCE : 가장나중
        //GatewayFilter filter = new OrderedGatewayFilter((exchange, chain) -> {} , 순서)
        return filter;
    }

}


