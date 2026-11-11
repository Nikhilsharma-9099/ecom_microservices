package com.app.ecom_application.order.clients;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.util.Optional;

@Configuration
public class UserServiceClientConfig {

    @Bean
    @LoadBalanced
    public RestClient.Builder userRestClientBuilder(){
        return RestClient.builder().baseUrl("http://localhost:8082");
    }

    @Bean
    public UserServiceClient userServiceClient(RestClient.Builder userRestClientBuilder){
        RestClient restClient = userRestClientBuilder.baseUrl("http://user")
                .defaultStatusHandler(HttpStatusCode::is4xxClientError, (request, response) -> Optional.empty())
                .build();
        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory
                .builderFor(adapter)
                .build();

        return factory.createClient(UserServiceClient.class);
    }
}
