package model.thing;

import view.animations.TurningAnimation;

public class Line extends javafx.scene.shape.Line {
    private final Ball ball;
    private TurningAnimation turningAnimation;

    public Line(double v, double v1, Ball ball) {
        super(v, v1, ball.getCenterX(), ball.getCenterY());
        this.ball = ball;
        this.setStroke(ball.getColor());
        this.ball.setLine(this);
    }

    public void setPlace() {
        this.setEndX(ball.getCenterX());
        this.setEndY(ball.getCenterY());
    }

    public TurningAnimation getTurningAnimation() {
        return turningAnimation;
    }

//    public void setTurningAnimation(int rotationSpeed, double windSpeed, int freezeTime) {
//        this.turningAnimation = new TurningAnimation(this, rotationSpeed, windSpeed, freezeTime);
//    }
}
