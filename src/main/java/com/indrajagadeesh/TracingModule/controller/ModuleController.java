package com.indrajagadeesh.TracingModule.controller;

import java.util.concurrent.TimeUnit;

import com.indrajagadeesh.TracingModule.Config.CustomProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<String> getCall() throws InterruptedException {

        String response = properties.getMessage();
        log.info("Application name {}",name);
        if(properties.isNextCall()){
            for(String url : properties.getUrls()){
            log.info(url);
            try{
            response += "<br>" + restTemplate.getForObject(url, String.class);
            } catch ( Exception e){
                response += e.getMessage();
            }
            }
        }

        if(properties.isDelayMethod())
            delayMethod();

        log.info("Application responce {}",response);

        return new ResponseEntity<>(response + " : current module name : "+name,
                properties.isHttpStatusSuccess() ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR);
    }

    void delayMethod() throws InterruptedException {
        log.info("this log is from delay method");
        TimeUnit.MICROSECONDS.sleep(properties.getDelay());
    }

}