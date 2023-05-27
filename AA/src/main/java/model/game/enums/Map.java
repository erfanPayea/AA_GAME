package model.game.enums;

import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.Objects;

public enum Map {
    MAP_1("Space", "space-bg.png", Color.BLACK),
    MAP_2("Fruit", "fruit-bg.png", Color.ALICEBLUE),
    MAP_3("Nature", "nature-bg.png", Color.INDIANRED),
    ;

    public static Map getMapByName(String name) {
        for (Map map : values()) {
            if (map.name.equals(name))
                return map;
        }
        return null;
    }

    private final String name;
    private final BackgroundImage backgroundImage;
    private final Color color;

    Map(String name, String backgroundName, Color color) {
        this.name = name;
        this.backgroundImage = new BackgroundImage(new Image(Objects.requireNonNull(Map.class.getResource(
                "/images/background/" + backgroundName)).toExternalForm(), 1554, 830, false, false),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public BackgroundImage getBackgroundImage() {
        return backgroundImage;
    }

    public Color getColor() {
        return this.color;
    }
}
