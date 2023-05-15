module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;


    opens view to javafx.fxml;
    exports view;
    exports controller;
    opens controller to javafx.fxml;
}