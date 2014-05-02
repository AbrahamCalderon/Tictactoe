package gameboard;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;

import model.Player;

public class Square implements View.OnClickListener {

    int squareId;
    int row;
    int column;
    private Player player;
    private Board board;
    private Activity context;
    private ImageView imageView;

    public Square(int row, int column, int squareId, Board board, Activity context){
        super();
        this.squareId = squareId;
        this.row = row;
        this.column = column;
        this.board = board;
        this.context = context;
        this.imageView = (ImageView) context.findViewById(squareId);
    }

    public Player getPlayer(){
        return player;
    }

    public void setPlayer(Player player) {
        System.out.println("[6] Inside set player...");
        this.player = player;
        Board.updateBoardViewWithPlayer(imageView, player);
    }

    public void setSquareId(int squareId){
        this.squareId = squareId;
    }

    public int getSquareId(){
        return squareId;
    }

    //Each square is onclick listener
    @Override
    public void onClick(View view) {
        System.out.println("[2] Inside onClick method of Square class..");
        try{
            board.clickSquare(row, column);
        }catch (Exception ex){
            System.out.println("Can't complete move.");
        }
    }

    public void makeWinningSquare(){
        Board.updateBoardViewWithPlayer(imageView, player, true);
    }
}

