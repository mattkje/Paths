package gruppe.fire.ui;


import gruppe.fire.fileHandling.FileManagement;
import gruppe.fire.logic.Link;
import gruppe.fire.logic.Story;
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

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

/**
 * This class represents the GUI for the game itself.
 */
public class GameDisplay extends Application {

    private MainUI mainMenu;

    private GameDisplayController controller;
    /**
     * Starting point for the game.
     * @param stage
     * @throws Exception
     */
    @Override
    public void start(Stage stage) {

        this.controller = new GameDisplayController();
                //Background
        BorderPane root = new BorderPane();
        this.mainMenu = new MainUI();
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

        HBox growBox = new HBox();
        growBox.setHgrow(growBox, Priority.ALWAYS); // set horizontal grow priority
        growBox.setMaxWidth(Double.MAX_VALUE); // set maximum width to a large value

        //Back to main menu
        Button mainMenuButton = new Button("MAIN MENU");
        mainMenuButton.setFont(font);
        mainMenuButton.setEffect(dropShadow);
        mainMenuButton.setTextFill(Color.WHITE);
        mainMenuButton.setOnAction(e ->{
            stage.close();
            Stage menuStage = new Stage();
            try {
                mainMenu.start(menuStage);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        Button saveButton = new Button("Save story");
        saveButton.setFont(font);
        saveButton.setEffect(dropShadow);
        saveButton.setTextFill(Color.WHITE);
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
                menuBar.getChildren().addAll(title, growBox, unsavedChanges, saveButton, mainMenuButton);
            } else {
                menuBar.getChildren().addAll(title, growBox, mainMenuButton);
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
        Label info = new Label("Stats");
        Label health = new Label("Health:");
        Label gold = new Label("Gold:");
        Label score = new Label("Score:");
        info.setTextFill(Color.WHITE);
        health.setTextFill(Color.WHITE);
        gold.setTextFill(Color.WHITE);
        score.setTextFill(Color.WHITE);
        info.setFont(titleFont);
        health.setFont(font);
        gold.setFont(font);
        score.setFont(font);
        inventory.add(info,0,1);
        inventory.add(health,0,2);
        inventory.add(gold,0,3);
        inventory.add(score,0,4);
        root.setRight(inventory);

        //The Game
        Label gameTitle = new Label();
        Label roomTitle = new Label();
        Text roomContent = new Text();
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

        File gameFile = new File(getActiveStoryPath());
        FileManagement handler = new FileManagement(gameFile);
        Story story = handler.readFile();

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
            actionBox.getChildren().add(nextPath);
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

    public String getActiveStoryPath(){
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader("Data/currentPathsFile.cfg"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        String activeStory;
        try {
            activeStory = reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return activeStory;
    }
}
