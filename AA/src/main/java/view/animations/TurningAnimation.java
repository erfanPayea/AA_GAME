package view.animations;

import javafx.animation.Transition;
import javafx.util.Duration;
import model.thing.Ball;

public class TurningAnimation extends Transition {
    private final Ball ball;
    private final double angleSpeed;
    private final double windSpeed;
    private final int freezeTime;
    private double angle;

    public TurningAnimation(Ball ball, double firstAngle, double rotationSpeed, double windSpeed, int freezeTime) {
        this.ball = ball;
        this.angle = firstAngle;
        this.angleSpeed = rotationSpeed / 5;
        this.windSpeed = windSpeed;
        this.freezeTime = freezeTime;

        this.setCycleDuration(Duration.INDEFINITE);
        this.setCycleCount(-1);
    }

    @Override
    protected void interpolate(double v) {

        // set new place :
        angle += angleSpeed;
        angle %= 360;
        this.ball.setCenterX(350 + 160 * Math.cos(Math.toRadians(angle)));
        this.ball.setCenterY(200 + 160 * Math.sin(Math.toRadians(angle)));
        this.ball.setLineLocation();
    }
}
