package com.springcloud.ecoomerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
//우리가 하는 프로젝트는 SpringCloud Netflix Eureka Server 이기때문에 해당 어노테이션을 붙여주어야한다.
public class EcoomerceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcoomerceApplication.class, args);
	}

}
