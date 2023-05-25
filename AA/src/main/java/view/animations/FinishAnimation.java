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
        this.setCycleCount(1);
        this.setCycleDuration(Duration.seconds(0.5));
    }

    @Override
    protected void interpolate(double v) {
        if (isWin) {
            for (Ball ball : balls) {
                ball.setCenterX((ball.getCenterX() - 767) * 1.1 + 767);
                ball.setCenterY((ball.getCenterY() - 400) * 1.1 + 400);
                ball.setLineLocation();
            }
        }
        else {
            if (v > 0.95) {
                for (Ball ball : balls) {
                    ball.setCenterX(767);
                    ball.setLineLocation();
                    this.stop();
                }
            }

            else if (v > 0.6) {
                for (Ball ball : balls) {
                    ball.setCenterX((767 - ball.getCenterX()) / 10 + ball.getCenterX());
                    ball.setCenterY(ball.getCenterY() + 50);
                    ball.setLineLocation();
                }
            }
        }
    }
}
