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
public class TCPServer implements TCPInterface{
    private ServerSocket serverSocket;
    private Socket server;
    private ServerSocket serverSocket2;
    private Socket server2;
    
    public void startServer(){
        try {
            server = serverSocket.accept();
            server2 = serverSocket2.accept();
        } catch (IOException ex) {
            Logger.getLogger(TCPServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @Override
    public void echoConnection() {
        try {
            serverSocket = new ServerSocket(2002);
            serverSocket2 = new ServerSocket(2003);
            startServer();
        } catch (IOException ex) {
            System.out.println("Error :" +ex);
        }
    }

    @Override
    public void sendMessage(char[][] output) {
        OutputStream jalanKeluar ;
        ObjectOutputStream jalanKeuarObjek ;
        try {
            jalanKeluar = server.getOutputStream();
            jalanKeuarObjek = new ObjectOutputStream(jalanKeluar);
            jalanKeuarObjek.writeObject(output);
        } catch (Exception e) {
            System.out.println("Error : "+e);
        }
    }

    @Override
    public void closeConnection() {
        try {
            serverSocket.close();
            server.close();
        } catch (IOException ex) {
            Logger.getLogger(TCPServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public char[][] listenMessage() {
        InputStream jalanMasuk;
        ObjectInputStream jalanMasukObjek;
        try {
            jalanMasuk = server.getInputStream();
            jalanMasukObjek = new ObjectInputStream(jalanMasuk);
            char[][] receivedBoard = (char[][]) jalanMasukObjek.readObject();
            if (receivedBoard != null) {
                return receivedBoard;
            }
        } catch (Exception ex) {
            Logger.getLogger(TCPServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void sendResetMessage() {
        OutputStream jalanKeluar ;
        ObjectOutputStream jalanKeuarObjek ;
        try {
            jalanKeluar = server2.getOutputStream();
            jalanKeuarObjek = new ObjectOutputStream(jalanKeluar);
            jalanKeuarObjek.writeUTF("RESET");
        } catch (Exception e) {
            System.out.println("Error : "+e);
        }
    }

    @Override
    public String listenResetMessage() {
        InputStream jalanMasuk;
        ObjectInputStream jalanMasukObjek;
        try {
            jalanMasuk = server2.getInputStream();
            jalanMasukObjek = new ObjectInputStream(jalanMasuk);
            String receivedString = (String) jalanMasukObjek.readUTF();
            if(receivedString != null){
                return receivedString;
            }
        } catch (Exception ex) {
            Logger.getLogger(TCPServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
}
