
/**
 * Created by Conny Garpö
    Standard Main.java for creating app and launching it.

 */

package me.garpo.masken;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import static me.garpo.masken.Direction.UP;

public class Main extends Application {

    // Define size of application
    public static final int SQUARE_SIZE = 20;
    public static final int WINDOW_WIDTH = 20 *SQUARE_SIZE;
    public static final int WINDOW_HEIGHT = 15 * SQUARE_SIZE;

    // snake initial settings
    private Direction direction = Direction.RIGHT;
    private boolean moved = false;
    private boolean running = false;

    // Animation initialisation
    private Timeline timeline = new Timeline();

    // The actual snake
    private ObservableList<Node> snake;



    private Parent createContent() {
        // Create window
        Pane root = new Pane();
        root.setPrefSize(WINDOW_WIDTH, WINDOW_WIDTH);

        // Create snake
        Group snakeBody = new Group();
        snake = snakeBody.getChildren();

        // Create Food
        Rectangle food = new Rectangle(SQUARE_SIZE, SQUARE_SIZE);
        food.setFill(Color.RED);
        food.setTranslateX((int)(Math.random() * (WINDOW_WIDTH - SQUARE_SIZE) ) / SQUARE_SIZE * SQUARE_SIZE );
        food.setTranslateY((int)(Math.random() * (WINDOW_HEIGHT - SQUARE_SIZE)) / SQUARE_SIZE * SQUARE_SIZE );

        // Main loop in the frame animation
        KeyFrame frame = new KeyFrame(Duration.seconds(0.2), event -> {
            if(!running)
                return;

            boolean toRemove = snake.size() > 1;

            Node tail = toRemove ? snake.remove(snake.size()-1) : snake.get(0);

            // If we eat the food we remember the tail x and y
            double tailX = tail.getTranslateX();
            double tailY = tail.getTranslateY();


            // Movement
            switch (direction){
                case UP:
                    tail.setTranslateX(snake.get(0).getTranslateX());
                    tail.setTranslateY(snake.get(0).getTranslateY() - SQUARE_SIZE);
                    break;
                case DOWN:
                    tail.setTranslateX(snake.get(0).getTranslateX());
                    tail.setTranslateY(snake.get(0).getTranslateY() + SQUARE_SIZE);
                    break;
                case LEFT:
                    tail.setTranslateX(snake.get(0).getTranslateX() - SQUARE_SIZE);
                    tail.setTranslateY(snake.get(0).getTranslateY());
                    break;
                case RIGHT:
                    tail.setTranslateX(snake.get(0).getTranslateX() + SQUARE_SIZE);
                    tail.setTranslateY(snake.get(0).getTranslateY());
                    break;
            }

            moved = true;

            if(toRemove)
                snake.add(0, tail);

            // Collision detection against own body
            for (Node rectangle : snake) {
                if(rectangle != tail && tail.getTranslateX() == rectangle.getTranslateX()
                        && tail.getTranslateY() == rectangle.getTranslateY()) {
                    gameOver();
                    break;
                }
            }
            // collision detection against walls
            if(tail.getTranslateX() < 0 || tail.getTranslateX() >= WINDOW_WIDTH
                    || tail.getTranslateY() < 0 || tail.getTranslateY() >= WINDOW_HEIGHT) {
                gameOver();
            }

            // Collision detection with food
            if(tail.getTranslateX() == food.getTranslateX() && tail.getTranslateY() == food.getTranslateY()){
                // Create new food
                food.setTranslateX((int)(Math.random() * (WINDOW_WIDTH - SQUARE_SIZE) ) / SQUARE_SIZE * SQUARE_SIZE );
                food.setTranslateY((int)(Math.random() * (WINDOW_HEIGHT - SQUARE_SIZE)) / SQUARE_SIZE * SQUARE_SIZE );

                // Add length to snake
                Rectangle newTail = new Rectangle(SQUARE_SIZE, SQUARE_SIZE);
                newTail.setTranslateX(tailX);
                newTail.setTranslateY(tailY);
                snake.add(newTail);
            }
        });

        timeline.getKeyFrames().add(frame);
        timeline.setCycleCount(Timeline.INDEFINITE);

        root.getChildren().addAll(food, snakeBody);
        return root;
    }

    private void gameOver(){
        stopGame();
        startGame();
    }

    private void startGame(){
        direction = Direction.RIGHT;
        Rectangle head = new Rectangle(SQUARE_SIZE, SQUARE_SIZE);
        snake.add(head);
        timeline.play();
        running = true;
    }

    private void stopGame(){
        running = false;
        timeline.stop();
        snake.clear();
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(createContent());
        scene.setOnKeyPressed(event -> {
            if(moved){
                switch (event.getCode()) {
                    case W:
                        if(direction != Direction.DOWN)
                            direction = Direction.UP;
                        break;
                    case S:
                        if(direction != Direction.UP)
                            direction = Direction.DOWN;
                        break;
                    case A:
                        if(direction != Direction.RIGHT)
                            direction = Direction.LEFT;
                        break;
                    case D:
                        if(direction != Direction.LEFT)
                            direction = Direction.RIGHT;
                        break;
                }
            }
            moved = false;
        });
        primaryStage.setTitle("Masken");
        primaryStage.setScene(scene);
        primaryStage.show();
        startGame();
    }

    public static void main(String[] args){
        launch(args);
    }
}
