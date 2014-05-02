package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Date;

/**
 * Created by Abraham on 4/5/14.
 */
public class HardAIComponent extends AIComponent {

    //Algorithm SETUP......
    /*************************************************************************************/
    private Player[][] cpuPositions;    //cpu player positions on board
    private Player[][] currentBoard; //entire board
    private Player[][] humanPts;        //human player positions on the board
    private Player cpuToken;             //cpu token
    private Point pointToReturn;        //The point to return from the algorithm
    private Player humanToken;          //human token

    public HardAIComponent(GameState currentState, Player cpuPlayer){
        super(currentState, cpuPlayer);
        currentBoard = currentState.getBoard();
        System.out.println("Inside HardAIComponent constructor");
    }

    public Point runAlgorithm(){
        System.out.println("Inside HardAIComponent initial runAlgorithm method...");

        //Determine which token the Computer is...
        if(cpuPlayer.equals(Player.X)){
            System.out.println("CPU Player is X!");
            cpuToken = Player.X;
            humanToken = Player.O;
            System.out.println("\nAbout to get what computer player owns...");
            cpuPositions = currentState.getXPlayerOwns();
            if(cpuPositions == null) System.out.println("Error in if stmt: obtaining cpuPositions");
            System.out.println("\nAbout to get what human player owns...");
            humanPts = currentState.getOPlayerOwns();
            if(cpuPositions == null) System.out.println("Error in if stmt: obtaining humanPts");
            System.out.println("\nExiting...");
        }
        else{
            System.out.println("CPU Player is O!");
            cpuToken = Player.O;
            humanToken = Player.X;
            System.out.println("\nAbout to get what computer player owns...");
            cpuPositions = currentState.getOPlayerOwns();
            if(cpuPositions == null) System.out.println("Error in else stmt: obtaining cpuPositions");
            else System.out.println("cpuPositions SIZE: " + cpuPositions.length);
            System.out.println("\nAbout to get what human player owns...");
            humanPts = currentState.getXPlayerOwns();
            if(humanPts == null) System.out.println("Error in else stmt: obtaining humanPts");
            else System.out.println("humanPts SIZE: " + humanPts.length);
            System.out.println("\nExiting...");
        }

        List<MoveResult> options = search(currentState);//----------------------------------------------
        System.out.println("---->>>>> MinMax Options: " + options.size());

        if(options.isEmpty()){
            System.out.println("Out of suggestions");
            System.exit(0);
        }

        Date date = new Date();
        Random random = new Random(date.getTime());
        int randomMove = random.nextInt(options.size());

        MoveResult as = options.get(randomMove);
        pointToReturn = new Point(as.getMove().getRow(), as.getMove().getColumn());
        return pointToReturn;
    }

    List<MoveResult> mixMaxDecision(GameState state){
        System.out.println("Inside HardAIComponent minMaxDecision method...");
        if(state.isTotallyEmpty()){
            System.out.println("****>>>>>>>>>>>>>>>> Inside isTotallyEmpty()");
            return state.sucessorList(cpuPlayer);
        }
        int val = maxValue(state);
        List<MoveResult> maxStates = new ArrayList<MoveResult>();
        for(MoveResult as : state.sucessorList(cpuToken)){
            if(as.getGameState().getStateValue() == val){
                maxStates.add(as);
            }
        }
        System.out.println("**************>>>>> sucessorList Size: " + state.sucessorList(cpuToken).size());
        System.out.println("**************>>>>> maxStates Size: " + maxStates.size());
        return maxStates;
    }

    private int maxValue(GameState state){
        System.out.println("Inside HardAIComponent maxValue method...");
        GameFinisher tc = state.winChecker();
        if(tc != null){
            return tc.utility(cpuPlayer);
        }

        int v = Integer.MIN_VALUE;

        for(MoveResult mr : state.sucessorList(cpuPlayer)){
            int minValue = minValue(mr.getGameState());
            mr.getGameState().setStateValue(minValue);
            v = Math.max(v, minValue);
        }
        return v;
    }

    private int minValue(GameState state){

        System.out.println("Inside HardAIComponent minValue method...");

        GameFinisher gc = state.winChecker();
        if(gc != null)
            return gc.utility(cpuToken);

        int val = Integer.MAX_VALUE;

        for(MoveResult as : state.sucessorList(humanToken)){
            int maxValue = maxValue(as.getGameState());
            as.getGameState().setStateValue(maxValue);
            val = Math.min(val, maxValue);
        }
        return val;
    }

    public List<MoveResult> search(GameState state){

        System.out.println("Inside HardAIComponent search method...");

        return mixMaxDecision(state);
    }
}