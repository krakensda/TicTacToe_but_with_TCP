/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoetcp.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;
import javax.swing.JButton;
import javax.swing.event.EventListenerList;
import tictactoetcp.model.BoardModel;
import tictactoetcp.model.TCPClient;
import tictactoetcp.model.TCPInterface;
import tictactoetcp.model.TCPServer;
import tictactoetcp.view.BoardView;

/**
 *
 * @author iqbal
 */
public class BoardController {
    private TCPInterface tcpControl;
    private BoardView view;
    private BoardModel board;
    private int i ;
    public BoardController(BoardModel board,BoardView view){
        this.board = board;
        this.view = view;
        this.view.setTitle("Gamenya udah jalan bambang");
        this.view.setVisible(true);
        initializeListener();
    }
    
    //myfunction
    public void initializeListener(){
        view.addClientButonListener(new ClientButtonListener());
        view.addServerButonListener(new ServerButtonListener());
        view.addSquareButonListener(new SquareButtonListener());
        view.addResetButonListener(new ResetButtonlistener());
    }
    
    public void resetTheGame(){
        TCPreceiveMessage  receive;
        board.startNewGame();
        view.updateGameBoard(board.getGameBoard());
        if(board.getPlayerToMove() == 'X')
            view.setCustomPlayerLabel("Player 1");
        if(board.getPlayerToMove() == 'O'){
            view.setCustomPlayerLabel("Player 2");
            receive = new TCPreceiveMessage();
            receive.setName("Listening... afterresetGame");
            receive.run();
        }
    }
    
    public boolean checkCurrentRunningThread(){
        Set<Thread> threads = Thread.getAllStackTraces().keySet();
        for (Thread t : threads) {
            System.out.println(t.getName());
            if (t.getName().equals("Listening : "+board.getPlayerToMove()) ){
                System.out.println("tictactoetcp.controller.BoardController.checkCurrentRunningThread()"+t.getName());
                return false;
            }     
        }
        return true;
    }
    
    public void winnerLoserCheck(){
        board.gameConditionCheker();
        if(board.gameIsComplete()){
                if(board.isIsWin() == 1){
                    view.setCustomPlayerLabel("You're Winner");
               }else if(board.isIsWin() == -1){
                    view.setCustomPlayerLabel("You're Loser");
                }else{
                    view.setCustomPlayerLabel("It's Tie");
                }
            }
    }
    //end of myfunction
    
    //listener class
    private class ServerButtonListener implements ActionListener{
        ServerStartConnection t1 = new ServerStartConnection();
        //TCPListenResetSignal t2 = new TCPListenResetSignal();
        @Override
        public void actionPerformed(ActionEvent ae) {
            tcpControl = new TCPServer();
            t1.setName("server thread");
            t1.start();
            board.setPlayer1();
            view.disableClientButton();
            view.disableServerButton();
            view.setCustomPlayerLabel("You're player 1");
//            t2.setDaemon(true);
//            t2.setName("listen reset "+board.getPlayerToMove());
//            t2.start();
        }
        
    }
    
    private class ClientButtonListener implements ActionListener{
        ClientStartConnection t1 = new ClientStartConnection();
        //TCPListenResetSignal t2 = new TCPListenResetSignal();
        TCPreceiveMessage receive;
        @Override
        public void actionPerformed(ActionEvent ae) {
            tcpControl = new TCPClient();
            t1.setName("client thread");            
            t1.start();
            board.setPlayer2();
            view.disableClientButton();
            view.disableServerButton();
            view.setCustomPlayerLabel("You're player 2");
            receive = new TCPreceiveMessage();
            receive.setName("Listening : "+board.getPlayerToMove());
            receive.start();
//            t2.setDaemon(true);
//            t2.setName("listen reset "+board.getPlayerToMove());
//            t2.start();
        }
        
    }
    
    private class SquareButtonListener implements ActionListener{
        TCPSendMessage send;
        TCPreceiveMessage receive;
        JButton[][] temp = view.getSquareButton();
        @Override
        public void actionPerformed(ActionEvent ae) {
            if(board.canMove()){
                int row=1,col=0;
                JButton square = (JButton) ae.getSource();
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        if(temp[i][j] == square){
                            row = i;
                            col = j;
                            break;
                        }

                    } 
                }

                board.makeMoveInSquare(row, col);
                view.updateGameBoard(board.getGameBoard());
                send = new TCPSendMessage();
                send.setName("sending.....");
                send.start();
                if(board.gameIsComplete() == false){
                    receive = new TCPreceiveMessage();
                    receive.setName("Listening : "+board.getPlayerToMove());
                    receive.start();
                }
            }
        }
        
    }
    
    private class ResetButtonlistener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            if(checkCurrentRunningThread()){
                TCPSendResetSignal t1 = new TCPSendResetSignal();
                t1.start();
            }else{
                view.showCustomDialogBox("You can only reset the game when it's your turn");
            }
        }
        
    }
    //end of listener class
    
    
    //thread class
    private class ServerStartConnection extends Thread{
        @Override
        public void run(){
            view.customMessageConditionLabel("Waiting for client.....");
            tcpControl.echoConnection();
            try {
                Thread.sleep(300);
            } catch (Exception e) {
            }
            view.customMessageConditionLabel("Client connected");
        }
    }
    
    private class ClientStartConnection extends Thread{
        @Override
        public void run(){
            view.customMessageConditionLabel("connecting to server....");
            tcpControl.echoConnection();
            try {
                Thread.sleep(300);
            } catch (Exception e) {
            }
            view.customMessageConditionLabel("connected");
        }
    }
    
    private class TCPSendMessage extends Thread{
        @Override
        public void run(){
            tcpControl.sendMessage(board.getGameBoard());
            winnerLoserCheck();
        }
    }
    
    private class TCPreceiveMessage extends Thread{
        public void run(){
            try {
                Thread.sleep(100);
            } catch (Exception e) {
            }
            board.setGameBoard(tcpControl.listenMessage());
            if(board.getGameBoard()[0][3] == 'Z'){
                resetTheGame();
            }
            view.updateGameBoard(board.getGameBoard());
            board.setMove(true);
            winnerLoserCheck();  
        }
    }
    
//    private class TCPListenResetSignal extends Thread{
//        @Override
//        public void run(){
//            try {
//                Thread.sleep(200);
//            } catch (InterruptedException e) {
//            }
//            String message = tcpControl.listenResetMessage();
//            if(message != null) 
//                resetTheGame();
//        }
//    }
    
    private class TCPSendResetSignal extends Thread{
        @Override
        public void run(){
            board.addResetSignal();
            tcpControl.sendMessage(board.getGameBoard());
            resetTheGame();
        }
    }
    //end of thread class
}
