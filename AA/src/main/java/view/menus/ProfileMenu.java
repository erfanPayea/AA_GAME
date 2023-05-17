package view.menus;

import controller.ProfileMenuController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import model.User;

import java.net.URL;
import java.util.Objects;

public class ProfileMenu extends Application {
    private static Stage stage;
    private ProfileMenuController controller;
    @FXML
    private Label password;
    @FXML
    private TextField newPassword;
    @FXML
    private Label username;
    @FXML
    private TextField newUsername;
    @FXML
    private Label message;
    {
        controller = new ProfileMenuController();
    }
    @Override
    public void start(Stage stage) throws Exception {
        ProfileMenu.stage = stage;
        URL url = new URL(Objects.requireNonNull(this.getClass().getResource("/FXML/profileMenu.fxml")).toExternalForm());
        AnchorPane pane = FXMLLoader.load(url);
        Text name = new Text(0, 20, User.currentUser.getUsername() + "'s profile'");
        name.setFill(Color.RED);
        name.setTextAlignment(TextAlignment.CENTER);
        name.setFont(Font.font(20));
        pane.getChildren().add(name);
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void initialize() {
        message.setText("This is a label for show Messages to you!");
        username.setText(User.currentUser.getUsername());
        password.setText(User.currentUser.getPassword());
        // todo : avatar
    }

    public void saveChanges() {
        this.message.setText(controller.saveChanges(newUsername.getText(), newPassword.getText()).toString());
        username.setText(User.currentUser.getUsername());
        password.setText(User.currentUser.getPassword());
    }

    public void backMainMenu() throws Exception {
        new MainMenu().start(stage);
    }

    public void logout() throws Exception {
        User.currentUser = null;
        new LoginMenu().start(stage);
    }

    public void deleteAccount() throws Exception {
        User.removeUser(User.currentUser);
        User.currentUser = null;
        new LoginMenu().start(stage);
    }
}
