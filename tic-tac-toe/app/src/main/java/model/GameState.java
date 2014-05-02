package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import gameboard.Board;
import model.GameState;
import model.GameFinisher;
import model.GameEnder;
import static gameboard.Board.ROWS;
import static gameboard.Board.COLUMNS;

public class GameState {

    private Player xPlayerOwns[][];
    private Player oPlayerOwns[][];
    private Player board[][];
    private List<MoveResult> sucessorList;
    private int stateValue = Integer.MAX_VALUE;

    //Constructor
    public GameState(){
        board = new Player[ROWS][COLUMNS];
        xPlayerOwns = new Player[ROWS][COLUMNS];
        oPlayerOwns = new Player[ROWS][COLUMNS];
    }

    public boolean isEmpty(int row, int column) {
        return board[row][column] == null;
    }

    public boolean isTotallyEmpty(){
        int xSpots = 0;
        int oSpots = 0;
        for(int i = 0; i < ROWS; i++){
            for (int j=0; j<COLUMNS; j++)
                if(xPlayerOwns[i][j] == Player.X)
                    xSpots++;
        }
        for(int i = 0; i < ROWS; i++){
            for (int j=0; j<COLUMNS; j++)
                if(oPlayerOwns[i][j] == Player.O)
                    oSpots++;
        }
        System.out.println("...<><><><> IS TOTALLY EMPTY: " + ((xSpots == 0) && (oSpots ==0)));
        return((xSpots == 0) && (oSpots ==0));
    }

    public boolean isFull(){
        for(int i = 0; i < ROWS; i++){
            for(int j = 0; j < COLUMNS; j++){
                if(board[i][j] == null)
                    return false;
            }
        }
        return true;
    }

    public void movePlayer(Player currentPlayer, int row, int column) {

        if(currentPlayer.equals(Player.X)){
            xPlayerOwns[row][column] = Player.X;    //update xPlayers move
            board[row][column] = Player.X;          //update board
            removeSuccessorListCache();
        }
        else if(currentPlayer.equals(Player.O)){
            oPlayerOwns[row][column] = Player.O;    //update oPlayers move
            board[row][column] = Player.O;          //update board
            removeSuccessorListCache();
        }
        else
            System.out.println("Not a player");
    }

    public Player getPlayerInSquare(int row, int column){
        return board[row][column];

    }

    public Player getOpponent(Player player){
        if(player.equals(Player.X))
            return Player.O;
        else
            return Player.X;
    }

    public List<MoveResult> sucessorList(Player player){

        if(sucessorList == null){

            sucessorList = new ArrayList<MoveResult>();

            for(int i=0; i < ROWS; i++){
                for (int j = 0; j < COLUMNS; j++){
                    if(isEmpty(i, j)){
                        GameState cloneState = getStateCopy();
                        cloneState.movePlayer(player, i, j);
                        sucessorList.add(new MoveResult(cloneState, new Move(player, i, j)));
                    }
                }
            }
        }
        return sucessorList;
    }

    public GameState getStateCopy(){
        GameState currentState = new GameState();
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                currentState.board[i][j] = this.board[i][j];
            }
        }
        currentState.xPlayerOwns = this.xPlayerOwns;
        currentState.oPlayerOwns = this.oPlayerOwns;

        return currentState;
    }

    //Get the winning condition if present
    public GameFinisher winChecker(){
        System.out.println("Inside winChecker");
        for (Map.Entry<GameEnder, Integer[][]> entry : Board.possiblities.entrySet()) {
            System.out.println("Inside winChecker's for-loop");
            GameEnder thisGameEnder = entry.getKey();
            Integer[][] combo = entry.getValue();
            if(comboMatch(combo, xPlayerOwns, Player.X)){
                System.out.println("compareComboWithPlayer returned Player.X");
                return new GameFinisher(Player.X, thisGameEnder);
            }
            if(comboMatch(combo, oPlayerOwns, Player.O)){
                System.out.println("compareComboWithPlayer returned Player.O");
                return new GameFinisher(Player.O, thisGameEnder);
            }
        }
        //tie?
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                if(isEmpty(i, j)) {
                    return null;
                }
            }
        }
        return new GameFinisher(null, GameEnder.DRAW);
    }

    public boolean comboMatch(Integer[][] combo, Player[][] playerPos, Player player){
        for(int i = 0; i < combo.length; i++){
            int x = combo[i][0];
            int y = combo[i][1];
            if(playerPos[x][y] == player)
                continue;
            else return false;
        }
        return true;
    }


    public Player[][] getXPlayerOwns(){

        System.out.println("Inside getXPlayerOwns()");
        return xPlayerOwns;
    }

    public Player[][] getOPlayerOwns(){
        System.out.println("Inside getOPlayerOwns()");
        return oPlayerOwns;
    }

    public Player[][] getBoard(){
        System.out.println("Inside getBoard()");
        return board;
    }

    public int getStateValue(){
        return stateValue;
    }
    public void setStateValue(int stateValue){
        this.stateValue = stateValue;
    }

    private void removeSuccessorListCache(){
        this.sucessorList = null;
    }

}


