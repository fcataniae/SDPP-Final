package com.sdpp.backend.rest.socket.domain;


import com.sdpp.backend.rest.domain.DocumentFile;
import com.sdpp.backend.rest.socket.domain.enums.Transaction;

import java.io.Serializable;
import java.util.Collection;

public class Operation implements Serializable {

    private Transaction transaction;
    private Collection<DocumentFile> documents;
    private Host host;

    public Operation() {
    }

    public Host getHost() {
        return host;
    }

    public void setHost(Host host) {
        this.host = host;
    }

    public Collection<DocumentFile> getDocuments() {
        return documents;
    }

    public void setDocuments(Collection<DocumentFile> documents) {
        this.documents = documents;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }
}
