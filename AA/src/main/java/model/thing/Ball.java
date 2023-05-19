package model.thing;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import view.animations.ShootingAnimation;

public class Ball extends Circle {
    private int number;
    private ShootingAnimation shootingAnimation;
    public Ball(double v, Paint paint, int number) {
        super(v, paint);
        this.number = number;
        this.shootingAnimation = new ShootingAnimation();
    }
}
