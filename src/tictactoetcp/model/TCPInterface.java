package tictactoetcp.model;

public interface TCPInterface {
    public void echoConnection();
    public void sendMessage(char[][] output);
    public void closeConnection();
    public char[][] listenMessage();
    public void sendResetMessage();
    public String listenResetMessage();
    
}
