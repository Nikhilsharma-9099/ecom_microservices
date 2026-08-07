package com.example.consumer.restclient;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @LoadBalanced
    @Bean
    public RestClient.Builder loadBalancedRestClientBuilder() {
        return RestClient.builder();
    }

    @Bean
    public RestClient restClient(@Qualifier("loadBalancedRestClientBuilder")RestClient.Builder builder) {
        return builder.baseUrl("http://provider")
                .build();
    }
}
