/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoetcp.model;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author iqbal
 */
public class TCPServer extends TCP{
    
    @Override
    public void echoConnection() {
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(2002);
            socket = serverSocket.accept();
        } catch (IOException ex) {
            System.out.println("Error :" +ex);
        }
    }
}
