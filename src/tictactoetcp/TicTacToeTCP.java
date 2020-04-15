/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoetcp;

import tictactoetcp.controller.BoardController;
import tictactoetcp.model.BoardModel;
import tictactoetcp.view.BoardView;

/**
 *
 * @author iqbal
 */
public class TicTacToeTCP {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       BoardController b1 = new BoardController(new BoardModel(), new BoardView());
       BoardController b2 = new BoardController(new BoardModel(), new BoardView());
    }
    
}
