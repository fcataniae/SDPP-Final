package com.sdpp.backend.rest.domain;

import static com.sdpp.backend.rest.domain.statics.NodeEnum.EMPTY;

public class Balancer {

    private String host = EMPTY.toString();
    private String port = EMPTY.toString();
    private String protocol = EMPTY.toString();

    public Balancer(){};

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getEndpoint() {
        return protocol.concat("://").concat(host).concat(":").concat(port);
    }
}
