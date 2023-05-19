package model.thing;

public class Line extends javafx.scene.shape.Line {
    private final Ball ball;

    public Line(double v, double v1, Ball ball) {
        super(v, v1, ball.getCenterX(), ball.getCenterY());
        this.ball = ball;
        this.setStroke(ball.getColor());
    }

    public void setPlace() {
        this.setEndX(ball.getCenterX());
        this.setEndY(ball.getCenterY());
    }
}
