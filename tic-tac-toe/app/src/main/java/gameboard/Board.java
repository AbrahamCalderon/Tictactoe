package gameboard;

import com.android.tictactoe.R;
import com.android.tictactoe.TicTacToeApplication;

import android.os.Handler;
import android.view.View;
import android.app.Activity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import model.AIComponent;
import model.AIPlaceHolder;
import model.EasyAIComponent;
import model.GameEnder;
import model.GameFinisher;
import model.GameFinisher;
import model.GameWinner;
import model.HardAIComponent;
import model.MedAIComponent;
import model.Player;
import model.GameState;
import model.PlayerSetup;
import model.Point;
import model.computerPlayer;

public class Board {

    //Size of board at all times
    public static int ROWS = 3;
    public static int COLUMNS = 3;

    //Board itself-- represented by 2-D Array of Squares
    private Square gameBoard[][];
    private Activity context;
    private GameState state;
    private PlayMode gameMode;
    private Player currentPlayer;
    private PlayerSetup playerSetup;
    private ImageView currentPlayerToken;
    private PlayMode mode;
    boolean isGameOver;
    private ImageView winImage;
    private View gameWinView;
    private View gameTieView;
    private View turnView;
    private static TextView currentPlayerName;
    private static PlayerSetup playerOptions;
    public List<int[]> possibleMoves = new ArrayList<int[]>();
    public int playerXCurrentStreak;
    public int playerOCurrentStreak;
    public int playerXBestStreak;
    public int playerOBestStreak;
    public int playerXMinimumMoves;
    public int playerOMinimumMoves;
    public int playerXwins;
    public int playerOwins;
    public int tiedGames;
    public String lastWon;



    //create id for each square so we can reference them with an image
    private int squareId[][] = {
            {R.id.square_1, R.id.square_2, R.id.square_3},
            {R.id.square_4, R.id.square_5, R.id.square_6},
            {R.id.square_7, R.id.square_8, R.id.square_9}
    };

    //POSSIBLE WAYS TO WIN GAME (8 POSSIBILITIES):
    //Col1, Col2, Col3, Row1, Row2, Row3, Diagnol1, Diagnol2
    public static HashMap<GameEnder, Integer[][]> possiblities;

    static{
        possiblities = new HashMap<GameEnder, Integer[][]>();
        possiblities.put(GameEnder.COLUMN1, new Integer[][] {{0,0},{1,0},{2,0}});
        /**
         * [[X, O, O],
         * [X, O, O],
         * [X, O, O]]
         */
        possiblities.put(GameEnder.COLUMN2, new Integer[][] {{0,1}, {1,1}, {2,1}});
        /**
         * [[O, X, O],
         * [O, X, O],
         * [O, X, O]]
         */
        possiblities.put(GameEnder.COLUMN3, new Integer[][] {{0,2}, {1,2}, {2,2}});
        /**
         * [[O, O, X],
         * [O, O, X],
         * [O, O, X]]
         */

        possiblities.put(GameEnder.ROW1, new Integer[][] {{0,0}, {0,1}, {0,2}});
        /**
         * [[X, X, X],
         * [O, O, O],
         * [O, O, O]]
         */

        possiblities.put(GameEnder.ROW2, new Integer[][] {{1,0},{1,1},{1,2}});
        /**
         * [[O, O, O],
         * [X, X, X],
         * [O, O, O]]
         */
        possiblities.put(GameEnder.ROW3, new Integer[][] {{2,0}, {2,1}, {2,2}});
        /**
         * [[O, O, O],
         * [O, O, O],
         * [X, X, X]]
         */
        possiblities.put(GameEnder.DIAGNOL1, new Integer[][] {{0,0},{1,1},{2,2}});
        /**
         * [[X, O, O],
         * [O, X, O],
         * [O, O, X]]
         */

        possiblities.put(GameEnder.DIAGNOL2, new Integer[][] {{0,2},{1,1},{2,0}});
        /**
         * [[O, O, X],
         * [O, X, O],
         * [X, O, O]]
         */
    }

