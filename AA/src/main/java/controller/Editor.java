package controller;

import model.User;
import model.game.Settings;
import view.enums.HotKeys;
import view.enums.Message;

import java.io.File;


public class Editor {
    private final User currentUser;

    public Editor() {
        this.currentUser = User.getCurrentUser();
    }


    public Message saveProfileChanges(String username, String password, String avatar, File avatarFile) {
        if (currentUser.getPassword() == null)
            return Message.GUEST_PROFILE;

        if (password.isEmpty() && username.isEmpty() && avatarFile == null) {
            if (avatar == null)
                return Message.ALL_EMPTY;
            else if (avatar.isEmpty())
                return Message.ALL_EMPTY;
        }

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

        if (avatar != null && !avatar.isEmpty())
            currentUser.setAvatar(avatar);

        if (avatarFile != null) {
            if (avatar == null)
                currentUser.setAvatar(avatarFile.toString());
            else if (!avatar.isEmpty())
                currentUser.setAvatar(avatarFile.toString());
        }

        User.saveToDatabase(currentUser);
        return Message.CHANGE_SUCCESS;
    }

    public Message saveSettingsChanges(String mapName, String level, String ballsNumber, boolean isMute, String shootKey,
                                       String freezeKey, String leftKey, String rightKey, String shoot2Key, String left2Key, String right2Key) {
        Settings currentSettings = currentUser.getSettings();
        if (mapName == null && level == null && ballsNumber == null && HotKeys.SHOOT.equals(shootKey)
                && HotKeys.FREEZE.equals(freezeKey) && HotKeys.LEFT.equals(leftKey) && HotKeys.RIGHT.equals(rightKey))
            return Message.ALL_EMPTY;

        currentSettings.setMap(mapName);
        currentSettings.setLevel(level);
        currentSettings.setBallNumbers(ballsNumber);
        currentSettings.setMute(isMute);

        HotKeys.SHOOT.setKeyName(shootKey);
        HotKeys.FREEZE.setKeyName(freezeKey);
        HotKeys.LEFT.setKeyName(leftKey);
        HotKeys.RIGHT.setKeyName(rightKey);
        HotKeys.PLAYER2SHOOT.setKeyName(shoot2Key);
        HotKeys.LEFT2.setKeyName(left2Key);
        HotKeys.RIGHT2.setKeyName(right2Key);

        currentSettings.setHotKeys(shootKey, shoot2Key, freezeKey, leftKey, rightKey, left2Key, right2Key);

        User.saveToDatabase(currentUser);
        return Message.CHANGE_SUCCESS;
    }

}
