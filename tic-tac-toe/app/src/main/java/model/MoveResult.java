package model;

public class MoveResult {
    private Move move;
    private GameState gameState;

    public MoveResult(GameState gameState, Move move){
        this.move = move;
        this.gameState = gameState;
    }

    public void setMove(Move move){
        this.move = move;
    }
    public void setGameState(GameState gameState){
        this.gameState = gameState;
    }
    public Move getMove(){
        return move;
    }
    public GameState getGameState(){
        return gameState;
    }
}

