package model.thing;

import controller.GameMenuController;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import model.User;

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

    public void receiveBall(Ball ball) throws Exception {
        this.balls.add(ball);
        Line line = new Line(this.getCenterX(), this.getCenterY(), ball);
        this.lines.add(line);
        this.pane.getChildren().add(line);

        System.out.println(this.balls.size());

        if (this.balls.size() == User.currentUser.getSettings().getBallNumbers())
            GameMenuController.getGameMenuController().winGame();
    }

    public ArrayList<Ball> getBalls() {
        return balls;
    }
}
