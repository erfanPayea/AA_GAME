package view.menus;

import controller.LoginMenuController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import view.enums.Message;

import java.net.URL;
import java.util.Objects;

public class LoginMenu extends Application {
    private static Stage stage;
    private final LoginMenuController controller;
    @FXML
    private TextField password;
    public CheckBox statLoggedIn;
    @FXML
    private TextField username;
    @FXML
    private Label message;

    {
        controller = new LoginMenuController();
    }
    public static void main(String[] args) {
//        User.loadUsersFromFile();
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
//        User loggedInUser;
//        if ((loggedInUser = User.loadStayLoggedIn()) != null) {
//            MainMenuController.setCurrentUser(loggedInUser);
//            new MainMenu(loggedInUser).start(stage);
//            return;
//        }

        LoginMenu.stage = stage;
        URL url = LoginMenu.class.getResource("/FXML/loginMenu.fxml");
        BorderPane borderPane = FXMLLoader.load(Objects.requireNonNull(url));
        Scene scene = new Scene(borderPane);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void initialize() {
        message.setText("This is a label for show Messages to you!");
    }

    public void register() {
        message.setText(controller.register(username.getText(), password.getText()).toString());
    }

    public void login() throws Exception {
        Message result = controller.login(username.getText(), password.getText(), statLoggedIn.isSelected());
        if (result == null) {
            new MainMenu().start(stage);
            return;
        }

        this.message.setText(result.toString());
    }
}