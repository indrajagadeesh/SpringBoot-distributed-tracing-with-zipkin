package com.indrajagadeesh.TracingModule.controller;

import java.util.concurrent.TimeUnit;

import com.indrajagadeesh.TracingModule.Config.CustomProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin
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
    public String getCall() throws InterruptedException {

        String response = properties.getMessage();
        log.info("Application name {}",name);
        if(properties.isNextCall()){
            String url = "http://"+properties.getHostname()+":"+properties.getPort()+properties.getUrlPath();
            log.info(url);
            response = restTemplate.getForObject(url, String.class);
        }

        if(properties.isDelayMethod())
            delayMethod();

        log.info("Application responce {}",response);

        return response + " : current module name : "+name;
    }

    void delayMethod() throws InterruptedException {
        log.info("this log is from delay method");
        TimeUnit.MICROSECONDS.sleep(properties.getDelay());
    }

}