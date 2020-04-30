package com.sdpp.backend.rest.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sdpp.backend.rest.domain.Balancer;
import com.sdpp.backend.rest.domain.Configuration;
import com.sdpp.backend.rest.domain.DocumentFile;
import com.sdpp.backend.rest.domain.MetaData;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Usuario: Franco
 * Project: node
 * Fecha: 7/8/2019
 **/
public class FileUtil {

    private static Configuration config;
    private static final String FILENAME = "config.json";
    private static final String UPDFOLDER = "/uploads/";
    private static String DOTS = ":";
    private static String SEPARATOR  = "/";



    public static Configuration getConfigurationFromRelativePath(String name) throws IOException, URISyntaxException {
        ObjectMapper om = new ObjectMapper();
        om.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        return om.readValue(getURIFromRelativePath( name), Configuration.class);
    }


    private static URL getURIFromRelativePath(String name) throws IOException, URISyntaxException {

        Path path = Paths.get(name);
        if(!Paths.get(name).toFile().exists())
           loadEmptyConfiguration(path);
        return Paths.get(name).toAbsolutePath().toUri().toURL();
    }

    private static void loadEmptyConfiguration(Path path) throws IOException, URISyntaxException {

        path.toFile().createNewFile();
        Configuration config = new Configuration();
        setConfigurationFileOnRelativePath(config);
    }

    public static Object getBinaryFileFromPath(String path) {

        return path;
    }

    public static DocumentFile createDocumentFile(Path p) throws IOException {
        DocumentFile doc = new DocumentFile();

        try {
            File file = p.toFile();
            BasicFileAttributes attr = Files.readAttributes(p, BasicFileAttributes.class);
            doc.setName(file.getName());
            MetaData meta = new MetaData();
            meta.setPath(file.getPath());
            meta.setCreateTime(new Date(attr.creationTime().toMillis()));
            meta.setModifiedTime(new Date(attr.lastModifiedTime().toMillis()));
            meta.setSize(attr.size());
            String ext = "";
            if (doc.getName().contains("."))
                ext = doc.getName().substring(doc.getName().lastIndexOf('.') + 1);
            meta.setExtension(ext);
            doc.setMeta(meta);
            doc.setChecksum(checksum(file));
        }catch (NoSuchAlgorithmException e){
            doc.setChecksum(p.toAbsolutePath().toString());
        }
        return doc;
    }

    public static List<DocumentFile> getSharedFolderList() throws IOException {
        String path = getSharedFolderPathName();
        List<DocumentFile> files = new ArrayList<>();
        Files.walk(Paths.get(path)).forEach( p -> {
            if(Files.isRegularFile(p)){
                try {

                    DocumentFile doc = createDocumentFile(p);
                    files.add(doc);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        return files;
    }

    private static String checksum(File file) throws IOException, NoSuchAlgorithmException {

         MessageDigest md = MessageDigest.getInstance("SHA-256");
        try (DigestInputStream dis = new DigestInputStream(new FileInputStream(file), md)) {
            while (dis.read() != -1) ; //empty loop to clear the data
            md = dis.getMessageDigest();
        }

        // bytes to hex
        StringBuilder result = new StringBuilder();
        for (byte b : md.digest()) {
            result.append(String.format("%02x", b));
        }
        return result.toString();

    }

    public static void setConfigurationFileOnRelativePathAndReload(Configuration config) throws IOException, URISyntaxException {

        setConfigurationFileOnRelativePath(config);
        loadConfig();
    }

    private static void setConfigurationFileOnRelativePath(Configuration config) throws IOException, URISyntaxException {
        ObjectMapper om = new ObjectMapper();
        om.writeValue(Paths.get(getURIFromRelativePath(FILENAME).toURI()).toFile(),config);
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


    public static Configuration getConfig() {

        if(config == null)
            loadConfig();
        return config;
    }


    public static String getSharedFolderPathName() {

        return getConfig().getSharedFolder().getPath();
    }


    public static String getUrl(){

        String url;
        Balancer balancer = getConfig().getBalancer();
        url = balancer.getProtocol();
        url = url + DOTS + SEPARATOR + SEPARATOR;
        url = url + balancer.getHost() + DOTS;
        url = url + balancer.getPort() + SEPARATOR;
        url = url + balancer.getPath();

        return url;
    }

    private static void loadConfig(){
        try {
            config = FileUtil.getConfigurationFromRelativePath(FILENAME);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
