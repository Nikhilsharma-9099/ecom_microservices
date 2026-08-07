package com.example.provider.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InstanceController {
    @Value("${server.port}")
    private String serverPort;

    private final String instanceId = java.util.UUID.randomUUID().toString();

    @GetMapping("/instance-info")
    public String getInstanceInfo(){
        System.out.println("Request received at instance running on port: " + serverPort);
        return "Instance served by Port: " + serverPort + ". Instance ID: " + instanceId;
    }
}
