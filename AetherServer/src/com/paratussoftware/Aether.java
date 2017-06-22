package com.paratussoftware;

import com.paratussoftware.requestHandling.ClientHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Aether {

    public static void main(String[] args){
        try {
            ServerSocket serverSocket = new ServerSocket(7777);
            while(true){
                Socket clientRequestSocket = serverSocket.accept();
                new Thread(new ClientHandler(clientRequestSocket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
