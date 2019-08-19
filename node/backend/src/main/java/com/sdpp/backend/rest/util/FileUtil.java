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

    private static final String UPDFOLDER = "uploads/";

    public static void createFileToPath(MultipartFile file, String sharedFolder) {

        String newFileName = sharedFolder + UPDFOLDER + file.getOriginalFilename();
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
}
