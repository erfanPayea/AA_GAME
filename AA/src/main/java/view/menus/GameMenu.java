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
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.User;
import model.game.Settings;


public class GameMenu extends Application {
    private static Stage stage;
    private Scene scene;
    private final GameMenuController controller;
    private final Settings settings;
    private Pane pane;
    private HBox scoreSheet;
    private VBox ballsGroupVBox;

    {
        this.settings = User.getCurrentUser().getSettings();
        this.controller = new GameMenuController(this);
    }

    @Override
    public void start(Stage stage) throws Exception {
        GameMenu.stage = stage;
        this.pane = new Pane();
        this.pane.setPrefWidth(700);
        this.pane.setPrefHeight(550);

        controller.initializeFirstParameters(this.pane);

        this.ballsGroupVBox = createBallsGroup(this.pane);
        this.pane.getChildren().add(ballsGroupVBox);

        this.controller.createRemainingBalls(this.pane);
        this.controller.createScoreSheet(this.pane);
        this.controller.createFreezeBar(this.pane);
        this.controller.createDegreeHBox(this.pane);

        this.pane.setBackground(new Background(settings.getMap().getBackgroundImage()));
        createPauseButton(this.pane);

        this.scene = new Scene(this.pane);
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
                    controller.pause();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        pauseButton.setLayoutX(600);
        pauseButton.setLayoutY(2);
        pauseButton.setPrefHeight(30);
        pauseButton.setPrefWidth(80);
        pauseButton.setTextFill(Color.WHITE);
        pauseButton.setBackground(new Background(new BackgroundFill(Color.CHOCOLATE, CornerRadii.EMPTY, Insets.EMPTY)));

        pane.getChildren().add(pauseButton);
    }

    private VBox createBallsGroup(Pane pane) {
        VBox ballsGroup = controller.createBallsGroup();

        ballsGroup.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                String keyName = keyEvent.getCode().getName();

                switch (keyName) {
                    case "Space" -> {
                        try {
                            controller.shoot(pane);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                    case "Esc" -> {
                        try {
                            pause();
                            controller.pause();
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                    case "Shift" -> {
                        try {
                            controller.playFreeze();
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                    case "Z" -> controller.moveLeft();
                    case "X" -> controller.moveRight();
                }
            }
        });

        return ballsGroup;
    }

    public void changeScore(int score) {

    }

    public Pane getPane() {
        return this.pane;
    }

    public VBox getBallsGroupVBox() {
        return ballsGroupVBox;
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

    public void winGame() throws Exception {
        this.pane.setBackground(new Background(new BackgroundFill(Color.GREEN, null, null)));
        this.pane.getChildren().get(1).requestFocus();
        showFinish();
    }

    public void looseGame() throws Exception {
        this.pane.setBackground(new Background(new BackgroundFill(Color.ORANGERED, null, null)));
        this.pane.getChildren().get(1).requestFocus();
        showFinish();
    }

    public void showFinish() throws Exception {
        Stage finishStage = new Stage();
        finishStage.initModality(Modality.APPLICATION_MODAL);
        finishStage.initOwner(stage);
        new FinishMenu().start(finishStage);
    }

    public void end() throws Exception {
        stage.close();
        new MainMenu().start(stage);
    }

}
