package com.indrajagadeesh.TracingModule.Config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
@ConfigurationProperties(prefix = "custom")
@Getter
@Setter
public class CustomProperties {
    private boolean nextCall = false;
    private String[] urls = {"http://localhost:8080/module"};
    private long delay = 0;
    private String message = "This is a test message";
    private boolean delayMethod = false;
    private boolean HttpStatusSuccess = true;

}