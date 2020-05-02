package com.sdpp.balancer.rest.domain;

import java.io.Serializable;
import java.util.Objects;

public class Sha256 implements Serializable {

    private String hashed;

    public Sha256(){

    }

    public Sha256(String hashed) {
        this.hashed = hashed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sha256 sha256 = (Sha256) o;
        return Objects.equals(hashed, sha256.hashed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hashed);
    }

    public String getHashed() {
        return hashed;
    }

    public void setHashed(String hashed) {
        this.hashed = hashed;
    }
}
