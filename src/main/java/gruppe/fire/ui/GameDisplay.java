package gruppe.fire.ui;


import gruppe.fire.fileHandling.DataBase;
import gruppe.fire.logic.*;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

/**
 * This class represents the GUI for the game itself.
 */
public class GameDisplay extends Application {

    private MainUI mainMenu;

    private PlayerMenu playerMenu;

    private GameDisplayController controller;
    /**
     * Starting point for the game.
     * @param stage
     * @throws Exception
     */
    @Override
    public void start(Stage stage) {

        this.controller = new GameDisplayController();
        DataBase dataBase = new DataBase();
        File gameFile = new File(dataBase.getActiveStoryPath());
        System.out.println(dataBase.getActivePlayerPath());
        File playerFile = new File(dataBase.getActivePlayerPath());
        GameBuilder handler = new GameBuilder(playerFile, gameFile);
        Game game = handler.createGame();
        Story story = game.getStory();
        System.out.println(game.getStory().getTitle());
        Player player = game.getPlayer();

                //Background
        BorderPane root = new BorderPane();
        this.mainMenu = new MainUI();
        this.playerMenu = new PlayerMenu();
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

        ParallelTransition parallelTransition = new ParallelTransition(translateTransition, translateTransition2, translateTransition3);
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

        HBox growBox = new HBox();
        growBox.setHgrow(growBox, Priority.ALWAYS); // set horizontal grow priority
        growBox.setMaxWidth(Double.MAX_VALUE); // set maximum width to a large value

        //Back to main menu
        Button mainMenuButton = new Button();
        ImageView mainMenuImage = new ImageView("/gruppe/fire/Media/home.png");
        mainMenuImage.setFitWidth(50);
        mainMenuImage.setFitHeight(50);
        mainMenuButton.setGraphic(mainMenuImage);
        mainMenuButton.setEffect(dropShadow);
        mainMenuButton.setOnAction(e ->{
            stage.close();
            Stage menuStage = new Stage();
            try {
                mainMenu.start(menuStage);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        Button playerMenuButton = new Button();
        ImageView playerMenuImage = new ImageView("/gruppe/fire/Media/menu.png");
        playerMenuImage.setFitWidth(50);
        playerMenuImage.setFitHeight(50);
        playerMenuButton.setGraphic(playerMenuImage);
        playerMenuButton.setEffect(dropShadow);
        playerMenuButton.setOnAction(e ->{
            try {
                playerMenu.start(stage);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        Button saveButton = new Button();
        ImageView saveImage = new ImageView("/gruppe/fire/Media/diskette.png");
        saveImage.setFitWidth(50);
        saveImage.setFitHeight(50);
        saveButton.setGraphic(saveImage);
        saveButton.setEffect(dropShadow);
        saveButton.setOnAction(e ->{
            controller.saveStory();
        });
        Label unsavedChanges = new Label("Story is not saved");
        unsavedChanges.setPadding(new Insets(17));
        unsavedChanges.setTextFill(Color.WHITE);
        unsavedChanges.setFont(Font.font("Segoe UI", FontWeight.BOLD, 12));

        HBox menuBar = new HBox();
        menuBar.setStyle("-fx-background-color: rgba(0,0,0,0.7); -fx-padding: 10px");

        try {
            if(controller.checkIfDefault() == true) {
                menuBar.getChildren().addAll(title, growBox, unsavedChanges, saveButton, playerMenuButton, mainMenuButton);
            } else {
                menuBar.getChildren().addAll(title, growBox, playerMenuButton, mainMenuButton);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        menuBar.setEffect(dropShadow);
        menuBar.setSpacing(10);
        root.setTop(menuBar);

        BorderPane gameWindow = new BorderPane();
        gameWindow.setStyle("-fx-background-color: rgba(0,0,0,0.7); -fx-background-radius: 20px; -fx-spacing: 20px");
        gameWindow.setMaxWidth(700);
        gameWindow.setMaxHeight(500);
        gameWindow.setEffect(dropShadow);

        //gameWindow.getChildren().add();
        root.setCenter(gameWindow);


        //Stats
        GridPane inventory = new GridPane();
        inventory.setAlignment(Pos.TOP_CENTER);
        inventory.setPrefHeight(500);
        inventory.setPrefWidth(300);
        inventory.setVgap(40);
        inventory.setStyle("-fx-background-color: rgba(0,0,0,0.7); -fx-padding: 10px");


        Image profileImage = player.getImage();
        ImageView playerPicture = new ImageView(profileImage);
        playerPicture.setFitWidth(50);
        playerPicture.setFitHeight(50);
        String playerString = player.getName();
        Label playerName = new Label(playerString);
        //HBox playerBox = new HBox();
        //playerBox.getChildren().addAll(playerPicture, playerName);

        Label info = new Label("Stats");
        Label health = new Label("Health:");
        Label gold = new Label("Gold:");
        Label score = new Label("Score:");

        playerName.setTextFill(Color.WHITE);
        info.setTextFill(Color.WHITE);
        health.setTextFill(Color.WHITE);
        gold.setTextFill(Color.WHITE);
        score.setTextFill(Color.WHITE);
        playerName.setFont(font);
        info.setFont(titleFont);
        health.setFont(font);
        gold.setFont(font);
        score.setFont(font);
        inventory.add(playerPicture,1,0);
        inventory.add(playerName,0,0);
        inventory.add(info,0,1);
        inventory.add(health,0,2);
        inventory.add(gold,0,3);
        inventory.add(score,0,4);
        HBox sideBox = new HBox();
        Button showInventory = new Button();
        ImageView showImage = new ImageView("/gruppe/fire/Media/back.png");
        showImage.setScaleX(-1);
        showInventory.setGraphic(showImage);
        showInventory.setStyle("-fx-background-color: rgba(0,0,0,0.7); -fx-background-radius: 0px; -fx-translate-y: 0px;");
        showInventory.setPrefSize(80, 2000);

        sideBox.getChildren().addAll(showInventory, inventory);
        root.setRight(sideBox);
        showInventory.setOnAction(e ->{
            if(!sideBox.getChildren().contains(inventory)){
                sideBox.getChildren().add(inventory);
                showImage.setScaleX(-1);
            } else {
                sideBox.getChildren().remove(inventory);
                showInventory.setGraphic(showImage);
                showImage.setScaleX(1);
            }

        });
        //root.setRight(sideBox);

        //The Game
        Label gameTitle = new Label();
        Label roomTitle = new Label();
        Text roomContent = new Text();
        roomContent.setWrappingWidth(400);
        Text gameRoom = new Text();
        HBox titleBox = new HBox();
        VBox gameBox = new VBox();
        HBox actionBox = new HBox();
        actionBox.setAlignment(Pos.CENTER);
        actionBox.setSpacing(20);
        gameTitle.setAlignment(Pos.CENTER);
        gameTitle.setFont(titleFont);
        gameTitle.setTextFill(Color.WHITE);
        roomTitle.setAlignment(Pos.CENTER);
        roomTitle.setFont(font);
        roomTitle.setTextFill(Color.WHITE);
        roomContent.setFont(font);
        roomContent.setFill(Color.WHITE);
        gameRoom.setTextAlignment(TextAlignment.CENTER);
        gameRoom.setFont(font);
        gameRoom.setFill(Color.WHITE);
        ImageView lives = new ImageView("/gruppe/fire/Media/health.png");
        ImageView livesLost = new ImageView("/gruppe/fire/Media/lostHealth.png");
        Label goldAmount = new Label();
        Label scoreAmount = new Label();
        inventory.add(lives,1,2);
        inventory.add(goldAmount,1,3);
        inventory.add(scoreAmount,1,4);

        HBox actionBar = new HBox();
        actionBar.setEffect(hopShadow);
        actionBar.setSpacing(30);
        actionBar.setAlignment(Pos.CENTER);
        actionBar.setStyle("-fx-background-color: rgba(0,0,0,0.7); -fx-padding: 10px");




        ArrayList links = story.getOpeningPassage().getLinks();
        int linkCount = links.size();

        for (int i = 0; i < linkCount; i++) {
            Link link = (Link) links.get(i);
            //String linkVariableName = "link" + (i + 1);
            Button nextPath = new Button("");
            nextPath.setFont(font);
            nextPath.setEffect(dropShadow);
            nextPath.setTextFill(Color.WHITE);
            nextPath.setText(link.getText());
            actionBar.getChildren().add(nextPath);
        }


        //Import text
        gameTitle.setText(story.getTitle());
        roomTitle.setText(story.getOpeningPassage().getTitle());
        roomContent.setText(story.getOpeningPassage().getContent());


        //TODO Implement goals here!!!
        goldAmount.setText("null");
        scoreAmount.setText("null");

        titleBox.getChildren().add(gameTitle);
        titleBox.setAlignment(Pos.CENTER);

        //TODO: ADD FUNC HERE


        goldAmount.setFont(font);
        goldAmount.setTextFill(Color.WHITE);
        scoreAmount.setFont(font);
        scoreAmount.setTextFill(Color.WHITE);
        gameWindow.setTop(titleBox);
        gameBox.setSpacing(50);
        gameBox.setAlignment(Pos.CENTER);
        gameBox.getChildren().addAll(roomTitle, gameRoom, roomContent, actionBox);
        gameBox.setPadding(new Insets(20));
        gameWindow.setCenter(gameBox);
        root.setBottom(actionBar);


        //Show stage
        Scene mainScene = new Scene(root, 1300,800);
        mainScene.getStylesheets().add(Objects.requireNonNull(this.getClass().getResource("/gruppe/fire/css/main.css")).toExternalForm());
        stage.setResizable(true);
        stage.setScene(mainScene);
        stage.setTitle("Paths");
        stage.getIcons().add(new Image("/gruppe/fire/Media/icon.png"));
        stage.show();
    }
}
