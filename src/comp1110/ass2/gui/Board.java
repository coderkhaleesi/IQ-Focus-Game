package comp1110.ass2.gui;

import comp1110.ass2.FocusGame;
import comp1110.ass2.Objective;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.DropShadow;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.text.*;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.control.Slider;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Board extends Application {

    private static final int[][] tileMeasure = {{3,2},{4,2},{4,2},{3,2},{3,2},{3,1},{3,2},{3,3},{2,2},{4,2}};

    /* the state of the tiles */
    String[] tileState = new String[10];   //  all off screen to begin with

    /* marker for unplaced tiles */
    public static final String NOT_PLACED = "";

    /* Define a drop shadow effect that we will apply to tiles */
    private static DropShadow dropShadow;

    /* message on completion */
    private final Text completionText = new Text("Well done!");

    /* the difficulty slider */
    private final Slider difficulty = new Slider();

    //Author: Samuel Hollis Brown
    private static final int SQUARE_SIZE = 50;
    private static final int GRID_WIDTH = 9*SQUARE_SIZE;
    private static final int GRID_HEIGHT = 5*SQUARE_SIZE;
    private static final int BOARD_HEIGHT = 463;

    private static final int OBJECTIVE_HEIGHT = 150;
    private static final int OBJECTIVE_WIDTH = 150;
    private static final int MARGIN_X = 10;
    private static final int MARGIN_Y = 30;
    private static final int BOARD_X = 20+4*SQUARE_SIZE; // 4 x Squaresize + 20
    private static final int BOARD_Y = 30;

    private static final int VIEWER_WIDTH = 933; //This was given
    private static final int VIEWER_HEIGHT = 700;//This was given

    private static final int PLAY_AREA_Y = BOARD_Y;
    private static final int PLAY_AREA_X = BOARD_X;

    private static final long ROTATION_THRESHOLD = 200; // This makes no difference to the actual scroll


    private final Group root = new Group();
    private final Group gtiles = new Group();
    private final Group controls = new Group();
    private final Group board = new Group();
    private final Group obj = new Group();
    private final Group buttons = new Group();

    private static final String URI_BASE = "assets/";

    //Author: Tanya Dixit
    class GTile extends ImageView {
        int tileID;

        /**
         * Construct a particular playing tile
         *
         * @param tile The letter representing the tile to be created.
         */
        GTile(char tile) {
            if (tile > 'j' || tile < 'a') {
                throw new IllegalArgumentException("Bad tile: \"" + tile + "\"");
            }
            this.tileID = tile - 'a';

            setFitWidth(tileMeasure[this.tileID][0] * SQUARE_SIZE);
            setFitHeight(tileMeasure[this.tileID][1]*SQUARE_SIZE);
            setEffect(dropShadow);
        }

        /**
         * Construct a playing tile, which is placed on the board at the start of the game,
         * as a part of some challenges
         *
         * @param tile  The letter representing the tile to be created.
         * @param orientation   The integer representation of the tile to be constructed
         */
        GTile(char tile, int orientation) {
            if (tile > 'j' || tile < 'a') {
                throw new IllegalArgumentException("Bad tile: \"" + tile + "\"");
            }
            this.tileID = tile - 'a';
            int length = 4;

            if (orientation%2 == 0) {
                setFitHeight(tileMeasure[this.tileID][1]*SQUARE_SIZE);
                setFitWidth(tileMeasure[this.tileID][0] * SQUARE_SIZE);
            }
            else {
                setFitHeight(tileMeasure[this.tileID][0] * SQUARE_SIZE);
                setFitWidth(tileMeasure[this.tileID][1]*SQUARE_SIZE);
            }
            setImage(new Image(Board.class.getResource(URI_BASE + tile + "-" + (char)(orientation+'0') + ".png").toString()));
            setEffect(dropShadow);
        }

        /**
         * A constructor used to build the objective tile.
         *
         * @param tile The tile to be displayed (one of 80 objectives)
         * @param x    The x position of the tile
         * @param y    The y position of the tile
         */
        GTile(int tile, int x, int y) {
            if (!(tile <= 80 && tile >= 1)) {
                throw new IllegalArgumentException("Bad tile: \"" + tile + "\"");
            }

            String t = String.format("%02d", tile);
            setImage(new Image(Board.class.getResource(URI_BASE + t + ".png").toString()));
            this.tileID = tile;
            setFitHeight(OBJECTIVE_HEIGHT);
            setFitWidth(OBJECTIVE_WIDTH);
            setEffect(dropShadow);

            setLayoutX(x);
            setLayoutY(y);
        }
    }

    /*
     * Adding drag and drop functionality
     * Author: Tanya Dixit & Samuel Hollis Brown
     */
    class DraggableTile extends GTile {
        int homeX, homeY;           // the position in the window where
        // the tile should be when not on the board
        double mouseX, mouseY;      // the last known mouse positions (used when dragging)
        Image[] images = new Image[4];
        int orientation;    // 0=North... 3=West
        char tileId;
        long lastRotationTime = System.currentTimeMillis(); // only allow rotation every ROTATION_THRESHOLD (ms)
        // This caters for mice which send multiple scroll events per tick.

        /**
         * Construct a draggable tile
         *
         * @param tile The tile identifier ('a' - 'j')
         */
        DraggableTile(char tile) {
            super(tile);
            for (int i = 0; i < 4; i++) {
                char idx = (char) (i + '0');
                images[i] = new Image(Board.class.getResource(URI_BASE + tile + "-" + idx + ".png").toString());
            }
            setImage(images[0]);
            orientation = 0;
            tileID = tile-'a';
            tileState[tile - 'a'] = NOT_PLACED; // start out off board

            /*Setting starting positions for every tile*/
            if (tile >= 'a' && tile <= 'b'){
                homeX = MARGIN_X;
                homeY = MARGIN_Y + 4*(tile-'a')*SQUARE_SIZE;
            } else if (tile == 'c') {
                homeX = MARGIN_X;
                homeY = MARGIN_Y + 4*(tile-'a')*SQUARE_SIZE - SQUARE_SIZE;
            } else if (tile >= 'd' && tile <= 'f'){
                homeX = MARGIN_X+BOARD_X + GRID_WIDTH ;
                homeY = MARGIN_Y + 4*(tile-'d')*SQUARE_SIZE;;
            } else if (tile == 'g') {
                homeX = MARGIN_X+4*SQUARE_SIZE+60;
                homeY = MARGIN_Y + BOARD_HEIGHT + 50;
            } else if (tile == 'h') {
                homeX = MARGIN_X+2*4*SQUARE_SIZE+75;
                homeY = MARGIN_Y + BOARD_HEIGHT + 50;
            } else if (tile == 'i') {
                homeX = MARGIN_X;
                homeY = MARGIN_Y + BOARD_HEIGHT + 50;
            } else if (tile == 'j') {
                homeX = MARGIN_X + 4*3*SQUARE_SIZE + 75;
                homeY = MARGIN_Y + BOARD_HEIGHT + 50;
            }
            setLayoutX(homeX);
            setLayoutY(homeY);

            /* event handlers */
            setOnScroll(event -> {            // scroll to change orientation
                if (System.currentTimeMillis() - lastRotationTime > ROTATION_THRESHOLD){
                    lastRotationTime = System.currentTimeMillis();
                    hideCompletion();
                    rotate();
                    event.consume();
                    checkCompletion();
                }
            });
            setOnMousePressed(event -> {      // mouse press indicates begin of drag
                mouseX = event.getSceneX();
                mouseY = event.getSceneY();
            });
            setOnMouseDragged(event -> {      // mouse is being dragged
                hideCompletion();
                toFront();
                double movementX = event.getSceneX() - mouseX;
                double movementY = event.getSceneY() - mouseY;
                setLayoutX(getLayoutX() + movementX);
                setLayoutY(getLayoutY() + movementY);
                mouseX = event.getSceneX();
                mouseY = event.getSceneY();
                event.consume();
            });
            setOnMouseReleased(event -> {     // drag is complete
                snapToGrid();
            });





            setOnKeyPressed(event -> { // This will be for the addTile function
                if (event.getCode() == KeyCode.P) {
                    placeTile(GRID_X, GRID_Y, orientation, tileId);
                    System.out.println("ENTER WAS PRESSED");
                    event.consume();

                }
            });
        }


        /** These are the global variables that are needed to make the placetile function: Shape x y orientation
         *
         *
         Author: Sam Hollis-Brown*/
        public int GRID_X = 0;
        public int GRID_Y = 0;




        private void placeTile(int GRID_X, int GRID_Y, int orientation, char tileId) {
            String a = String.valueOf(tileId);
            String x = String.valueOf(GRID_X);
            String y = String.valueOf(GRID_Y);
            String o = String.valueOf(orientation);
            String tileString = a + x + y + o;
            System.out.println(tileString);
            String boardTiles = "";
            boardTiles = boardTiles + tileString;
        }

        /**
         * Snap the tile to the nearest grid position (if it is over the grid)
         *
         * Author: Samuel H-B
         */

        private void snapToGrid() {
            int A = PLAY_AREA_X;
            int B = PLAY_AREA_X + SQUARE_SIZE;
            int C = PLAY_AREA_X + (2 * SQUARE_SIZE);
            int D = PLAY_AREA_X + (3 * SQUARE_SIZE);
            int E = PLAY_AREA_X + (4 * SQUARE_SIZE);
            int F = PLAY_AREA_X + (5 * SQUARE_SIZE);
            int G = PLAY_AREA_X + (6 * SQUARE_SIZE);
            int H = PLAY_AREA_X + (7 * SQUARE_SIZE);
            int I = PLAY_AREA_X + (8 * SQUARE_SIZE);


            /* removed alreadyOccupied for now as it's not behaving properly */
            //if (alreadyOccupied()) { //or onBoard && alreadyOccupied
              //  snapToHome();
            if (onBoard()) {                 //&& (!alreadyOccupied())
//                if ((getLayoutX() < PLAY_AREA_X ) || (getLayoutX() > PLAY_AREA_X + (8 * SQUARE_SIZE + SQUARE_SIZE/2))) {
//                    setLayoutX(0);
//                } else
                if ((getLayoutX() >= PLAY_AREA_X ) && (getLayoutX() < (PLAY_AREA_X + SQUARE_SIZE/2))) {
                        setLayoutX(A);
                    GRID_X = 1;
                }
                else if ((getLayoutX() >= PLAY_AREA_X + SQUARE_SIZE/2) && (getLayoutX() < PLAY_AREA_X + (SQUARE_SIZE + SQUARE_SIZE/2))) {
                        setLayoutX(B);
                    GRID_X = 2;
                }
                else if ((getLayoutX() >= PLAY_AREA_X + (SQUARE_SIZE + SQUARE_SIZE/2)) && (getLayoutX() < PLAY_AREA_X + (2 * SQUARE_SIZE + SQUARE_SIZE/2))) {
                        setLayoutX(C);
                    GRID_X = 3;
                }
                else if ((getLayoutX() >= PLAY_AREA_X + (2 * SQUARE_SIZE + SQUARE_SIZE/2)) && (getLayoutX() < PLAY_AREA_X + (3 * SQUARE_SIZE + SQUARE_SIZE/2))) {
                        setLayoutX(D);
                    GRID_X = 4;
                }
                else if ((getLayoutX() >= PLAY_AREA_X + (3 * SQUARE_SIZE + SQUARE_SIZE/2)) && (getLayoutX() < PLAY_AREA_X + (4 * SQUARE_SIZE + SQUARE_SIZE/2))) {
                        setLayoutX(E);
                    GRID_X = 5;
                }
                else if ((getLayoutX() >= PLAY_AREA_X + (4 * SQUARE_SIZE + SQUARE_SIZE/2)) && (getLayoutX() < PLAY_AREA_X + (5 * SQUARE_SIZE + SQUARE_SIZE/2))) {
                        setLayoutX(F);
                    GRID_X = 6;
                }
                else if ((getLayoutX() >= PLAY_AREA_X + (5 * SQUARE_SIZE + SQUARE_SIZE/2)) && (getLayoutX() < PLAY_AREA_X + (6 * SQUARE_SIZE + SQUARE_SIZE/2))) {
                        setLayoutX(G);
                    GRID_X = 7;
                }
                else if ((getLayoutX() >= PLAY_AREA_X + (6 * SQUARE_SIZE + SQUARE_SIZE/2)) && (getLayoutX() < PLAY_AREA_X + (7 * SQUARE_SIZE + SQUARE_SIZE/2))) {
                        setLayoutX(H);
                    GRID_X = 8;
                }
                else if ((getLayoutX() >= PLAY_AREA_X + (7 * SQUARE_SIZE + SQUARE_SIZE/2)) && (getLayoutX() < PLAY_AREA_X + (8 * SQUARE_SIZE + SQUARE_SIZE/2))) {
                        setLayoutX(I);
                    GRID_X = 9;
                }

                /// NOW FOR THE Y SET

                if ((getLayoutY() >= PLAY_AREA_Y ) && (getLayoutY() < (PLAY_AREA_Y + SQUARE_SIZE/2))) {
                    setLayoutY(PLAY_AREA_Y); // A
                    GRID_Y = 1;
                }
                else if ((getLayoutY() >= PLAY_AREA_Y + SQUARE_SIZE/2 ) && (getLayoutY() < PLAY_AREA_Y + (SQUARE_SIZE + SQUARE_SIZE/2))) {
                    setLayoutY(PLAY_AREA_Y + SQUARE_SIZE); // B
                    GRID_Y = 2;
                }
                else if ((getLayoutY() >= PLAY_AREA_Y + (SQUARE_SIZE + SQUARE_SIZE/2)) && (getLayoutY() < PLAY_AREA_Y + (2 * SQUARE_SIZE + SQUARE_SIZE/2))) {
                    setLayoutY(PLAY_AREA_Y + (2 * SQUARE_SIZE)); // C
                    GRID_Y = 3;
                }
                else if ((getLayoutY() >= PLAY_AREA_Y + (2 * SQUARE_SIZE + SQUARE_SIZE/2)) && (getLayoutY() < PLAY_AREA_Y + (3 * SQUARE_SIZE + SQUARE_SIZE/2))) {
                    setLayoutY(PLAY_AREA_Y + (3 * SQUARE_SIZE)); // D
                    GRID_Y = 4;
                }
                else if ((getLayoutY() >= PLAY_AREA_Y + (3 * SQUARE_SIZE + SQUARE_SIZE/2)) && (getLayoutY() < PLAY_AREA_Y + (4 * SQUARE_SIZE + SQUARE_SIZE/2))) {
                    setLayoutY(PLAY_AREA_Y + (4 * SQUARE_SIZE)); // E
                    GRID_Y = 5;
                }



                // MAKING THE NULL SPACES UNPLACABLE
                //Review block 3
                // Author: Samuel H-B
                 // leftmost null space
                if (getLayoutX() >= (BOARD_X - 10) && getLayoutX() < (BOARD_X + SQUARE_SIZE) && getLayoutY() > (PLAY_AREA_Y + (SQUARE_SIZE * 4)) && getLayoutY() < (PLAY_AREA_Y + GRID_HEIGHT)) {
                    snapToHome();
                } else if (orientation % 2 == 0) {
                        if (getLayoutX() >= (BOARD_X - 10) && getLayoutX() < (BOARD_X + SQUARE_SIZE) && (getLayoutY() + tileMeasure[tileID][1] * SQUARE_SIZE) >= (BOARD_Y + (SQUARE_SIZE * 4) + 20)) {
                            snapToHome();
                        }
                } else if (orientation % 2 == 1) {
                        if (getLayoutX() >= (BOARD_X - 10) && getLayoutX() < (BOARD_X + SQUARE_SIZE) && (getLayoutY() + tileMeasure[tileID][0] * SQUARE_SIZE) >= (BOARD_Y + (SQUARE_SIZE * 4) + 20)) {
                            snapToHome();
                        }
                }

                // right null space
//                if (getLayoutX() >= (BOARD_X + (SQUARE_SIZE * 8)) && getLayoutX() < (BOARD_X + (SQUARE_SIZE * 9)) && getLayoutY() >= (PLAY_AREA_Y + (SQUARE_SIZE * 4)) && getLayoutY() < (PLAY_AREA_Y + (SQUARE_SIZE * 5))) {
//                    snapToHome();
//                }
                // First the y offset then the x
                if ( onBoard() && orientation % 2 == 0) {
                        if (getLayoutX() >= (BOARD_X + (SQUARE_SIZE * 8)) && getLayoutX() < (BOARD_X + (SQUARE_SIZE * 9)) && (getLayoutY() + (tileMeasure[tileID][1] * SQUARE_SIZE) >= (BOARD_Y + (SQUARE_SIZE * 4)))) {
                            snapToHome();
                        }
                        if ((getLayoutX() + (tileMeasure[tileID][0] * SQUARE_SIZE)) >= (BOARD_X + GRID_WIDTH + 10)) {
                            snapToHome();
                        }
                }
                if (onBoard() && orientation % 2 == 1) {
                        if (getLayoutX() > (BOARD_X + (SQUARE_SIZE * 8)) && getLayoutX() < (BOARD_X + GRID_WIDTH) && (getLayoutY() + (tileMeasure[tileID][0] * SQUARE_SIZE) >= (BOARD_Y + (SQUARE_SIZE * 4)))) {
                            snapToHome();
                        }
                        if ((getLayoutX() + (tileMeasure[tileID][1] * SQUARE_SIZE)) >= (BOARD_X + GRID_WIDTH + 10)) {
                        snapToHome();
                        }
                }






                setPosition();
            }
            else {
                snapToHome();
            }
            checkCompletion();
        }



        /**
         * @return true if the tile is on the board
         */

        private boolean onBoard() { // Author: Sam Hollis-Brown Writing the check conditions for the tile to be within the borders of the grid

            if (getLayoutX() > (PLAY_AREA_X ) && (getLayoutX() < (PLAY_AREA_X + GRID_WIDTH))
                    && getLayoutY() > PLAY_AREA_Y  && (getLayoutY() < (PLAY_AREA_Y + GRID_HEIGHT))){

                    if (orientation%2 ==0) {
                        if (getLayoutX() + ((tileMeasure[tileID][0])*SQUARE_SIZE) <= (PLAY_AREA_X + GRID_WIDTH + 20)
                        && getLayoutY() + ((tileMeasure[tileID][1])*SQUARE_SIZE) <= (PLAY_AREA_Y + GRID_HEIGHT + 20)) {
                        return true;
                    }}
                    if (orientation%2 ==1) {
                        if (getLayoutX() + ((tileMeasure[tileID][1])*SQUARE_SIZE) <= (PLAY_AREA_X + GRID_WIDTH + 20)
                        && getLayoutY() + ((tileMeasure[tileID][0])*SQUARE_SIZE) <= (PLAY_AREA_Y + GRID_HEIGHT + 20)) {
                        return true;
                    }}
            }
            return false;
        }

        /**
         * a function to check whether the current destination cell
         * is already occupied by another tile
         *
         * @return true if the destination cell for the current tile
         * is already occupied, and false otherwise
         *
         * Author: Tanya Dixit
         */
        private boolean alreadyOccupied() {
            int x = (int) (getLayoutX() + (SQUARE_SIZE / 2) - PLAY_AREA_X) / SQUARE_SIZE;
            int y = (int) (getLayoutY() + (SQUARE_SIZE / 2) - PLAY_AREA_Y) / SQUARE_SIZE;


            int ctile = tileID;
            int cor = orientation;


            int idx1 = x;
            int idx2 = y;

            for (int i = 0; i < tileState.length; i++) {

                if (tileState[i].equals(NOT_PLACED))
                    continue;

                System.out.println(tileState[i]);
                int tIdx1 = Character.getNumericValue(tileState[i].charAt(0));
                int tIdx2 = Character.getNumericValue(tileState[i].charAt(1));
                int tOrientation = Character.getNumericValue(tileState[i].charAt(2));

                if (tOrientation%2 == 0) {

                    if ( ((idx1 == tIdx1) && (idx2 == tIdx2)) || ((idx1 == tIdx1 + tileMeasure[i][0] - 1) && (idx2 == tIdx2 + tileMeasure[i][1] - 1))) {
                        return true;
                    }

                } else if (tOrientation%2 == 1) {
                    if ( (x >= (tIdx1) && x <= (tIdx1 + tileMeasure[i][1])) && (y >= (tIdx2) && y <= (tIdx2 + tileMeasure[i][0])) )
                        return true;
                }
            }
            return false;
        }


        /**
         * Snap the tile to its home position (if it is not on the grid)
         * Author: Tanya Dixit
         */
        private void snapToHome() {
            setLayoutX(homeX);
            setLayoutY(homeY);
            setFitHeight(tileMeasure[this.tileID][1] * SQUARE_SIZE);
            setFitWidth(tileMeasure[this.tileID][0] * SQUARE_SIZE);
            setImage(images[0]);
            orientation = 0;
            tileState[tileID] = NOT_PLACED;
        }


        /**
         * Rotate the tile by 90 degrees and update any relevant state
         */
        private void rotate() {
            orientation = (orientation + 1) % 4;
            setImage(images[(orientation)]);
            setPreserveRatio(true);
            if (orientation%2 == 0 ) { //Author: Mahesh Gundubogula (Review Block 4)
                setFitWidth((tileMeasure[tileID][0])*SQUARE_SIZE);
                setFitHeight((tileMeasure[tileID][1])*SQUARE_SIZE);
            } else if (orientation%2 == 1 ) {
                setFitHeight((tileMeasure[tileID][0])*SQUARE_SIZE);
                setFitWidth((tileMeasure[tileID][1])*SQUARE_SIZE);
            }
            toFront();
            setPosition();
        }


        /**
         * Determine the grid-position of the origin of the tile
         * or 'NOT_PLACED' if it is off the grid, taking into account its rotation.
         *
         * Author: Tanya Dixit
         */
        private void setPosition() {
            int x = (int) (getLayoutX() - PLAY_AREA_X) / SQUARE_SIZE;
            int y = (int) (getLayoutY() - PLAY_AREA_Y) / SQUARE_SIZE;
            if (x < 0)
                tileState[tileID] = NOT_PLACED;
            else {
                String val = Integer.toString(x)+Integer.toString(y)+orientation;
                tileState[tileID] = val;
            }
        }

        /**
         * Check game completion and update status
         * Author: Tanya Dixit
         */
        private void checkCompletion() {

            for (int i = 0; i < tileState.length; i++) {
                if (tileState[i] == NOT_PLACED)
                    return;
            }
            showCompletion();
        }

        /**
         * @return the mask placement represented as a string
         * Author: Tanya Dixit
         */
        public String toString() {
            return "" + tileState[tileID];
        }
    }


    //Author: Tanya Dixit
    private void setUpHandlers(Scene scene) {
        /* create handlers for key press and release events */
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.M) {
                event.consume();
            } else if (event.getCode() == KeyCode.Q) {
                Platform.exit();
                event.consume();
            } else if (event.getCode() == KeyCode.SLASH) {
                gtiles.setOpacity(0);
                event.consume();
            }
        });
        scene.setOnKeyReleased(event -> {

            if (event.getCode() == KeyCode.SLASH) {
                gtiles.setOpacity(1.0);
                event.consume();
            }
        });
    }

    /**
     * Show the completion message
     */
    private void showCompletion() {
        completionText.toFront();
        completionText.setOpacity(1);
    }


    /**
     * Hide the completion message
     */
    private void hideCompletion() {
        completionText.toBack();
        completionText.setOpacity(0);
    }


    /**
     * Create the message to be displayed when the player completes the puzzle.
     * Author: Tanya Dixit
     */
    private void makeCompletion() {
        completionText.setFill(Color.BLACK);
        completionText.setEffect(dropShadow);
        completionText.setCache(true);
        completionText.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 80));
        completionText.setLayoutX(40);
        completionText.setLayoutY(MARGIN_Y+GRID_HEIGHT+5*SQUARE_SIZE);
        completionText.setTextAlignment(TextAlignment.CENTER);
        root.getChildren().add(completionText);
    }

    //Author: Tanya Dixit
    private void makeObjective(String objectivePassed) {

        //clear if already created objective
        obj.getChildren().clear();
        double opacity = 0.2;
        String objective = objectivePassed;
        char[] objectiveArr = objective.toCharArray();

        Color[] c = new Color[9];

        for (int i = 0; i < objectiveArr.length; i++) {
            if (objectiveArr[i] == 'R')
                c[i] = Color.RED;
            else if (objectiveArr[i] == 'G')
                c[i] = Color.GREEN;
            else if (objectiveArr[i] == 'B')
                c[i] = Color.BLUE;
            else if (objectiveArr[i] == 'W')
                c[i] = Color.ANTIQUEWHITE;
        }

        /*
         * 9 rectangles on the grid to color the grid according to our objective
         */
        Rectangle o1 = new Rectangle(BOARD_X+3*SQUARE_SIZE, BOARD_Y+SQUARE_SIZE,SQUARE_SIZE,SQUARE_SIZE);
        Rectangle o2 = new Rectangle(BOARD_X+4*SQUARE_SIZE, BOARD_Y+SQUARE_SIZE,SQUARE_SIZE,SQUARE_SIZE);
        Rectangle o3 = new Rectangle(BOARD_X+5*SQUARE_SIZE, BOARD_Y+SQUARE_SIZE,SQUARE_SIZE,SQUARE_SIZE);
        Rectangle o4 = new Rectangle(BOARD_X+3*SQUARE_SIZE, BOARD_Y+2*SQUARE_SIZE,SQUARE_SIZE,SQUARE_SIZE);
        Rectangle o5 = new Rectangle(BOARD_X+4*SQUARE_SIZE, BOARD_Y+2*SQUARE_SIZE,SQUARE_SIZE,SQUARE_SIZE);
        Rectangle o6 = new Rectangle(BOARD_X+5*SQUARE_SIZE, BOARD_Y+2*SQUARE_SIZE,SQUARE_SIZE,SQUARE_SIZE);
        Rectangle o7 = new Rectangle(BOARD_X+3*SQUARE_SIZE, BOARD_Y+3*SQUARE_SIZE,SQUARE_SIZE,SQUARE_SIZE);
        Rectangle o8 = new Rectangle(BOARD_X+4*SQUARE_SIZE, BOARD_Y+3*SQUARE_SIZE,SQUARE_SIZE,SQUARE_SIZE);
        Rectangle o9 = new Rectangle(BOARD_X+5*SQUARE_SIZE, BOARD_Y+3*SQUARE_SIZE,SQUARE_SIZE,SQUARE_SIZE);

        o1.setFill(c[0]);
        o2.setFill(c[1]);
        o3.setFill(c[2]);
        o4.setFill(c[3]);
        o5.setFill(c[4]);
        o6.setFill(c[5]);
        o7.setFill(c[6]);
        o8.setFill(c[7]);
        o9.setFill(c[8]);

        o1.setOpacity(opacity);
        o2.setOpacity(opacity);
        o3.setOpacity(opacity);
        o4.setOpacity(opacity);
        o5.setOpacity(opacity);
        o6.setOpacity(opacity);
        o7.setOpacity(opacity);
        o8.setOpacity(opacity);
        o9.setOpacity(opacity);

        obj.getChildren().addAll(o1,o2,o3,o4,o5,o6,o7,o8,o9);
    }

    private void background() {
        Image image1 = new Image(Board.class.getResource("assets/background.jpg").toString());
        ImageView bk = new ImageView(image1);
        board.getChildren().add(bk);
        bk.setX(0);
        bk.setY(0);
        bk.setFitWidth(VIEWER_WIDTH);
        bk.setFitHeight(VIEWER_HEIGHT);
        board.toBack();
    }


    private void makeGrid() { // Author: Samuel Hollis-Brown

        /* two black rectangles to show null spaces */
        Rectangle outline = new Rectangle(BOARD_X, BOARD_Y, GRID_WIDTH, GRID_HEIGHT);
        board.getChildren().add(outline);

        Rectangle nullspace1 = new Rectangle(BOARD_X, 30+4*SQUARE_SIZE, 50, 50);
        board.getChildren().add(nullspace1);
        Rectangle nullspace2 = new Rectangle((BOARD_X + GRID_WIDTH - 50), 30+4*SQUARE_SIZE, 50, 50);
        board.getChildren().add(nullspace2);
        outline.setFill(Color.TRANSPARENT);
        outline.setStroke(Color.BLACK);

        /* Lines to create the vertical lines for the grid */
        Line l1 = new Line((BOARD_X + SQUARE_SIZE), 30, (BOARD_X + SQUARE_SIZE), 30+GRID_HEIGHT);
        board.getChildren().add(l1);
        Line l2 = new Line((BOARD_X + 2*SQUARE_SIZE), 30, (BOARD_X + 2*SQUARE_SIZE), 30+GRID_HEIGHT);
        board.getChildren().add(l2);
        Line l3 = new Line((BOARD_X + 3*SQUARE_SIZE), 30, (BOARD_X + 3*SQUARE_SIZE), 30+GRID_HEIGHT);
        board.getChildren().add(l3);
        Line l4 = new Line((BOARD_X + 4*SQUARE_SIZE), 30, (BOARD_X + 4*SQUARE_SIZE), 30+GRID_HEIGHT);
        board.getChildren().add(l4);
        Line l5 = new Line((BOARD_X + 5*SQUARE_SIZE), 30, (BOARD_X + 5*SQUARE_SIZE), 30+GRID_HEIGHT);
        board.getChildren().add(l5);
        Line l6 = new Line((BOARD_X + 6*SQUARE_SIZE), 30, (BOARD_X + 6*SQUARE_SIZE), 30+GRID_HEIGHT);
        board.getChildren().add(l6);
        Line l7 = new Line((BOARD_X + 7*SQUARE_SIZE), 30, (BOARD_X + 7*SQUARE_SIZE), 30+GRID_HEIGHT);
        board.getChildren().add(l7);
        Line l8 = new Line((BOARD_X + 8*SQUARE_SIZE), 30, (BOARD_X + 8*SQUARE_SIZE), 30+GRID_HEIGHT);
        board.getChildren().add(l8);

        /* Lines to create the horizontal lines for the grid */
        Line y1 = new Line(BOARD_X, 30+SQUARE_SIZE, (BOARD_X + GRID_WIDTH), 30+SQUARE_SIZE);
        board.getChildren().add(y1);
        Line y2 = new Line(BOARD_X, 30+2*SQUARE_SIZE, (BOARD_X + GRID_WIDTH), 30+2*SQUARE_SIZE);
        board.getChildren().add(y2);
        Line y3 = new Line(BOARD_X, 30+3*SQUARE_SIZE, (BOARD_X + GRID_WIDTH), 30+3*SQUARE_SIZE);
        board.getChildren().add(y3);
        Line y4 = new Line(BOARD_X, 30+4*SQUARE_SIZE, (BOARD_X + GRID_WIDTH), 30+4*SQUARE_SIZE);
        board.getChildren().add(y4);
        board.toBack();
    }

    /* Make tiles at the start of a new game */
    private void makeTiles() {
        gtiles.getChildren().clear();
        for (char m = 'a'; m <= 'j'; m++) {
            gtiles.getChildren().add(new DraggableTile(m));
        }
    }

    //Author: Tanya Dixit
    private void newGame() {
        try {
            hideCompletion();
            FocusGame game = new FocusGame((int) difficulty.getValue()-1);

            String objective = Objective.newObjective((int) difficulty.getValue()-1);

            String [] resSet = {""};
            makeTiles();
            makeObjective(objective);
        } catch (IllegalArgumentException e) {
            System.err.println("Uh oh. " + e);
            e.printStackTrace();
            Platform.exit();
        }
        resetPieces();
    }

    //Author: Tanya Dixit
    private void newInterestingChallenges() {
        //Read from file and create challenge here
        //add newGame functionality as well

        try {
            FileInputStream fileIn = new FileInputStream("interestingchallenges.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            HashMap<String, String> challenges = (HashMap<String, String>) in.readObject();
            Random rand = new Random();
            String objective = "";
            int i = rand.nextInt(challenges.size());
            int j = 0;
            for (Map.Entry<String,String> entry : challenges.entrySet()) {
                j++;
                if (j == i)
                    objective = entry.getKey();
            }
            in.close();
            fileIn.close();

            makeTiles();
            makeObjective(objective);
        } catch (IOException i) {
            i.printStackTrace();
            return;
        } catch (ClassNotFoundException c) {
            System.out.println("Interesting challenges not found");
            c.printStackTrace();
            return;
        }
    }

    //Author: Tanya Dixit
    private void makeControls() {

        Button button = new Button("Restart");
        button.setLayoutX(BOARD_X + GRID_WIDTH - 120);
        button.setLayoutY(BOARD_Y + GRID_HEIGHT + 20);
        button.setStyle("-fx-font: 14 verdana; -fx-base: #3398A5;");
        button.setTooltip(new Tooltip("Click me for a new game with new challenge according to difficulty"));
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                newGame();
            }
        });
        controls.getChildren().add(button);

        Button buttonInterestingChallenges = new Button("Challenge me more!!!");
        buttonInterestingChallenges.setLayoutX(MARGIN_X+GRID_WIDTH-120);
        buttonInterestingChallenges.setLayoutY(BOARD_Y + GRID_HEIGHT + 140);
        buttonInterestingChallenges.setStyle("-fx-font: 14 verdana; -fx-base: #DF98A5;");
        buttonInterestingChallenges.setTooltip(new Tooltip("Click me for instructions"));
        buttonInterestingChallenges.setTooltip(new Tooltip("Click me for interesting challenges"));
        buttonInterestingChallenges.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                newInterestingChallenges();
            }
        });
        controls.getChildren().add(buttonInterestingChallenges);

        difficulty.setMin(1);
        difficulty.setMax(5);
        difficulty.setValue(0);
        difficulty.setShowTickLabels(true);
        difficulty.setShowTickMarks(true);
        difficulty.setMajorTickUnit(1);
        difficulty.setMinorTickCount(1);
        difficulty.setSnapToTicks(true);
        difficulty.setTooltip(new Tooltip("Set the difficulty level by sliding me"));

        difficulty.setLayoutX(MARGIN_X+GRID_WIDTH - 80);
        difficulty.setLayoutY(MARGIN_Y + GRID_HEIGHT + 60);
        difficulty.setStyle("-fx-font: 14 verdana; -fx-base: #3398A5;");
        controls.getChildren().add(difficulty);

        final Label difficultyCaption = new Label("Difficulty:");
        difficultyCaption.setTextFill(Color.GREY);
        difficultyCaption.setLayoutX(MARGIN_X+GRID_WIDTH - 160);
        difficultyCaption.setLayoutY(MARGIN_Y + GRID_HEIGHT + 60);
        difficultyCaption.setStyle("-fx-font: 14 verdana; -fx-base: #3398A5;");
        controls.getChildren().add(difficultyCaption);

        Text iqFocus = new Text("IQ Focus");
        iqFocus.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        iqFocus.setX(MARGIN_X+GRID_WIDTH-50);
        iqFocus.setY(25);
        iqFocus.setFill(Color.BLACK);
        iqFocus.setStrokeWidth(2);
        iqFocus.setStroke(Color.BLACK);
        controls.getChildren().add(iqFocus);

    }

    /**
     * Put all of the tiles back in their home position
     */
    private void resetPieces() {
        gtiles.toFront();
        for (Node n : gtiles.getChildren()) {
            ((DraggableTile) n).snapToHome();
        }
    }

    //Author: Tanya Dixit
    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("TILES GAME");
        Scene scene = new Scene(root, VIEWER_WIDTH, VIEWER_HEIGHT);

        root.getChildren().add(gtiles);
        root.getChildren().add(board);
        root.getChildren().add(controls);
        root.getChildren().add(obj);
        root.getChildren().add(buttons);

        setUpHandlers(scene);
        background();
        newGame();
        makeTiles();
        makeGrid();

        Button button = new Button("Show me how to play!!!");
        button.setLayoutX(MARGIN_X+GRID_WIDTH-120);
        button.setLayoutY(MARGIN_Y+GRID_HEIGHT+20);
        button.setStyle("-fx-font: 14 verdana; -fx-base: #DF98A5;");
        button.setTooltip(new Tooltip("Click me for instructions"));

        Button buttonReset = new Button("Reset!");
        buttonReset.setLayoutX(MARGIN_X+GRID_WIDTH - 205);
        buttonReset.setLayoutY(MARGIN_Y+GRID_HEIGHT+20);
        buttonReset.setStyle("-fx-font: 14 verdana; -fx-base: #3398A5;");
        buttonReset.setTooltip(new Tooltip("Click me to send tiles to starting position"));

        Label label = new Label("Keep the tiles making sure the pattern in the middle is satisfied. Scroll to rotate the tiles.");
        label.setLayoutX(MARGIN_X+GRID_WIDTH/2-100);
        label.setLayoutY(MARGIN_Y+GRID_HEIGHT/2+75);

        Popup popup = new Popup();
        popup.getContent().add(label);

        label.setMinWidth(100);
        label.setMinHeight(100);
        // added here because needed primaryStage
        EventHandler<ActionEvent> event =
                new EventHandler<ActionEvent>() {

                    public void handle(ActionEvent e)
                    {
                        if (!popup.isShowing())
                            popup.show(primaryStage);
                        else
                            popup.hide();
                    }
                };

        EventHandler<ActionEvent> eventReset =
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        resetPieces();
                    }
                };

        // when button is pressed
        button.setOnAction(event);
        buttonReset.setOnAction(eventReset);

        root.getChildren().add(button);
        root.getChildren().add(buttonReset);

        makeControls();
        makeCompletion();
        newInterestingChallenges();
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
