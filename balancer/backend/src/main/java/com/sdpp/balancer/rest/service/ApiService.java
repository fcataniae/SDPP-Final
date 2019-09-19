package com.sdpp.balancer.rest.service;

import com.sdpp.balancer.rest.domain.FileWrapper;
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
        return sendDataDummy();
    }

    //Solo para testing
    private Object sendDataDummy(){

        List<FileWrapper> lista = new ArrayList<>();

        FileWrapper file = new FileWrapper();
        file.setName("Code Clean");
        file.setType("PDF");
        file.setNode("Nodo 124");
        lista.add(file);
        file = new FileWrapper();
        file.setName("RQ - Paquete comercio");
        file.setType("DOCX");
        file.setNode("Nodo 122");
        lista.add(file);
        file = new FileWrapper();
        file.setName("Secuencia");
        file.setType("TXT");
        file.setNode("Nodo 123");
        lista.add(file);

        return lista;
    }

}
