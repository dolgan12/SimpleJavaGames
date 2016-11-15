package me.garpo.space_invaders;

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

/**
 * Created by dolgan12 on 2016-11-14.
 */
public class Main extends Application{
    public static final int WINDOW_WIDTH = 800;
    public static final int WINDOW_HEIGHT = 600;

    // Settings for number of aliens
    public static final int ALIEN_COLUMN = 11;
    public static final int ALIEN_ROW = 5;

    // Add background image
    Image backgroundImage = new Image("me/garpo/space_invaders/Resourses/ScreenshotStarfield.png");
    ImageView backgroundView = new ImageView(backgroundImage);

    // Add alien image
    Image alienImage = new Image("me/garpo/space_invaders/Resourses/invader.png");
    ImageView alienView = new ImageView(alienImage);

    // Add player image
    private Image playerImage = new Image("me/garpo/space_invaders/Resourses/player.png");
    private ImageView playerView = new ImageView(playerImage);

    // Create an array of images with all the invaders
    ImageView[] aliens = new ImageView[ALIEN_COLUMN * ALIEN_ROW];


    private Parent createContent() {

        Pane root = new Pane();
        root.setPrefSize(WINDOW_WIDTH, WINDOW_HEIGHT);


        root.getChildren().addAll(backgroundView, playerView);

        return root;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Scene scene = new Scene(createContent());

        // Make background fit the stage
        backgroundView.fitWidthProperty().bind(primaryStage.widthProperty());
        backgroundView.fitHeightProperty().bind(primaryStage.heightProperty());

        // player properties in the stage
        playerView.setPreserveRatio(true);
        playerView.setFitWidth(75);
        playerView.setY(WINDOW_HEIGHT - 35);
        playerView.setX(WINDOW_WIDTH /2 - 75/2);


        primaryStage.setTitle("Space Invaders");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args){
        launch(args);
    }


}
