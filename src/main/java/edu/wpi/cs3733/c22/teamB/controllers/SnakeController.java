package edu.wpi.cs3733.c22.teamB.controllers;

import edu.wpi.cs3733.c22.teamB.Bapp;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import static javafx.application.Application.launch;

public class SnakeController extends AbsPage {

    @FXML Pane contentPane;

    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    public static final int BLOCK_SIZE = 40;
    public static final int APP_W = 20 * BLOCK_SIZE;
    public static final int APP_H = 15 * BLOCK_SIZE;

    private Direction direction = Direction.RIGHT;
    private boolean moved = false;
    private boolean running = false;

    private Timeline timeline = new Timeline();

    private ObservableList<Node> snake;

    private Parent createContent() {
        Pane root = contentPane;

        root.setPrefSize(APP_W, APP_H + 5*BLOCK_SIZE);

        Group snakeBody = new Group();
        snake = snakeBody.getChildren();

        // Generates the food
        Rectangle food = new Rectangle(BLOCK_SIZE, BLOCK_SIZE);
        food.setFill(Color.BLUE);
        food.setTranslateX((int)(Math.random() * (APP_W - BLOCK_SIZE)) / BLOCK_SIZE * BLOCK_SIZE);
        food.setTranslateX((int)(Math.random() * (APP_H - BLOCK_SIZE)) / BLOCK_SIZE * BLOCK_SIZE);

        KeyFrame frame = new KeyFrame(Duration.seconds(0.2), event -> {
            if (!running)
                return;
            boolean toRemove = snake.size() > 1;

            Node tail = toRemove ? snake.remove(snake.size() - 1) : snake.get(0);

            double tailX = tail.getTranslateX();
            double tailY = tail.getTranslateY();

            // snake directions
            switch (direction) {
                case UP:
                    tail.setTranslateX(snake.get(0).getTranslateX());
                    tail.setTranslateY(snake.get(0).getTranslateY() - BLOCK_SIZE);
                    break;
                case DOWN:
                    tail.setTranslateX(snake.get(0).getTranslateX());
                    tail.setTranslateY(snake.get(0).getTranslateY() + BLOCK_SIZE);
                    break;
                case LEFT:
                    tail.setTranslateX(snake.get(0).getTranslateX() - BLOCK_SIZE);
                    tail.setTranslateY(snake.get(0).getTranslateY());
                    break;
                case RIGHT:
                    tail.setTranslateX(snake.get(0).getTranslateX() + BLOCK_SIZE);
                    tail.setTranslateY(snake.get(0).getTranslateY());
                    break;
            }

            moved = true;

            if (toRemove){
                snake.add(0, tail);
            }

            // collision; if snake hits its own body
            for (Node rect : snake){
                if (rect != tail && tail.getTranslateX() == rect.getTranslateX()
                        && tail.getTranslateY() == rect.getTranslateY()){
                    restartGame();
                    break;
                }
            }
            // collision against walls of screen
            if (tail.getTranslateX() < 0 || tail.getTranslateX() >= APP_W || tail.getTranslateY() < 0 || tail.getTranslateY() >= APP_W){
                restartGame();
            }

            // snake eat fude
            if (tail.getTranslateX() == food.getTranslateX() && tail.getTranslateY() == food.getTranslateY()){
                food.setTranslateX((int)(Math.random() * (APP_W - BLOCK_SIZE)) / BLOCK_SIZE * BLOCK_SIZE);
                food.setTranslateY((int)(Math.random() * (APP_H - BLOCK_SIZE)) / BLOCK_SIZE * BLOCK_SIZE);

                Rectangle rect = new Rectangle(BLOCK_SIZE, BLOCK_SIZE);
                rect.setTranslateX(tailX);
                rect.setTranslateY(tailY);

                snake.add(rect);
            }

        });

        // necessary for animation to be infinite
        timeline.getKeyFrames().add(frame);
        timeline.setCycleCount(Timeline.INDEFINITE);

        root.getChildren().addAll(food, snakeBody);
        return root;
    }

    private void restartGame(){
        stopGame();
        startGame();
    }

    private void startGame(){
        direction = Direction.RIGHT;
        Rectangle head = new Rectangle(BLOCK_SIZE, BLOCK_SIZE);
        snake.add(head);
        timeline.play();
        running = true;
    }

    private void stopGame(){
        running = false;
        timeline.stop();
        snake.clear();
    }

   // @Override
    public void initialize() {
        createContent();

        Bapp.getPrimaryStage().getScene().setOnKeyPressed(event -> {
            System.out.println("hello");
            System.out.println(event);
            System.out.println(event.getCode());
            if (!moved)
                return;

            switch (event.getCode()){
                    case W:
                        if (direction != Direction.DOWN)
                            direction = Direction.UP;
                        break;
                    case S:
                        if (direction!= Direction.UP)
                            direction = Direction.DOWN;
                        break;
                    case A:
                        if (direction != Direction.RIGHT)
                            direction = Direction.LEFT;
                        break;
                    case D:
                        if (direction != Direction.LEFT)
                            direction = Direction.RIGHT;
                        break;
            }

        });
        startGame();

        initResize();
        resize();
        namePage();
    }


    @Override
    public void namePage() {
        AnchorHomeController.curAnchorHomeController.setPageName("Snake");
    }
}
