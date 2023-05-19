package model.thing;

public class Line extends javafx.scene.shape.Line {
    private final Ball ball;

    public Line(double v, double v1, Ball ball) {
        super(v, v1, ball.getCenterX() - v, ball.getCenterY() - v1);
        this.ball = ball;
    }

    public void setPlace() {
        this.setStartX(ball.getCenterX() - this.getLayoutX());
        this.setStartY(ball.getCenterY() - this.getLayoutY());
    }
}
