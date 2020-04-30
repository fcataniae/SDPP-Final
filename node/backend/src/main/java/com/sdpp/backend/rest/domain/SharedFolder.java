package com.sdpp.backend.rest.domain;

import static com.sdpp.backend.rest.domain.statics.NodeEnum.EMPTY;

public class SharedFolder {

    private String path = EMPTY.toString();;

    public SharedFolder(){}

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
