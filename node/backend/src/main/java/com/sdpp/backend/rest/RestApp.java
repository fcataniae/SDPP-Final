package com.sdpp.backend.rest;

import com.fasterxml.jackson.databind.JsonNode;
import com.sdpp.backend.rest.service.components.MongoDBConnection;
import com.sdpp.backend.rest.service.components.WatcherSystemService;
import com.sdpp.backend.rest.util.FileUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Properties;

/**
 * Usuario: Franco
 * Project: node
 * Fecha: 6/8/2019
 **/
@SpringBootApplication
public class RestApp {

    public static void main(String[] args) {

        SpringApplication.run(RestApp.class, args);

    }

    @Bean
    public MongoDBConnection mongoDBConnection() {
        Properties props = new Properties();
        JsonNode config = FileUtil.getConfig();
        props.setProperty("url", config.get("db").get("url").asText());
        return new MongoDBConnection(props);
    }

    @Bean
    public WatcherSystemService watcherSystemService(){
        return new WatcherSystemService(mongoDBConnection());
    }
}
