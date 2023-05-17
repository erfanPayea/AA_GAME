package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import model.User;

public class MainMenuController {
    private User currentUser;

    {
        currentUser = User.currentUser;
    }
}