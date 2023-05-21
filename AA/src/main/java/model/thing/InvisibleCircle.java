package model.thing;

import controller.GameMenuController;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import model.User;
import view.animations.TurningAnimation;

import java.util.ArrayList;

public class InvisibleCircle extends Circle {
    private final Pane pane;
    private final ArrayList<Ball> balls;
    private final int rotationSpeed;
    private final double windSpeed;
    private final int freezeTime;
    public InvisibleCircle(double v, double v1, double v2, Pane pane, int rotationSpeed, double windSpeed, int freezeTime) {
        super(v, v1, v2);
        this.pane = pane;
        this.rotationSpeed = rotationSpeed;
        this.windSpeed = windSpeed;
        this.freezeTime = freezeTime;
        this.balls = new ArrayList<>();
    }

    public void receiveBall(Ball ball) throws Exception {
        if (!this.balls.contains(ball)) {
            this.balls.add(ball);
            Line line = new Line(this.getCenterX(), this.getCenterY(), ball);
            this.pane.getChildren().add(line);

            ball.setTurningAnimation(rotationSpeed, windSpeed, freezeTime);
            ball.getTurningAnimation().play();
//            line.setTurningAnimation(rotationSpeed, windSpeed, freezeTime);

            if (this.balls.size() == User.currentUser.getSettings().getBallNumbers()) {
                GameMenuController.getGameMenuController().winGame();
            }
        }
    }

    public ArrayList<Ball> getBalls() {
        return balls;
    }

    public void play() {
        for (Ball ball : this.balls)
            ball.getTurningAnimation().play();
    }

    public void stop() {
        for (Ball ball : this.balls)
            ball.getTurningAnimation().stop();
    }
}
