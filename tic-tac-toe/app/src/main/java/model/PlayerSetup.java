package model;

import model.Player;

public class PlayerSetup {

    private Player human;
    public String player1Name;
    public String player2Name;

    public boolean firstMove;
    public boolean vsComputer;
    public String player1Symbol;
    public String player2Symbol;
    public String firstPlayerToGo;
    public String difficulty;

    public PlayerSetup(){

        human = Player.X;
    }

    public static Player chooseRandomPlayer(){

        /*int f = (int) (Math.random() * 2);*/
        int f = (int) ( Math.random() * 2);
        if(f == 0){
            return Player.X;
        }
        else
            return Player.O;
    }

    private Player getHuman(){
        return human;
    }

    public static Player getOpponent(Player player){
        return player.equals(Player.X)? Player.O : Player.X;
    }

    public void setFirstPlayerToGo(String firstPlayerToGo){
        this.firstPlayerToGo = firstPlayerToGo;
    }

    public String getFirstPlayerToGo(){
        return firstPlayerToGo;
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

    public boolean isFirstMove() {
        return firstMove;
    }

    public void setFirstMove(boolean firstMove) {
        this.firstMove = firstMove;
    }

    public boolean isVsComputer() {
        return vsComputer;
    }

    public void setVsComputer(boolean vsComputer) {
        this.vsComputer = vsComputer;
    }

    public String getPlayer1Symbol() {
        return player1Symbol;
    }

    public void setPlayer1Symbol(String player1Symbol) {
        this.player1Symbol = player1Symbol;
    }

    public String getPlayer2Symbol() {
        return player2Symbol;
    }

    public void setPlayer2Symbol(String player2Symbol) {
        this.player2Symbol = player2Symbol;
    }


    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }
}