    //constructor
    public Board(Activity context, PlayerSetup playerInformation) {
        this.context = context;
        configureViews();
        playerOptions = playerInformation;
        createBoard(); //initializes board.. fill with squares
        initializeState(); //initialize state... empty squares
        System.out.println("XXXXXXXXXXXXXXXXXXXXXX");
        System.out.println("[1] Current Player: " + currentPlayer.toString());
        updateBoardViewWithPlayer(currentPlayerToken, currentPlayer);
        updateCurrentPlayerName (currentPlayer);
        playerXMinimumMoves = 10;
        playerOMinimumMoves = 10;
        playerXBestStreak = 0;
        playerOBestStreak = 0;
        playerXCurrentStreak = 0;
        playerOCurrentStreak = 0;
        tiedGames = 0;
        playerXwins = 0;
        playerOwins = 0;
        lastWon = "";


        //currentPlayerName.setText(playerOptions.getPlayer1Name());
    }



    private void configureViews(){
        currentPlayerToken = (ImageView) context.findViewById(R.id.init_turn_image);
        winImage = (ImageView) context.findViewById(R.id.game_win_image);

        gameWinView = context.findViewById(R.id.game_winner);
        gameTieView = context.findViewById(R.id.game_tie);
        turnView = context.findViewById(R.id.init_turn);
        currentPlayerName = (TextView) context.findViewById(R.id.turnName);

    }


    //Creates the gird of 3 x 3 squares
    public void createBoard(){
        gameBoard = new Square[ROWS][COLUMNS];
        for(int i = 0; i < ROWS; i++){
            for(int j = 0; j < COLUMNS; j++){
                gameBoard[i][j] = new Square(i, j, squareId[i][j], this, this.context);
            }
        }
    }

    public void setPossibleMoves ()
    {
        possibleMoves.clear();

        for (int x=0; x < 3; x++)
        {
            for (int i = 0; i < 3; i++)
            {
                int [] possibility = new int[2];
                possibility [0] = x;
                possibility [1] = i;
                possibleMoves.add(possibility);
            }
        }
    }

