package model;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import gameboard.Board;
import static gameboard.Board.ROWS;
import static gameboard.Board.COLUMNS;

/**
 * Created by Abraham on 4/5/14.
 */
public class MedAIComponent extends AIComponent {

    public MedAIComponent(GameState currentState, Player cpuPlayer){
        super(currentState, cpuPlayer);
        System.out.println("Inside MedAIComponent constructor");
    }

    public Point runAlgorithm(){
        System.out.println("Inside runAlgorithm");

        //Algorithm SETUP......
        /*************************************************************************************/
        Player[][] currentBoard = currentState.getBoard();  //entire board
        Player[][] cpuPositions;    //cpu player positions on board
        Player[][] humanPts;        //human player positions on the board
        Player cpToken;             //cpu token
        Player humanToken;          //human token

        Point pointToReturn;        //The point to return from the algorithm
        //This is the move the computer will make


        //Determine which token the Computer is...
        if(cpuPlayer.equals(Player.X)){
            System.out.println("CPU Player is X!");
            cpToken = Player.X;
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
            cpToken = Player.O;
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

        //Now we get all available spots to move... spots equal to null -- EMPTY
        //Store each spot as a Point with (x,y) values in the ArrayList freeSpots!

        //Create the arraylist
        ArrayList<Point> freeSpots = new ArrayList<Point>();

        System.out.println("About to iterate and find empty spots!");
        //iterate through the current board state and collect all empty spots (add them freeSpots)
        for (int i = 0; i < ROWS; i++) { //x
            System.out.println("Inside i:");
            for (int j = 0; j < COLUMNS; j++) { //y
                System.out.println("Inside j:");
                if((currentBoard[i][j] == null)){
                    System.out.println("Inside if stmt of for loops");
                    Point p = new Point(i, j);
                    freeSpots.add(p);
                }
            }

            System.out.println("FINISHED FINDING freeSpots!");
        }

        System.out.println("freeSpots SIZE: " + freeSpots.size());

        /***************************************************************************************/
        //PERFORMING ACTUAL ALGORITHM (5 STEPS PROCESS)

        //After we get the list of free spots (points), we need to determine which one to choose for our move
        //Our primary choice should be the winning move! ... so check if this spot exists first
        //Get the computer player's locations of all his current tokens using helper method getPointsForPlayer()

        System.out.println("About to get cpu player positions");
        ArrayList<Point> CPUPts = getPointsForPlayer(cpuPositions, cpToken); //returns array of points for CPU tokens

        System.out.println("About to get human player positions");
        ArrayList<Point> HUMANPts = getPointsForPlayer(humanPts, humanToken);

        // 1. Add each empty point from the freeSpots array to cpuPositions one by one and check if the new collection of
        //    cpuPositions results in a WIN. If don't proceed to step 2.

        pointToReturn = winningPositionExists(freeSpots, CPUPts);
        if(pointToReturn != null)
            return pointToReturn;

        //2. Check for block -- another words, is there a position that human player could win, if so, take that spot
        //If spot does not exist, proceed to step 3.

        pointToReturn = winningPositionExists(freeSpots, HUMANPts);
        if(pointToReturn != null)
            return pointToReturn;

        //3. Check for corner. If not exist, proceed to step 4

        pointToReturn = getCornerThatExists(freeSpots);
        if(pointToReturn != null)
            return pointToReturn;

        //4. Check for center. If not exist, proceed to step 5

        pointToReturn = getCenterIfExists(freeSpots);
        if(pointToReturn != null)
            return pointToReturn;

        //5. Check for side. (EITHER THIS CONDITION OR ONE FROM ABOVE SHOULD HAVE BEEN TRUE BY THIS POINT!!)

        pointToReturn = getSideIfExists(freeSpots);
        if(pointToReturn != null)
            return pointToReturn;

        // RETURN NULL IF NONE OF THE CONDITIONS ABOVE WERE TRUE -- IT IS AN ERROR IF THIS ALGORITHM RETURNS NULL.
        //IN BE THE CASE, THAT THE GAME IS A DRAW, AND THIS ALGORITHM SHOULD HAVE NEVER BEEN INVOKED IN THE FIRST PLACE!
        return null;

    }

    public ArrayList<Point> getPointsForPlayer(Player[][] spots, Player token){

        System.out.println("Token being checked for: " + token.toString());
        System.out.println("Inside getPointsForPlayer");
        System.out.println("---->>>>>>>>>Spots SIZE: " + spots.length + " by " + spots[0].length);
        ArrayList<Point> pointsForPlayer = new ArrayList<Point>();

        System.out.println("Player's spots: ");
        System.out.println(" | " + spots[0][0] + " | " + spots[0][1] + " | " + spots[0][2] + " | ");
        System.out.println(" | " + spots[1][0] + " | " + spots[1][1] + " | " + spots[1][2] + " | ");
        System.out.println(" | " + spots[2][0] + " | " + spots[2][1] + " | " + spots[2][2] + " | ");


        for (int i = 0; i < ROWS; i++) { //x
            System.out.println("I: " + i);
            for (int j = 0; j < COLUMNS; j++) { //y
                System.out.println("J: " + j);
                if(!(spots[i][j] == null)){
                    System.out.println("This point matches this players");
                    Point pt = new Point(i, j);
                    pointsForPlayer.add(pt);
                }
                else
                    System.out.println("NO MATCHES!");
            }
        }

        return pointsForPlayer;
    }

    public boolean pointsEqualAWin(ArrayList<Point> pts){

        System.out.println("Inside pointsEqualAWin");
        //Compare the current CPU array list of point to the winning combination arrays, and check
        //for a match
        for(Map.Entry<GameEnder, Integer[][]> entry : Board.possiblities.entrySet()){
            System.out.println("Inside the MediumAIComponent's winChecker");
            GameEnder thisGameEnder = entry.getKey();
            Integer[][]combo = entry.getValue(); // a winning combination

            //need to check if this winning combination exists as part of the CPU's positions
            // (compare two arrays)
            if(comboExistInCPUPts(combo, pts)){
                return true;
            }
        }
        return false;
    }

    public boolean comboExistInCPUPts(Integer[][] combo, ArrayList<Point> pts){
        System.out.println("Inside comboExistInCPUPts");

        //Generate equivalent point for winning combination array (combo)
        Point p1 = new Point(combo[0][0], combo[0][1]);
        Point p2 = new Point(combo[1][0], combo[1][1]);
        Point p3 = new Point(combo[2][0], combo[2][1]); //These point represent the winning positions
        //for the combination passed in (combo)

        //Check if these point exist pts (the CPU's token positions)
        if(pts.contains(p1) && pts.contains(p2) && pts.contains(p3))
            return true;

        return false;
    }

    public Point winningPositionExists(ArrayList<Point> freeSpots, ArrayList<Point> playerPts){
        System.out.println("Inside winningPositionExists");

        for(Point p : freeSpots){
            playerPts.add(p);

            if(pointsEqualAWin(playerPts)){
                Point pointToReturn = p;
                return pointToReturn;
            }
            else{
                playerPts.remove(p);
                //continue;
            }
        }
        return null;
    }

    public Point getCornerThatExists(ArrayList<Point> freeSpots){
        System.out.println("Inside getcornerThatExists");

        //Gather CORNER points for the matrix grid (board)
        Point c1 = new Point(0, 0);  // TOP-LEFT CORNER
        Point c2 = new Point(0, COLUMNS-1); //TOP-RIGHT
        Point c3 = new Point(ROWS - 1, 0); //BOTTOM-LEFT
        Point c4 = new Point(ROWS -1, COLUMNS -1); //BOTTOM-RIGHT

        ArrayList<Point> emptyCorners = new ArrayList<Point>();
        if (freeSpots.contains(c1))
            emptyCorners.add(c1);
        if (freeSpots.contains(c2))
            emptyCorners.add(c2);
        if (freeSpots.contains(c3))
            emptyCorners.add(c3);
        if (freeSpots.contains(c4))
            emptyCorners.add(c4);

        int size = emptyCorners.size();

        //Randomly choose a corner spot
        Random r = new Random();
        int index = r.nextInt(size - 0) + 0;

        if(freeSpots.get(index)!=null)
            return freeSpots.get(index);

        return null;
    }

    //method only returns center point if exist for MATRIX OF ODD NO. X ODD NO.
    public Point getCenterIfExists(ArrayList<Point> freeSpots){
        System.out.println("Inside getCeterIfExists");

        //Obtain the points for any center for a odd x odd dimension matrix

        /*
        int x = ROWS / 2 + 1;
        int y = COLUMNS /2 + 1;
        */
        int x = 1;
        int y = 1;
        Point cPoint = new Point(x, y);
        if (freeSpots.contains(cPoint)){
            return cPoint;
        }
        return null;
    }

    public Point getSideIfExists(ArrayList<Point> freeSpots){
        System.out.println("Inside getSideIfExists");

        //obtain the points for the sides of a odd dimension number matrix
        ArrayList<Point> sidePoints = new ArrayList<Point>();
        /**
         * [[0,0][0,1][0,2]
         *  [1,0][1,1][1,2]
         *  [2,0][2,1][2,2]]
         */

        for (Point p : freeSpots){
            if(p.getX() == 0 && p.getY() == 1){ //gets top side
                Point ptTS = new Point(p.getX(), p.getY());
                sidePoints.add(ptTS);
            }
            if(p.getX() == 2 && p.getY() == 1){ //get bottom side
                Point ptBS = new Point(p.getX(), p.getY());
                sidePoints.add(ptBS);
            }
            if(p.getX()==1 && p.getY() == 0){ //gets left side
                Point ptLS = new Point(p.getX(), p.getY());
                sidePoints.add(ptLS);
            }
            if(p.getX()== 1 && p.getY()==2){ //get right side
                Point ptRS = new Point(p.getX(), p.getY());
                sidePoints.add(ptRS);
            }

        }

        //Top side's side points that exist:
        /*
        for (Point p : freeSpots){
            if(p.getX() == 0 && p.getY() != 0 && p.getY() != COLUMNS - 1){ //gets top side
                Point ptTS = new Point(p.getX(), p.getY());
                sidePoints.add(ptTS);
            }
            if(p.getX() == ROWS - 1 && p.getY() != 0 && p.getY() != COLUMNS - 1){ //get bottom side
                Point ptBS = new Point(p.getX(), p.getY());
                sidePoints.add(ptBS);
            }
            if(p.getY() == 0 && p.getX() != 0 && p.getX() != ROWS - 1){ //gets left side
                Point ptLS = new Point(p.getX(), p.getY());
                sidePoints.add(ptLS);
            }
            if(p.getY() == COLUMNS - 1 && p.getX() != 0 && p.getX() != ROWS - 1){ //get right side
                Point ptRS = new Point(p.getX(), p.getY());
                sidePoints.add(ptRS);
            }

        }
        */

        //Randomly choose a side spot:
        int size = sidePoints.size();

        //Randomly choose a corner spot
        Random r = new Random();
        int index = r.nextInt(size-0) + 0;

        if(freeSpots.get(index)!=null)
            return freeSpots.get(index);

        return null;
    }
}