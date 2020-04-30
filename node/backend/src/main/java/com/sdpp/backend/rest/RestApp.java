package com.sdpp.backend.rest;

import com.sdpp.backend.rest.domain.Configuration;
import com.sdpp.backend.rest.service.components.InMemoryPathController;
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

}
