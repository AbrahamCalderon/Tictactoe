package com.android.tictactoe;
import gameboard.PlayMode;
import android.app.Application;

public class TicTacToeApplication extends Application {

    private PlayMode gameMode;

    @Override
    public void onCreate(){
        super.onCreate();
        loadTwoPlayerGame();
    }


    public void loadTwoPlayerGame(){
        gameMode = PlayMode.TWO_PLAYER;
    }


    public PlayMode getGameMode(){

        return gameMode;
    }
}
