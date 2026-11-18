package com.ecom.api_gateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {
	
	@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
		return builder.routes()
				.route("product", r -> r.path("/api/products/**")
//						.filters(f -> f.rewritePath("/products(?<segment>/?.*)", "/api/products${segment}"))
						.uri("lb://product"))
				.route("order", r -> r.path("/api/orders/**", "/api/cart/**")
//						.filters(f -> f.rewritePath("/orders(?<segment>/?.*)", "/api/orders${segment}")
//								.rewritePath("/cart(?<segment>/?.*)", "/api/cart${segment}"))
						.uri("lb://order"))	
				.route("user", r -> r.path("/api/users/**")
//						.filters(f -> f.rewritePath("/users(?<segment>/?.*)", "/api/users${segment}"))
						.uri("lb://user"))
				.build();
	}

}
