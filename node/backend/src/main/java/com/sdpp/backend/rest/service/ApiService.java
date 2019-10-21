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
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

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

    @GetMapping(value = "file/{id}", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Resource getFilesById(@PathVariable ("id") String name) throws IOException {


        DocumentFile document = new DocumentFile();
        document.setName(name);
        document =  mongoDBConnection.getEntity(DocumentFile.class, document);
        String finalPath = document.getMeta().getPath().concat("\\").concat(document.getName());
        File file = new File(finalPath);
        Path path = Paths.get(file.getAbsolutePath());
        return new ByteArrayResource(Files.readAllBytes(path));
    }

    @GetMapping(value = "files", produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel> getSharedList() throws IOException {


        List<DocumentFile> files = mongoDBConnection.getAllEntities(DocumentFile.class);
        Collection<EntityModel> models = new ArrayList<>();
        for (DocumentFile f : files) {
            EntityModel<DocumentFile> model = new EntityModel<>(f);
            Link link = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ApiService.class).getFilesById(f.getName())).withSelfRel();
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
