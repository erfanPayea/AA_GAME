package view.enums;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;

public enum Message {
    DEFAULT("This is a label for show Messages to you!", Color.DARKCYAN),
    // Login menu
    EMPTY_FIELD("Please fill all fields", Color.YELLOW),
    NOT_MATCH("username and password didn't match!", Color.ORANGERED),
    USERNAME_EXISTS("This username is already in use", Color.ORANGERED),
    LOW_PASSWORD("Your password should have at least 4 characters!", Color.DARKCYAN),
    LOW_USERNAME("Your username should have at least 4 characters!", Color.DARKCYAN),
    GUEST_ERROR("You can't stay logged in with a guest", Color.ORANGERED),
    GUEST_PROFILE("Changing fields is not available for guests!", Color.DARKCYAN),
    REGISTER_SUCCESS("Successfully registered; You can log in now!", Color.LIGHTGREEN),
    // Profile menu
    ALL_EMPTY("All fields are empty!", Color.GOLD),
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
