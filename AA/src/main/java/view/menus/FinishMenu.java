package view.menus;

import controller.GameMenuController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.User;

import java.net.URL;
import java.util.Objects;

public class FinishMenu extends Application {
    private static Stage stage;
    @FXML
    private Label winOrLoose;
    @FXML
    private Label score;
    @FXML
    private Label timePlayed;
    private final User currentUser;
    {
        currentUser = User.currentUser;
    }
    @Override
    public void start(Stage stage) throws Exception {
        FinishMenu.stage = stage;
        URL url = new URL(Objects.requireNonNull(this.getClass().getResource("/FXML/finishMenu.fxml")).toExternalForm());
        AnchorPane pane = FXMLLoader.load(url);
        Scene scene = new Scene(pane);
        stage.setScene(scene);

        stage.show();
    }

    @FXML
    private void initialize() {
        if (currentUser.hasWinLastGame) {
            this.winOrLoose.setText("You win!");
            this.winOrLoose.setTextFill(Color.GREEN);
        }
        else {
            this.winOrLoose.setText("You loose!");
            this.winOrLoose.setTextFill(Color.ORANGERED);
        }

        this.score.setText(String.valueOf(currentUser.getLastGameScore()));
        this.timePlayed.setText(String.valueOf(currentUser.lastTimePlayed));
    }

    @FXML
    private void restart() throws Exception {
        GameMenuController.getGameMenuController().restartGame();
        stage.close();
    }

    @FXML
    private void goMainMenu() throws Exception {
        GameMenuController.getGameMenuController().finishGame();
        stage.close();
    }
}