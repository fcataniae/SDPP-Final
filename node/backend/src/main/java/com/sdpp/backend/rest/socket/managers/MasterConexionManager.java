package com.sdpp.backend.rest.socket.managers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sdpp.backend.rest.domain.DocumentFile;
import com.sdpp.backend.rest.service.ApiService;
import com.sdpp.backend.rest.socket.domain.Host;
import com.sdpp.backend.rest.socket.domain.Operation;
import com.sdpp.backend.rest.socket.domain.OperationResponse;
import com.sdpp.backend.rest.socket.domain.enums.Transaction;
import com.sdpp.backend.rest.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;

public class MasterConexionManager implements Runnable{

    private static Logger logger = LoggerFactory.getLogger(MasterConexionManager.class);
    private static int defaultport = 9001;
    private final int restPort;
    private final int serverPort;
    private ObjectMapper om = new ObjectMapper();


    public MasterConexionManager(int serverPort, int restPort) {
        this.restPort = restPort;
        this.serverPort = serverPort;
    }

    @Override
    public void run() {
        String config = FileUtil.getConfig().getBalancer().getHost();
        try(
            Socket socket = new Socket(config, defaultport);
            ){
            logger.info("Connecting to master");
            Collection<DocumentFile> documents = new ArrayList<>(FileUtil.getSharedFolderList().values());
            Collection<DocumentFile> documentsSockets = new ArrayList<>();
            for (DocumentFile d : documents) {
                d.setLink(getDocumentLink(d));
                documentsSockets.add(d);
            }
            Operation operation = new Operation();
            operation.setHost(getHost());
            operation.setDocuments(documentsSockets);
            operation.setTransaction(Transaction.CONEXION);
            DataOutputStream os = new DataOutputStream(socket.getOutputStream());

            logger.info("Sending operation to master");
            os.writeUTF(om.writeValueAsString(operation));

            DataInputStream is = new DataInputStream(socket.getInputStream());

            OperationResponse or = om.readValue(is.readUTF(), OperationResponse.class);
            logger.info("Response: {}", or.getResult());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Host getHost() throws UnknownHostException {

        Host host = new Host();
        host.setIp(InetAddress.getLocalHost().getHostAddress());
        host.setPort(serverPort);
        return host;
    }

    private String getDocumentLink(DocumentFile d) throws IOException {
        return "http://"+ InetAddress.getLocalHost().getHostAddress() + ":" + restPort + ApiService.getLinkForDocumentFile(d);
    }
}
