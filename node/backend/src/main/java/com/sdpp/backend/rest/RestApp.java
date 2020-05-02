package com.sdpp.backend.rest;

import com.sdpp.backend.rest.service.components.WatcherSystemService;
import com.sdpp.backend.rest.socket.PeerServerController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Usuario: Franco
 * Project: node
 * Fecha: 6/8/2019
 **/
@SpringBootApplication
public class RestApp implements CommandLineRunner {

    public static void main(String[] args) {

        SpringApplication.run(RestApp.class, args);

    }

    @Autowired
    private PeerServerController peerServerController;
    @Autowired
    private WatcherSystemService watcherSystemService;

    @Override
    public void run(String... args) throws Exception {
        new Thread(watcherSystemService).start();
        new Thread(peerServerController).start();
    }
}
