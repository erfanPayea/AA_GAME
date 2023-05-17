module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;


    exports model;
    opens model to com.google.gson;
    exports controller;
    opens controller to javafx.fxml;
    exports view.enums;
    opens view.enums to javafx.fxml;
    exports view.menus;
    opens view.menus to javafx.fxml;
}