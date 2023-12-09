package io.jonathanlee.splitapigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;

@SpringBootApplication
@EnableWebFluxSecurity
public class SplitApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(SplitApiGatewayApplication.class, args);
	}

}
