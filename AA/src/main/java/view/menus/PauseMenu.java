package view.menus;

import controller.GameMenuController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
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
    private Label shootKey;
    @FXML
    private Label freezeKey;
    @FXML
    private Label rightKey;
    @FXML
    private Label leftKey;
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
        this.leftKey.setText(HotKeys.LEFT.toString());
        this.rightKey.setText(HotKeys.RIGHT.toString());
        this.freezeKey.setText(HotKeys.FREEZE.toString());
        this.shootKey.setText(HotKeys.SHOOT.toString());
        this.player2ShootKey.setText(HotKeys.PLAYER2SHOOT.toString());
    }

    @FXML
    private void continueGame() {
        gameMenuController.continueGame();
        stage.close();
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
