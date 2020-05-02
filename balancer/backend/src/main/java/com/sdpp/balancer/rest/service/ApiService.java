package com.sdpp.balancer.rest.service;

import com.sdpp.balancer.socket.BalancerServerController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;

@RestController
@CrossOrigin
@RequestMapping("/balancer-api")
public class ApiService {

    private BalancerServerController ssc;

    @Autowired
    public ApiService(BalancerServerController ssc){
        this.ssc = ssc;
    }

    @GetMapping("/search/files")
    public Object doSearch(@RequestParam LinkedHashMap<String,String> params){
        return ssc.doSearch(params);
    }



}
