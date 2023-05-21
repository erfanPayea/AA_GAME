package model.thing;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import view.animations.ShootingAnimation;
import view.animations.TurningAnimation;

public class Ball extends Circle {
    private final static int radius;
    private final int number;
    private final Color color;
    private Line line;
    private TurningAnimation turningAnimation;

    static {
        radius = 10;
    }

    public Ball(int number, Color color) {
        super(radius, color);
        this.number = number;
        this.color = color;
        this.setAccessibleText(String.valueOf(number));
        if (color != Color.ALICEBLUE)
            this.setStroke(Color.ALICEBLUE);
        else this.setStroke(Color.BLACK);
        this.setStrokeWidth(2);
    }

    public Ball(double centerX, double centerY, int number, Color color) {
        super(centerX, centerY, radius, color);
        this.number = number;
        this.color = color;
        this.setAccessibleText(String.valueOf(number));
        if (color != Color.ALICEBLUE)
            this.setStroke(Color.ALICEBLUE);
        else this.setStroke(Color.BLACK);
        this.setStrokeWidth(2);
    }

    public int getNumber() {
        return this.number;
    }

    public Color getColor() {
        return color;
    }

    public TurningAnimation getTurningAnimation() {
        return turningAnimation;
    }

    public void setTurningAnimation(int rotationSpeed, double windSpeed, int freezeTime) {
        this.turningAnimation = new TurningAnimation(this, rotationSpeed, windSpeed, freezeTime);
    }

    public void setLine(Line line) {
        this.line = line;
    }

    public Line getLine() {
        return line;
    }
}
