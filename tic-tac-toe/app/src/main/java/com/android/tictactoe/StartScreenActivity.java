package com.android.tictactoe;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.View;
import android.app.Activity;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.content.Context;

import gameboard.PlayMode;

public class StartScreenActivity extends Activity {

    public RadioButton player1x;
    public RadioButton player1o;
    public RadioButton player2x;
    public RadioButton player2o;
    public Switch computerSwitch;
    public RadioButton firstMoveX;
    public RadioButton firstMoveO;
    public TextView player1Name;
    public TextView player2Name;
    public Button startButton;
    public Button rulesButton;
    private PlayMode gameMode;

    //**********************************
    private RadioButton easyMode;
    private RadioButton medMode;
    private RadioButton hardMode;
    //*********************************

    private TicTacToeApplication tttApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);

        tttApp = (TicTacToeApplication) getApplication();
        gameMode = tttApp.getGameMode();

        computerSwitch = (Switch) findViewById(R.id.switch1);
        player1x = (RadioButton) findViewById(R.id.player1x);
        player1o = (RadioButton) findViewById(R.id.player1o);
        player2x = (RadioButton) findViewById(R.id.player2x);
        player2o = (RadioButton) findViewById(R.id.player2o);
        firstMoveX = (RadioButton) findViewById(R.id.firstMoveX);
        firstMoveO = (RadioButton) findViewById(R.id.firstMoveO);
        player1Name = (TextView) findViewById(R.id.player1Name);
        player2Name = (TextView) findViewById(R.id.player2Name);
        easyMode = (RadioButton) findViewById(R.id.easy);
        medMode = (RadioButton) findViewById(R.id.medium);
        hardMode = (RadioButton) findViewById(R.id.hard);

        //startButton = (Button) findViewById(R.id.startButton);

        //configureViews();
        /*
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }*/
    }

/*    private void configureViews(){
        //Set views for buttons
        singlePlayerButton = (Button) findViewById(R.id.start_screen_one_player_button);
        twoPlayerButton = (Button) findViewById(R.id.start_screen_two_player_button);
        rulesButton = (Button) findViewById(R.id.start_screen_rules_button);
    }*/

    /**METHOD ONLY FOR DEMO PURPOSES */
    public void beginGame(View view){

        Intent intent = new Intent(this, GameScreenActivity.class);

        String nameOfPlayer2;
        String nameOfPlayer1;
        String player1;
        String player2;
        String switchVsComputer;
        String firstMove;
        String difficulty;

        if (player1Name.length() > 0)
        {
            nameOfPlayer1 = player1Name.getText().toString();
        }
        else
        {
            nameOfPlayer1 = "player 1";
        }

        if (player2Name.length() > 0)
        {
            nameOfPlayer2 = player2Name.getText().toString();
        }
        else
        {
            nameOfPlayer2 = "player 2";
        }

        /*
        if (player2x.isChecked())
        {
            player2 = "x";
            player1 = "o";
        }
        else
        {
            player2 = "o";
            player1 = "x";
        }
        */

        if(player1x.isChecked()){
            player1 = "x";
            player2 = "o";
        }
        else{
            player1 = "o";
            player2 = "x";
        }

        if (computerSwitch.isChecked())
        {
            switchVsComputer = "true";
        }
        else
        {
            switchVsComputer = "false";
        }

        if (firstMoveO.isChecked())
        {
            firstMove = "o";
        }
        else
        {
            firstMove = "x";
        }

        //Get difficulty level
        if(easyMode.isChecked()){
            difficulty="easy";
        }
        else if(medMode.isChecked()){
            difficulty = "medium";
        }
        else{
            difficulty = "hard";
        }

        //String [] startGameInformation = {nameOfPlayer1, nameOfPlayer2, player1, player2, switchVsComputer, firstMove};

        intent.putExtra("player1Name", nameOfPlayer1);
        intent.putExtra("player2Name", nameOfPlayer2);
        intent.putExtra("player1", player1);
        intent.putExtra("player2", player2);
        intent.putExtra("switch", switchVsComputer);
        intent.putExtra("firstMove", firstMove);
        intent.putExtra("difficultyLevel", difficulty);

        //intent.putExtra("gameInformation", startGameInformation);
        startActivity(intent);
    }

    //onclick method for two player button
    public void twoPlayerButtonClicked(View view){
        tttApp.loadTwoPlayerGame();
        finish();
    }

    public void player1xClick (View view)
    {
        if (player2x.isChecked())
        {
            player2o.toggle();
        }
    }

    public void player2xClick (View view)
    {
        if (player1x.isChecked())
        {
            player1o.toggle();
        }

    }

    public void player1oClick (View view)
    {
        if (player2o.isChecked())
        {
            player2x.toggle();
        }
    }

    public void player2oClick (View view)
    {
        if (player1o.isChecked())
        {
            player1x.toggle();
        }

    }

    public void easyModeClick(View view){
        if (medMode.isChecked()){
            medMode.toggle();
        }
        if(hardMode.isChecked()){
            hardMode.toggle();
        }
    }

    public void mediumModeClick(View view){
        if (easyMode.isChecked()){
            easyMode.toggle();
        }
        if(hardMode.isChecked()){
            hardMode.toggle();
        }
    }

    public void hardModeClick(View view){
        if (easyMode.isChecked()){
            easyMode.toggle();
        }
        if (medMode.isChecked()){
            medMode.toggle();
        }
    }

    public void viewRules(View view){

        final Context context = this;
        Intent intent = new Intent(context, RulesActivity.class);
        startActivity(intent);
    }
    /**
     @Override
     public boolean onCreateOptionsMenu(Menu menu) {

     // Inflate the menu; this adds items to the action bar if it is present.
     getMenuInflater().inflate(R.menu.start_screen, menu);
     return true;
     }

     @Override
     public boolean onOptionsItemSelected(MenuItem item) {
     // Handle action bar item clicks here. The action bar will
     // automatically handle clicks on the Home/Up button, so long
     // as you specify a parent activity in AndroidManifest.xml.
     int id = item.getItemId();
     if (id == R.id.action_settings) {
     return true;
     }
     return super.onOptionsItemSelected(item);
     }


     public static class PlaceholderFragment extends Fragment {

     public PlaceholderFragment() {
     }

     @Override
     public View onCreateView(LayoutInflater inflater, ViewGroup container,
     Bundle savedInstanceState) {
     View rootView = inflater.inflate(R.layout.fragment_start_screen, container, false);
     return rootView;
     }
     }
     */

}
