package com.sdpp.balancer.socket.domain;

import java.util.Objects;

public class Host {


    private int port;
    private String ip;

    @Override
    public String toString() {
        return "Host{" +
                "port=" + port +
                ", ip='" + ip + '\'' +
                '}';
    }

    public Host() {
    }

    public Host(int port, String ip) {
        this.port = port;
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Host host = (Host) o;
        return port == host.port &&
                Objects.equals(ip, host.ip);
    }

    @Override
    public int hashCode() {
        return Objects.hash(port, ip);
    }
}
