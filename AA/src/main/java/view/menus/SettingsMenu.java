package view.menus;

import controller.Editor;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.User;
import model.game.Settings;
import model.game.enums.Level;
import model.game.enums.Map;
import view.enums.HotKeys;
import view.enums.Message;

import java.net.URL;
import java.util.Objects;

public class SettingsMenu extends Application {
    private static Stage stage;
    private final Editor editor;
    private final Settings currentSettings;
    @FXML
    private ChoiceBox<String> mapChoiceBox;
    @FXML
    private ChoiceBox<String> levelNumberChoiceBox;
    @FXML
    private ChoiceBox<String> ballsNumberChoiceBox;
    @FXML
    private Label result;
    @FXML
    private CheckBox isMute;
    @FXML
    private TextField shootKey;
    @FXML
    private TextField shoot2Key;
    @FXML
    private TextField freezeKey;
    @FXML
    private TextField rightKey;
    @FXML
    private TextField leftKey;
    @FXML
    private TextField right2Key;
    @FXML
    private TextField left2Key;

    {
        editor = new Editor();
        currentSettings = User.getCurrentUser().getSettings();
    }

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
        // current settings show :
        currentSettings.updateHotkeys();
        this.isMute.setSelected(currentSettings.isMute());

        for (Map map : Map.values())
            this.mapChoiceBox.getItems().add(map.getName());
        this.mapChoiceBox.setValue(this.currentSettings.getMap().getName());

        for (Level level : Level.values())
            this.levelNumberChoiceBox.getItems().add(String.valueOf(level.getNumber()));
        this.levelNumberChoiceBox.setValue(String.valueOf(this.currentSettings.getLevel().getNumber()));

        for (int ballNumber = 16; ballNumber < 26; ballNumber++)
            this.ballsNumberChoiceBox.getItems().add(String.valueOf(ballNumber));
        this.ballsNumberChoiceBox.setValue(String.valueOf(this.currentSettings.getBallNumbers()));

        this.leftKey.setText(HotKeys.LEFT.getKeyName());
        this.rightKey.setText(HotKeys.RIGHT.getKeyName());
        this.left2Key.setText(HotKeys.LEFT2.getKeyName());
        this.right2Key.setText(HotKeys.RIGHT2.getKeyName());
        this.freezeKey.setText(HotKeys.FREEZE.getKeyName());
        this.shootKey.setText(HotKeys.SHOOT.getKeyName());
        this.shoot2Key.setText(HotKeys.PLAYER2SHOOT.getKeyName());

        Message.DEFAULT.sendMessage(this.result);
    }

    @FXML
    public void saveChanges() {
        Message message = editor.saveSettingsChanges(this.mapChoiceBox.getValue(), this.levelNumberChoiceBox.getValue(),
                ballsNumberChoiceBox.getValue(), isMute.isSelected(), this.shootKey.getText(), this.freezeKey.getText(),
                this.leftKey.getText(), this.rightKey.getText(), shoot2Key.getText(), left2Key.getText(), right2Key.getText());

        this.isMute.setSelected(currentSettings.isMute());
        message.sendMessage(this.result);
    }

    @FXML
    public void backMainMenu() throws Exception {
        new MainMenu().start(stage);
    }
}
