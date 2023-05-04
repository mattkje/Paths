package gruppe.fire.ui;

import gruppe.fire.fileHandling.FileToStory;
import gruppe.fire.logic.Link;
import gruppe.fire.logic.Story;
import javafx.animation.*;
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
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class PlayerMenu extends Application {

    private GameDisplay gameDisplay;

    private ParallelTransition parallelTransition;

    private PlayerMenuController controller;
    @Override
    public void start(Stage stage) {

        this.gameDisplay =  new GameDisplay();
        this.controller = new PlayerMenuController();
        //Background
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: linear-gradient(#5130b4, #402593)");
        ImageView citySkyline = new ImageView("/gruppe/fire/Media/gameBG.png");
        ImageView citySkyline2 = new ImageView("/gruppe/fire/Media/gameBG.png");
        ImageView citySkyline3 = new ImageView("/gruppe/fire/Media/gameBG.png");
        TranslateTransition translateTransition =
                new TranslateTransition(Duration.millis(10000), citySkyline);
        translateTransition.setFromX(0);
        translateTransition.setToX(-1 * 1300);
        translateTransition.setInterpolator(Interpolator.LINEAR);

        TranslateTransition translateTransition2 =
                new TranslateTransition(Duration.millis(10000), citySkyline2);
        translateTransition2.setFromX(1300);
        translateTransition2.setToX(0);
        translateTransition2.setInterpolator(Interpolator.LINEAR);

        TranslateTransition translateTransition3 =
                new TranslateTransition(Duration.millis(10000), citySkyline3);
        translateTransition3.setFromX(2600);
        translateTransition3.setToX(1300);
        translateTransition3.setInterpolator(Interpolator.LINEAR);

        parallelTransition = new ParallelTransition(translateTransition, translateTransition2, translateTransition3);
        parallelTransition.setCycleCount(Animation.INDEFINITE);

        parallelTransition.play();

        //citySkyline.fitWidthProperty().bind(root.widthProperty());
        citySkyline.fitHeightProperty().bind(root.heightProperty());
        //citySkyline2.fitWidthProperty().bind(root.widthProperty());
        citySkyline2.fitHeightProperty().bind(root.heightProperty());
        citySkyline3.fitHeightProperty().bind(root.heightProperty());
        citySkyline.setStyle("-fx-opacity: 0.1");
        citySkyline2.setStyle("-fx-opacity: 0.1");
        citySkyline3.setStyle("-fx-opacity: 0.1");
        root.getChildren().addAll(citySkyline, citySkyline2, citySkyline3);


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

        Label playerTitle = new Label("Game options");
        playerTitle.setAlignment(Pos.CENTER);
        playerTitle.setTextFill(Color.WHITE);
        playerTitle.setFont(titleFont);

        Label playerGold = new Label("Gold");
        playerGold.setTextFill(Color.WHITE);
        playerGold.setFont(font);

        Label playerHealth = new Label("Health");
        playerHealth.setTextFill(Color.WHITE);
        playerHealth.setFont(font);

        Label playerScore = new Label("Score");
        playerScore.setTextFill(Color.WHITE);
        playerScore.setFont(font);

        Label playerInventory = new Label("Inventory");
        playerInventory.setTextFill(Color.WHITE);
        playerInventory.setFont(font);

        GridPane playerOptions = new GridPane();
        playerOptions.add(playerGold,0,0);
        playerOptions.add(playerHealth,0,1);
        playerOptions.add(playerScore,0,2);
        playerOptions.add(playerInventory,0,3);
        playerOptions.setAlignment(Pos.CENTER);
        playerOptions.setPadding(new Insets(20));
        playerOptions.setVgap(50);

        VBox playerBox = new VBox();
        playerBox.getChildren().addAll(playerTitle, playerOptions);
        playerBox.setStyle("-fx-background-color: rgba(0,0,0,0.7); -fx-background-radius: 40px");
        playerBox.setPrefSize(400, 400);
        playerBox.setMaxHeight(700);
        playerBox.setMaxWidth(1000);
        playerBox.setAlignment(Pos.CENTER);
        playerBox.setEffect(dropShadow);
        playerBox.setSpacing(30);
        root.setCenter(playerBox);
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
