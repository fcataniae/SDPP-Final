package com.sdpp.backend.rest.domain.statics;

public enum NodeEnum {

    EMPTY("");

    private String value;

    NodeEnum(String val){
        this.value = val;
    }

    @Override
    public String toString() {
        return this.value;
    }

}
