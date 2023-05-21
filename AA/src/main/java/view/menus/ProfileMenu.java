package view.menus;

import controller.Editor;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.User;
import view.enums.Message;

import java.net.URL;
import java.util.Objects;

public class ProfileMenu extends Application {
    private static Stage stage;
    private final Editor editor;
    @FXML
    private Label password;
    @FXML
    private TextField newPassword;
    @FXML
    private Label username;
    @FXML
    private TextField newUsername;
    @FXML
    private Label result;

    {
        editor = new Editor();
    }

    @Override
    public void start(Stage stage) throws Exception {
        ProfileMenu.stage = stage;
        URL url = new URL(Objects.requireNonNull(this.getClass().getResource("/FXML/profileMenu.fxml")).toExternalForm());
        AnchorPane pane = FXMLLoader.load(url);
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void initialize() {
        this.username.setText(User.getCurrentUser().getUsername());
        this.password.setText(User.getCurrentUser().getPassword());
        Message.DEFAULT.sendMessage(this.result);
        // todo : avatar
    }

    public void saveChanges() throws Exception {
        Message message = this.editor.saveProfileChanges(this.newUsername.getText(), this.newPassword.getText());
        message.sendMessage(this.result);

        this.username.setText(User.getCurrentUser().getUsername());
        this.password.setText(User.getCurrentUser().getPassword());
    }

    public void backMainMenu() throws Exception {
        new MainMenu().start(stage);
    }

    // todo : add alert for these :
    public void logout() throws Exception {
        User.setStayLoggedIn(null);
        new LoginMenu().start(stage);
    }

    public void deleteAccount() throws Exception {
        User.remove();
        new LoginMenu().start(stage);
    }
}
