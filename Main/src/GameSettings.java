package game;

public class GameSettings {
    private int boardSize = 3;
    private String player1Name = "Player 1";
    private String player2Name = "Player 2";

    public GameSettings() {
    }

    public GameSettings(int boardSize, String player1Name, String player2Name) {
        this.boardSize = boardSize;
        this.player1Name = player1Name;
        this.player2Name = player2Name;
    }

    public int getBoardSize() {
        return boardSize;
    }

    public void setBoardSize(int boardSize) {
        this.boardSize = boardSize;
    }

    public String getPlayer1Name() {
        return player1Name;
    }

    public void setPlayer1Name(String player1Name) {
        this.player1Name = player1Name;
    }

    public String getPlayer2Name() {
        return player2Name;
    }

    public void setPlayer2Name(String player2Name) {
        this.player2Name = player2Name;
    }
}