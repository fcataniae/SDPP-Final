package com.sdpp.backend.rest.service.components.managers;

import java.net.Socket;

public class SocketThread implements Runnable {

    private final Socket client;

    public SocketThread(Socket client){
        this.client = client;

    }

    @Override
    public void run(){


    }
}
