package com.indrajagadeesh.TracingModule.controller;

import java.util.concurrent.TimeUnit;

import com.indrajagadeesh.TracingModule.Config.CustomProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/")
@Slf4j
public class ModuleController {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private CustomProperties properties;

    @Value("${spring.application.name}")
    private String name;

    @GetMapping("/module")
    public String getCall(){

        String response = properties.getMessage();
        log.info("Application name {}",name);
        if(properties.getNextCall()){
            response = restTemplate.getForObject("http://"+properties.getHostName()+":"+properties.getPort(), String.class);
        }
        TimeUnit.MICROSECONDS.sleep(properties.getDelay());
        log.info("Application responce {}",response);
        return response;
    }

    
}