package com.sdpp.backend.rest.service.components;

import com.sdpp.backend.rest.domain.DocumentFile;
import com.sdpp.backend.rest.domain.MetaData;
import com.sdpp.backend.rest.util.FileUtil;
import com.sun.nio.file.ExtendedWatchEventModifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(WatcherSystemService.class);
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
            logger.info("Starting watch service at path {}", path);
            watchService = FileSystems.getDefault().newWatchService();
            registerPath();
            watchEvents();
        } catch (IOException | InterruptedException e) {
            logger.warn("Error while starting watcher service in path {}",path, e );
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
        logger.info("processing delete event for path {}", path);
        DocumentFile doc = new DocumentFile();
        doc.setName(path.getFileName().toString());
        doc.getMeta().setPath(path.getParent().toString());
        doc = mongoDBConnection.getEntity(DocumentFile.class, doc);
        mongoDBConnection.removeEntity(DocumentFile.class, doc);
        logger.info("event delete procesed succesfully");
    }

    private void processCreateEvent(Path path) {
        logger.info("processing create event for path {}", path);
        DocumentFile doc = createDocumentFileFromPath(path);
        mongoDBConnection.insertEntity(DocumentFile.class, doc);
        logger.info("event create procesed succesfully");
    }

    private DocumentFile createDocumentFileFromPath(Path p) {
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
            logger.error("Error while creating object from path {}",p.getFileName().toString(),e);
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
        logger.info("new path set {}", path);
        mongoDBConnection.initDbWithFolderContent();
        WatchKey key = registerPath();
        logger.info("canceling watcher service in previous path");
        key.cancel();
        startService();
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        startService();
    }

    public static void main(String[] args) {
        Integer e = 1;
        Integer e2 = e + 1;
        System.out.println(e.compareTo(e2));
    }
}
