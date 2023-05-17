package controller;

import model.User;
import view.enums.Message;

public class LoginMenuController {
    public Message login(String username, String password, boolean stayLoggedIn) {
        User user = User.getUserByUsername(username);
        if (user == null)
            return Message.NOT_MATCH;
        else if (user.isPasswordIncorrect(password))
            return Message.NOT_MATCH;
        else {
            if (stayLoggedIn)
                User.setStayLoggedIn(user);

            User.currentUser = user;
            return null;
        }
    }

    public Message register(String username, String password) {
        if (username == null || username.length() < 4)
            return Message.LOW_USERNAME;
        else if (User.getUserByUsername(username) != null)
            return Message.USERNAME_EXISTS;
        else if (password == null || password.length() < 4)
            return Message.LOW_PASSWORD;
        else {
            new User(username, password);
            return Message.REGISTER_SUCCESS;
        }
    }
}