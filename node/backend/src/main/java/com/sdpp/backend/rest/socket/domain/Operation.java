package com.sdpp.backend.rest.socket.domain;


import com.sdpp.backend.rest.socket.domain.enums.Transaction;

import java.io.Serializable;
import java.util.Collection;

public class Operation implements Serializable {

    private Transaction transaction;
    Collection<DocumentFileSocket> documents;


    public Operation() {
    }

    public Collection<DocumentFileSocket> getDocuments() {
        return documents;
    }

    public void setDocuments(Collection<DocumentFileSocket> documents) {
        this.documents = documents;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }
}
