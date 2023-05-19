package view.animations;

import controller.GameMenuController;
import javafx.animation.Transition;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import model.thing.Ball;
import model.thing.InvisibleCircle;

import java.util.ArrayList;

public class ShootingAnimation extends Transition {
    private final GameMenuController gameMenuController;
    private final Pane pane;
    private final Ball ball;

    public ShootingAnimation(GameMenuController gameMenuController, Pane pane, Ball ball) {
        this.gameMenuController = gameMenuController;
        this.pane = pane;
        this.ball = ball;
        this.setCycleDuration(Duration.millis(3));
        this.setCycleCount(-1);
    }

    @Override
    protected void interpolate(double v) {
        double nextY = this.ball.getCenterY() - 1;

        if (isHitAnotherBall())
        {
            gameMenuController.finishGame();
            this.stop();
        }

        if (gameMenuController.isOnInvisibleCircle(ball)) {
            if (pane.getChildren().get(0) instanceof InvisibleCircle invisibleCircle)
                invisibleCircle.receiveBall(ball);

            this.stop();
        }

        this.ball.setCenterY(nextY);
    }

    private boolean isHitAnotherBall() {
        for (Node node : pane.getChildren()) {
            if (node instanceof Ball target) {
                if (target != this.ball) {
                    if (gameMenuController.areBallsHit(this.ball, target))
                        return true;
                }
            }
        }
        return false;
    }
}
