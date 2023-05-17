package view.enums;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;

public enum Message {
    // Login menu
    EMPTY_FIELD("Please fill all fields", Color.YELLOW),
    NOT_MATCH("username and password didn't match!", Color.ORANGERED),
    USERNAME_EXISTS("This username is already in use", Color.ORANGERED),
    LOW_PASSWORD("Your password should have at least 4 characters!", Color.DARKCYAN),
    LOW_USERNAME("Your username should have at least 4 characters!", Color.DARKCYAN),
    REGISTER_SUCCESS("Successfully registered; You can log in now!", Color.LIGHTGREEN),

    // Register menu
    ALL_EMPTY("All fields are empty!", Color.YELLOW),
    CHANGE_SUCCESS("Changes applied successfully", Color.LIGHTGREEN),
    ;
    private final String message;
    private final Color color;

    Message(String message, Color color) {
        this.message = message;
        this.color = color;
    }

    public void sendMessage(Label result) {
        result.setText(this.message);
        result.setTextFill(this.color);
    }
}
