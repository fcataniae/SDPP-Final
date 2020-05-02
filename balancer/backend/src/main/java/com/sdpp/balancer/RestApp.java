package com.sdpp.balancer;

import com.sdpp.balancer.socket.BalancerServerController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RestApp implements CommandLineRunner {

    public static void main(String[] args) {

        SpringApplication.run(RestApp.class, args);
    }

    @Autowired
    private BalancerServerController balancerServerController;
    @Override
    public void run(String... args) throws Exception {

        new Thread(balancerServerController).start();
    }
}
