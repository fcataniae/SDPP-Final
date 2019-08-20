package com.sdpp.balancer.rest.service;

import com.sdpp.balancer.socket.ServerSocketController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/rest/api")
public class ApiService {

    ServerSocketController ssc;
    @Autowired
    public ApiService(ServerSocketController ssc){
        this.ssc = ssc;
    }

    @GetMapping("/search")
    public Object doSearch(){
        List<String> lista = new ArrayList<>();
        lista.add("prueba1");
        lista.add("prueba2");
        lista.add("prueba3");
        lista.add("prueba4");
        lista.add("prueba5");
        System.out.println(lista);
        return lista;
    }
}
