package com.sdpp.backend.rest.service.components;

import com.sdpp.backend.rest.domain.DocumentFile;
import com.sdpp.backend.rest.util.FileUtil;
import com.sun.nio.file.ExtendedWatchEventModifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.nio.file.*;

@Component
public class WatcherSystemService implements ApplicationRunner {

    private String path;
    private static final Logger logger = LoggerFactory.getLogger(WatcherSystemService.class);
    private static WatchService watchService;
    private final static WatchEvent.Kind[] kinds = new WatchEvent.Kind[]{StandardWatchEventKinds.ENTRY_CREATE,
            StandardWatchEventKinds.ENTRY_DELETE};

    private InMemoryPathController inMemoryPathController;

    @Autowired
    public WatcherSystemService(InMemoryPathController inMemoryPathController){
        this.inMemoryPathController = inMemoryPathController;
    }

    public void startService() {
        try {
            path = FileUtil.getSharedFolderPathName();
            if(!StringUtils.isEmpty(path)) {
                logger.info("Starting watch service at path {}", path);
                watchService = FileSystems.getDefault().newWatchService();
                registerPath();
                watchEvents();
            }else{
                logger.info("Watcher service doesn't start cause of empty path, waiting for update in configuration...");
            }
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
        inMemoryPathController.deleteDocument(doc);
        logger.info("event delete procesed succesfully");
    }

    private void processCreateEvent(Path path) {

        try {
            logger.info("processing create event for path {}", path);
            DocumentFile doc = createDocumentFileFromPath(path);
            inMemoryPathController.saveDocument(doc);
            logger.info("event create procesed succesfully");
        } catch (IOException e) {
            logger.error(e.getLocalizedMessage(), e);
            logger.error("couldn't retrieve information from file {}", path.toAbsolutePath().toString());
        }

    }

    private DocumentFile createDocumentFileFromPath(Path p) throws IOException {

        return FileUtil.createDocumentFile(p);
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
        WatchKey key = registerPath();
        inMemoryPathController.initFolderContent();
        logger.info("canceling watcher service in previous path");
        key.cancel();
        startService();
    }

    @Override
    public void run(ApplicationArguments args) throws Exception{
        startService();
    }
}
