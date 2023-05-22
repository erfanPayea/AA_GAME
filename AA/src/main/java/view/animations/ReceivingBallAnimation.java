package view.animations;

import javafx.animation.Transition;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class ReceivingBallAnimation extends Transition {
    private final Circle centerCircle;
    private int number;
    {
        number = 1;
    }

    public ReceivingBallAnimation(Circle centerCircle) {
        this.centerCircle = centerCircle;
        this.setCycleCount(-1);
        this.setCycleDuration(Duration.millis(3));
    }

    @Override
    protected void interpolate(double v) {

        if (number < 50)
            centerCircle.setCenterY(centerCircle.getCenterY() - 0.2);

        else if (number == 100) {
            centerCircle.setCenterY(200);
            number = 1;
            this.stop();
        }

        else centerCircle.setCenterY(centerCircle.getCenterY() + 0.2);
        number ++;
    }
}
