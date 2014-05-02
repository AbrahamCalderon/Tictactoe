package model;

import java.util.List;

/**
 * Created by Abraham on 4/5/14.
 */
public class AIPlaceHolder extends AIComponent {

    public AIPlaceHolder(GameState currentState, Player cpuPlayer, List<int[]> possibleMoves){
        super(currentState, cpuPlayer, possibleMoves);
    }

    //Testing easy mode
    public Point getRandomPoint(){
        int randomNumber = (int) (Math.random() * possibleMoves.size());
        int [] randomMove = possibleMoves.get(randomNumber);
        possibleMoves.remove(randomNumber);

        return new Point(randomMove[0], randomMove[1]);
    }
}
