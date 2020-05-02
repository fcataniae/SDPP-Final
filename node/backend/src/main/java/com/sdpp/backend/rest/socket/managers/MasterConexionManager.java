package com.sdpp.backend.rest.socket.managers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sdpp.backend.rest.domain.DocumentFile;
import com.sdpp.backend.rest.service.ApiService;
import com.sdpp.backend.rest.socket.domain.DocumentFileSocket;
import com.sdpp.backend.rest.socket.domain.Operation;
import com.sdpp.backend.rest.socket.domain.OperationResponse;
import com.sdpp.backend.rest.socket.domain.enums.Transaction;
import com.sdpp.backend.rest.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;

public class MasterConexionManager implements Runnable{

    private static Logger logger = LoggerFactory.getLogger(MasterConexionManager.class);
    private static int defaultport = 9001;
    private ObjectMapper om = new ObjectMapper();

    @Override
    public void run() {
        String config = FileUtil.getConfig().getBalancer().getHost();
        try(
            Socket socket = new Socket(config, defaultport);
            ){
            logger.info("Connecting to master");
            Collection<DocumentFile> documents = new ArrayList<>(FileUtil.getSharedFolderList().values());
            Collection<DocumentFileSocket> documentsSockets = new ArrayList<>();
            for (DocumentFile d : documents) {
                DocumentFileSocket ds = new DocumentFileSocket();
                BeanUtils.copyProperties(d,ds);
                ds.setLink("http://localhost:8081" + ApiService.getLinkForDocumentFile(d).getHref());
                documentsSockets.add(ds);
            }
            Operation operation = new Operation();
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
}
