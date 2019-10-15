package com.sdpp.backend.rest.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.sdpp.backend.rest.domain.DocumentFile;
import com.sdpp.backend.rest.service.components.MongoDBConnection;
import com.sdpp.backend.rest.service.components.WatcherSystemService;
import com.sdpp.backend.rest.util.CustomCacheBuilder;
import com.sdpp.backend.rest.util.FileUtil;
import com.sdpp.backend.rest.util.RestUtil;
import org.ehcache.Cache;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Usuario: Franco
 * Project: node
 * Fecha: 7/8/2019
 **/

@RestController
@RequestMapping(("/rest/api"))
@CrossOrigin
public class ApiService {

    private MongoDBConnection mongoDBConnection;
    private WatcherSystemService watcherSystemService;

    private static final String NAMECACHE = "querys";
    private static final int POOLCACHE = 400;
    private static final int TTLCACHE = 60;

    private static Cache CACHE = buildCache();

    @Value("${build.version}")
    private String version;

    public ApiService(MongoDBConnection mongoDBConnection,
                      WatcherSystemService watcherSystemService){
        this.watcherSystemService = watcherSystemService;
        this.mongoDBConnection = mongoDBConnection;
    }


    @GetMapping("version")
    public Object getVersion(){
        Map<String, String> v = new LinkedHashMap<>();
        v.put("version","SDPP-Node v".concat(version));
        return v;
    }

    @GetMapping("config/server")
    public Object getConfigurations() {
        return FileUtil.getConfig();
    }

    @PostMapping("config/server")
    public void updateConfiguration(@RequestBody JsonNode json) throws IOException, URISyntaxException, InterruptedException {
        String previousPath = FileUtil.getSharedFolderPathName();
        FileUtil.setJsonFileOnClassLoader(json);
        String newPath = FileUtil.getSharedFolderPathName();
        if(!previousPath.equals(newPath))
            watcherSystemService.setNewPath();

    }

    @GetMapping("file/{id}")
    public Object getFiles(@PathVariable ("id") String path){
        return FileUtil.getBinaryFileFromPath(path);
    }

    @GetMapping("files")
    public List<DocumentFile> getSharedList(){
        return mongoDBConnection.getAllEntities(DocumentFile.class);
    }

    @GetMapping("search")
    public Object doSearch(){
        return RestUtil.getObjectForUrl(FileUtil.getUrl());
    }

    @PostMapping("upload/file")
    public void uploadFile(@RequestParam("file") MultipartFile file){
        FileUtil.createFileToPath(file);
    }

    private static Cache buildCache() {
        return CustomCacheBuilder.newCache(NAMECACHE,POOLCACHE,TTLCACHE);
    }

}
