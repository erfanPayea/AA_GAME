package view.menus;

import controller.GameMenuController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import view.enums.HotKeys;

import java.net.URL;
import java.util.Objects;

public class PauseMenu extends Application {
    private static Stage stage;
    private final GameMenuController gameMenuController;

    @FXML
    private ChoiceBox<String> musicList;
    @FXML
    private Label shootKey;
    @FXML
    private Label freezeKey;
    @FXML
    private Label rightKey;
    @FXML
    private Label leftKey;
    @FXML
    private Label right2Key;
    @FXML
    private Label left2Key;
    @FXML
    private Label player2ShootKey;

    {
        gameMenuController = GameMenuController.getGameMenuController();
    }

    @Override
    public void start(Stage stage) throws Exception {
        PauseMenu.stage = stage;
        URL url = new URL(Objects.requireNonNull(this.getClass().getResource("/FXML/pauseMenu.fxml")).toExternalForm());
        AnchorPane pane = FXMLLoader.load(url);
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void initialize() {
        this.musicList.getItems().add("Gta");
        this.musicList.getItems().add("HipHop");
        this.musicList.getItems().add("Windles");

        this.leftKey.setText(HotKeys.LEFT.getKeyName());
        this.rightKey.setText(HotKeys.RIGHT.getKeyName());
        this.left2Key.setText(HotKeys.LEFT2.getKeyName());
        this.right2Key.setText(HotKeys.RIGHT2.getKeyName());
        this.freezeKey.setText(HotKeys.FREEZE.getKeyName());
        this.shootKey.setText(HotKeys.SHOOT.getKeyName());
        this.player2ShootKey.setText(HotKeys.PLAYER2SHOOT.getKeyName());
    }

    @FXML
    private void pauseMusic() {
        gameMenuController.pauseMusic();
    }

    @FXML
    private void playMusic() {
        gameMenuController.playMusic();
    }

    @FXML
    private void continueGame() {
        gameMenuController.continueGame();
        stage.close();
    }

    @FXML
    private void setMusic() {
        gameMenuController.changeMusic(musicList.getValue());
    }

    @FXML
    private void restartGame() throws Exception {
        gameMenuController.restartGame();
    }

    @FXML
    private void exitGame() throws Exception {
        gameMenuController.finishGame();
    }
}
