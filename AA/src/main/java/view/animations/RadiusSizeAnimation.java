package view.animations;

import controller.GameMenuController;
import javafx.animation.Transition;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.util.Duration;
import model.thing.Ball;

public class RadiusSizeAnimation extends Transition {
    private final Group balls;
    private double radius;
    private int direction;

    public RadiusSizeAnimation(Group balls) {
        this.balls = balls;
        this.radius = 10;
        this.direction = 1;
        this.setCycleCount(-1);
        this.setCycleDuration(Duration.millis(100));
    }

    @Override
    protected void interpolate(double v) {
        radius += direction * 0.01;

        if (radius > 11)
            direction = -1;

        if (radius < 10)
            direction = 1;

        for (Node node : balls.getChildren()) {
            if (node instanceof Ball ball) {
                if (radius > 11) {
                    if (isHitAnotherBall(ball)) {
                        try {
                            GameMenuController.getGameMenuController().looseGame(null);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                        this.stop();
                    }

                }
                ball.setRadius(radius);
            }
        }
    }

    public boolean isHitAnotherBall(Ball ball) {
        for (Node node : balls.getChildren()) {
            if (node instanceof Ball target) {
                if (target != ball) {
                    if (this.areBallsHit(ball, target))
                        return true;
                }
            }
        }
        return false;
    }

    private boolean areBallsHit(Ball ball1, Ball ball2) {
        return GameMenuController.getGameMenuController().areBallsHit(ball1, ball2);
    }
}
