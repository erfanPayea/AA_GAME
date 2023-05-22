module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires javafx.media;

    exports model;
    opens model to com.google.gson;
    exports controller;
    opens controller to javafx.fxml;
    exports view.enums;
    opens view.enums to javafx.fxml;
    exports view.menus;
    opens view.menus to javafx.fxml;
    exports view.animations;
    opens view.animations to javafx.fxml;
    exports model.game;
    opens model.game to com.google.gson;
    exports model.thing;
    opens model.thing to com.google.gson;
    exports model.game.enums;
    opens model.game.enums to com.google.gson;
}