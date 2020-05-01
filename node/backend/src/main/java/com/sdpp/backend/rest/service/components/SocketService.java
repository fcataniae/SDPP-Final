package com.sdpp.backend.rest.service.components;

import com.sdpp.backend.rest.service.components.managers.SocketThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@Order(2)
public class SocketService implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(SocketService.class);

    @Value("${socket.server.port}")
    private int port;
    @Value("${socket.server.threadPool}")
    private int pool;

    private ServerSocket server;
    private boolean active = true;
    private ExecutorService executorService;

    public void stopThread() {

        this.active = false;
    }

    public SocketService(){

        super();
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
            Runnable worker = new SocketThread(client);
            logger.info("dispatching client to manager");
            executorService.execute(worker);
        }while(active);
    }

    @Override
    public void run()  {
        try {
            startSocketServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
