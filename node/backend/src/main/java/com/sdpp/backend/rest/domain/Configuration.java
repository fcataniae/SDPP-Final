package com.sdpp.backend.rest.domain;

public class Configuration {

    private Balancer balancer;
    private SharedFolder sharedFolder;

    public Configuration(){
        this.balancer = new Balancer();
        this.sharedFolder = new SharedFolder();
    }

    public Balancer getBalancer() {
        return balancer;
    }

    public void setBalancer(Balancer balancer) {
        this.balancer = balancer;
    }

    public SharedFolder getSharedFolder() {
        return sharedFolder;
    }

    public void setSharedFolder(SharedFolder sharedFolder) {
        this.sharedFolder = sharedFolder;
    }

}
