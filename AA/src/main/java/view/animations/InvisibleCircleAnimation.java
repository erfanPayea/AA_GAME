package view.animations;

import javafx.animation.Transition;
import model.thing.InvisibleCircle;

public class InvisibleCircleAnimation extends Transition {
    private final InvisibleCircle invisibleCircle;
    private double rotationSpeed;
    private final double widSpeed;
    private final int freezeTime;

    public InvisibleCircleAnimation(InvisibleCircle invisibleCircle, int rotationSpeed, double windSpeed, int freezeTime) {
        this.invisibleCircle = invisibleCircle;
        this.rotationSpeed = 6.28 * invisibleCircle.getRadius() * (((double) rotationSpeed) / 60);
        this.widSpeed = windSpeed;
        this.freezeTime = freezeTime;
    }
    @Override
    protected void interpolate(double v) {

    }
}
