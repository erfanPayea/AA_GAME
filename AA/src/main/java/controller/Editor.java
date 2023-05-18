package controller;

import model.User;
import model.game.Settings;
import view.enums.Message;

import java.security.SecureRandom;

public class Editor {
    private final User currentUser;

    public Editor() {
        this.currentUser = User.currentUser;
    }


    public Message saveProfileChanges(String username, String password) { // todo : avatar
        if (password.isEmpty() && username.isEmpty())
            return Message.ALL_EMPTY;

        if (!username.isEmpty()) {
            if (username.length() < 4)
                return Message.LOW_USERNAME;
            if (!password.isEmpty() && password.length() < 4)
                return Message.LOW_PASSWORD;

            currentUser.setUsername(username);
        }
        if (!password.isEmpty()) {
            if (password.length() < 4)
                return Message.LOW_PASSWORD;
            currentUser.setPassword(password);
        }

        saveToDatabase();
        return Message.CHANGE_SUCCESS;
    }

    public Message saveSettingsChanges(String mapName, String level, String ballsNumber, boolean isMute) {
        Settings currentSettings = currentUser.getSettings();
        if (mapName == null && level == null && ballsNumber == null)
            return Message.ALL_EMPTY;

        currentSettings.setMap(mapName);
        currentSettings.setLevel(level);
        currentSettings.setBallNumbers(ballsNumber);
        currentSettings.setMute(isMute);

        saveToDatabase();
        return Message.CHANGE_SUCCESS;
    }

    public void saveToDatabase() {
        if (User.loadStayLoggedIn() != null)
            User.setStayLoggedIn(currentUser);

        User.saveUsersToFile();
    }
}
