package view.menus;

import controller.GameMenuController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.User;

import java.net.URL;
import java.util.Objects;

public class FinishMenu extends Application {
    private static Stage stage;
    @FXML
    private Label winOrLoose;
    @FXML
    private Label score;

    private final User currentUser;

    {
        currentUser = User.getCurrentUser();
    }

    @Override
    public void start(Stage stage) throws Exception {
        FinishMenu.stage = stage;
        URL url = new URL(Objects.requireNonNull(this.getClass().getResource("/FXML/finishMenu.fxml")).toExternalForm());
        AnchorPane pane = FXMLLoader.load(url);
        Scene scene = new Scene(pane);
        FinishMenu.stage.setScene(scene);

        FinishMenu.stage.show();
    }

    @FXML
    private void initialize() {
        if (currentUser.hasWinLastGame) {
            this.winOrLoose.setText("You win!");
            this.winOrLoose.setTextFill(Color.GREEN);
        } else {
            this.winOrLoose.setText("You loose!");
            this.winOrLoose.setTextFill(Color.ORANGERED);
        }

        this.score.setText(String.valueOf(currentUser.getLastGameScore()));
    }

    @FXML
    private void restart() throws Exception {
        GameMenuController.getGameMenuController().restartGame();
        FinishMenu.stage.close();
    }

    @FXML
    private void goMainMenu() throws Exception {
        FinishMenu.stage.close();
        GameMenuController.getGameMenuController().finishGame();
    }
}