package gruppe.fire.ui;

import gruppe.fire.fileHandling.FileToStory;
import gruppe.fire.logic.Link;
import gruppe.fire.logic.Story;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class PlayerMenu extends Application {

    private GameDisplay gameDisplay;
    @Override
    public void start(Stage stage) {

        this.gameDisplay =  new GameDisplay();
        //Background
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: linear-gradient(#5130b4, #402593)");
        ImageView background = new ImageView("/gruppe/fire/Media/gameBG.png");
        background.fitWidthProperty().bind(root.widthProperty());
        background.fitHeightProperty().bind(root.heightProperty());
        background.setStyle("-fx-opacity: 0.1");
        root.getChildren().add(background);


        //Shadows and fonts
        DropShadow dropShadow = new DropShadow();
        dropShadow.setOffsetY(5.0);
        dropShadow.setColor(Color.color(0, 0, 0, 0.5));
        DropShadow hopShadow = new DropShadow();
        hopShadow.setOffsetY(-5.0);
        hopShadow.setColor(Color.color(0, 0, 0, 0.5));
        DropShadow glow = new DropShadow();
        glow.setColor(Color.WHITE);
        glow.setSpread(1);
        glow.setRadius(2);
        Font font = Font.font("Arial Rounded MT Bold", FontWeight.BOLD, 24);
        Font titleFont = Font.font("Freestyle Script", 44);

        //logo
        ImageView title = new ImageView("/gruppe/fire/Media/logo.png");
        title.setFitWidth(107);
        title.setFitHeight(50);

        ToolBar playerToolBar = new ToolBar();
        Button button = new Button("BACK");
        button.setFont(font);
        button.setEffect(dropShadow);
        button.setTextFill(Color.WHITE);
        button.setOnAction(e ->{
            try {
                gameDisplay.start(stage);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        playerToolBar.getItems().add(button);
        root.setTop(playerToolBar);
        //Show stage
        Scene playerStage = new Scene(root, 1300,800);
        playerStage.getStylesheets().add(Objects.requireNonNull(this.getClass().getResource("/gruppe/fire/css/main.css")).toExternalForm());
        stage.setResizable(true);
        stage.setScene(playerStage);
        stage.setTitle("Paths");
        stage.getIcons().add(new Image("/gruppe/fire/Media/icon.png"));
        stage.show();
    }
}
