package com.sdpp.backend.rest.domain;

import java.io.Serializable;
import java.util.Objects;

public class DocumentFile implements Serializable {

    private String name;
    private String link;
    private MetaData meta;
    private Sha256 sha256;

    public DocumentFile() {
        meta = new MetaData();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DocumentFile that = (DocumentFile) o;
        return Objects.equals(sha256, that.sha256);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sha256);
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Sha256 getSha256() {
        return sha256;
    }

    public void setSha256(Sha256 sha256) {
        this.sha256 = sha256;
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