    //sets the initial state of board -- squares with no players in them
    public void initializeState(){

        setPossibleMoves();

        if (playerOptions.getFirstPlayerToGo().equalsIgnoreCase(Player.X.toString())){
            currentPlayer = Player.X;
        }
        else if (playerOptions.getFirstPlayerToGo().equalsIgnoreCase(Player.O.toString())){
            currentPlayer = Player.O;
        }
        else
            currentPlayer = PlayerSetup.chooseRandomPlayer();

        if(currentPlayer == null)System.out.println(">>>>>>> Player not RETURNED FROM chooseRandomPlayer() >>>>>>");
        else System.out.println(">>>>>>>>>>>> currentPlayer was initialized! >>>>>>>>>>>> Player: " + currentPlayer.toString() + " GOES FIRST!!");
        state = new GameState();

        System.out.println("CheckPoint 1");
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                gameBoard[i][j].setPlayer(null);
            }
        }

        System.out.println("CheckPoint 2");
        TicTacToeApplication tttApp = (TicTacToeApplication) context.getApplication();

        System.out.println("CheckPoint 3");

        System.out.println("CheckPoint 4");
        isGameOver = false;
        System.out.println("1 ******************* > > > " + isGameOver);
        updateModeDisplay();

        //CHECK TO SEE IF THE COMPUTER WAS CHOSEN TO GO FIRST!
        if(playerOptions.vsComputer && !(currentPlayer.toString().equalsIgnoreCase(playerOptions.getPlayer1Symbol()))){
            System.out.println("****************************************************");
            System.out.println("Player1 Token: " + playerOptions.player1Symbol
                    + "     Player2 Token: " + playerOptions.player2Symbol
                    + "     Current Player Token is: " + currentPlayer.toString());
            System.out.println("****************************************************");

            //Computer was chosen to go first, THUS move the computer
            moveComputer();
        }

    }

    public void initPlayerTokenImage(){
        if(currentPlayer.equals(Player.X))
            currentPlayerToken = (ImageView) context.findViewById(R.drawable.orange_x_rs);
        if(currentPlayer.equals(Player.O))
            currentPlayerToken = (ImageView) context.findViewById(R.drawable.blue_o_rs);
    }

    //Get a particular cell in the board
    public Square getSquare(int row, int column){
        return gameBoard[row][column];
    }

    //invoked when square is clicked .. invoked in square class
    public void clickSquare(int r, int c) throws Exception{

        if(isGameOver){
            System.out.println("Game is over, cannot complete move");
            Toast.makeText(context, "Game is over. Press reset to play again.", Toast.LENGTH_SHORT).show();
            return;
        }

        if(state.isEmpty(r, c)){

            // here you would add one to the counter to keep track of the number of moves that the current player has made


            System.out.println("[3] Inside clickSquare method, and square is empty");
            System.out.println("[3] Square clicked was: " + r + " , " + c);
            placeCurrentPlayer(r, c);
            updateTurnView();

            //removes the place where the player clicked out of the possible moves open
            for (int x =0; x < possibleMoves.size(); x++)
            {
                int [] possibleMove = possibleMoves.get(x);
                if (r == possibleMove [0] && c == possibleMove [1])
                {
                    possibleMoves.remove(x);
                }
            }

            //if the game ends, it prevents the computer from going forward and making a move
            GameFinisher gf = state.winChecker();
            if(gf!=null){
                return;
            }

            if(playerOptions.vsComputer){
                //Check if computer is game and then run method
                moveComputer();
            }
        }
        else{
            Toast.makeText(context, "Square is already occupied", Toast.LENGTH_SHORT).show();
        }
    }

    public void moveComputer(){

        //the computer makes a move, randomly
        if(playerOptions.vsComputer && playerOptions.getDifficulty().equalsIgnoreCase("easy")){

            EasyAIComponent easyAi = new EasyAIComponent(state, currentPlayer, possibleMoves);
            Point retPoint = easyAi.getRandomPoint();
            placeCurrentPlayer(retPoint.getX(), retPoint.getY());
            updateTurnView();

        }

        if(playerOptions.vsComputer && playerOptions.getDifficulty().equalsIgnoreCase("medium")){

            MedAIComponent mediumAi = new MedAIComponent(state, currentPlayer);
            Point retPoint = mediumAi.runAlgorithm();

            System.out.println("----->>> Returned point: (" + retPoint.getX() + ", " + retPoint.getY() + ")");
            System.out.println("2 ***************** > > > " + isGameOver);

            placeCurrentPlayer(retPoint.getX(), retPoint.getY());

            updateTurnView();

        }

        if(playerOptions.vsComputer && playerOptions.getDifficulty().equalsIgnoreCase("hard")){
            System.out.println("2 ***************** > > > " + isGameOver);
            HardAIComponent hardAi = new HardAIComponent(state, currentPlayer);
            Point retPoint = hardAi.runAlgorithm();
            System.out.println("----->>> Returned point: (" + retPoint.getX() + ", " + retPoint.getY() + ")");
            System.out.println("3 ***************** > > > " + isGameOver);
            placeCurrentPlayer(retPoint.getX(), retPoint.getY());
            updateTurnView();
        }
    }

    //update who's turn ----------------------------------------------------------------------------
    public void updateTurnView(){
        updateBoardViewWithPlayer(currentPlayerToken, currentPlayer);
    }

    //updates the game screen with current player symbol placing
    public static void updateBoardViewWithPlayer(ImageView imageView, Player player) {
        updateBoardViewWithPlayer(imageView, player, false);
        if (player != null)
            updateCurrentPlayerName(player);
    }

    //determine which token to display for this move
    public static void updateBoardViewWithPlayer(ImageView imageView, Player player, boolean winner) {
        System.out.println("[7] Inside updateBoardViewWithPlayer...");
        if(player == null) {
            System.out.println("[7] Inside updateBoardViewWithPlayer... player =null");
            imageView.setImageResource(R.drawable.empty_white_rs);
        }else if(player.equals(Player.X) && !winner) {
            imageView.setImageResource(R.drawable.orange_x_rs);
        }else if(player.equals(Player.X) && winner){
            imageView.setImageResource(R.drawable.green_x_rs);
        }
        else if (player.equals(Player.O) && !winner) {
            imageView.setImageResource(R.drawable.blue_o_rs);
        }
        else if(player.equals(Player.O) && winner){
            imageView.setImageResource(R.drawable.green_o_rs);
        }
    }

    public static void updateCurrentPlayerName (Player player){
        if(player == null)System.out.println(">>>>>>>>>>>>>>> PLAYER IS NULL >>>>>>>>>>>>>>>>>>");
        if (player.equals(Player.X))
        {
            if (playerOptions.getPlayer1Symbol().toLowerCase().equals("x"))
            {
                currentPlayerName.setText(playerOptions.getPlayer1Name());
            }
            else
            {
                currentPlayerName.setText(playerOptions.getPlayer2Name());
            }
        }
        else
        {
            if (playerOptions.getPlayer1Symbol().toLowerCase().equals("o"))
            {
                currentPlayerName.setText(playerOptions.getPlayer1Name());
            }
            else
            {
                currentPlayerName.setText(playerOptions.getPlayer2Name());
            }
        }
    }

    //moves current player into the square with (r,c) coordinates
    private void placeCurrentPlayer(int r, int c) {
        System.out.println("[4] Inside placeCurrentPlayer..");
        state.movePlayer(currentPlayer, r, c); //update state obj
        gameBoard[r][c].setPlayer(currentPlayer); //update board

        GameFinisher gf = state.winChecker();
        if(gf!=null){
            System.out.println("GameFinisher gf returned a finisher!");
            endGame(gf);
        }
        else
            switchPlayers();
    }

    //Determines if game ended due to draw or winning player
    private void endGame(GameFinisher finisher){
        turnView.setVisibility(View.GONE);
        if(finisher.isDraw()){
            tiedGames++;
            System.out.println("CATS Game!");
            gameTieView.setVisibility(View.VISIBLE);
            System.out.println("00000it is a draw, inside the draw method");

            if (playerOCurrentStreak > playerOBestStreak)
            {
                playerOBestStreak = playerOCurrentStreak;
                playerOCurrentStreak = 0;
            }
            if (playerXCurrentStreak > playerXBestStreak)
            {
                playerXBestStreak = playerXCurrentStreak;
                playerXCurrentStreak = 0;
            }
            lastWon = "";

        }
        else{ //the current winner must have won

            //this checks if x was the last one that won, and if so, it adds it to its streak,
            // it means that the streak of o ended, so it checks if its a new best streak or not
            if (lastWon.equals("x")){
                playerXCurrentStreak++;
                if (playerOCurrentStreak > playerOBestStreak){
                    playerOBestStreak = playerOCurrentStreak;
                    playerOCurrentStreak = 0;
                }
            }

            if (lastWon.equals("o")){
                playerOCurrentStreak++;
                if (playerXCurrentStreak > playerXBestStreak){
                    playerXBestStreak = playerXCurrentStreak;
                    playerXCurrentStreak = 0;
                }
            }

            if (lastWon.equals(""))
            {
                if (currentPlayer == Player.X)
                {
                    playerXCurrentStreak++;
                }
                if (currentPlayer == Player.O)
                {
                    playerOCurrentStreak++;
                }
            }

            if (currentPlayer == Player.X){
                playerXwins++;
                lastWon = "x";
            }
            if (currentPlayer == Player.O){
                playerOwins++;
                lastWon = "o";
            }


            System.out.println("Player " + currentPlayer + " won. Congratualations!");
            gameWinView.setVisibility(View.VISIBLE);
            Integer[][] winningCells = possiblities.get(finisher.getGameEnder());
            for(int i = 0; i < winningCells.length; i++){
                gameBoard[winningCells[i][0]][winningCells[i][1]].makeWinningSquare();
            }

            updateBoardViewWithPlayer(winImage, finisher.getPlayer());
        }

        isGameOver = true;
    }

    public void updateModeDisplay(){
         turnView.setVisibility(View.VISIBLE);
         gameWinView.setVisibility(View.GONE);
         gameTieView.setVisibility(View.GONE);
    }

    public void switchPlayers(){
        currentPlayer = PlayerSetup.getOpponent(currentPlayer);
    }

    public int getPlayerXCurrentStreak() {
        return playerXCurrentStreak;
    }

    public void setPlayerXCurrentStreak(int currentStreak){
        this.playerXCurrentStreak= currentStreak;
    }

    public int getPlayerOCurrentStreak() {
        return playerOCurrentStreak;
    }

    public void setPlayerOCurrentStreak(int currentStreak){
        this.playerOCurrentStreak = currentStreak;
    }

    public int getPlayerXBestStreak() {
        return playerXBestStreak;
    }

    public int getPlayerOBestStreak() {
        return playerOBestStreak;
    }

    public int getPlayerXMinimumMoves() {
        return playerXMinimumMoves;
    }

    public int getPlayerOMinimumMoves() {
        return playerOMinimumMoves;
    }

    public int getPlayerXwins () { return playerXwins;}

    public int getPlayerOwins () {return playerOwins;}

    public int getTiedGames() {
        return tiedGames;
    }

    public String getLastWon() {
        return lastWon;
    }

    public void setLastWon(String lastWon) {
        this.lastWon = lastWon;
    }

}
