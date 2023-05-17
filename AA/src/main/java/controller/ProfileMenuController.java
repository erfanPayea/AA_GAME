package controller;

import model.User;
import view.enums.Message;

import java.util.Objects;

public class ProfileMenuController {
    private User currentUser;

    public ProfileMenuController() {
        this.currentUser = User.currentUser;
    }

    public Message saveChanges(String username, String password) { // todo : avatar
        if (password == null && username == null)
            return Message.EMPTY_FIELD;

        if (username != null) {
            if (username.length() < 4)
                return Message.LOW_USERNAME;
            currentUser.setUsername(username);
        }
        if (password != null) {
            if (password.length() < 4)
                return Message.LOW_PASSWORD;
            currentUser.setPassword(password);
        }

        return Message.CHANGE_SUCCESS;
    }
}
