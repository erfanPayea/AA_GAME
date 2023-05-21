package controller;

import model.User;
import model.game.Settings;
import view.enums.Message;


public class Editor {
    private final User currentUser;

    public Editor() {
        this.currentUser = User.getCurrentUser();
    }


    public Message saveProfileChanges(String username, String password) { // todo : avatar
        if (currentUser.getPassword() == null)
            return Message.GUEST_PROFILE;

        if (password.isEmpty() && username.isEmpty())
            return Message.ALL_EMPTY;

        if (!username.isEmpty()) {
            if (username.length() < 4)
                return Message.LOW_USERNAME;
            if (User.getUserByUsername(username) != null)
                return Message.USERNAME_EXISTS;
            if (!password.isEmpty() && password.length() < 4)
                return Message.LOW_PASSWORD;

            currentUser.setUsername(username);
        }
        if (!password.isEmpty()) {
            if (password.length() < 4)
                return Message.LOW_PASSWORD;
            currentUser.setPassword(password);
        }

        User.saveToDatabase(currentUser);
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

        User.saveToDatabase(currentUser);
        return Message.CHANGE_SUCCESS;
    }

}
