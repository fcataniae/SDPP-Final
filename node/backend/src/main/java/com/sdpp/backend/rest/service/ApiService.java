package com.sdpp.backend.rest.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.sdpp.backend.rest.util.FileUtil;
import com.sdpp.backend.rest.util.RestUtil;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Base64;

/**
 * Usuario: Franco
 * Project: node
 * Fecha: 7/8/2019
 **/

@RestController
@RequestMapping(("/rest/api"))
public class ApiService {

    private static final String CONFIG = "config.json";
    private JsonNode config;

    public ApiService(){
        loadConfig();

    }

    @GetMapping("config/server")
    public Object getConfigurations() {
        return config;
    }

    @PostMapping("config/server")
    public void updateConfiguration(@RequestBody JsonNode json) throws IOException, URISyntaxException {
        FileUtil.setJsonFileOnClassLoader(CONFIG,json);
        loadConfig();
    }

    @GetMapping("file/{id}")
    public Object getFiles(@PathVariable ("id") String path){
        return FileUtil.getBinaryFileFromPath(path);
    }

    @GetMapping("files")
    public Object getSharedList(){
        return FileUtil.getSharedFolderList(getPath());
    }

    @GetMapping("searh")
    public Object doSearch(){
        //RestUtil.getObjectForUrl(getUrl());
        return getUrl();
    }










    private static String BALANCER ="balancer";
    private static String HOST = "host";
    private static String PORT = "port";
    private static String PATH = "path";
    private static String PROTOCOL = "protocol";
    private static String DOTS = ":";
    private static String SEPARATOR  = "/";

    private String getUrl(){

        String url;

        url = config.get(BALANCER).get(PROTOCOL).asText();
        url = url + DOTS + SEPARATOR + SEPARATOR;
        url = url + config.get(BALANCER).get(HOST).asText() + DOTS;
        url = url + config.get(BALANCER).get(PORT).asText() + SEPARATOR;
        url = url + config.get(BALANCER).get(PATH).asText();

        return url;
    }

    private String getPath(){
        return config.get("sharedfolder").get("path").asText();
    }

    private void loadConfig(){
        try {
            config = (JsonNode) FileUtil.getJsonFileFromClassLoader(CONFIG);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
