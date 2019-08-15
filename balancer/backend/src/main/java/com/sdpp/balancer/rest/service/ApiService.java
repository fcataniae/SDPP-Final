package com.sdpp.balancer.rest.service;

import com.sdpp.balancer.socket.ServerSocketController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/rest/api")
public class ApiService {

    ServerSocketController ssc;
    @Autowired
    public ApiService(ServerSocketController ssc){
        this.ssc = ssc;
    }
}
