package controller;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Node;
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
import view.animations.*;
import view.menus.GameMenu;

import java.util.ArrayList;
import java.util.Objects;

public class GameMenuController {
    public Circle center;
    private static GameMenuController gameMenuController;
    private static boolean is2Player;
    private final GameMenu gameMenu;
    private final User currentUser;
    private final Settings settings;
    private MediaPlayer musicPlayer;
    private ReceivingBallAnimation receivingBallAnimation;
    private InvisibleCircle invisibleCircle;
    private Text ballsNumberText;
    private Text scoreSheetText;
    private VBox ballsGroupVBox;
    private VBox upBallsGroupVBox;
    private ProgressBar freezeBar;
    private ArrayList<Ball> balls;
    private final ArrayList<ShootingAnimation> shootingAnimations;
    private FadeTransition ballsFadeTransition;
    private FadeTransition linessFadeTransition;
    private boolean isOnPhase4;
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

    public static void setIs2Player(boolean is2Player) {
        GameMenuController.is2Player = is2Player;
    }

    public void initializeFirstParameters(Pane pane) {
        if (!currentUser.getSettings().isMute() && this.musicPlayer == null) {
            this.musicPlayer = new MediaPlayer(new Media(Objects.requireNonNull(this.getClass().getResource(
                            "/Media/Gta.mp3")).toExternalForm()));

            this.musicPlayer.play();
        }

        this.score = 0;
        Label name = new Label(settings.getMap().getName());
        if (this.settings.getMap().getColor() != Color.ALICEBLUE) name.setTextFill(Color.WHITE);
        name.setFont(new Font(40));
        name.setAlignment(Pos.CENTER);
        name.setPrefHeight(54);
        name.setPrefWidth(153);
        name.setLayoutX(691);
        name.setLayoutY(373);

        Circle centerCircle = new Circle(767, 400, 60, settings.getMap().getColor());
        this.receivingBallAnimation = new ReceivingBallAnimation(centerCircle);
        this.center = centerCircle;

        this.invisibleCircle = new InvisibleCircle(767, 400, 160, pane, settings);
        this.invisibleCircle.setVisible(false);
        pane.getChildren().add(centerCircle);
        pane.getChildren().add(name);
        this.invisibleCircle.play();

        TurningAnimation.setParameters(settings.getLevel());
        TurningAnimation.stopReverse();
        this.isOnPhase4 = false;

        if (balls != null) {
            this.checkFodPhases();
        }

        currentUser.getSettings().updateHotkeys();
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

    public void createDegreeHBox(Pane pane) {
        Text yourDegree = new Text("degree: ");
        yourDegree.setFill(Color.WHITE);
        yourDegree.setFont(new Font(18));

        Text degreeText = ShootingAnimation.getDegreeText();
        degreeText.setFill(Color.WHITE);
        degreeText.setFont(new Font(20));

        HBox hBox = new HBox(yourDegree, degreeText);
        hBox.setSpacing(10);
        hBox.setLayoutY(105);
        pane.getChildren().add(hBox);
    }

    public VBox createBallsGroup(Pane pane) {
        int size = this.settings.getBallNumbers();

        ArrayList<Ball> balls = new ArrayList<>();

        for (int ballNumber = size; ballNumber > 0; ballNumber--)
            balls.add(new Ball(ballNumber, settings.getMap().getColor()));

        this.ballsGroupVBox = new VBox();
        this.ballsGroupVBox.setSpacing(5);
        for (Ball ball : balls)
            this.ballsGroupVBox.getChildren().add(ball);

        this.ballsGroupVBox.setLayoutX(755);
        this.ballsGroupVBox.setLayoutY(650);

        this.balls = balls;

        if (is2Player) {
            this.upBallsGroupVBox = new VBox();
            for (Ball ball : balls)
                this.upBallsGroupVBox.getChildren().add(ball.for2Player());

            this.upBallsGroupVBox.setLayoutX(755);
            this.upBallsGroupVBox.setLayoutY(-375 + (20 - currentUser.getSettings().getBallNumbers()) * 25);
            this.upBallsGroupVBox.setSpacing(5);
            pane.getChildren().add(upBallsGroupVBox);
        }

        return ballsGroupVBox;
    }

    public void shoot() {
        if (this.balls.size() == 0)
            return;

        this.balls.remove(0);
        this.ballsGroupVBox.getChildren().remove(0);
        if (is2Player && upBallsGroupVBox.getChildren().size() > 0) {
            this.upBallsGroupVBox.getChildren().remove(0);
            this.upBallsGroupVBox.setLayoutY(this.upBallsGroupVBox.getLayoutY() + 25);
        }

        this.score += this.settings.getLevel().getScorePerBall();

        // view changes :

        int newBallsNumber = Integer.parseInt(this.ballsNumberText.getText()) - 1;
        this.ballsNumberText.setText(String.valueOf(newBallsNumber));
        if (newBallsNumber < 6)
            this.ballsNumberText.setFill(Color.GREEN);

        else if (newBallsNumber <= this.settings.getBallNumbers() / 2 + 1)
            this.ballsNumberText.setFill(Color.GOLD);

        this.scoreSheetText.setText(String.valueOf(this.score));

        if (this.freezeBar.getProgress() != 1 && !TurningAnimation.isIsOnFreeze())
            this.freezeBar.setProgress(this.freezeBar.getProgress() + 0.125);

        this.checkFodPhases();
    }

    public void shootPlayer1(Pane pane) {
        if (ballsGroupVBox.getChildren().size() == 0)
            return;

        Ball ball = this.balls.get(0);
        Ball shootedBall = new Ball(ball.getNumber(), ((Ball) ballsGroupVBox.getChildren().get(0)).getColor());
        shootedBall.setCenterX(ballsGroupVBox.getLayoutX() + 10);
        shootedBall.setCenterY(640);

        ShootingAnimation shootingAnimation = new ShootingAnimation(shootedBall, -1);
        pane.getChildren().add(shootedBall);
        this.shootingAnimations.add(shootingAnimation);
        shootingAnimation.play();

        shoot();
    }

    public void shootPlayer2(Pane pane) {
        if (upBallsGroupVBox.getChildren().size() == 0)
            return;

        Ball ball = this.balls.get(0);
        Ball shootedBall = new Ball(ball.getNumber(), ((Ball) upBallsGroupVBox.getChildren().get(0)).getColor());
        shootedBall.setCenterX(upBallsGroupVBox.getLayoutX() + 10);
        shootedBall.setCenterY(140);

        ShootingAnimation upShootingAnimation = new ShootingAnimation(shootedBall, 1);
        pane.getChildren().add(shootedBall);
        this.shootingAnimations.add(upShootingAnimation);
        upShootingAnimation.play();

        shoot();
    }

    private void checkFodPhases() {
        if (balls.size() == Math.floor(settings.getBallNumbers() * 0.75))
            this.runPhase2();

        else if (balls.size() == Math.floor(settings.getBallNumbers() * 0.5) + 1)
            this.runPhase3();

        else if (balls.size() == Math.floor(settings.getBallNumbers() * 0.25) + 1)
            runPhase4();
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
        this.ballsFadeTransition = new FadeTransition(Duration.seconds(2), invisibleCircle.getGroupOfBalls());
        this.linessFadeTransition = new FadeTransition(Duration.seconds(2), invisibleCircle.getGroupOfLines());
        ballsFadeTransition.setCycleCount(-1);
        linessFadeTransition.setCycleCount(-1);

        Timeline showTimeLine = new Timeline(new KeyFrame(Duration.seconds(2), actionEvent -> {
            ballsFadeTransition.setFromValue(0);
            linessFadeTransition.setFromValue(0);
            ballsFadeTransition.setToValue(1);
            linessFadeTransition.setToValue(1);

            ballsFadeTransition.play();
            linessFadeTransition.play();
        }));

        showTimeLine.setCycleCount(1);

        Timeline fadeTimeLine = new Timeline(new KeyFrame(Duration.seconds(1), actionEvent ->
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

    public void runPhase4() {
        this.isOnPhase4 = true;
        ShootingAnimation.setWindSpeed(settings.getLevel().getWindSpeed());
        ShootingAnimation.playPhase4();
    }

    public void moveRight() {
        if (this.isOnPhase4) {
            double nextX = this.ballsGroupVBox.getLayoutX() + 10;
            if (nextX < 1514)
                this.ballsGroupVBox.setLayoutX(nextX);
        }
    }

    public void moveLeft() {
        if (this.isOnPhase4) {
            double nextX = this.ballsGroupVBox.getLayoutX() - 10;
            if (nextX > 20)
                this.ballsGroupVBox.setLayoutX(nextX);
        }
    }

    public void moveRight2() {
        if (this.isOnPhase4) {
            double nextX = this.upBallsGroupVBox.getLayoutX() + 10;
            if (nextX < 1514)
                this.upBallsGroupVBox.setLayoutX(nextX);
        }
    }

    public void moveLeft2() {
        if (this.isOnPhase4) {
            double nextX = this.upBallsGroupVBox.getLayoutX() - 10;
            if (nextX > 20)
                this.upBallsGroupVBox.setLayoutX(nextX);
        }
    }

    public InvisibleCircle getInvisibleCircle() {
        return this.invisibleCircle;
    }

    public void doReceiveAnimation() {
        this.receivingBallAnimation.play();

        MediaPlayer mediaPlayer = new MediaPlayer(new Media(Objects.requireNonNull(
                this.getClass().getResource("/Media/receiveBall.mp3")).toExternalForm()));
        mediaPlayer.play();
    }

    public boolean isOnInvisibleCircle(Ball ball) {
        return ball.getBoundsInParent().intersects(this.invisibleCircle.getLayoutBounds());
    }

    public boolean areBallsHit(Ball ball1, Ball ball2) {
        if (ball1 == ball2)
            return false;

        return (ball1.getRadius() + ball2.getRadius()) * (ball1.getRadius() + ball2.getRadius()) - 60
                > (ball1.getCenterY() - ball2.getCenterY()) * (ball1.getCenterY() - ball2.getCenterY()) +
                (ball1.getCenterX() - ball2.getCenterX()) * (ball1.getCenterX() - ball2.getCenterX());
    }

    public void pauseMusic() {
        this.musicPlayer.pause();
    }

    public void playMusic() {
        this.musicPlayer.play();
    }

    public void changeMusic(String music) {
        if (music != null) {
            this.musicPlayer.stop();
            this.musicPlayer = new MediaPlayer(new Media(Objects.requireNonNull(this.getClass().getResource(
                    "/Media/" + music + ".mp3")).toExternalForm()));
            this.musicPlayer.play();
        }
    }

    public void pause() {
        TurningAnimation.stopReverse();
        ShootingAnimation.stopPhase4();
        this.invisibleCircle.stop();
        for (ShootingAnimation shootingAnimation : this.shootingAnimations) {
            shootingAnimation.stop();
        }
        if (this.linessFadeTransition != null) {
            this.ballsFadeTransition.stop();
            this.linessFadeTransition.stop();
        }
    }

    public void continueGame() {
        this.gameMenu.continueGame();
        this.invisibleCircle.play();
        this.checkFodPhases();
    }

    public void restartGame() throws Exception {
        this.pause();
        gameMenu.restart();
        this.musicPlayer.stop();
        this.musicPlayer.play();
    }

    public void winGame() {
        this.musicPlayer.stop();

        FinishAnimation finishAnimation = new FinishAnimation(true, invisibleCircle.getBalls());
        finishAnimation.play();

        this.pause();
        this.currentUser.hasWinLastGame = true;
        this.currentUser.setLastGameScore(this.score);
        gameMenu.winGame();
    }

    public void looseGame(Ball lastBall) {
        this.musicPlayer.stop();

        if (lastBall != null)
            gameMenu.getPane().getChildren().remove(lastBall);

        gameMenu.getPane().getChildren().remove(ballsGroupVBox);
        gameMenu.getPane().getChildren().remove(upBallsGroupVBox);

        FinishAnimation finishAnimation = new FinishAnimation(false, invisibleCircle.getBalls());
        finishAnimation.play();

        this.pause();
        this.currentUser.hasWinLastGame = false;
        this.currentUser.setLastGameScore(score);
        gameMenu.looseGame();
    }

    public void finishGame() throws Exception {
        this.pause();
        this.musicPlayer.stop();
        gameMenu.end();
    }
}
