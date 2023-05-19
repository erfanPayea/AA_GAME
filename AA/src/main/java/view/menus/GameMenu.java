package view.menus;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import javafx.stage.Stage;
import model.game.Settings;
import model.thing.CenterCircle;

import java.net.URL;
import java.util.Objects;

public class GameMenu extends Application {
    private static Stage stage;
    private final Settings settings;
    @FXML
    private Circle circle;
    @FXML
    private Label name;

    public GameMenu(Settings settings) {
        this.settings = settings;
    }

    @Override
    public void start(Stage stage) throws Exception {
        GameMenu.stage = stage;
        URL url = new URL(Objects.requireNonNull(this.getClass().getResource("/FXML/gameMenu.fxml")).toExternalForm());
        Pane pane = FXMLLoader.load(url);
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void initialize() {
        circle.setFill(settings.getMap().getColor());
        name.setText(settings.getMap().getName());
    }
}
