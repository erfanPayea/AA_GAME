package model.thing;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import view.animations.ShootingAnimation;

public class Ball extends Circle {
    private final static int radius;
    private Pane pane;
    private final int number;
    private final Color color;
    private ShootingAnimation shootingAnimation;
    static {
        radius = 12;
    }
    public Ball(Pane pane, int number, Color color) {
        super(radius, color);
        this.pane = pane;
        this.number = number;
        this.color = color;
        this.setAccessibleText(String.valueOf(number));
    }

    public int getNumber() {
        return this.number;
    }

    public Color getColor() {
        return color;
    }

    public void setShootingAnimation(ShootingAnimation shootingAnimation) {
        this.shootingAnimation = shootingAnimation;
    }
}
