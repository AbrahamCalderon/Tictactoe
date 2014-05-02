package com.android.tictactoe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.android.tictactoe.R;

import gameboard.Board;
import gameboard.Square;
import model.PlayerSetup;

import static gameboard.Board.COLUMNS;
import static gameboard.Board.ROWS;

//Main options menu -- screen
public class GameScreenActivity extends Activity {

    private Board gameBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);

        String player1Name = getIntent().getExtras().get("player1Name").toString();
        String player2Name = getIntent().getExtras().get("player2Name").toString();
        String player1Symbol = getIntent().getExtras().get("player1").toString();
        String player2Symbol = getIntent().getExtras().get("player2").toString();
        String vsComputer = getIntent().getExtras().get("switch").toString();
        String firstPlayerToGo = getIntent().getExtras().get("firstMove").toString();
        String difficulty = getIntent().getExtras().get("difficultyLevel").toString();

        PlayerSetup playerInformation = new PlayerSetup();
        playerInformation.setPlayer1Name(player1Name);
        playerInformation.setPlayer2Name(player2Name);
        playerInformation.setPlayer1Symbol(player1Symbol);
        playerInformation.setPlayer2Symbol(player2Symbol);
        playerInformation.setFirstPlayerToGo(firstPlayerToGo);
        playerInformation.setDifficulty(difficulty);


        if (vsComputer.equals("true"))
        {
            playerInformation.setVsComputer(true);
        }
        else
        {
            playerInformation.setVsComputer(false);
        }


        gameBoard = new Board(this, playerInformation);
        applySquareOnClickListener();
    }

    public void applySquareOnClickListener(){
        for(int i = 0; i < ROWS; i++){
            for(int j = 0; j < COLUMNS; j++){
                System.out.println("[1] About to apply onclick listener!!!");
                Square square = gameBoard.getSquare(i, j);
                findViewById(square.getSquareId()).setOnClickListener(gameBoard.getSquare(i, j));

            }
        }
    }

    public void resetButtonClicked(View view){

        int playerOCurrentStreak = gameBoard.getPlayerOCurrentStreak();
        int playerXCurrentStreak = gameBoard.getPlayerXCurrentStreak();
        String lastPlayerWon = gameBoard.getLastWon();

        gameBoard.initializeState();
        gameBoard.updateTurnView();

        gameBoard.setLastWon(lastPlayerWon);
        gameBoard.setPlayerOCurrentStreak(playerOCurrentStreak);
        gameBoard.setPlayerXCurrentStreak(playerXCurrentStreak);
    }

    public void onEndGameClick(View view){
        int playerXBestStreak;
        int playerOBestStreak;

        if (gameBoard.getPlayerXCurrentStreak() > gameBoard.getPlayerXBestStreak()){
            playerXBestStreak = gameBoard.getPlayerXCurrentStreak();
        }
            else {
                playerXBestStreak = gameBoard.getPlayerXBestStreak();
            }

        if (gameBoard.getPlayerOCurrentStreak() > gameBoard.getPlayerOBestStreak()){
            playerOBestStreak = gameBoard.getPlayerOCurrentStreak();
        }
        else {
            playerOBestStreak = gameBoard.getPlayerOBestStreak();
        }


        int playerXWins = gameBoard.getPlayerXwins();
        int playerOWins = gameBoard.getPlayerOwins();


        Intent statsIntent = new Intent(this, EndGameScreenActivity.class);

        statsIntent.putExtra("tiedGames", gameBoard.getTiedGames());
        statsIntent.putExtra("xStreak", playerXBestStreak);
        statsIntent.putExtra("oStreak", playerOBestStreak);
        statsIntent.putExtra("xWins", playerXWins);
        statsIntent.putExtra("oWins", playerOWins);

        startActivity(statsIntent);

    }

}
