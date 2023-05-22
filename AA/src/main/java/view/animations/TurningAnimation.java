package view.animations;

import controller.GameMenuController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.Transition;
import javafx.util.Duration;
import model.game.enums.Level;
import model.thing.Ball;

public class TurningAnimation extends Transition {
    private static boolean isOnFreeze;
    private static int freezeTime;
    private static double angleSpeed;
    private static double windSpeed;
    private final Ball ball;
    private double angle;

    static {
        isOnFreeze = false;
    }

    public TurningAnimation(Ball ball, double firstAngle) {
        this.ball = ball;
        this.angle = firstAngle;

        this.setCycleDuration(Duration.INDEFINITE);
        this.setCycleCount(-1);
    }

    public static void setParameters(Level level) {
        angleSpeed = (double) level.getRotationSpeed() / 5;
        windSpeed = level.getWindSpeed();
        freezeTime = level.getFreezeTime();

    }

    public static void playFreeze() {
        isOnFreeze = true;
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(freezeTime * 1000), actionEvent ->
                isOnFreeze = false));
        timeline.setCycleCount(1);
        timeline.play();
    }

    public static boolean isIsOnFreeze() {
        return isOnFreeze;
    }

    @Override
    protected void interpolate(double v) {
        if (!isOnFreeze)
            this.angle += angleSpeed;
        else this.angle += angleSpeed * 0.35;

        this.angle %= 360;
        this.ball.setCenterX(350 + 160 * Math.cos(Math.toRadians(angle)));
        this.ball.setCenterY(200 + 160 * Math.sin(Math.toRadians(angle)));
        this.ball.setLineLocation();
    }
}
