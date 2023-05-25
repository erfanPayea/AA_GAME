package view.animations;

import controller.GameMenuController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.Transition;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import model.thing.Ball;

import java.util.concurrent.atomic.AtomicInteger;


public class ShootingAnimation extends Transition {
    public static double windSpeed;
    public static double degree;
    private static final Text degreeText;
    private static Timeline timeline;
    private final GameMenuController gameMenuController;
    private final Ball ball;
    private final int direction;

    static {
        degree = 0;
        degreeText = new Text(String.valueOf(0));
    }

    public ShootingAnimation(Ball ball, int direction) {
        this.gameMenuController = GameMenuController.getGameMenuController();
        this.ball = ball;
        this.direction = direction;
        this.setCycleDuration(Duration.millis(3));
        this.setCycleCount(-1);
    }

    public static Text getDegreeText() {
        return degreeText;
    }

    public static void setWindSpeed(double windSpeed) {
        ShootingAnimation.windSpeed = windSpeed;
    }

    public static void playPhase4() {
        AtomicInteger direction = new AtomicInteger(1);
        timeline = new Timeline(new KeyFrame(Duration.seconds(2.5), actionEvent -> {
            if (degree + direction.get() * windSpeed > 15)
                direction.set(-1);
            else if(degree + direction.get() * windSpeed < -15)
                direction.set(1);

            degree += windSpeed * direction.get();
            degreeText.setText(String.valueOf(degree));
        }));

        timeline.setCycleCount(-1);
        timeline.play();
    }

    public static void stopPhase4() {
        if (timeline != null)
            timeline.stop();
    }

    public static void stopPhase2() {

    }

    @Override
    protected void interpolate(double v) {
        double nextY = this.ball.getCenterY() + direction;
        double nextX = this.ball.getCenterX() + Math.sin(Math.toRadians(degree));

        if (isHitAnotherBall() || nextY > 790 || nextY < 20 || nextX < 20 || nextX > 1510) {
            try {
                gameMenuController.looseGame(this.ball);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            this.stop();
        }

        if (gameMenuController.isOnInvisibleCircle(ball)) {
            try {
                gameMenuController.getInvisibleCircle().receiveBall(ball, direction == 1);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            this.stop();
        }

        this.ball.setCenterY(nextY);
        this.ball.setCenterX(nextX);
    }

    private boolean isHitAnotherBall() {
        for (Node node : gameMenuController.getInvisibleCircle().getBalls()) {
            if (node instanceof Ball target) {
                if (gameMenuController.areBallsHit(this.ball, target))
                    return true;
            }
        }
        return false;
    }

}
