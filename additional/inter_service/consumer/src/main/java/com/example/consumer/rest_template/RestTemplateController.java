package com.example.consumer.rest_template;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/rest-template")
@RequiredArgsConstructor
public class RestTemplateController {

    private final RestTemplateClient restTemplateClient;

    @GetMapping("/instance")
    public String getInstanceInfo() {
//        RestTemplate restTemplate = new RestTemplate();
//        String response = restTemplate.getForObject("htpp://localhost:8081/instance-info", String.class);
//        return response;
        return restTemplateClient.getInstanceInfo();
    }
}
