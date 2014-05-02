package model;

public class GameFinisher {

    Player player;
    private GameEnder gameEnder;

    //reps player positions
    private Player[][] moves;

    public GameFinisher(Player player, GameEnder gameEnder){
        this.player = player;
        this.gameEnder = gameEnder;
    }

    //Addition
    public GameFinisher(Player player, GameEnder gameEnder, Player[][] moves){
        this.player = player;
        this.gameEnder = gameEnder;
        this.moves = moves;
    }

    public void setPlayer(Player player){
        this.player = player;
    }
    public Player getPlayer(){
        return player;
    }
    public void setGameEnder(GameEnder gameEnder){
        this.gameEnder = gameEnder;
    }
    public GameEnder getGameEnder(){
        return gameEnder;
    }
    public void setMoves(Player[][] moves){
        this.moves = moves;
    }
    public Player[][] getMoves(){
        return moves;
    }

    public boolean isDraw(){
        return (this.gameEnder == GameEnder.DRAW) && (this.player == null);
    }

    public int utility(Player maxPlayer){
        if(isDraw()){
            return 0;
        }
        if(player.equals(maxPlayer)){
            return 1;
        }
        else if(player.equals(maxPlayer.equals(Player.X) ? Player.O : Player.X)){
            return -1;
        }
        throw new RuntimeException("Unexpected terminating condition");
    }

}
