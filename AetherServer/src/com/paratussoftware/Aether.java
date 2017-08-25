package com.paratussoftware;

import com.paratussoftware.requestHandling.ClientHandler;
import com.paratussoftware.ui.AetherView;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Aether {

    private static AetherView view;

    public static void main(final String[] args) {
        view = AetherView.showView();
        try {
            final ServerSocket serverSocket = new ServerSocket(7777);
            while (true) {
                final Socket clientRequestSocket = serverSocket.accept();
                new Thread(new ClientHandler(clientRequestSocket)).start();
            }
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    public static AetherView getView() {
        return view;
    }
}
