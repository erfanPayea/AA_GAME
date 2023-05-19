package model.game.enums;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.util.Objects;

public enum Map {
    MAP_1("Night", new ImageView(new Image(Objects.requireNonNull(Map.class.getResource(
            "/images/backgrounds/white-bg.webp")).toString(), 800 ,600, false, false)),
            Color.BLACK),
    MAP_2("Fruit", null, Color.ORANGE), // todo : not null
    MAP_3("Nature", null, Color.GREEN),
    ;

    public static Map getMapByName(String name) {
        for (Map map : values()) {
            if (map.name.equals(name))
                return map;
        }
        return null;
    }

    private final String name;
    private final ImageView background;
    private final Color color;

    Map(String name, ImageView background, Color color) {
        this.name = name;
        this.background = background;
        this.color = color;
    }

    public String getName() {
        return name;
    }
    public ImageView getBackground() {
        return background;
    }

    public Color getColor() {
        return this.color;
    }
}
