package com.sdpp.balancer.socket.domain;

import com.sdpp.balancer.rest.domain.DocumentFile;
import com.sdpp.balancer.socket.domain.enums.Transaction;

import java.io.Serializable;
import java.util.Collection;

public class Operation implements Serializable {

    private Transaction transaction;
    Collection<DocumentFile> documents;


    public Operation() {
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
