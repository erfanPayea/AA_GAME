package view.animations;

import javafx.animation.Transition;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import model.thing.Ball;

public class TurningAnimation extends Transition {
    private final Ball ball;
    private final double rotationSpeed;
    private final double windSpeed;
    private final int freezeTime;
    private double angle = 1.5 * Math.PI ;

    public TurningAnimation(Ball ball, double rotationSpeed, double windSpeed, int freezeTime) {
        this.ball = ball;
        this.rotationSpeed = rotationSpeed / (1.5 * Math.PI);
        this.windSpeed = windSpeed;
        this.freezeTime = freezeTime;

        this.setCycleDuration(Duration.INDEFINITE);
        this.setCycleCount(-1);
    }

    @Override
    protected void interpolate(double v) {
        Rotate rotate = new Rotate();
        rotate.setPivotX(350);
        rotate.setPivotY(200);
        rotate.setAngle(rotationSpeed);
        this.ball.getTransforms().add(rotate);
        this.ball.getLine().getTransforms().add(rotate);
    }
}
