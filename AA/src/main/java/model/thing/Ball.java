package model.thing;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import view.animations.BallAnimation;

public class Ball extends Circle {
    private int number;
    private BallAnimation ballAnimation;
    public Ball(double v, Paint paint, int number) {
        super(v, paint);
        this.number = number;
        this.ballAnimation = new BallAnimation();
    }
}
