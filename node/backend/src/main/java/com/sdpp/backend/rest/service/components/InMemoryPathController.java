package com.sdpp.backend.rest.service.components;

import com.sdpp.backend.rest.domain.DocumentFile;
import com.sdpp.backend.rest.exceptions.NoSuchDocumentException;
import com.sdpp.backend.rest.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Component
public class InMemoryPathController {

    private static final Logger logger = LoggerFactory.getLogger(InMemoryPathController.class);
    private static final String PREF = "";
    private static final String DOT = ".";
    private List<DocumentFile> documents;

    public InMemoryPathController(){
        documents = new ArrayList<>();
        initFolderContent();
    }

    void initFolderContent(){
        try {
            documents = FileUtil.getSharedFolderList();
        } catch (IOException e) {
            logger.error("Error while initializing DB w folder content",e);
        }

    }

    public List<DocumentFile> getDocuments() {

        return documents;
    }

    public void deleteDocument(DocumentFile doc) {

        documents.remove(doc);
    }

    public void saveDocument(DocumentFile doc) {
        documents.add(doc);
    }

    public DocumentFile getDocument(DocumentFile document) {
        for (DocumentFile d : documents) {
            if (d.equals(document)) {
                return d;
            }
        }
        throw new NoSuchDocumentException("Couldn´t find document with checksum " + document.getChecksum());
    }
}
