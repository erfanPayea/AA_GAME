package view.animations;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.Transition;
import javafx.util.Duration;
import model.game.enums.Level;
import model.thing.Ball;

import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

public class TurningAnimation extends Transition {
    private static boolean isOnFreeze;
    private static int freezeTime;
    private static int direction;
    private static Timeline reverseTimeLine;
    private static double angleSpeed;
    private final Ball ball;
    private double angle;

    static {
        isOnFreeze = false;
        direction = 1;
    }

    public TurningAnimation(Ball ball, double firstAngle) {
        this.ball = ball;
        this.angle = firstAngle;

        this.setCycleDuration(Duration.INDEFINITE);
        this.setCycleCount(-1);
    }

    public static void setParameters(Level level) {
        angleSpeed = (double) level.getRotationSpeed() / 5;
        freezeTime = level.getFreezeTime();
    }

    public static void playFreeze() {
        isOnFreeze = true;
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(freezeTime), actionEvent ->
                isOnFreeze = false));
        timeline.setCycleCount(1);
        timeline.play();
    }

    public static void playReverse() {
        Timeline firstreverseTimeLine = new Timeline(new KeyFrame(Duration.seconds(1), actionEvent -> direction *= -1));
        firstreverseTimeLine.setCycleCount(1);
        firstreverseTimeLine.play();

        Random random = new Random();
        AtomicReference<Double> randomNumber = new AtomicReference<>(random.nextDouble(4, 6));

        reverseTimeLine = new Timeline(new KeyFrame(Duration.seconds(randomNumber.get()), actionEvent ->
        {
            direction *= -1;
            randomNumber.set(random.nextDouble(4, 6));
        }));
        reverseTimeLine.setCycleCount(-1);
        reverseTimeLine.play();
    }

    public static void stopReverse() {
        if (reverseTimeLine == null)
            return;
        reverseTimeLine.stop();
    }

    public static boolean isIsOnFreeze() {
        return isOnFreeze;
    }

    @Override
    protected void interpolate(double v) {
        if (!isOnFreeze)
            this.angle += direction * angleSpeed;
        else this.angle += direction * angleSpeed * 0.35;

        this.angle %= 360;
        this.ball.setCenterX(767 + 160 * Math.cos(Math.toRadians(angle)));
        this.ball.setCenterY(450 + 160 * Math.sin(Math.toRadians(angle)));
        this.ball.setLineLocation();
    }
}
