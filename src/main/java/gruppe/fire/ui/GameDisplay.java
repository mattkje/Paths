package gruppe.fire.ui;


import gruppe.fire.fileParsing.StoryFileHandler;
import javafx.application.Application;
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

/**
 * This class represents the GUI for the game itself.
 */
public class GameDisplay extends Application {

    private MainUI mainMenu;
    /**
     * Starting point for the game.
     * @param stage
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
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
        Button button = new Button("MAIN MENU");
        button.setFont(font);
        button.setEffect(dropShadow);
        button.setTextFill(Color.WHITE);
        button.setStyle("-fx-background-color: #2e048f; -fx-background-radius: 20px ; -fx-cursor: HAND");
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: rgba(46,4,143,0.62); -fx-background-radius: 20px ; -fx-cursor: HAND "));
        button.setOnMouseClicked(e -> button.setStyle("-fx-background-color: rgba(46,4,143,0.41); -fx-background-radius: 20px ; -fx-cursor: HAND "));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #2e048f; -fx-background-radius: 20px ; -fx-cursor: HAND "));
        button.setOnAction(e ->{
            stage.close();
            Stage menuStage = new Stage();
            try {
                mainMenu.start(menuStage);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        HBox menuBar = new HBox();
        menuBar.setStyle("-fx-background-color: rgba(0,0,0,0.7); -fx-padding: 10px");
        menuBar.getChildren().addAll(title, growBox, button);
        menuBar.setEffect(dropShadow);
        root.setTop(menuBar);

        BorderPane gameWindow = new BorderPane();
        gameWindow.setStyle("-fx-background-color: rgba(0,0,0,0.7); -fx-background-radius: 20px; -fx-spacing: 20px");
        gameWindow.setMaxWidth(700);
        gameWindow.setMaxHeight(500);
        gameWindow.setEffect(dropShadow);

        Text storyString = new Text(StoryFileHandler.fileToString());
        storyString.setFill(Color.WHITE);
        storyString.setFont(font);
        //gameWindow.getChildren().add();
        root.setCenter(gameWindow);

        Button action1 = new Button("Action 1");
        action1.setFont(font);
        action1.setEffect(dropShadow);
        action1.setTextFill(Color.WHITE);
        action1.setStyle("-fx-background-color: #2e048f; -fx-background-radius: 20px ; -fx-cursor: HAND");
        action1.setOnMouseEntered(e -> action1.setStyle("-fx-background-color: rgba(46,4,143,0.62); -fx-background-radius: 20px ; -fx-cursor: HAND "));
        action1.setOnMouseClicked(e -> action1.setStyle("-fx-background-color: rgba(46,4,143,0.41); -fx-background-radius: 20px ; -fx-cursor: HAND "));
        action1.setOnMouseExited(e -> action1.setStyle("-fx-background-color: #2e048f; -fx-background-radius: 20px ; -fx-cursor: HAND "));
        action1.setOnAction(e ->{
            System.out.println("Work");
        });
        Button action2 = new Button("Action 2");
        action2.setFont(font);
        action2.setEffect(dropShadow);
        action2.setTextFill(Color.WHITE);
        action2.setStyle("-fx-background-color: #2e048f; -fx-background-radius: 20px ; -fx-cursor: HAND");
        action2.setOnMouseEntered(e -> action2.setStyle("-fx-background-color: rgba(46,4,143,0.62); -fx-background-radius: 20px ; -fx-cursor: HAND "));
        action2.setOnMouseClicked(e -> action2.setStyle("-fx-background-color: rgba(46,4,143,0.41); -fx-background-radius: 20px ; -fx-cursor: HAND "));
        action2.setOnMouseExited(e -> action2.setStyle("-fx-background-color: #2e048f; -fx-background-radius: 20px ; -fx-cursor: HAND "));
        action2.setOnAction(e ->{
            System.out.println("Work");
        });
        HBox actionBar = new HBox();
        actionBar.setEffect(hopShadow);
        actionBar.setSpacing(30);
        actionBar.setAlignment(Pos.CENTER);
        actionBar.setStyle("-fx-background-color: rgba(0,0,0,0.7); -fx-padding: 10px");
        actionBar.getChildren().addAll(action1, action2);
        root.setBottom(actionBar);

        //Stats
        GridPane inventory = new GridPane();
        inventory.setAlignment(Pos.TOP_CENTER);
        inventory.setPrefHeight(500);
        inventory.setPrefWidth(300);
        inventory.setVgap(40);
        inventory.setStyle("-fx-background-color: rgba(0,0,0,0.7); -fx-padding: 10px");
        Label info = new Label("Stats");
        Label health = new Label("Health:   ");
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

        //Preview Game (DELETE LATER)
        Label gameTitle = new Label("Murder Mystery");
        Label roomTitle = new Label("Library");
        Button action3 = new Button("Look at the knife");
        action3.setTextFill(Color.WHITE);
        action3.setStyle("-fx-background-color: #2e048f; -fx-background-radius: 20px ; -fx-cursor: HAND");
        action3.setOnMouseEntered(e -> action3.setStyle("-fx-background-color: rgba(46,4,143,0.62); -fx-background-radius: 20px ; -fx-cursor: HAND "));
        action3.setOnMouseClicked(e -> action3.setStyle("-fx-background-color: rgba(46,4,143,0.41); -fx-background-radius: 20px ; -fx-cursor: HAND "));
        action3.setOnMouseExited(e -> action3.setStyle("-fx-background-color: #2e048f; -fx-background-radius: 20px ; -fx-cursor: HAND "));
        Button action4 = new Button("Take the knife");
        action4.setTextFill(Color.WHITE);
        action4.setStyle("-fx-background-color: #2e048f; -fx-background-radius: 20px ; -fx-cursor: HAND");
        action4.setOnMouseEntered(e -> action4.setStyle("-fx-background-color: rgba(46,4,143,0.62); -fx-background-radius: 20px ; -fx-cursor: HAND "));
        action4.setOnMouseClicked(e -> action4.setStyle("-fx-background-color: rgba(46,4,143,0.41); -fx-background-radius: 20px ; -fx-cursor: HAND "));
        action4.setOnMouseExited(e -> action4.setStyle("-fx-background-color: #2e048f; -fx-background-radius: 20px ; -fx-cursor: HAND "));
        Text gameRoom = new Text("The door opens to another room. \n You see a knife covered in blood.");
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
        gameRoom.setTextAlignment(TextAlignment.CENTER);
        gameRoom.setFont(font);
        gameRoom.setFill(Color.WHITE);
        action1.setText("Go back");
        action2.setText("Try to open the door");
        titleBox.getChildren().add(gameTitle);
        titleBox.setAlignment(Pos.CENTER);
        actionBox.getChildren().addAll(action3, action4);
        ImageView heart = new ImageView("/gruppe/fire/Media/health.png");
        ImageView heart2 = new ImageView("/gruppe/fire/Media/health.png");
        ImageView heart3 = new ImageView("/gruppe/fire/Media/lostHealth.png");
        Label goldAmount = new Label("200");
        Label scoreAmount = new Label("4570");
        goldAmount.setFont(font);
        goldAmount.setTextFill(Color.WHITE);
        scoreAmount.setFont(font);
        scoreAmount.setTextFill(Color.WHITE);
        inventory.add(heart,1,2);
        inventory.add(heart2,2,2);
        inventory.add(heart3,3,2);
        inventory.add(goldAmount,3,3);
        inventory.add(scoreAmount,3,4);
        gameWindow.setTop(titleBox);
        gameBox.setSpacing(50);
        gameBox.setAlignment(Pos.CENTER);
        gameBox.getChildren().addAll(roomTitle, gameRoom, actionBox);
        gameWindow.setCenter(gameBox);


        //Show stage
        Scene mainScene = new Scene(root, 1300,800);
        stage.setResizable(true);
        stage.setScene(mainScene);
        stage.setTitle("Paths");
        stage.getIcons().add(new Image("/gruppe/fire/Media/icon.png"));
        stage.show();
    }
}
