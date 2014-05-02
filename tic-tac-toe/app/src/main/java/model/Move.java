package model;

public class Move {
    int row;
    int column;
    Player playerToMove;

    public Move(Player playerToMove, int row, int column){
        this.playerToMove = playerToMove;
        this.row = row;
        this.column = column;
    }

    public int getRow(){
        return row;
    }
    public int getColumn(){
        return column;
    }
    public Player getPlayerToMove(){
        return playerToMove;
    }
    public void setRow(int row){
        this.row = row;
    }
    public void setColumn(int row){
        this.column = column;
    }
    public void setPlayerToMove(Player playerToMove){
        this.playerToMove = playerToMove;
    }

}
