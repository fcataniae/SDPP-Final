package com.sdpp.backend.rest.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sdpp.backend.rest.service.ApiService;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

/**
 * Usuario: Franco
 * Project: node
 * Fecha: 7/8/2019
 **/
public class FileUtil {


    public static Object getJsonFileFromClassLoader(String name) throws IOException {
        ObjectMapper om = new ObjectMapper();
        return om.readTree(getURIFromClassLoader( name));
    }


    private static URL getURIFromClassLoader(String name){
        return FileUtil.class.getClassLoader().getResource(name);
    }

    public static Object getBinaryFileFromPath(String path) {

        return path;
    }

    public static Object getSharedFolderList(String path) {
        return path;
    }

    public static void setJsonFileOnClassLoader(String configFile, JsonNode json) throws IOException, URISyntaxException {
        ObjectMapper om = new ObjectMapper();
        om.writeValue(Paths.get(getURIFromClassLoader(configFile).toURI()).toFile(),json);
    }
}
