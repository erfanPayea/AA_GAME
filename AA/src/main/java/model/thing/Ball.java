package model.thing;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import view.animations.ShootingAnimation;

public class Ball extends Circle {
    private final static int radius;
    private final int number;
    private final Color color;
    static {
        radius = 10;
    }
    public Ball(Pane pane, int number, Color color) {
        super(radius, color);
        this.number = number;
        this.color = color;
        this.setAccessibleText(String.valueOf(number));
        if (color != Color.ALICEBLUE)
            this.setStroke(Color.ALICEBLUE);
        else this.setStroke(Color.BLACK);
    }

    public int getNumber() {
        return this.number;
    }

    public Color getColor() {
        return color;
    }

}
