package view.menus;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.game.Settings;
import model.thing.InvisibleCircle;

public class GameMenu extends Application {
    private static Stage stage;
    private final Settings settings;

    public GameMenu(Settings settings) {
        this.settings = settings;
    }

    @Override
    public void start(Stage stage) throws Exception {
        GameMenu.stage = stage;
        Pane pane = new Pane();
        pane.setPrefWidth(700); pane.setPrefHeight(550);
        createCenterCircle(pane);

        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    public void createCenterCircle(Pane pane) {
        Label name = new Label(settings.getMap().getName());
        name.setTextFill(Color.WHITE);
        name.setFont(new Font(40)); name.setAlignment(Pos.CENTER);
        name.setPrefHeight(54); name.setPrefWidth(153);
        name.setLayoutX(274); name.setLayoutY(173);

        Circle circle = new Circle(350, 200,80, settings.getMap().getColor());
        InvisibleCircle invisibleCircle = new InvisibleCircle(350, 200, 120); invisibleCircle.setVisible(false);

        pane.getChildren().add(circle);
        pane.getChildren().add(invisibleCircle);
        pane.getChildren().add(name);
    }
}
