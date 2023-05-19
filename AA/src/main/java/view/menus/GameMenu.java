package view.menus;

import controller.GameMenuController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.game.Settings;
import model.thing.Ball;

import java.util.ArrayList;

public class GameMenu extends Application {
    private static Stage stage;
    private final GameMenuController controller;
    private VBox ballsGroup;

    public GameMenu(Settings settings) {
        this.controller = new GameMenuController(this, settings);
    }

    @Override
    public void start(Stage stage) throws Exception {
        GameMenu.stage = stage;
        Pane pane = new Pane();
        pane.setPrefWidth(700); pane.setPrefHeight(550);
        controller.createAndAddCenterCircle(pane);

        this.ballsGroup = createBallsGroup(pane);
        pane.getChildren().add(ballsGroup);

        Scene scene = new Scene(pane);
        stage.setScene(scene);

        this.ballsGroup.requestFocus();
        stage.show();
    }

    private VBox createBallsGroup(Pane pane) {
        VBox ballsGroup = controller.createBallsGroup(pane);

        ballsGroup.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                String keyName = keyEvent.getCode().getName();

                if (keyName.equals("Space"))
                    controller.shoot(pane);
            }
        });
        return ballsGroup;
    }

    public void shootFirst() {
        this.ballsGroup.getChildren().remove(0);
    }
}
