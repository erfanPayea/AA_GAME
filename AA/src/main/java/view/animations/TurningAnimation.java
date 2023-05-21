package view.animations;

import javafx.animation.Transition;
import javafx.scene.Node;

public class TurningAnimation extends Transition {
    private final Node node;
    private final int rotationSpeed ;
    private final double windSpeed;
    private final int freezTime;

    public TurningAnimation(Node node, int rotationSpeed, double windSpeed, int freezTime) {
        this.node = node;
        this.rotationSpeed = rotationSpeed;
        this.windSpeed = windSpeed;
        this.freezTime = freezTime;
    }

    @Override
    protected void interpolate(double v) {

    }
}
