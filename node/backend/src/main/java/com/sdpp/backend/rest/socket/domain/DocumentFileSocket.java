package com.sdpp.backend.rest.socket.domain;

import com.sdpp.backend.rest.domain.DocumentFile;

import java.io.Serializable;

public class DocumentFileSocket extends DocumentFile implements Serializable {

    private String link;

    public DocumentFileSocket(){}

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
