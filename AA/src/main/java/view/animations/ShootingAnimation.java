package view.animations;

import controller.GameMenuController;
import javafx.animation.Transition;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import model.thing.Ball;


public class ShootingAnimation extends Transition {
    private final GameMenuController gameMenuController;
    private final Pane pane;
    private final Ball ball;

    public ShootingAnimation(Pane pane, Ball ball) {
        this.gameMenuController = GameMenuController.getGameMenuController();
        this.pane = pane;
        this.ball = ball;
        this.setCycleDuration(Duration.millis(3));
        this.setCycleCount(-1);
    }

    @Override
    protected void interpolate(double v) {
        double nextY = this.ball.getCenterY() - 1;

        if (isHitAnotherBall() || nextY < 20) {
            try {
                gameMenuController.looseGame();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            this.stop();
        }

        if (gameMenuController.isOnInvisibleCircle(ball)) {
            try {
                gameMenuController.getInvisibleCircle().receiveBall(ball);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            this.stop();
        }

        this.ball.setCenterY(nextY);
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
