package tictactoetcp.model;
public class BoardModel {
    private char playerToMove;
    private char playerOpponent;
    private char[][] gameBoard = new char[3][4];
    private int moveCount = 0;
    private boolean gameIsComplete = false;
    private int isWin = 0;
    private boolean canMove;

    //getter 
    public char getPlayerToMove() { return playerToMove; }
    public char[][] getGameBoard() { return gameBoard; }
    public int getMoveCount() { return moveCount; }
    public int isIsWin() { return isWin; }
    public boolean canMove() { return canMove; }
    public boolean gameIsComplete() {return gameIsComplete;}
    
    public void gameConditionCheker(){
         performWinCheck();
        checkMoveCount();
        if (gameIsComplete) {
            canMove = false;
        }
    }
    //end of getter
    
    //setter
    public void setPlayer1(){ 
        playerToMove = 'X'; 
        playerOpponent = 'O';
        resetGameBoard();
        canMove = true;
    }
    public void setPlayer2(){ 
        playerToMove = 'O'; 
        playerOpponent = 'X';
        resetGameBoard();
        canMove = false;
    }
    public void setMove(boolean move){ canMove = move; }
    
    public void setGameBoard(char[][] input){ this.gameBoard = input;}
    //end of setter
    
    //myfunction
    public void addResetSignal(){
        gameBoard[0][3] = 'Z';
    }
    public void startNewGame(){
        moveCount = 0;
        isWin = 0;
        gameIsComplete = false;
        resetGameBoard();
        if(playerToMove == 'X'){
            canMove = true;
        }
        
        if(playerToMove == 'O'){
            canMove = false;
        }
    }
    
    public void resetGameBoard(){
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                gameBoard[i][j] = ' ';
            }
        }
    }
    
    public boolean squareHasBeenPlayerd(int row, int col){
        return gameBoard[row][col] != ' ';
    }

    public boolean makeMoveInSquare(int row, int col){
        if(canMove || !squareHasBeenPlayerd(row, col)){
            gameBoard[row][col] = playerToMove;
            canMove = false;
            return true;
        }
        return false;
    }
    
    public void checkMoveCount(){
        moveCount = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if(gameBoard[i][j] != ' ')
                    moveCount++;
            }
        }
        if (moveCount == 9) gameIsComplete = true;
        
    }
    
    public void performWinCheck(){
        if(rowWin() || colWin() || diagWin())
            gameIsComplete = true;
    }
    
    public boolean rowWin(){
        for (int i = 0; i < 3; i++) {
            int meCount = 0, opponentCount =0;
            for (int j = 0; j < 3; j++) {
                if(gameBoard[i][j] == playerToMove) ++meCount;
                if(gameBoard[i][j] == playerOpponent) ++opponentCount;
            }
            if(meCount == 3 || opponentCount == 3){
                if(meCount == 3) isWin=1;
                if(opponentCount == 3 ) isWin = -1;
                return true;
            }
        }
        return false;
    }
    
    public boolean colWin(){
        for (int i = 0; i < 3; i++) {
            int meCount = 0, opponentCount =0;
            for (int j = 0; j < 3; j++) {
                if(gameBoard[j][i] == playerToMove) ++meCount;
                if(gameBoard[j][i] == playerOpponent) ++opponentCount;
            }
            if(meCount == 3 || opponentCount == 3){
                if(meCount == 3) isWin=1;
                if(opponentCount == 3 ) isWin = -1;
                return true;
            }
        }
        return false;
    }
    
    public boolean diagWin(){
         if ( gameBoard[0][0] == playerToMove && gameBoard[1][1] == playerToMove && gameBoard[2][2] == playerToMove ) {
            isWin = 1;
            return true;
        } else if  ( gameBoard[2][0] == playerToMove && gameBoard[1][1] == playerToMove && gameBoard[0][2] == playerToMove ) {
            isWin = 1;
            return true;
        } else if  ( gameBoard[0][0] == playerOpponent && gameBoard[1][1] == playerOpponent && gameBoard[2][2] == playerOpponent ) {
            isWin =-1;
            return true;
        } else if  ( gameBoard[2][0] == playerOpponent && gameBoard[1][1] == playerOpponent && gameBoard[0][2] == playerOpponent ) {
             isWin = -1;
            return true;
        } else {
            return false;
        }
    }
    //end of myfunction
    
    
}
