package com.university.configurationserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;


@EnableConfigServer
@SpringBootApplication
public class ConfigurationServerApplication 
{    
    public static void main(String[] args) {

        //SpringApplication.run(ConfigurationServerApplication.class, args);
        SpringApplication app = new SpringApplication(ConfigurationServerApplication.class);
        
/*        
        String mode = System.getenv("CONFIG_MODE");
        if ("native".equalsIgnoreCase(mode)) {
            app.setAdditionalProfiles("native");
        } else {
            app.setAdditionalProfiles("git");
        }
        app.setAdditionalProfiles("native");
        */
        app.run(args);
    }
}