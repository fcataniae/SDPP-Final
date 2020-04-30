package com.sdpp.backend.rest.domain;

import java.io.Serializable;
import java.util.Objects;

public class DocumentFile implements Serializable {

    private String name;
    private String checksum;
    private MetaData meta;

    public DocumentFile() {
        meta = new MetaData();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DocumentFile that = (DocumentFile) o;
        return checksum.equals(that.checksum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(checksum);
    }

    public String getChecksum() {
        return checksum;
    }

    public void setChecksum(String checksum) {
        this.checksum = checksum;
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

