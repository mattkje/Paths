package gruppe.fire.ui;


import gruppe.fire.fileHandling.DataBase;
import gruppe.fire.fileHandling.FileToGame;
import gruppe.fire.logic.*;
import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;

/**
 * This class represents the GUI for the game itself.
 */
public class GameDisplay {

    private FileToGame fileToGame;

    private Label gameTitle;
    private Label roomTitle;
    private Text roomContent;
    private Label healthAmount;
    private Label goldAmount;

    private Label scoreAmount;

    private HBox actionBar;

    /**
     * Starting point for the game.
     * @throws Exception
     */
    public void start(Scene scene, Boolean ifSaved) {


        GameDisplayController controller = new GameDisplayController();
        JukeBox jukeBox = new JukeBox();
        DataBase dataBase = new DataBase();
        File gameFile = new File(dataBase.getActiveStoryPath());
        File playerFile = new File(dataBase.getActivePlayerPath());
        GameBuilder handler = new GameBuilder(playerFile, gameFile);
        Game game = handler.createGame();
        Player player = game.getPlayer();

        MediaPlayer mediaPlayer = jukeBox.getGameplayMusic();
        mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.seek(javafx.util.Duration.ZERO));
        mediaPlayer.play();

                //Background
        BorderPane root = (BorderPane) scene.getRoot();
        root.getChildren().clear();
        MainMenu mainMenu = new MainMenu();
        root.setStyle("-fx-background-color: linear-gradient(#6746a9, #3829cd)");
        MainMenuController mainMenuController = new MainMenuController();
        mainMenuController.getBackground(root);

        //Fonts
        //Font font = Font.loadFont(GameDisplay.class.getResource("/gruppe/fire/fonts/Comfortaa.ttf").toExternalForm(), 24);
        //Font pauseFont = Font.loadFont(GameDisplay.class.getResource("/gruppe/fire/fonts/Comfortaa.ttf").toExternalForm(), 34);
        //Font menuFont = Font.loadFont(GameDisplay.class.getResource("/gruppe/fire/fonts/Pacifico-Regular.ttf").toExternalForm(), 44);

        Font font = Font.font("Comfortaa", 24);
        Font pauseFont = Font.font("Comfortaa", 34);
        Font menuFont = Font.font("Pacifico", 44);


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


