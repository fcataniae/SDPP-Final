package com.sdpp.backend.rest.service.components;

import com.sdpp.backend.rest.domain.DocumentFile;
import com.sdpp.backend.rest.domain.Sha256;
import com.sdpp.backend.rest.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@Component
public class InMemoryPathController {

    private static final Logger logger = LoggerFactory.getLogger(InMemoryPathController.class);
    private static final String PREF = "";
    private static final String DOT = ".";
    private Map<Sha256, DocumentFile> documents;

    public InMemoryPathController(){
        documents = new HashMap<>();
        initFolderContent();
    }

    void initFolderContent(){
        try {
            logger.info("Retrieving documenfiles in {}", FileUtil.getSharedFolderPathName());
            documents = FileUtil.getSharedFolderList();
            logger.info("Retrieved {} document files", documents.size());
        } catch (IOException e) {
            logger.error("Error while initializing DB w folder content",e);
        }

    }

    public Map<Sha256, DocumentFile> getDocuments() {

        return documents;
    }

    public void deleteDocument(DocumentFile doc) {

        documents.remove(doc.getSha256());
    }

    public void saveDocument(DocumentFile doc) {

        documents.put(doc.getSha256(), doc);
    }

    public DocumentFile getDocument(DocumentFile document) {

        return documents.get(document.getSha256());
    }
}
