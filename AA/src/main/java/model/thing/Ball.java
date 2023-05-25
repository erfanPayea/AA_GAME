package model.thing;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
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
        if (color != Color.ALICEBLUE)
            this.setStroke(Color.ALICEBLUE);
        else this.setStroke(Color.BLACK);
        this.setStrokeWidth(2);
    }

    public Ball(double angle, Color color) {
        super(radius, color);
        number = 0;
        this.color = color;
        if (color != Color.ALICEBLUE)
            this.setStroke(Color.ALICEBLUE);
        else this.setStroke(Color.BLACK);
        this.setStrokeWidth(2);

        this.setCenterX(350 + 160 * Math.cos(Math.toRadians(angle)));
        this.setCenterY(450 + 160 * Math.sin(Math.toRadians(angle)));
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

    public void setTurningAnimation() {
        this.turningAnimation = new TurningAnimation(this, 90);
    }

    public void setTurningAnimation(double firstAngle) {
        this.turningAnimation = new TurningAnimation(this, firstAngle);
    }

    public void setLine(Line line) {
        this.line = line;
    }

    public void setLineLocation() {
        this.line.setEndX(this.getCenterX());
        this.line.setEndY(this.getCenterY());
    }
}
