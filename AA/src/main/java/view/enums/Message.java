package view.enums;

public enum Message {
    // Login menu
    NOT_MATCH("username and password didn't match!"),
    USERNAME_EXISTS("This username is already in use"),
    LOW_PASSWORD("Your password should have at least 4 characters!"),
    LOW_USERNAME("Your username should have at least 4 characters!"),
    REGISTER_SUCCESS("Successfully registered; You can log in now!"),

    // Register menu
    EMPTY_FIELD("All fields are empty!"),
    CHANGE_SUCCESS("Changes applied successfully"),
    ;
    private final String message;

    Message(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return this.message;
    }
}
