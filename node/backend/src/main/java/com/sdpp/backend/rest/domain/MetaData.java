package com.sdpp.backend.rest.domain;

import java.io.Serializable;
import java.time.LocalDate;

public class MetaData implements Serializable {

    private LocalDate createdTime;
    private LocalDate modifiedTime;
    private String author;
    private String extension;
    private String path;
    private Long size;

    public LocalDate getCreateTime() {
        return createdTime;
    }

    public void setCreateTime(LocalDate createTime) {
        this.createdTime = createTime;
    }

    public LocalDate getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(LocalDate modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public MetaData() {
    }
}
