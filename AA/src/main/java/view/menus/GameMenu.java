package view.menus;

import controller.GameMenuController;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.User;
import model.game.Settings;


public class GameMenu extends Application {
    private static Stage stage;
    private final GameMenuController controller;
    private Scene scene;
    private HBox remainingBallsHBox;
    private VBox ballsGroupVBox;

    public GameMenu(Settings settings) {
        this.controller = new GameMenuController(this, settings);
    }

    @Override
    public void start(Stage stage) throws Exception {
        GameMenu.stage = stage;
        Pane pane = new Pane();
        pane.setPrefWidth(700); pane.setPrefHeight(550);
        controller.createAndAddCenterCircle(pane);

        this.ballsGroupVBox = createBallsGroup(pane);
        pane.getChildren().add(ballsGroupVBox);

        this.remainingBallsHBox = createRemainingBalls();
        pane.getChildren().add(this.remainingBallsHBox);

        pane.setBackground(new Background(User.currentUser.getSettings().getMap().getBackgroundImage()));
        createPauseButton(pane);

        this.scene = new Scene(pane);
        stage.setScene(scene);

        this.ballsGroupVBox.requestFocus();
        stage.show();
    }

    private void createPauseButton(Pane pane) {
        Button pauseButton = new Button("Pause");
        pauseButton.setOnMouseClicked(new EventHandler() {

            @Override
            public void handle(Event event) {
                try {
                    pause();
                    controller.pause(pane);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        pauseButton.setLayoutX(600); pauseButton.setLayoutY(2);
        pauseButton.setPrefHeight(30); pauseButton.setPrefWidth(80);
        pauseButton.setTextFill(Color.WHITE);
        pauseButton.setBackground(new Background(new BackgroundFill(Color.CHOCOLATE, CornerRadii.EMPTY, Insets.EMPTY)));

        pane.getChildren().add(pauseButton);
    }

    private VBox createBallsGroup(Pane pane) {
        VBox ballsGroup = controller.createBallsGroup(pane);

        ballsGroup.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                String keyName = keyEvent.getCode().getName();

                if (keyName.equals("Space"))
                    controller.shoot(pane);

                if (keyName.equals("Esc")) {
                    try {
                        pause();
                        controller.pause(pane);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        return ballsGroup;
    }

    private HBox createRemainingBalls() {
        return controller.createRemainingBalls();
    }

    public void shootFirst() {
        this.ballsGroupVBox.getChildren().remove(0);
        if (this.remainingBallsHBox.getChildren().get(1) instanceof Text ballsNumberText) {
            int newBallsNumber = Integer.parseInt(ballsNumberText.getText()) - 1;
            ballsNumberText.setText(String.valueOf(newBallsNumber));
            if (newBallsNumber < 6)
                ballsNumberText.setFill(Color.GREEN);
            else if (newBallsNumber <= User.currentUser.getSettings().getBallNumbers() / 2 + 1)
                ballsNumberText.setFill(Color.GOLD);
        }
    }

    public void pause() throws Exception {
        PauseMenu pauseMenu = new PauseMenu();
        pauseMenu.start(stage);
    }

    public void continueGame() {
        this.ballsGroupVBox.requestFocus();
        stage = new Stage();
        stage.setScene(this.scene);
        stage.show();
    }

    public void restart() throws Exception {
        start(stage);
    }

    public void end() throws Exception {
        this.stop();
        new MainMenu().start(stage);
    }
}
