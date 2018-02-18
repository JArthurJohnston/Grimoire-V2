package com.paratussoftware.requestHandling;

import org.junit.Test;

import java.net.Socket;

import static org.junit.Assert.*;

public class ClientHandlerTest {

    @Test
    public void testConstruction(){
        Socket mockSocket = new Socket();

        ClientHandler clientHandler = new ClientHandler(mockSocket);

        assertSame(mockSocket, clientHandler.getSocket());
        assertTrue(clientHandler.getGestureBuffer().isEmpty());
    }
    
}