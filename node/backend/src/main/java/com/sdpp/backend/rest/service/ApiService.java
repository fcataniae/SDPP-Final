package com.sdpp.backend.rest.service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheLoader;
import com.sdpp.backend.rest.domain.Configuration;
import com.sdpp.backend.rest.domain.DocumentFile;
import com.sdpp.backend.rest.service.components.InMemoryPathController;
import com.sdpp.backend.rest.service.components.WatcherSystemService;
import com.sdpp.backend.rest.util.CustomCacheBuilder;
import com.sdpp.backend.rest.util.FileUtil;
import com.sdpp.backend.rest.util.RestUtil;


import org.springframework.beans.factory.annotation.Autowired;
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

    private InMemoryPathController inMemoryPathController;
    private WatcherSystemService watcherSystemService;

    private static final String NAMECACHE = "querys";
    private static final int POOLCACHE = 400;
    private static final int TTLCACHE = 60;

    private static Cache CACHE = buildCache();

    @Value("${build.version}")
    private String version;

    @Autowired
    public ApiService(InMemoryPathController inMemoryPathController,
                      WatcherSystemService watcherSystemService){
        this.watcherSystemService = watcherSystemService;
        this.inMemoryPathController = inMemoryPathController;
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
    public void updateConfiguration(@RequestBody Configuration config) throws IOException, URISyntaxException {


        String previousPath = FileUtil.getSharedFolderPathName();
        FileUtil.setConfigurationFileOnRelativePathAndReload(config);
        String newPath = FileUtil.getSharedFolderPathName();
        if(!previousPath.equals(newPath))
            watcherSystemService.setNewPath();

    }

    @GetMapping(value = "file/{checksum}")
    public ResponseEntity<Resource> getFilesById(@PathVariable ("checksum") String checksum) throws IOException {


        DocumentFile document = new DocumentFile();
        document.setChecksum(checksum);
        document =  inMemoryPathController.getDocument(document);
        String finalPath = document.getMeta().getPath();
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


        Collection<DocumentFile> files = inMemoryPathController.getDocuments();
        Collection<EntityModel> models = new ArrayList<>();
        for (DocumentFile f : files) {
            EntityModel<DocumentFile> model = new EntityModel<>(f);
            Link link = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ApiService.class)
                    .getFilesById(f.getChecksum()))
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
    public Object doSearch(@RequestParam LinkedHashMap<String,String> params){

        String key = getParamsAsKey(params);
        Object result = CACHE.getIfPresent(key);
        if(Objects.isNull(result)){
           result = RestUtil.getObjectForUrl("",params);
           CACHE.put(key, result);
        }
        return result;
    }

    private String getParamsAsKey(LinkedHashMap<String, String> params) {
        StringBuilder key = new StringBuilder();
        params.entrySet().forEach( (k) -> {
            if( k.getValue() != null){
                key.append(k).append("%").append(k.getValue()).append("%%$%%");
            }
        });
        return key.toString();
    }

    @PostMapping("upload/file")
    public void uploadFile(@RequestParam("file") MultipartFile file){


        FileUtil.createFileToPath(file);
    }

    private static <K, V> Cache<K, V> buildCache() {


        return CustomCacheBuilder.newCache(POOLCACHE,TTLCACHE);
    }

}
