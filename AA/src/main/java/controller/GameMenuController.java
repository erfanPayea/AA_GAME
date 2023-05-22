package controller;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import model.User;
import model.game.Settings;
import model.thing.Ball;
import model.thing.InvisibleCircle;
import view.animations.ReceivingBallAnimation;
import view.animations.ShootingAnimation;
import view.animations.TurningAnimation;
import view.menus.GameMenu;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class GameMenuController {
    public Circle center;
    private static GameMenuController gameMenuController;
    private final GameMenu gameMenu;
    private final User currentUser;
    private final Settings settings;
    private ReceivingBallAnimation receivingBallAnimation;
    private MediaPlayer mediaPlayer;
    private InvisibleCircle invisibleCircle;
    private Text ballsNumberText;
    private Text scoreSheetText;
    private ProgressBar freezeBar;
    private ArrayList<Ball> balls;
    private final ArrayList<ShootingAnimation> shootingAnimations;
    private int score;

    public GameMenuController(GameMenu gameMenu) {
        this.currentUser = User.getCurrentUser();
        this.settings = currentUser.getSettings();
        this.gameMenu = gameMenu;
        this.score = 0;
        this.shootingAnimations = new ArrayList<>();
        gameMenuController = this;
    }

    public static GameMenuController getGameMenuController() {
        return gameMenuController;
    }

    public void initializeFirstParameters(Pane pane) {
        this.score = 0;
        Label name = new Label(settings.getMap().getName());
        if (this.settings.getMap().getColor() != Color.ALICEBLUE) name.setTextFill(Color.WHITE);
        name.setFont(new Font(40));
        name.setAlignment(Pos.CENTER);
        name.setPrefHeight(54);
        name.setPrefWidth(153);
        name.setLayoutX(274);
        name.setLayoutY(173);

        Circle centerCircle = new Circle(350, 200, 60, settings.getMap().getColor());
        this.receivingBallAnimation = new ReceivingBallAnimation(centerCircle);
        this.center = centerCircle;

        this.invisibleCircle = new InvisibleCircle(350, 200, 160, pane, settings);
        this.invisibleCircle.setVisible(false);
        pane.getChildren().add(centerCircle);
        pane.getChildren().add(name);
        this.invisibleCircle.play();

        TurningAnimation.setParameters(settings.getLevel());
        TurningAnimation.stopReverse();
    }

    public void createRemainingBalls(Pane pane) {
        Text remainingBalls = new Text("Remaining balls: ");
        this.ballsNumberText = new Text(String.valueOf(settings.getBallNumbers()));

        remainingBalls.setFill(Color.WHITE);
        this.ballsNumberText.setFill(Color.ORANGERED);
        remainingBalls.setFont(new Font(18));
        this.ballsNumberText.setFont(new Font(20));
        HBox hBox = new HBox(remainingBalls, this.ballsNumberText);
        hBox.setSpacing(10);
        pane.getChildren().add(hBox);
    }

    public void createScoreSheet(Pane pane) {
        Text yourScore = new Text("Your score: ");
        this.scoreSheetText = new Text(String.valueOf(0));

        yourScore.setFill(Color.WHITE);
        this.scoreSheetText.setFill(Color.LIGHTCYAN);
        yourScore.setFont(new Font(18));
        this.scoreSheetText.setFont(new Font(20));
        HBox hBox = new HBox(yourScore, this.scoreSheetText);
        hBox.setSpacing(10);
        hBox.setLayoutY(20);

        pane.getChildren().add(hBox);
    }

    public void createFreezeBar(Pane pane) {
        Text freezeBarText = new Text("Freeze access: ");
        this.freezeBar = new ProgressBar(0);

        freezeBarText.setFill(Color.WHITE);
        freezeBarText.setFont(new Font(18));
        freezeBarText.setLayoutY(65);
        this.freezeBar.setLayoutY(80);

        pane.getChildren().add(freezeBarText);
        pane.getChildren().add(this.freezeBar);
    }

    public VBox createBallsGroup() {
        int size = this.settings.getBallNumbers();

        ArrayList<Ball> balls = new ArrayList<>();

        for (int ballNumber = size; ballNumber > 0; ballNumber--)
            balls.add(new Ball(ballNumber, settings.getMap().getColor()));

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

        Ball shootedBall = new Ball(ball.getNumber(), ball.getColor());
        shootedBall.setCenterX(350);
        shootedBall.setCenterY(440);

        ShootingAnimation shootingAnimation = new ShootingAnimation(pane, shootedBall);
        pane.getChildren().add(shootedBall);
        this.shootingAnimations.add(shootingAnimation);
        shootingAnimation.play();

        this.score += this.settings.getLevel().getScorePerBall();

        // view changes :

        this.gameMenu.getBallsGroupVBox().getChildren().remove(0);

        int newBallsNumber = Integer.parseInt(this.ballsNumberText.getText()) - 1;
        this.ballsNumberText.setText(String.valueOf(newBallsNumber));
        if (newBallsNumber < 6)
            this.ballsNumberText.setFill(Color.GREEN);
        else if (newBallsNumber <= this.settings.getBallNumbers() / 2 + 1)
            this.ballsNumberText.setFill(Color.GOLD);

        this.scoreSheetText.setText(String.valueOf(this.score));

        if (this.freezeBar.getProgress() != 1 && !TurningAnimation.isIsOnFreeze())
            this.freezeBar.setProgress(this.freezeBar.getProgress() + 0.125);

        //Phase changes :

        if (balls.size() == Math.floor(settings.getBallNumbers() * 0.75))
            this.runPhase2();

        else if (balls.size() == Math.floor(settings.getBallNumbers() * 0.5))
            this.runPhase3();
//
//        else if (balls.size() == Math.floor(settings.getBallNumbers() * 0.25))
//            this.runPhase4();
    }

    public void playFreeze() {
        if (freezeBar.getProgress() == 1)
            TurningAnimation.playFreeze();

        freezeBar.setProgress(0);
    }

    public void runPhase2() {
        this.invisibleCircle.getRadiusSizeAnimation().play();
        TurningAnimation.playReverse();
    }

    public void runPhase3() {
        FadeTransition ballsFadeTransition = new FadeTransition(Duration.millis(2000), invisibleCircle.getGroupOfBalls());
        FadeTransition linessFadeTransition = new FadeTransition(Duration.millis(2000), invisibleCircle.getGroupOfLines());
        ballsFadeTransition.setCycleCount(-1);
        linessFadeTransition.setCycleCount(-1);

        Timeline showTimeLine = new Timeline(new KeyFrame(Duration.millis(2000), actionEvent -> {
            ballsFadeTransition.setFromValue(0);
            linessFadeTransition.setFromValue(0);
            ballsFadeTransition.setToValue(1);
            linessFadeTransition.setToValue(1);

            ballsFadeTransition.play();
            linessFadeTransition.play();
        }));

        showTimeLine.setCycleCount(1);

        Timeline fadeTimeLine = new Timeline(new KeyFrame(Duration.millis(1000), actionEvent ->
        {
            ballsFadeTransition.setFromValue(1);
            linessFadeTransition.setFromValue(1);
            ballsFadeTransition.setToValue(0);
            linessFadeTransition.setToValue(0);

            ballsFadeTransition.play();
            linessFadeTransition.play();

        }));

        fadeTimeLine.setCycleCount(1);
        fadeTimeLine.play();
        showTimeLine.play();
    }

    public InvisibleCircle getInvisibleCircle() {
        return this.invisibleCircle;
    }

    public void doReceiveAnimation() {
        this.receivingBallAnimation.play();

        this.mediaPlayer = new MediaPlayer(new Media(Objects.requireNonNull(
                this.getClass().getResource("/Media/receiveBall.mp3")).toExternalForm()));
        mediaPlayer.play();
    }

    public boolean isOnInvisibleCircle(Ball ball) {
        return ball.getBoundsInParent().intersects(this.invisibleCircle.getLayoutBounds());
    }

    public boolean areBallsHit(Ball ball1, Ball ball2) {
        if (ball1 == ball2)
            return false;

        return ball1.getBoundsInParent().intersects(ball2.getBoundsInParent());
    }

    public void pause() {
        this.invisibleCircle.stop();
        for (ShootingAnimation shootingAnimation : this.shootingAnimations) {
            shootingAnimation.stop();
        }
    }

    public void continueGame() {
        this.gameMenu.continueGame();
        this.invisibleCircle.play();
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
        this.pause();
        this.currentUser.hasWinLastGame = false;
        this.currentUser.lastTimePlayed = 10; // todo : time
        this.currentUser.setLastGameScore(score);
        gameMenu.looseGame();
    }

    public void finishGame() throws Exception {
        gameMenu.end();
    }
}
