package com.sdpp.backend.rest.service.components;

import com.sdpp.backend.rest.service.components.managers.SocketThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

@Component
public class SocketService implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(SocketService.class);
    @Value("${socket.server.port}")
    private int port;
    private ServerSocket server;
    private boolean active = true;

    public void stopThread() {
        this.active = false;
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

    }

    private void manageConnections() throws IOException {
        Socket client = null;
        do{
            logger.info("waiting for new connection");
            client = server.accept();
            logger.info("Conection accepted from {}:{}",client.getLocalAddress(),client.getPort());
            Thread t = new SocketThread(client);
            logger.info("dispatching client to manager");
            t.start();
        }while(active);
    }

    @Override
    public void run(String... args) throws Exception {
        startSocketServer();
    }
}