        //logo
        Label title = new Label("Paths");
        title.setFont(menuFont);

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
            try {
                mainMenu.startMain(scene);
                mediaPlayer.dispose();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        //Options
        VBox optionsBox = new VBox();
        optionsBox.setSpacing(30);
        optionsBox.setStyle("-fx-background-color: rgba(0,0,0,0.7)");
        optionsBox.setAlignment(Pos.CENTER);
        Button resume = new Button("Resume game");
        resume.setFont(pauseFont);
        resume.setPrefSize(400,100);
        Button restart = new Button("Restart game");
        restart.setFont(pauseFont);
        restart.setPrefSize(400,100);
        Button tutorial = new Button("Help");
        tutorial.setFont(pauseFont);
        tutorial.setPrefSize(400,100);
        Button settings = new Button("Settings");
        settings.setFont(pauseFont);
        settings.setPrefSize(400, 100);
        Button back = new Button("Exit to main menu");
        back.setFont(pauseFont);
        back.setPrefSize(400, 100);
        optionsBox.getChildren().addAll(resume, restart, tutorial, settings, back);

        StackPane gameWindowPane = new StackPane();
        Button optionsButton = new Button();
        ImageView playerMenuImage = new ImageView("/gruppe/fire/Media/menu.png");
        playerMenuImage.setFitWidth(50);
        playerMenuImage.setFitHeight(50);
        optionsButton.setGraphic(playerMenuImage);
        optionsButton.setEffect(dropShadow);
        optionsButton.setOnAction(e ->{
            gameWindowPane.getChildren().add(optionsBox);
            root.setBottom(null);
        });
        resume.setOnAction(e ->{
            gameWindowPane.getChildren().remove(optionsBox);
            root.setBottom(actionBar);
        });
        restart.setOnAction(e ->{
            start(scene, ifSaved);
            mediaPlayer.dispose();
        });
        back.setOnAction(e ->{
            try {
                mainMenu.startMain(scene);
                mediaPlayer.dispose();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        Button saveButton = new Button();
        ImageView saveImage = new ImageView("/gruppe/fire/Media/diskette.png");
        VBox saveBox = controller.saveStory();

        Button saveStateButton = new Button();
        saveStateButton.setGraphic(saveImage);


        saveImage.setFitWidth(50);
        saveImage.setFitHeight(50);
        saveButton.setGraphic(saveImage);
        saveButton.setEffect(dropShadow);
        Label unsavedChanges = new Label("Story is not saved");
        unsavedChanges.setPadding(new Insets(17));
        unsavedChanges.setTextFill(Color.WHITE);
        unsavedChanges.setFont(Font.font("Segoe UI", FontWeight.BOLD, 12));

        HBox menuBar = new HBox();
        menuBar.setStyle("-fx-background-color: rgba(0,0,0,0.7); -fx-padding: 10px");

        try {
            if(controller.checkIfDefault() == true) {
                menuBar.getChildren().addAll(title, growBox, unsavedChanges, saveButton, optionsButton, mainMenuButton);
            } else {
                menuBar.getChildren().addAll(title, growBox, saveStateButton, optionsButton, mainMenuButton);
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


        gameWindowPane.getChildren().add(gameWindow);
        root.setCenter(gameWindowPane);


        //Stats
        GridPane inventory = new GridPane();
        inventory.setAlignment(Pos.CENTER_LEFT);
        //inventory.setPrefHeight(500);
        //inventory.setPrefWidth(300);
        inventory.setVgap(40);
        inventory.setHgap(100);


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
        Label inventoryTitle= new Label("Inventory");
        //this.inventoryList = new ListView();
        FlowPane inventoryPane = new FlowPane();
        inventoryPane.setId("inventoryPane");

        playerName.setTextFill(Color.WHITE);

        info.setTextFill(Color.WHITE);
        health.setTextFill(Color.WHITE);
        gold.setTextFill(Color.WHITE);
        score.setTextFill(Color.WHITE);
        inventoryTitle.setTextFill(Color.WHITE);
        playerName.setFont(font);
        info.setFont(menuFont);
        health.setFont(font);
        gold.setFont(font);
        score.setFont(font);
        inventoryTitle.setFont(menuFont);
        inventory.add(playerPicture,1,0);
        inventory.add(playerName,0,0);
        inventory.add(info,0,1);
        inventory.add(health,0,2);
        inventory.add(gold,0,3);
        inventory.add(score,0,4);
        VBox inventoryBox = new VBox();
        inventoryBox.setStyle("-fx-background-color: rgba(0,0,0,0.7); -fx-padding: 50px");
        inventoryBox.getChildren().addAll(inventory, inventoryTitle ,inventoryPane);
        //double rootWidth = root.getWidth();
        inventoryBox.prefWidthProperty().bind(root.widthProperty().multiply(0.3));
        HBox sideBox = new HBox();
        Button showInventory = new Button();
        ImageView showImage = new ImageView("/gruppe/fire/Media/back.png");
        TranslateTransition translate = new TranslateTransition();
        translate.setNode(showImage);
        translate.setDuration(Duration.millis(1500));
        translate.setCycleCount(Animation.INDEFINITE);
        translate.setFromX(-5);
        translate.setToX(5);
        translate.setAutoReverse(true);
        translate.play();

        showImage.setScaleX(-1);
        showInventory.setGraphic(showImage);
        showInventory.setStyle("-fx-background-color: rgba(0,0,0,0.7); -fx-background-radius: 0px; -fx-translate-y: 0px;");
        showInventory.setPrefSize(80, 2000);

        sideBox.getChildren().addAll(showInventory, inventoryBox);
        root.setRight(sideBox);
        showInventory.setOnAction(e ->{
            if(!sideBox.getChildren().contains(inventoryBox)){
                sideBox.getChildren().add(inventoryBox);
                showImage.setScaleX(-1);
            } else {
                sideBox.getChildren().remove(inventoryBox);
                showInventory.setGraphic(showImage);
                showImage.setScaleX(1);
            }

        });

        //The Game
        this.gameTitle = new Label();
        this.roomTitle = new Label();
        this.roomContent = new Text();
        roomContent.setWrappingWidth(400);
        Text gameRoom = new Text();
        HBox titleBox = new HBox();
        VBox gameBox = new VBox();

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(event1 -> {
            root.setCenter(gameWindow);
        });

        saveButton.setOnAction(e ->{
            saveBox.getChildren().add(cancelButton);
            root.setCenter(saveBox);
        });

        gameTitle.setAlignment(Pos.CENTER);
        gameTitle.setFont(menuFont);
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
        this.healthAmount = new Label();
        this.goldAmount = new Label();
        this.scoreAmount = new Label();
        inventory.add(healthAmount,2,2);
        inventory.add(lives,1,2);
        inventory.add(goldAmount,2,3);
        inventory.add(scoreAmount,2,4);

        this.actionBar = new HBox();
        actionBar.setEffect(hopShadow);
        actionBar.setSpacing(30);
        actionBar.setAlignment(Pos.CENTER);
        actionBar.setStyle("-fx-background-color: rgba(0,0,0,0.7); -fx-padding: 10px");




        //Import text
        saveStateButton.setOnAction(e ->{
            Passage currentPassage = game.getCurrentPassage();
            game.gameToFile(game, currentPassage);
        });

        if(ifSaved == true){
            this.fileToGame = new FileToGame();
            Game game1 = fileToGame.readFile();
            controller.writeOpeningPassage(game1, actionBar, inventoryPane, gameTitle, roomTitle,
                    roomContent, healthAmount, goldAmount, scoreAmount, font, dropShadow);
        } else {
            controller.writeOpeningPassage(game, actionBar, inventoryPane, gameTitle, roomTitle,
                    roomContent, healthAmount, goldAmount, scoreAmount, font, dropShadow);
        }


        titleBox.getChildren().add(gameTitle);
        titleBox.setAlignment(Pos.CENTER);

        healthAmount.setFont(font);
        healthAmount.setTextFill(Color.WHITE);
        goldAmount.setFont(font);
        goldAmount.setTextFill(Color.WHITE);
        scoreAmount.setFont(font);
        scoreAmount.setTextFill(Color.WHITE);
        gameWindow.setTop(titleBox);
        gameBox.setSpacing(50);
        gameBox.setAlignment(Pos.CENTER);
        gameBox.getChildren().addAll(roomTitle, gameRoom, roomContent);
        gameBox.setPadding(new Insets(20));
        gameWindow.setCenter(gameBox);
        root.setBottom(actionBar);


    }
}
