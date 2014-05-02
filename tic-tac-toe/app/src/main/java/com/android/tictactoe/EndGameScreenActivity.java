package com.android.tictactoe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class EndGameScreenActivity extends  Activity{

    public TextView xStreak;
    public TextView oStreak;
    public TextView xWon;
    public TextView oWon;
    public TextView tied;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game_screen);

        xStreak = (TextView) findViewById(R.id.playerXStreak);
        oStreak = (TextView) findViewById(R.id.playerOStreak);
        xWon = (TextView) findViewById(R.id.xWon);
        oWon = (TextView) findViewById(R.id.oWon);
        tied = (TextView) findViewById(R.id.gamesTied);

        int playerXStreak = (Integer) getIntent().getExtras().get("xStreak");
        int playerOStreak = (Integer) getIntent().getExtras().get("oStreak");
        int playerXWon = (Integer) getIntent().getExtras().get("xWins");
        int playerOWon = (Integer) getIntent().getExtras().get("oWins");
        int gamesTied = (Integer) getIntent().getExtras().get("tiedGames");

        xStreak.setText("" + playerXStreak);
        oStreak.setText("" + playerOStreak);
        xWon.setText("" + playerXWon);
        oWon.setText("" + playerOWon);
        tied.setText("" + gamesTied);
    }
}