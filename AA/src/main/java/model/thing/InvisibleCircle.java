package model.thing;

import controller.GameMenuController;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import model.User;
import model.game.Settings;

import java.util.ArrayList;

public class InvisibleCircle extends Circle {
    private final Pane pane;
    private final Group balls;
    private int defaultBallsNumber;
    private final Settings settings;

    public InvisibleCircle(double v, double v1, double v2, Pane pane, Settings settings) {
        super(v, v1, v2);
        this.pane = pane;
        this.settings = settings;
        this.balls = new Group();
        this.setDefaultBalls();
        this.pane.getChildren().add(this);
        this.pane.getChildren().add(this.balls);
    }

    private void setDefaultBalls() {
        this.defaultBallsNumber = 1;
        switch (settings.getMap().getName()) {
            case "Space" -> defaultBallsNumber = 5;
            case "Fruit" -> defaultBallsNumber = 6;
            case "Nature" -> defaultBallsNumber = 7;
        }

        for (int i = 0; i < defaultBallsNumber; i++) {
            double angle = (double) (i * 360) / (double) defaultBallsNumber;
            Ball ball = new Ball(angle, this.settings.getMap().getColor());

            ball.setTurningAnimation(angle);
            balls.getChildren().add(ball);

            Line line = new Line(this.getCenterX(), this.getCenterY(), ball);
            this.pane.getChildren().add(line);
        }
    }

    public void receiveBall(Ball ball) throws Exception {
        if (!this.balls.getChildren().contains(ball)) {
            GameMenuController.getGameMenuController().doReceiveAnimation();

            pane.getChildren().remove(ball);
            this.balls.getChildren().add(ball);

            Line line = new Line(this.getCenterX(), this.getCenterY(), ball);
            this.pane.getChildren().add(line);

            ball.setTurningAnimation();
            ball.getTurningAnimation().play();

            if (this.balls.getChildren().size() == User.getCurrentUser().getSettings().getBallNumbers() + defaultBallsNumber)
                GameMenuController.getGameMenuController().winGame();
        }
    }

    public ArrayList<Ball> getBalls() {
        ArrayList<Ball> ballArrayList = new ArrayList<>();
        for (Node node : balls.getChildren()) {
            if (node instanceof Ball ball) {
                ballArrayList.add(ball);
            }
        }
        return ballArrayList;
    }

    public void play() {
        try {
            for (Node node : this.balls.getChildren()) {
                if (node instanceof Ball ball)
                    ball.getTurningAnimation().play();
            }
        } catch (Exception ignored) {

        }
    }

    public void playFreeze() {
        try {
            for (Node node : this.balls.getChildren()) {
                if (node instanceof Ball ball)
                    ball.getTurningAnimation().playFreeze();
            }
        }
        catch (Exception ignored) {

        }
    }

    public void stop() {
        try {
            for (Node node : this.balls.getChildren()) {
                if (node instanceof Ball ball)
                    ball.getTurningAnimation().stop();
            }
        } catch (Exception ignored) {
        }
    }
}
