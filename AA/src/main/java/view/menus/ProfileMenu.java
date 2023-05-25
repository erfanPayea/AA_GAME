package view.menus;

import controller.Editor;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    private ImageView avatar1;
    @FXML
    private ImageView avatar2;
    @FXML
    private ImageView avatar3;
    @FXML
    private ChoiceBox<String> avatarChoiceBox;
    @FXML
    private Label password;
    @FXML
    private TextField newPassword;
    @FXML
    private Label username;
    @FXML
    private TextField newUsername;
    @FXML
    private ImageView avatar;
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

        for (int i = 1; i < 4; i++)
            avatarChoiceBox.getItems().add(String.valueOf(i));

        this.avatar.setImage(new Image(Objects.requireNonNull(this.getClass().getResource("/images/avatar/avatar" +
                User.getCurrentUser().getAvatarNumber() + ".png")).toExternalForm()));

        this.avatar1.setImage(new Image(Objects.requireNonNull(
                this.getClass().getResource("/images/avatar/avatar1.png")).toExternalForm()));
        this.avatar2.setImage(new Image(Objects.requireNonNull(
                this.getClass().getResource("/images/avatar/avatar2.png")).toExternalForm()));
        this.avatar3.setImage(new Image(Objects.requireNonNull(
                this.getClass().getResource("/images/avatar/avatar3.png")).toExternalForm()));

        Message.DEFAULT.sendMessage(this.result);
    }

    public void saveChanges() {
        Message message = this.editor.saveProfileChanges(this.newUsername.getText(), this.newPassword.getText(),
                avatarChoiceBox.getValue());
        message.sendMessage(this.result);

        this.username.setText(User.getCurrentUser().getUsername());
        this.password.setText(User.getCurrentUser().getPassword());
        this.avatar.setImage(new Image(Objects.requireNonNull(this.getClass().getResource(
                "/images/avatar/avatar"+ User.getCurrentUser().getAvatarNumber() +".png")).toExternalForm()));
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
