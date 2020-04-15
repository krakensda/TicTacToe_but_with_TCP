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
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author iqbal
 */
public abstract class TCP {
    protected Socket socket;
        
    public abstract void echoConnection();

    public void sendMessage(char[][] output) {
        OutputStream jalanKeluar ;
        ObjectOutputStream jalanKeuarObjek ;
        try {
            jalanKeluar = socket.getOutputStream();
            jalanKeuarObjek = new ObjectOutputStream(jalanKeluar);
            jalanKeuarObjek.writeObject(output);
        } catch (Exception e) {
            System.out.println("Error : "+e);
        }
    }
    
    public void closeConnection() {
        try {
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(TCPServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public char[][] listenMessage() {
        InputStream jalanMasuk;
        ObjectInputStream jalanMasukObjek;
        try {
            jalanMasuk = socket.getInputStream();
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
}
