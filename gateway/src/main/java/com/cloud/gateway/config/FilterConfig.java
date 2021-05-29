package com.cloud.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration // 해당 어노테이션은 스프링부트가 시작할때 해당 어노테이션이 적혀있는 class들을 모아서 미리 메모리에 저장한다.
public class FilterConfig {
//    @Bean
    public RouteLocator gatwayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/first-service/**")
                        .filters(f -> f.addRequestHeader("first-request", "first-request-header")
                                .addResponseHeader("first-response", "first-response")
                        .addRequestParameter("jinho","men"))
                        .uri("http://localhost:8011/"))
                .route(r -> r.path("/second-service/**")
                        .filters(f -> f.addResponseHeader("second-request", "second-request-header")
                                .addResponseHeader("second-response", "second-response"))
                        .uri("http://localhost:8012/"))
                .build();
    }
    // RouteLocatorBuilder객채로 필터링을 설정한다.
    // f->f 익명함수이므로 JAVASCipt 화살함수라고 생각하면됨  filters() 메소드 내부 함수가 존재
    // Request,Response 의 헤더or파라미터 등을 생성하여 해당 uri에 같이 요청,응답한다.
}
