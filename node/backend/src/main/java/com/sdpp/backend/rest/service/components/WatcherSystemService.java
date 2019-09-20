package com.sdpp.backend.rest.service.components;

import com.sdpp.backend.rest.util.FileUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.*;

@Component
public class WatcherSystemService implements ApplicationRunner {

    private String path;

    private static WatchService watchService;

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
                System.out.println(
                        "Event kind:" + event.kind()
                                + ". File affected: " + event.context() + ".");
            }
            key.reset();
        }
    }
    private WatchKey registerPath() throws IOException {
        Path p = Paths.get(path);
        return p.register(
                watchService,
                StandardWatchEventKinds.ENTRY_CREATE,
                StandardWatchEventKinds.ENTRY_DELETE,
                StandardWatchEventKinds.ENTRY_MODIFY);
    }

    public void setNewPath() throws IOException {
        path = FileUtil.getSharedFolderPathName();
        WatchKey key = registerPath();
        key.cancel();
        startService();
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        startService();
    }
}
