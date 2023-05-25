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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.User;
import view.enums.Message;

import java.io.File;
import java.net.URL;
import java.util.Objects;

public class ProfileMenu extends Application {
    private static Stage stage;
    private final User currentUser;
    private final Editor editor;
    private File avatarFile;
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
        currentUser = User.getCurrentUser();
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
        this.username.setText(this.currentUser.getUsername());
        this.password.setText(this.currentUser.getPassword());

        for (int i = 1; i < 4; i++)
            avatarChoiceBox.getItems().add(String.valueOf(i));

        this.avatar1.setImage(new Image(Objects.requireNonNull(
                this.getClass().getResource("/images/avatar/avatar1.png")).toExternalForm()));
        this.avatar2.setImage(new Image(Objects.requireNonNull(
                this.getClass().getResource("/images/avatar/avatar2.png")).toExternalForm()));
        this.avatar3.setImage(new Image(Objects.requireNonNull(
                this.getClass().getResource("/images/avatar/avatar3.png")).toExternalForm()));

        if (this.currentUser.getAvatar().length() == 1)
            this.avatar.setImage(new Image(Objects.requireNonNull(this.getClass().getResource(
                    "/images/avatar/avatar"+ User.getCurrentUser().getAvatar() +".png")).toExternalForm()));
        else this.avatar.setImage(new Image(currentUser.getAvatar()));

        Message.DEFAULT.sendMessage(this.result);
    }

    public void saveChanges() {
        Message message = this.editor.saveProfileChanges(this.newUsername.getText(), this.newPassword.getText(),
                avatarChoiceBox.getValue(), avatarFile);
        message.sendMessage(this.result);

        this.username.setText(this.currentUser.getUsername());
        this.password.setText(this.currentUser.getPassword());

        if (avatarFile == null)
            this.avatar.setImage(new Image(Objects.requireNonNull(this.getClass().getResource(
                    "/images/avatar/avatar"+ User.getCurrentUser().getAvatar() +".png")).toExternalForm()));

        else this.avatar.setImage(new Image(avatarFile.toString()));
    }

    public void backMainMenu() throws Exception {
        new MainMenu().start(stage);
    }

    public void logout() throws Exception {
        User.setStayLoggedIn(null);
        new LoginMenu().start(stage);
    }

    public void deleteAccount() throws Exception {
        User.remove();
        new LoginMenu().start(stage);
    }

    public void chooseImage() {
        FileChooser fileChooser = new FileChooser();
        this.avatarFile = fileChooser.showOpenDialog(new Stage());
    }
}