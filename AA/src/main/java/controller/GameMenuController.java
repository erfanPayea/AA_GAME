package controller;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.game.Settings;
import model.thing.Ball;
import model.thing.InvisibleCircle;
import view.animations.ShootingAnimation;
import view.menus.GameMenu;
import view.menus.MainMenu;

import java.util.ArrayList;

public class GameMenuController {
    private static GameMenuController gameMenuController;
    private final GameMenu gameMenu;
    private final Settings settings;
    private ArrayList<Ball> balls;

    public GameMenuController(GameMenu gameMenu, Settings settings) {
        this.settings = settings;
        this.gameMenu = gameMenu;
        gameMenuController = this;
    }

    public static GameMenuController getGameMenuController() {
        return gameMenuController;
    }

    public void createAndAddCenterCircle(Pane pane) {
        Label name = new Label(settings.getMap().getName());
        if (settings.getMap().getColor() != Color.ALICEBLUE) name.setTextFill(Color.WHITE);
        name.setFont(new Font(40)); name.setAlignment(Pos.CENTER);
        name.setPrefHeight(54); name.setPrefWidth(153);
        name.setLayoutX(274); name.setLayoutY(173);

        Circle circle = new Circle(350, 200,60, settings.getMap().getColor());
        InvisibleCircle invisibleCircle = new InvisibleCircle(350, 200, 170, pane);
        invisibleCircle.setVisible(false);

        pane.getChildren().add(invisibleCircle);
        pane.getChildren().add(circle);
        pane.getChildren().add(name);
    }

    public HBox createRemainingBalls() {
        Text remainingBalls = new Text("Remaining balls: ");
        Text balls = new Text(String.valueOf(settings.getBallNumbers()));

        remainingBalls.setFill(Color.WHITE);
        balls.setFill(Color.ORANGERED);
        remainingBalls.setFont(new Font(18));
        balls.setFont(new Font(20));
        HBox hBox = new HBox(remainingBalls, balls);
        hBox.setSpacing(10);
        return hBox;
    }

    public VBox createBallsGroup(Pane pane) {
        int size = settings.getBallNumbers();

        ArrayList<Ball> balls = new ArrayList<>();

        for (int ballNumber = size; ballNumber > 0; ballNumber--)
            balls.add(new Ball(pane, ballNumber, settings.getMap().getColor()));

        VBox ballsGroup = new VBox();
        ballsGroup.setSpacing(5);
        for (Ball ball : balls)
            ballsGroup.getChildren().add(ball);

        ballsGroup.setLayoutX(338);
        ballsGroup.setLayoutY(450);

        this.balls = balls;
        return ballsGroup;
    }

    public void shoot(Pane pane) {
        if (this.balls.size() == 0)
            return;

        Ball ball = this.balls.get(0);
        this.balls.remove(0);
        this.gameMenu.shootFirst();

        Ball shootedBall = new Ball(pane, ball.getNumber(), ball.getColor());
        shootedBall.setCenterX(350); shootedBall.setCenterY(440);

        ShootingAnimation shootingAnimation = new ShootingAnimation(this, pane, shootedBall);
        pane.getChildren().add(shootedBall);
        shootingAnimation.play();
    }

    public boolean isOnInvisibleCircle(Ball ball) {
        return (ball.getCenterX() - 350) * (ball.getCenterX() - 350) +
                (ball.getCenterY() - 200) * (ball.getCenterY() - 200) < 28900.03 ;
    }

    public boolean areBallsHit(Ball ball1, Ball ball2) {
        return (ball1.getCenterX() - ball2.getCenterX()) * (ball1.getCenterX() - ball2.getCenterX()) +
                (ball1.getCenterY() - ball2.getCenterY()) * (ball1.getCenterY() - ball2.getCenterY()) < 450.03;
    }

    public void pause(Pane pane) throws Exception {
//        gameMenu.restart();
    }

    public void continueGame() {
        gameMenu.continueGame();
    }

    public void restartGame() throws Exception {
        gameMenu.restart();
    }

    public void looseGame() {
        int score = settings.getLevel().getScorePerBall() * (settings.getBallNumbers() - balls.size()); // todo : time
    }

    public void finishGame() throws Exception {
        gameMenu.end();
    }

}
