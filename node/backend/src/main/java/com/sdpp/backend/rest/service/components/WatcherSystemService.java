package com.sdpp.backend.rest.service.components;

import com.sdpp.backend.rest.domain.DocumentFile;
import com.sdpp.backend.rest.domain.MetaData;
import com.sdpp.backend.rest.util.FileUtil;
import com.sun.nio.file.ExtendedWatchEventModifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Date;

@Component
public class WatcherSystemService implements ApplicationRunner {

    private String path;

    private static WatchService watchService;
    private final static WatchEvent.Kind[] kinds = new WatchEvent.Kind[]{StandardWatchEventKinds.ENTRY_CREATE,
            StandardWatchEventKinds.ENTRY_DELETE};

    @Autowired
    private MongoDBConnection mongoDBConnection;

    public WatcherSystemService(){
    }

    public void startService() {
        try {
            path = FileUtil.getSharedFolderPathName();
            System.out.println("Starting watch service at path " + path);
            watchService = FileSystems.getDefault().newWatchService();
            registerPath();
            watchEvents();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            throw new ClosedWatchServiceException();
        }
    }

    private void watchEvents() throws InterruptedException {
        WatchKey key;
        while ((key = watchService.take()) != null) {
            for (WatchEvent<?> event : key.pollEvents()) {
                Path dir = (Path) key.watchable();
                Path fullPath = dir.resolve(event.context().toString());
                if(Files.isRegularFile(fullPath)){
                    if(event.kind().equals(StandardWatchEventKinds.ENTRY_CREATE))
                        processCreateEvent(fullPath);
                }
                if(event.kind().equals(StandardWatchEventKinds.ENTRY_DELETE)){
                    processDeleteEvent(fullPath);
                }
            }
            key.reset();
        }
    }

    private void processDeleteEvent(Path path) {
        DocumentFile doc = new DocumentFile();
        doc.setName(path.getFileName().toString());
        doc.getMeta().setPath(path.getParent().toString());
        doc = mongoDBConnection.getEntity(DocumentFile.class, doc);
        mongoDBConnection.removeEntity(DocumentFile.class, doc);
    }

    private void processCreateEvent(Path path) {
        DocumentFile doc = createFromPath(path);
        mongoDBConnection.insertEntity(DocumentFile.class, doc);
    }

    private DocumentFile createFromPath(Path p) {
        DocumentFile doc = new DocumentFile();
        try {
            BasicFileAttributes attr = Files.readAttributes(p, BasicFileAttributes.class);
            doc.setName(p.getFileName().toString());
            MetaData meta = new MetaData();
            meta.setPath(p.getParent().toString());
            meta.setCreateTime(new Date(attr.creationTime().toMillis()));
            meta.setModifiedTime(new Date(attr.lastModifiedTime().toMillis()));
            meta.setSize(attr.size());
            meta.setExtension(doc.getName().substring(doc.getName().lastIndexOf('.') + 1));
            doc.setMeta(meta);
        }catch (IOException e) {
            e.printStackTrace();
        }
        return doc;
    }
    private WatchKey registerPath() throws IOException {
        Path p = Paths.get(path);
        return p.register(
                watchService,
                kinds,
                ExtendedWatchEventModifier.FILE_TREE);
    }

    public void setNewPath() throws IOException {
        path = FileUtil.getSharedFolderPathName();
        mongoDBConnection.initDbWithFolderContent();
        WatchKey key = registerPath();
        key.cancel();
        startService();
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        startService();
    }
}
