package com.sdpp.balancer.socket.managers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sdpp.balancer.rest.domain.DocumentFile;
import com.sdpp.balancer.socket.domain.Host;
import com.sdpp.balancer.socket.domain.Operation;
import com.sdpp.balancer.socket.domain.OperationResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.MultiValueMap;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ConnectionManager implements Runnable{

    private static final Logger logger = LoggerFactory.getLogger(ConnectionManager.class);
    private final Socket client;
    private final String tag;
    private final MultiValueMap<Host, DocumentFile> documents;
    private Operation operation;
    private ObjectMapper om = new ObjectMapper();

    public ConnectionManager(Socket client, MultiValueMap<Host, DocumentFile> documents){
        this.client = client;
        this.tag = Thread.currentThread().toString() ;
        this.documents = documents;
    }


    @Override
    public void run() {

        try {
            logger.info("{} waiting for client request",tag);
            DataInputStream is = new DataInputStream(client.getInputStream());

            operation = om.readValue(is.readUTF(), Operation.class);

            logger.info("{} transaction received from client {}",tag,client.getInetAddress().toString());

            synchronized (documents) {

                switch (operation.getTransaction()){
                    case CONEXION:
                        addDocuments(operation);
                        break;
                    case DESCONEXION:
                        deleteDocuments();
                        break;

                    default:
                        ObjectOutputStream os = new ObjectOutputStream(client.getOutputStream());
                        OperationResponse resp = new OperationResponse();
                        resp.setResult("Unsuported operation");
                        os.writeObject(resp);
                        logger.info("{} operation unsuported.",tag);
                        os.close();
                        break;
                }
            }

        }catch (Exception e){
            logger.warn("{} Error while making transaction {}", tag, operation.getTransaction().name(), e);
        }
    }

    private void deleteDocuments() throws IOException {

        Host host = new Host();
        host.setIp(client.getInetAddress().getCanonicalHostName());
        host.setPort(client.getPort());
        documents.remove(host);
        DataOutputStream os = new DataOutputStream(client.getOutputStream());
        OperationResponse resp = new OperationResponse();
        resp.setResult("Files removed from balancer");
        logger.info("{} sending response to client", tag);
        os.writeUTF(om.writeValueAsString(resp));
    }

    private void addDocuments(Operation operation) throws IOException {

        Host host = new Host();
        host.setIp(client.getInetAddress().getCanonicalHostName());
        host.setPort(client.getPort());
        documents.put(host, new ArrayList<>(operation.getDocuments()));
        DataOutputStream os = new DataOutputStream(client.getOutputStream());
        OperationResponse resp = new OperationResponse();
        resp.setResult("Files added to balancer and started to mantain");
        logger.info("{} sending response to client", tag);
        os.writeUTF(om.writeValueAsString(resp));

    }
}
