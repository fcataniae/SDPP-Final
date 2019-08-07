package com.sdpp.backend.rest.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.sdpp.backend.rest.util.FileUtil;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;

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
    public Object getConfigurations() throws IOException {
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
