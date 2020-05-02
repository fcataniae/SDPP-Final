package com.sdpp.balancer.socket.domain;


import java.io.Serializable;

public class OperationResponse implements Serializable {

    private String result;

    @Override
    public String toString() {
        return "OperationResponse{" +
                "result='" + result + '\'' +
                '}';
    }


    public OperationResponse() {
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
