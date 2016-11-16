package me.garpo.space_invaders;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Created by dolgan12 on 2016-11-14.
 */
public class Main extends Application{
    public static final int WINDOW_WIDTH = 800;
    public static final int WINDOW_HEIGHT = 600;

    // Settings for number of aliens
    public static final int ALIEN_COLUMN = 11;
    public static final int ALIEN_ROW = 5;

    //
    public static final int PLAYER_WIDTH = 75;
    public static final int ALIEN_WIDTH = 50;

    private boolean running = false;

    // Add background image
    private Image backgroundImage = new Image("me/garpo/space_invaders/Resourses/ScreenshotStarfield.png");
    private ImageView backgroundView = new ImageView(backgroundImage);

    // Add alien image
    private Image alienImage = new Image("me/garpo/space_invaders/Resourses/invader.png");
    private ImageView alienView = new ImageView(alienImage);

    // Add player image
    private Image playerImage = new Image("me/garpo/space_invaders/Resourses/player.png");
    private ImageView playerView = new ImageView(playerImage);

    // Create an array of images with all the invaders
    private ImageView[] aliens = new ImageView[ALIEN_COLUMN * ALIEN_ROW];

    // Animation initialisation
    private Timeline timeline = new Timeline();


    private Parent createContent() {

        Pane root = new Pane();
        root.setPrefSize(WINDOW_WIDTH, WINDOW_HEIGHT);

        // player properties in the stage
        playerView.setPreserveRatio(true);
        playerView.setFitWidth(PLAYER_WIDTH);
        playerView.setY(WINDOW_HEIGHT - 35);
        playerView.setX(WINDOW_WIDTH /2 - 75/2);

        // Main Game Loop
        KeyFrame frame = new KeyFrame(Duration.seconds(0.2), event -> {
            if(!running)
                return;
        } );

        timeline.getKeyFrames().add(frame);
        timeline.setCycleCount(Timeline.INDEFINITE);

        root.getChildren().addAll(backgroundView, playerView);

        return root;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Scene scene = new Scene(createContent());

        // Make background fit the stage
        backgroundView.fitWidthProperty().bind(primaryStage.widthProperty());
        backgroundView.fitHeightProperty().bind(primaryStage.heightProperty());




        primaryStage.setTitle("Space Invaders");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args){
        launch(args);
    }


}
