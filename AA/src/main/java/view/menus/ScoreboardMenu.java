package view.menus;

import controller.ScoreboardController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.User;
import model.game.enums.Level;

import java.net.URL;
import java.util.LinkedList;
import java.util.Objects;

public class ScoreboardMenu extends Application {
    private static Stage stage;
    private final ScoreboardController controller;
    @FXML
    private ChoiceBox<String> levelChoiceBox;
    @FXML
    private VBox users;
    @FXML
    private VBox highScores;
    @FXML
    private VBox rankNumbers;

    {
        controller = new ScoreboardController();
    }

    @Override
    public void start(Stage stage) throws Exception {
        ScoreboardMenu.stage = stage;
        URL url = new URL(
                Objects.requireNonNull(this.getClass().getResource("/FXML/scoreboardMenu.fxml")).toExternalForm());

        AnchorPane pane = FXMLLoader.load(url);
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void initialize() {
        levelChoiceBox.getItems().add("All");
        for (Level level : Level.values()) {
            levelChoiceBox.getItems().add(String.valueOf(level.getNumber()));
        }
        levelChoiceBox.setValue("ALL");
        setTable();
    }


    private void setTable() {
        int level;
        try {
            level = Integer.parseInt(this.levelChoiceBox.getValue());
        } catch (Exception ex) {
            level = 0;
        }

        LinkedList<User> rankUsers = controller.getRanks(level);

        int size = rankNumbers.getChildren().size();
        if (size > 1) {
            rankNumbers.getChildren().remove(1, size);
            users.getChildren().remove(1, size);
            highScores.getChildren().remove(1, size);
        }

        for (int index = 0; index < rankUsers.size(); index++) {
            Label rankLabel = new Label(String.valueOf(index + 1));
            setLabel(rankLabel, rankNumbers, index + 1);
            rankNumbers.getChildren().add(rankLabel);

            Label userLabel = new Label(rankUsers.get(index).getUsername());
            setLabel(userLabel, users, index + 1);
            users.getChildren().add(userLabel);

            Label scoreLabel = new Label(String.valueOf(rankUsers.get(index).getHighScores()[level]));
            setLabel(scoreLabel, highScores, index + 1);
            highScores.getChildren().add(scoreLabel);
        }
    }

    private void setLabel(Label label, VBox parent, int rank) {
        if (rank == 1)
            label.setBackground(new Background(new BackgroundFill(Color.GOLD, CornerRadii.EMPTY, Insets.EMPTY)));
        else if (rank == 2)
            label.setBackground(new Background(new BackgroundFill(Color.SILVER, CornerRadii.EMPTY, Insets.EMPTY)));
        else if (rank == 3)
            label.setBackground(new Background(new BackgroundFill(Color.BROWN, CornerRadii.EMPTY, Insets.EMPTY)));

        label.setAlignment(Pos.CENTER);
        label.setPrefWidth(parent.getPrefWidth());
        label.setPrefHeight(10);
    }

    @FXML
    private void sort() {
        setTable();
    }

    @FXML
    private void goMainMenu() throws Exception {
        new MainMenu().start(stage);
    }

}
