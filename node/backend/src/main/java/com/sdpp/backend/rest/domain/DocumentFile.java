package com.sdpp.backend.rest.domain;

import java.io.Serializable;

public class DocumentFile implements Serializable {

    private String name;
    private MetaData meta;

    public DocumentFile() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MetaData getMeta() {
        return meta;
    }

    public void setMeta(MetaData meta) {
        this.meta = meta;
    }
}

