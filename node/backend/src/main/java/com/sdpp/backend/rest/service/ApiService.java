package com.sdpp.backend.rest.service;

import com.sdpp.backend.rest.domain.DocumentFile;
import com.sdpp.backend.rest.service.components.MongoDBConnection;
import com.sdpp.backend.rest.service.components.WatcherSystemService;
import com.sdpp.backend.rest.util.CustomCacheBuilder;
import com.sdpp.backend.rest.util.FileUtil;
import com.sdpp.backend.rest.util.RestUtil;

import com.fasterxml.jackson.databind.JsonNode;

import org.ehcache.Cache;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.ArrayList;
import java.util.Collections;

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


        return Collections.singletonMap("version","SDPP-Node v".concat(version));
    }

    @GetMapping("config/server")
    public Object getConfigurations() {


        return FileUtil.getConfig();
    }

    @PostMapping("config/server")
    public void updateConfiguration(@RequestBody JsonNode json) throws IOException, URISyntaxException {


        String previousPath = FileUtil.getSharedFolderPathName();
        FileUtil.setJsonFileOnClassLoader(json);
        String newPath = FileUtil.getSharedFolderPathName();
        if(!previousPath.equals(newPath))
            watcherSystemService.setNewPath();

    }

    @GetMapping(value = "file/{id}")
    public ResponseEntity<Resource> getFilesById(@PathVariable ("id") String name) throws IOException {


        DocumentFile document = new DocumentFile();
        document.setName(name);
        document =  mongoDBConnection.getEntity(DocumentFile.class, document);
        String finalPath = document.getMeta().getPath().concat("\\").concat(document.getName());
        File file = new File(finalPath);
        Path path = Paths.get(file.getAbsolutePath());
        ByteArrayResource resource =  new ByteArrayResource(Files.readAllBytes(path));
        String contentType = getMediaTypeByExtension(document.getMeta().getExtension());
        return ResponseEntity.ok()
                .header("Content-disposition","filename="+document.getName())
                .header("Content-Type", contentType)
                .body(resource);
    }
    private String getMediaTypeByExtension(String ext) {

        switch (ext.toLowerCase()){
            case "pdf":
                return MediaType.APPLICATION_PDF_VALUE;
            case "png":
                return MediaType.IMAGE_PNG_VALUE;
            case "jpg":
            case "jpeg":
                return MediaType.IMAGE_JPEG_VALUE;
            case "xlsx":
                return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
            case "docx":
                return "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
            default:
                return MediaType.TEXT_PLAIN_VALUE;
        }
    }

    @GetMapping(value = "files")
    public CollectionModel<EntityModel> getSharedList() throws IOException {


        Collection<DocumentFile> files = mongoDBConnection.getAllEntities(DocumentFile.class);
        Collection<EntityModel> models = new ArrayList<>();
        for (DocumentFile f : files) {
            EntityModel<DocumentFile> model = new EntityModel<>(f);
            Link link = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ApiService.class)
                    .getFilesById(f.getName()))
                    .withSelfRel();
            model.add(link);
            models.add(model);
        }
        CollectionModel<EntityModel> col = new CollectionModel<>(models);
        Link link = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ApiService.class).getSharedList()).withSelfRel();
        col.add(link);
        return col;
    }

    @GetMapping("search")
    public Object doSearch(@RequestParam LinkedHashMap params){


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
