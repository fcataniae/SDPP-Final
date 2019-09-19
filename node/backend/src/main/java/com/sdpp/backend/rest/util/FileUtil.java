package com.sdpp.backend.rest.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
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

    private static JsonNode config;
    private static final String FILENAME = "config.json";
    private static String SHF = "sharedfolder";
    private static final String UPDFOLDER = "uploads/";
    private static String BALANCER ="balancer";
    private static String HOST = "host";
    private static String PORT = "port";
    private static String PATH = "path";
    private static String PROTOCOL = "protocol";
    private static String DOTS = ":";
    private static String SEPARATOR  = "/";




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

    public static Object getSharedFolderList() {
        throw new UnsupportedOperationException();
    }

    public static void setJsonFileOnClassLoader(JsonNode json) throws IOException, URISyntaxException {
        ObjectMapper om = new ObjectMapper();
        om.writeValue(Paths.get(getURIFromClassLoader(FILENAME).toURI()).toFile(),json);
    }


    public static void createFileToPath(MultipartFile file) {

        String newFileName = getSharedFolderPathName() + UPDFOLDER + file.getOriginalFilename();
        File f = new File(newFileName);

        f.getParentFile().mkdirs();
        try (
            FileOutputStream out = new FileOutputStream(f);
        ){
            f.createNewFile();
            out.write(file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static JsonNode getConfig() {
        if(config == null)
            loadConfig();
        return config;
    }


    private static String getSharedFolderPathName() {
        return config.get(SHF).get(PATH).asText();
    }


    public static String getUrl(){

        String url;
        url = config.get(BALANCER).get(PROTOCOL).asText();
        url = url + DOTS + SEPARATOR + SEPARATOR;
        url = url + config.get(BALANCER).get(HOST).asText() + DOTS;
        url = url + config.get(BALANCER).get(PORT).asText() + SEPARATOR;
        url = url + config.get(BALANCER).get(PATH).asText();

        return url;
    }

    private static void loadConfig(){
        try {
            config = (JsonNode) FileUtil.getJsonFileFromClassLoader(FILENAME);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
