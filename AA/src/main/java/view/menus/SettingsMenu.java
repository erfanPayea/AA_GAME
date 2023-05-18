package view.menus;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.User;
import model.game.Settings;
import model.game.enums.Level;
import model.game.enums.Map;

import java.net.URL;
import java.util.Objects;

public class SettingsMenu extends Application {
    private static Stage stage;
    @FXML
    private Label map;
    @FXML
    private ChoiceBox<String> mapChoiceBox;
    @FXML
    private Label levelNumber;
    @FXML
    private ChoiceBox<Integer> levelNumberChoiceBox;
    @FXML
    private Label ballsNumber;
    @FXML
    private ChoiceBox<Integer> ballsNumberChoiceBox;
    @Override
    public void start(Stage stage) throws Exception {
        SettingsMenu.stage = stage;
        URL url = new URL(
                Objects.requireNonNull(this.getClass().getResource("/FXML/settingsMenu.fxml")).toExternalForm());

        AnchorPane pane = FXMLLoader.load(url);
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void initialize() {
        Settings currentSettings = User.currentUser.getSettings();
        // current settings show :
        this.map.setText(currentSettings.getMap().getName());
        this.levelNumber.setText(String.valueOf(currentSettings.getLevel().getNumber()));
        this.ballsNumber.setText(String.valueOf(currentSettings.getBallNumbers()));

        for (Map map : Map.values()) {
            this.mapChoiceBox.getItems().add(map.getName());
        }
        for (Level level : Level.values()) {
            this.levelNumberChoiceBox.getItems().add(level.getNumber());
        }
        for (int ballNumber = 21; ballNumber < 31; ballNumber++) {
            this.ballsNumberChoiceBox.getItems().add(ballNumber);
        }
    }
}
