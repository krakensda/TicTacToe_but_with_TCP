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
public class TCPClient implements TCPInterface{
    private Socket clieSocket;
    private Socket clieSocket2;
        
    @Override
    public void echoConnection() {
        try {
            clieSocket = new Socket("localhost", 2002);
            clieSocket2 = new Socket("localhost", 2003);
        } catch (IOException ex) {
            System.out.println("Error :" +ex);
        }
    }

    @Override
    public void sendMessage(char[][] output) {
        OutputStream jalanKeluar ;
        ObjectOutputStream jalanKeuarObjek ;
        try {
            jalanKeluar = clieSocket.getOutputStream();
            jalanKeuarObjek = new ObjectOutputStream(jalanKeluar);
            jalanKeuarObjek.writeObject(output);
        } catch (Exception e) {
            System.out.println("Error : "+e);
        }
    }

    @Override
    public void closeConnection() {
        try {
            clieSocket.close();
            clieSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(TCPServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public char[][] listenMessage() {
        InputStream jalanMasuk;
        ObjectInputStream jalanMasukObjek;
        try {
            jalanMasuk = clieSocket.getInputStream();
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
            jalanKeluar = clieSocket2.getOutputStream();
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
            jalanMasuk = clieSocket2.getInputStream();
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
