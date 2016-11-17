package me.garpo.space_invaders;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Created by dolgan12 on 2016-11-14.
 *
 */
public class SpaceInvaders extends Application{

    Pane root = new Pane();

    public static final int WINDOW_WIDTH = 1200;
    public static final int WINDOW_HEIGHT = 800;

    // Settings for number of aliens
    public static final int ALIEN_COLUMN = 11;
    public static final int ALIEN_ROW = 5;

    //
    public static final int PLAYER_WIDTH = 75;
    public static final int ALIEN_SPACE = 20;
    public static final int ALIEN_WIDTH = 50;
    public static final int LEFT_INDENT = 225;
    public static final int TOP_INDENT = 30;

    private boolean running = false;
    private UserAction action = UserAction.NONE;

    // Add background image
    private Image backgroundImage = new Image("me/garpo/space_invaders/Resourses/ScreenshotStarfield.png");
    private ImageView backgroundView = new ImageView(backgroundImage);

    // Add alien image
    private Image alienImage = new Image("me/garpo/space_invaders/Resourses/invader.png");
    private ImageView alienView = new ImageView(alienImage);

    // Add player image
    private Image playerImage = new Image("me/garpo/space_invaders/Resourses/player.png");
    private ImageView player = new ImageView(playerImage);

    // Create an array of images with all the invaders
    private ImageView[] aliens = new ImageView[ALIEN_COLUMN * ALIEN_ROW];

    // Animation initialisation
    private Timeline timeline = new Timeline();

    private void shootBullet(){
        System.out.println("boom");
    }


    private void startGame(){
        player.setTranslateY(WINDOW_HEIGHT - 35);
        player.setTranslateX(WINDOW_WIDTH /2 - 75/2);

        createInvation(root);

        timeline.play();
        running = true;
    }

    // Function that runs to halt the main gameloop
    private void stopGame(){
        running = false;
        timeline.stop();
    }

    /*
    *   Creates all the aliens and adds them to the aliens array and gives them
    *   the initial positions on the screen.
    *
    * */
    private void createInvation(Pane root){
        int index;
        for(int i = 0; i < ALIEN_ROW; i++){
            for(int j = 0; j < ALIEN_COLUMN; j++){
                index = i * ALIEN_COLUMN + j;
                aliens[index] = new ImageView(alienImage);
                aliens[index].setPreserveRatio(true);
                aliens[index].setTranslateX(j * (ALIEN_WIDTH + ALIEN_SPACE) + LEFT_INDENT);
                aliens[index].setTranslateY(i * (ALIEN_WIDTH + ALIEN_SPACE) + TOP_INDENT);
                aliens[index].setFitWidth(ALIEN_WIDTH);
                root.getChildren().add(aliens[index]);
            }
        }
    }
    /*
    * Function that creates the Parent Pane with its content.
    * */
    private Parent createContent() {

        root.setPrefSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        root.getChildren().add(backgroundView);

        // player properties in the stage
        player.setPreserveRatio(true);
        player.setFitWidth(PLAYER_WIDTH);




        // Main Game Loop in 60fps
        KeyFrame frame = new KeyFrame(Duration.seconds(0.016), event -> {
            if(!running)
                return;

            switch (action){
                case LEFT:
                    if(player.getTranslateX() - 5 >= 0)
                        player.setTranslateX(player.getTranslateX() -5);
                    break;
                case RIGHT:
                    if(player.getTranslateX() + PLAYER_WIDTH + 5 <= WINDOW_WIDTH)
                        player.setTranslateX(player.getTranslateX() + 5);
                    break;
                case NONE:
                    break;
            }
        } );

        timeline.getKeyFrames().add(frame);
        timeline.setCycleCount(Timeline.INDEFINITE);

        root.getChildren().add(player);

        return root;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Scene scene = new Scene(createContent());

        // Make background fit the stage
        backgroundView.fitWidthProperty().bind(primaryStage.widthProperty());
        backgroundView.fitHeightProperty().bind(primaryStage.heightProperty());

        scene.setOnKeyPressed(event -> {
            switch (event.getCode()){
                case A:
                    action = UserAction.LEFT;
                    break;
                case D:
                    action = UserAction.RIGHT;
                    break;
            }
        });

        scene.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case A:
                    action = UserAction.NONE;
                    break;
                case D:
                    action = UserAction.NONE;
                    break;
                case SPACE:
                    shootBullet();
                    break;
            }
        });

        primaryStage.setTitle("Space Invaders");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
        startGame();
    }

    public static void main(String[] args){
        launch(args);
    }


}
