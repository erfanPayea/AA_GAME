package view.animations;

import javafx.animation.Transition;
import javafx.util.Duration;
import model.thing.Ball;

import java.util.ArrayList;

public class FinishAnimation extends Transition {
    private final boolean isWin;
    private final ArrayList<Ball> balls;
    public FinishAnimation(boolean isWin, ArrayList<Ball> balls) {
        this.isWin = isWin;
        this.balls = balls;
        this.setCycleCount(5);
        this.setCycleDuration(Duration.seconds(0.5));
    }

    @Override
    protected void interpolate(double v) {
        if (isWin) {
            for (Ball ball : balls) {
                ball.setCenterX((ball.getCenterX() - 350) * 1.1 + 350);
                ball.setCenterY((ball.getCenterY() - 200) * 1.1 + 200);
                ball.setLineLocation();
            }
        }
        else {
            for (Ball ball : balls) {
                ball.setCenterX((350 - ball.getCenterX()) / 30 + ball.getCenterX());
                ball.setCenterY((360 - ball.getCenterY()) / 30 + ball.getCenterY());
                ball.setLineLocation();
            }
        }
    }
}
