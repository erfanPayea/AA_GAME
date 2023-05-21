package model.thing;

public class Line extends javafx.scene.shape.Line {

    public Line(double v, double v1, Ball ball) {
        super(v, v1, ball.getCenterX(), ball.getCenterY());
        this.setStroke(ball.getColor());
        ball.setLine(this);
    }
}
