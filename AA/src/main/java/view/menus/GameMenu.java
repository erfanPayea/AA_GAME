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
import javafx.stage.Modality;
import javafx.stage.Stage;

import view.enums.HotKeys;
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
        this.pane.setPrefWidth(1534);
        this.pane.setPrefHeight(820);

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

        pauseButton.setLayoutX(1430);
        pauseButton.setLayoutY(2);
        pauseButton.setPrefHeight(30);
        pauseButton.setPrefWidth(80);
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

                if (HotKeys.SHOOT.equals(keyName)) {
                    try {
                        controller.shootPlayer1(pane);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                } else if (HotKeys.FREEZE.equals(keyName)) {
                    try {
                        controller.playFreeze();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                } else if (HotKeys.RIGHT.equals(keyName))
                    controller.moveRight();

                else if (HotKeys.LEFT.equals(keyName))
                    controller.moveLeft();

                else if (HotKeys.RIGHT2.equals(keyName))
                    controller.moveRight2();

                else if (HotKeys.LEFT2.equals(keyName))
                    controller.moveLeft2();

                else if (HotKeys.PLAYER2SHOOT.equals(keyName))
                    controller.shootPlayer2(pane);

                else if (keyName.equals("Esc")) {
                    try {
                        pause();
                        controller.pause();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }

            }
        });

        return ballsGroup;
    }

    public Pane getPane() {
        return this.pane;
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

    public void winGame() {
        this.pane.setBackground(new Background(new BackgroundFill(Color.GREEN, null, null)));
        this.pane.getChildren().get(1).requestFocus();
        showFinish();
    }

    public void looseGame() {
        this.pane.setBackground(new Background(new BackgroundFill(Color.ORANGERED, null, null)));
        this.pane.getChildren().get(1).requestFocus();
        showFinish();
    }

    public void showFinish() {
        Stage finishStage = new Stage();
        finishStage.initModality(Modality.APPLICATION_MODAL);
        finishStage.initOwner(stage);

//        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1.5), actionEvent -> {
//            try {
//                new FinishMenu().start(new Stage());
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//        }));
//
//        timeline.setCycleCount(1);
//        timeline.play();

        try {
            new FinishMenu().start(finishStage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void end() throws Exception {
        stage.close();
        new MainMenu().start(stage);
    }

}
