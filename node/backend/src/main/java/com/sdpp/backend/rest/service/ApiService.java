package com.sdpp.backend.rest.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.sdpp.backend.rest.domain.DocumentFile;
import com.sdpp.backend.rest.service.components.MongoDBConnection;
import com.sdpp.backend.rest.service.components.WatcherSystemService;
import com.sdpp.backend.rest.util.FileUtil;
import com.sdpp.backend.rest.util.RestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

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

    public ApiService(MongoDBConnection mongoDBConnection,
                      WatcherSystemService watcherSystemService){
        this.watcherSystemService = watcherSystemService;
        this.mongoDBConnection = mongoDBConnection;
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
    public List<DocumentFile> getSharedList() throws IOException {
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



}
