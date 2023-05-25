package model.thing;

import javafx.scene.paint.Color;

public class Line extends javafx.scene.shape.Line {

    public Line(double v, double v1, Ball ball, Color color) {
        super(v, v1, ball.getCenterX(), ball.getCenterY());
        this.setStroke(color);
        ball.setLine(this);
    }
}
