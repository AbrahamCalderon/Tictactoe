package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Abraham on 4/5/14.
 */
//AIComponent.java
public abstract class AIComponent {
    protected GameState currentState;
    protected Player cpuPlayer;
    public List<int[]> possibleMoves = new ArrayList<int[]>();

    public AIComponent(GameState currentState, Player cpuPlayer, List<int[]> possibleMoves){
        this.currentState = currentState.getStateCopy();
        this.cpuPlayer = cpuPlayer;
        this.possibleMoves = possibleMoves;
    }

    public AIComponent(GameState currentState, Player cpuPlayer){
        this.currentState = currentState.getStateCopy();
        this.cpuPlayer = cpuPlayer;
    }

    public void setGameState(GameState currentState){
        this.currentState = currentState;
    }
    public GameState getCurrentState(){
        return currentState;
    }
    public void setCpuPlayer(Player cpuPlayer){
        this.cpuPlayer = cpuPlayer;
    }
    public Player getCpuPlayer(){
        return cpuPlayer;
    }

    public List<int[]> getPossibleMoves() {
        return possibleMoves;
    }

    public void setPossibleMoves(List<int[]> possibleMoves) {
        this.possibleMoves = possibleMoves;
    }
}
