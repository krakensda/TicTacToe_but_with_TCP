package tictactoetcp.model;
public class BoardModel {
    private char playerToMove;
    private char[][] gameBoard;
    private int moveCount;
    private boolean gameIsComplete;
    private boolean isWin;
    private boolean canMove;

    //getter 
    public char getPlayerToMove() { return playerToMove; }
    public char[][] getGameBoard() { return gameBoard; }
    public int getMoveCount() { return moveCount; }
    public boolean isIsWin() { return isWin; }
    public boolean canMove() { return canMove; }
    //end of getter
    
    //setter
    public void setPlayer1(){ playerToMove = 'X'; }
    public void setPlayer2(){ playerToMove = 'O'; }
    public void setMove(boolean move){ canMove = move; }
    //end of setter
    
    //myfunction
    public void startNewGame(){
        moveCount = 0;
        isWin = false;
        gameIsComplete = false;
    }
    
    public void resetGameBoard(){
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                gameBoard[i][j] = ' ';
            }
        }
    }

    public void makeMoveInSquare(int row, int col){
    }
    //end of myfunction
    
    
}
