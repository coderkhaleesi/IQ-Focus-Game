package comp1110.ass2.gui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;

/**
 * A very simple viewer for piece placements in the IQ-Focus game.
 * <p>
 * NOTE: This class is separate from your main game class.  This
 * class does not play a game, it just illustrates various piece
 * placements.
 *
 * Author: Tanya Dixit
 */
public class Viewer extends Application {


    private static final String URI_BASE = "assets/";

    /* board layout */
    private static final int SQUARE_SIZE = 100;
    private static final int VIEWER_WIDTH = 933; //write some logic including BOARD_WIDTH
    private static final int VIEWER_HEIGHT = 700;
    private static final int BOARD_WIDTH = 933/2;
    private static final int BOARD_HEIGHT = 700/2;
    private static final int MARGIN_X = 30;
    private static final int MARGIN_Y = 30;

    private static final int BOARD_Y = MARGIN_Y;
    private static final int BOARD_X = MARGIN_X + (3 * SQUARE_SIZE) + SQUARE_SIZE + MARGIN_X;

    private static final String BASEBOARD_URI = Viewer.class.getResource(URI_BASE + "board.png").toString();


    private final Group root = new Group();
    private final Group controls = new Group();
    private final Group board = new Group();



    private TextField textField;

    /**
     * Draw a placement in the window, removing any previously drawn one
     *
     * @param placement A valid placement string
     */
    void makePlacement(String placement) {

        double xGive = placement.charAt(1)*10;
        double yGive = placement.charAt(2)*10;


        ImageView iv = new ImageView(new Image(Viewer.class.getResource("assets/"+ placement.charAt(0)+"-"+placement.charAt(3) +".png").toString()));
        //iv.setPreserveRatio(true);
        //iv.setFitHeight(SQUARE_SIZE);
        //iv.setFitWidth(SQUARE_SIZE);    The pieces f and h are going to be harder to resize given their unique shope that means this sizing method is flawed.
        // However, if the images remain in their unaltered state that means they all remain in the same relative size but still too big for the window.
        iv.setX(xGive);
        iv.setY(yGive);
        root.getChildren().add(iv);


    }


    private void makeSimpleImage() {

    }

    /*
     * An important method to set the layout and create the playing board
     */
    private void makeBoard() {
        board.getChildren().clear();

        ImageView baseboard = new ImageView();
        baseboard.setImage(new Image(BASEBOARD_URI));
        baseboard.setFitWidth(BOARD_WIDTH);
        baseboard.setFitHeight(BOARD_HEIGHT);
        baseboard.setLayoutX(BOARD_X);
        baseboard.setLayoutY(BOARD_Y);
        board.getChildren().add(baseboard);

        board.toBack();

    }

    /**
     * Create a basic text field for input and a refresh button.
     */
    private void makeControls() {
        Label label1 = new Label("Placement:");
        textField = new TextField();
        textField.setPrefWidth(300);
        Button button = new Button("Refresh");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                makePlacement(textField.getText());
                textField.clear();
            }
        });
        HBox hb = new HBox();
        hb.getChildren().addAll(label1, textField, button);
        hb.setSpacing(10);
        hb.setLayoutX(130);
        hb.setLayoutY(VIEWER_HEIGHT - 50);
        controls.getChildren().add(hb);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("FocusGame Viewer");
        Scene scene = new Scene(root, VIEWER_WIDTH, VIEWER_HEIGHT);

        root.getChildren().add(controls);
        root.getChildren().add(board);

        makeControls();


        // This is the way that imageview works in conjunction with the assets and how to add them to the scene.
        ImageView testImage = new ImageView(new Image(Viewer.class.getResource("assets/a-0.png").toString()));
        testImage.setFitHeight(SQUARE_SIZE);
        testImage.setFitWidth(SQUARE_SIZE);
        testImage.setY(300.0);
        testImage.setX(300.0);
        //root.getChildren().add(testImage);
        //create makeplacement method by just translating the actions and code here into the method that changes with the input string

        makeBoard();

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
