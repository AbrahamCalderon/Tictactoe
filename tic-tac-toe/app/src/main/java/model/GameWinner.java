package model;

public class GameWinner {
    GameEnder gameEnder;
    Player winner;

    public GameWinner(GameEnder gameEnder, Player winner){
        super();
        this.gameEnder = gameEnder;
        this.winner = winner;
    }

    public void setGameWinner(GameEnder gameEnder){
        this.gameEnder = gameEnder;
    }
    public void setWinner(Player winner){
        this.winner = winner;
    }
    public GameEnder getGameEnder(){
        return gameEnder;
    }
    public Player getWinner(){
        return winner;
    }
}
