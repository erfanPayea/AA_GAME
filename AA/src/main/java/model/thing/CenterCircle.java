package model.thing;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class CenterCircle extends Circle {
    public CenterCircle(Color color) {
        super(80, color);
        this.setLayoutX(350); this.setLayoutY(200);
    }
}
