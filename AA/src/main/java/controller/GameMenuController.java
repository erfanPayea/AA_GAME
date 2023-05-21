package controller;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.User;
import model.game.Settings;
import model.thing.Ball;
import model.thing.InvisibleCircle;
import view.animations.ShootingAnimation;
import view.menus.GameMenu;

import java.util.ArrayList;

public class GameMenuController {
    private static GameMenuController gameMenuController;
    private final GameMenu gameMenu;
    private final User currentUser;
    private final Settings settings;
    private InvisibleCircle invisibleCircle;
    private ArrayList<Ball> balls;
    private int score;

    public GameMenuController(GameMenu gameMenu) {
        this.currentUser = User.currentUser;
        this.settings = currentUser.getSettings();
        this.gameMenu = gameMenu;
        this.score = 0;
        gameMenuController = this;
    }

    public static GameMenuController getGameMenuController() {
        return gameMenuController;
    }

    public void initializeFirstParameters(Pane pane) {
        this.score = 0;
        Label name = new Label(settings.getMap().getName());
        if (this.settings.getMap().getColor() != Color.ALICEBLUE) name.setTextFill(Color.WHITE);
        name.setFont(new Font(40)); name.setAlignment(Pos.CENTER);
        name.setPrefHeight(54); name.setPrefWidth(153);
        name.setLayoutX(274); name.setLayoutY(173);

        Circle circle = new Circle(350, 200,60, settings.getMap().getColor());
        this.invisibleCircle = new InvisibleCircle(350, 200, 160, pane, settings.getLevel().getRotationSpeed(),
                settings.getLevel().getWindSpeed(), settings.getLevel().getFreezeTime());
        this.invisibleCircle.setVisible(false);

        pane.getChildren().add(this.invisibleCircle);
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

    public HBox createScoreSheet() {
        Text yourScore = new Text("Your score: ");
        Text score = new Text(String.valueOf(0));

        yourScore.setFill(Color.WHITE);
        score.setFill(Color.LIGHTCYAN);
        yourScore.setFont(new Font(18));
        score.setFont(new Font(20));
        HBox hBox = new HBox(yourScore, score);
        hBox.setSpacing(10); hBox.setLayoutY(20);
        return hBox;
    }


    public VBox createBallsGroup(Pane pane) {
        int size = this.settings.getBallNumbers();

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

    public void shoot(Pane pane) throws Exception {
        if (this.balls.size() == 0)
            return;

        Ball ball = this.balls.get(0);
        this.balls.remove(0);
        this.gameMenu.shootFirst();

        Ball shootedBall = new Ball(pane, ball.getNumber(), ball.getColor());
        shootedBall.setCenterX(350); shootedBall.setCenterY(440);

        ShootingAnimation shootingAnimation = new ShootingAnimation(pane, shootedBall);
        pane.getChildren().add(shootedBall);
        shootingAnimation.play();

        this.score += this.settings.getLevel().getScorePerBall();
        gameMenu.changeScore(this.score);

    }

    public boolean isOnInvisibleCircle(Ball ball) {
        return ball.getBoundsInParent().intersects(this.invisibleCircle.getLayoutBounds());
    }

    public boolean areBallsHit(Ball ball1, Ball ball2) {
        return (ball1.getCenterX() - ball2.getCenterX()) * (ball1.getCenterX() - ball2.getCenterX()) +
                (ball1.getCenterY() - ball2.getCenterY()) * (ball1.getCenterY() - ball2.getCenterY()) < 350;
    }

    public void pause() throws Exception {
        this.invisibleCircle.stop();
    }

    public void continueGame() {
        gameMenu.continueGame();
    }

    public void restartGame() throws Exception {
        gameMenu.restart();
    }

    public void winGame() throws Exception {
        this.pause();
        this.currentUser.hasWinLastGame = true;
        this.currentUser.lastTimePlayed = 10; // todo : time
        this.currentUser.setLastGameScore(this.score);
        gameMenu.winGame();
    }

    public void looseGame() throws Exception {
        this.currentUser.hasWinLastGame = false;
        this.currentUser.lastTimePlayed = 10; // todo : time
        this.currentUser.setLastGameScore(score);
        gameMenu.looseGame();
    }

    public void finishGame() throws Exception {
        gameMenu.end();
    }

}
