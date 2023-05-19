package controller;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import model.game.Settings;
import model.thing.Ball;
import model.thing.InvisibleCircle;
import view.animations.InvisibleCircleAnimation;
import view.animations.ShootingAnimation;
import view.menus.GameMenu;

import java.util.ArrayList;

public class GameMenuController {
    private final GameMenu gameMenu;
    private final Settings settings;
    private ArrayList<Ball> balls;

    public GameMenuController(GameMenu gameMenu, Settings settings) {
        this.settings = settings;
        this.gameMenu = gameMenu;
    }

    public void createAndAddCenterCircle(Pane pane) {
        Label name = new Label(settings.getMap().getName());
        name.setTextFill(Color.WHITE);
        name.setFont(new Font(40)); name.setAlignment(Pos.CENTER);
        name.setPrefHeight(54); name.setPrefWidth(153);
        name.setLayoutX(274); name.setLayoutY(173);

        Circle circle = new Circle(350, 200,60, settings.getMap().getColor());
        InvisibleCircle invisibleCircle = new InvisibleCircle(350, 200, 170, pane);
        invisibleCircle.setVisible(false);

        InvisibleCircleAnimation invisibleCircleAnimation = new InvisibleCircleAnimation(invisibleCircle,
                settings.getLevel().getRotationSpeed(), settings.getLevel().getWindSpeed(),
                settings.getLevel().getFreezeTime());
        invisibleCircleAnimation.play();

        pane.getChildren().add(invisibleCircle);
        pane.getChildren().add(circle);
        pane.getChildren().add(name);
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

    public void finishGame() {
    }
}
