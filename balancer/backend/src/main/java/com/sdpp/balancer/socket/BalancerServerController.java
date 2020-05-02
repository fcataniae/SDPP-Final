package com.sdpp.balancer.socket;


import com.sdpp.balancer.rest.domain.DocumentFile;
import com.sdpp.balancer.socket.domain.Host;
import com.sdpp.balancer.socket.exceptions.SearchExecutionException;
import com.sdpp.balancer.socket.managers.ConnectionManager;
import com.sdpp.balancer.socket.managers.SearchManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.*;

@Controller
public class BalancerServerController implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(BalancerServerController.class);
    private ExecutorService executorService;
    private boolean active = true;
    private ServerSocket server;
    private MultiValueMap<Host, DocumentFile> documents;


    @Value("${socket.server.port}")
    private int port;
    @Value("${socket.server.threadPool}")
    private int pool;



    public BalancerServerController(){
        documents = new LinkedMultiValueMap<>();
    }

    private void startSocketServer() throws IOException {
        try {
            start();
            manageConnections();
        } catch (IOException e) {
            logger.error("Error while starting server at port {}",port,e);
        }finally {
            logger.info("Clossing socket server...");
            server.close();
        }
    }

    private void start() throws IOException {

        server = new ServerSocket(port);
        logger.info("Socket server started at port {}", port);
        this.executorService = Executors.newFixedThreadPool(pool);
    }

    private void manageConnections() throws IOException {
        Socket client = null;
        do{
            logger.info("waiting for new connection");
            client = server.accept();
            logger.info("Conection accepted from {}:{}",client.getLocalAddress(),client.getPort());
            Runnable worker = new ConnectionManager(client, documents);
            logger.info("dispatching client to manager");
            executorService.execute(worker);
        }while(active);
    }

    @Override
    public void run() {
        try {
            startSocketServer();
        } catch (IOException e) {
            logger.error("Error initializating server", e);
            throw new RuntimeException(e);
        }
    }

    public List<DocumentFile> doSearch(LinkedHashMap<String, String> params)  {

        List<DocumentFile> toReturn = new ArrayList<>();
        try {
            ExecutorService executor = Executors.newFixedThreadPool(20);
            Collection<Callable<List<DocumentFile>>> callables = new ArrayList<>();
            documents.forEach((k, v) -> callables.add(new SearchManager(v, params)));
            List<Future<List<DocumentFile>>> futures = executor.invokeAll(callables);
            for (Future<List<DocumentFile>> future : futures) {
                List<DocumentFile> value = future.get();
                toReturn.addAll(value);
            }
            executor.shutdownNow();
        }catch (InterruptedException | ExecutionException e){
            logger.error("error doing search for params {}", params.toString(), e);
            throw new SearchExecutionException(e.getMessage(),e);
        }
        return toReturn;
    }
}
