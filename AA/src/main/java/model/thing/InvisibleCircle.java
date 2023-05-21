package model.thing;

import controller.GameMenuController;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import model.User;
import view.animations.TurningAnimation;

import java.util.ArrayList;

public class InvisibleCircle extends Circle {
    private final Pane pane;
    private final Group balls;
    private final int rotationSpeed;
    private final double windSpeed;
    private final int freezeTime;
    public InvisibleCircle(double v, double v1, double v2, Pane pane, int rotationSpeed, double windSpeed, int freezeTime) {
        super(v, v1, v2);
        this.pane = pane;
        this.rotationSpeed = rotationSpeed;
        this.windSpeed = windSpeed;
        this.freezeTime = freezeTime;
        this.balls = new Group();
        this.pane.getChildren().add(this);
        this.pane.getChildren().add(this.balls);
    }

    public void receiveBall(Ball ball) throws Exception {
        if (!this.balls.getChildren().contains(ball)) {
            pane.getChildren().remove(ball);
            ball.setCenterY(ball.getCenterY()); ball.setCenterX(ball.getCenterX());
            this.balls.getChildren().add(ball);

            Line line = new Line(this.getCenterX(), this.getCenterY(), ball);
            this.pane.getChildren().add(line);

            ball.setTurningAnimation(rotationSpeed, windSpeed, freezeTime);
            ball.getTurningAnimation().play();

            if (this.balls.getChildren().size() == User.currentUser.getSettings().getBallNumbers()) {
                GameMenuController.getGameMenuController().winGame();
            }
        }
    }

    public ArrayList<Ball> getBalls() {
        ArrayList<Ball> ballArrayList = new ArrayList<>();
        for (Node node : balls.getChildren()) {
            if (node instanceof Ball ball) {
                ballArrayList.add(ball);
            }
        }
        return ballArrayList;
    }

    public void play() {
        for (Node node : this.balls.getChildren()) {
            if (node instanceof Ball ball)
                ball.getTurningAnimation().play();
        }
    }

    public void stop() {
        for (Node node : this.balls.getChildren()) {
            if (node instanceof Ball ball)
                ball.getTurningAnimation().stop();
        }
    }
}
