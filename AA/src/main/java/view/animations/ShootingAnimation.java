package view.animations;

import controller.GameMenuController;
import javafx.animation.Transition;
import javafx.scene.Group;
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

//        if (isHitAnotherBall() || nextY < 20)
//        {
//            try {
//                gameMenuController.looseGame();
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//            this.stop();
//        }

        if (gameMenuController.isOnInvisibleCircle(ball)) {
            if (pane.getChildren().get(0) instanceof InvisibleCircle invisibleCircle) {
                try {
                    invisibleCircle.receiveBall(ball);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

            InvisibleCircleAnimation invisibleCircleAnimation = new InvisibleCircleAnimation((InvisibleCircle) pane.getChildren().get(0),
                    5, 1.2,
                    7);
            invisibleCircleAnimation.play();

            this.stop();
        }

        this.ball.setCenterY(nextY);
    }

    private boolean isHitAnotherBall() {
        if (this.pane.getChildren().get(1) instanceof Group group) {
            for (Node node : group.getChildren()) {
                if (node instanceof Ball target) {
                    if (gameMenuController.areBallsHit(target, this.ball))
                        return true;
                }
            }
            return false;
        }

        return false;
    }

}
