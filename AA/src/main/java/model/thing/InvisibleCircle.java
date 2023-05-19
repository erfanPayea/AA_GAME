package model.thing;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

import java.util.ArrayList;

public class InvisibleCircle extends Circle {
    private final Pane pane;
    private final ArrayList<Ball> balls;
    private final ArrayList<Line> lines;
    public InvisibleCircle(double v, double v1, double v2, Pane pane) {
        super(v, v1, v2);
        this.pane = pane;
        this.balls = new ArrayList<>();
        this.lines = new ArrayList<>();
    }

    public void receiveBall(Ball ball) {
        balls.add(ball);
        Line line = new Line(this.getCenterX(), this.getCenterY(), ball);
        lines.add(line);
        pane.getChildren().add(line);
    }

    public ArrayList<Ball> getBalls() {
        return balls;
    }
}
