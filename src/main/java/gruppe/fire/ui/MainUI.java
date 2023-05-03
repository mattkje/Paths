package gruppe.fire.ui;

import gruppe.fire.fileHandling.FileManagement;
import javafx.animation.*;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

/**
 * This class represents a starting point for the GUI (Main menu).
 */
public class MainUI extends Application {

    private GameDisplay game;

    private MainUiController controller;

    private File selectedFile;

    /**
     *
     * @param stage
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        //Universal app version
        String version = "Version: 2023.05.03";

        this.controller = new MainUiController();

        this.game = new GameDisplay();

        BorderPane root = new BorderPane();
        //Background Image
        Image backgroundImage = new Image("/gruppe/fire/Media/titleBackground.png");
        ImageView anim1 = new ImageView("/gruppe/fire/Media/anim1.png");
        ImageView anim2 = new ImageView("/gruppe/fire/Media/anim2.png");
        ImageView anim3 = new ImageView("/gruppe/fire/Media/anim3.png");
        ImageView anim4 = new ImageView("/gruppe/fire/Media/anim4.png");
        ImageView anim5 = new ImageView("/gruppe/fire/Media/anim5.png");
        BackgroundImage background = new BackgroundImage(backgroundImage, null, null, null, null);
        Background backgroundObject = new Background(background);

        root.setBackground(backgroundObject);

        //Animated background letters.
        Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);

        Random random = new Random();
        for (int i = 0; i < 1000; i++) {
            int x1 = 100;
            int y1 = random.nextInt(300);
            int x2 = 300;
            int y2 = random.nextInt(300);
            int x3 = 600;
            int y3 = random.nextInt(300);
            int x4 = 900;
            int y4 = random.nextInt(300);
            int x5 = 1100;
            int y5 = random.nextInt(300);
            KeyFrame keyFrame1 = new KeyFrame(Duration.seconds(i * 10), new KeyValue(anim1.translateXProperty(), x1), new KeyValue(anim1.translateYProperty(), y1));
            KeyFrame keyFrame2 = new KeyFrame(Duration.seconds(i * 10), new KeyValue(anim2.translateXProperty(), x2), new KeyValue(anim2.translateYProperty(), y2));
            KeyFrame keyFrame3 = new KeyFrame(Duration.seconds(i * 10), new KeyValue(anim3.translateXProperty(), x3), new KeyValue(anim3.translateYProperty(), y3));
            KeyFrame keyFrame4 = new KeyFrame(Duration.seconds(i * 10), new KeyValue(anim4.translateXProperty(), x4), new KeyValue(anim4.translateYProperty(), y4));
            KeyFrame keyFrame5 = new KeyFrame(Duration.seconds(i * 10), new KeyValue(anim5.translateXProperty(), x5), new KeyValue(anim5.translateYProperty(), y5));
            timeline.getKeyFrames().addAll(keyFrame1, keyFrame2, keyFrame3, keyFrame4, keyFrame5);
        }
        timeline.play();
        Pane animPane = new Pane();
        animPane.getChildren().addAll(anim1, anim2, anim3, anim4, anim5);
        animPane.setLayoutX(-200);
        animPane.setLayoutY(100);
        animPane.setOpacity(0.2);
        root.getChildren().addAll(animPane);


        //Main menu title
        BorderPane titlePane = new BorderPane();
        ImageView title = new ImageView("/gruppe/fire/Media/title.png");
        title.setFitHeight(200);
        title.setFitWidth(512);
        titlePane.setCenter(title);
        root.setTop(titlePane);

        //Shadows and fonts
        DropShadow dropShadow = new DropShadow();
        dropShadow.setOffsetY(5.0);
        dropShadow.setColor(Color.color(0, 0, 0, 0.5));
        DropShadow titleShadow = new DropShadow();
        titleShadow.setColor(Color.color(1,1,1));
        titleShadow.setSpread(1);
        titleShadow.setRadius(3);
        DropShadow glow = new DropShadow();
        glow.setColor(Color.WHITE);
        glow.setSpread(1);
        glow.setRadius(2);
        Font font = Font.font("Arial",FontWeight.BOLD, 24);
        Font titleFont = Font.font("Freestyle Script", 24);

        //Import file menu.
        VBox importMenu = new VBox();
        Label label = new Label("Saved stories:");
        ImageView selectGame = new ImageView("/gruppe/fire/Media/GameSelect.png");
        selectGame.setFitHeight(90);
        selectGame.setFitWidth(250);
        label.setFont(font);
        label.setTextFill(Color.WHITE);
        importMenu.getChildren().add(selectGame);
        importMenu.setStyle("-fx-background-color: rgba(0,0,0,0.7); -fx-background-radius: 40px");
        importMenu.setPrefWidth(400);
        importMenu.setMaxHeight(400);
        importMenu.setSpacing(20);
        importMenu.setAlignment(Pos.TOP_CENTER);
        importMenu.setEffect(dropShadow);
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Paths files (*.paths)", "*.paths");
        fileChooser.getExtensionFilters().add(extFilter);


        //Open file button.
        Button continueButton = new Button("Open paths file");
        continueButton.setEffect(dropShadow);
        Button startGame = new Button("Start");
        startGame.setEffect(dropShadow);
        Label noFile = new Label("No file selected");
        continueButton.setOnAction(e -> {
            this.selectedFile = fileChooser.showOpenDialog(stage);

            //Prevents user from opening non-paths files (typing direct path will bypass filter)
            if(!String.valueOf(selectedFile).endsWith(".paths") && selectedFile != null){
                noFile.setText("Incorrect file type!");

                //Stores the file path to a txt file and also sets status as file path.
            }else if(selectedFile != null) {
                Path currentFile = Path.of(selectedFile.getPath());
                try {
                    FileWriter writer;
                    writer = new FileWriter("Data/currentPathsFile.cfg");
                    writer.write(String.valueOf(currentFile));
                    writer.close();
                    noFile.setText(String.valueOf(currentFile));

                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

                //Sets status if user cancels open file.
            } else {
                noFile.setText("No file was selected");
            }
        });
        continueButton.setFont(font);
        continueButton.setTextFill(Color.WHITE);
        startGame.setFont(font);
        startGame.setTextFill(Color.WHITE);

        //Game Starting point. Will open new stage.
        startGame.setOnAction(e -> {
            if(String.valueOf(selectedFile).endsWith(".paths") && selectedFile != null){
                try {
                    game.start(stage);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                noFile.setText("You have to select a file first.");
            }


        });


        //Buttons to open saved stories.
        //TODO rewrite when file writing is developed.
        GridPane customStories = new GridPane();
        customStories.setAlignment(Pos.CENTER);
        customStories.setVgap(4);
        FileManagement handler = new FileManagement(selectedFile);

        //Sets slot titles as story title.
        String[] storyTitles = handler.readSavedStories();

        Button defaultStory1 = new Button();
        defaultStory1.setText(storyTitles[0]);
        defaultStory1.setTextFill(Color.WHITE);
        defaultStory1.setId("storyButtons");
        defaultStory1.setOnAction(e ->{
            try {
                controller.setActiveFile("paths1.paths");
                game.start(stage);
            } catch (Exception ex) {
                noFile.setText("This slot is empty");
            }
        });
        customStories.add(defaultStory1, 0, 0);

        Button defaultStory2 = new Button();
        defaultStory2.setText(storyTitles[1]);
        defaultStory2.setTextFill(Color.WHITE);
        defaultStory2.setId("storyButtons");
        defaultStory2.setOnAction(e ->{
            try {
                controller.setActiveFile("paths2.paths");
                game.start(stage);
            } catch (Exception ex) {
                noFile.setText("This slot is empty");
            }
        });
        customStories.add(defaultStory2, 0, 1);

        Button defaultStory3 = new Button();
        defaultStory3.setText(storyTitles[2]);
        defaultStory3.setTextFill(Color.WHITE);
        defaultStory3.setId("storyButtons");
        defaultStory3.setOnAction(e ->{
            try {
                controller.setActiveFile("paths3.paths");
                game.start(stage);
            } catch (Exception ex) {
                noFile.setText("This slot is empty");
            }
        });
        customStories.add(defaultStory3, 0, 2);

        Button defaultStory4 = new Button();
        defaultStory4.setText(storyTitles[3]);
        defaultStory4.setTextFill(Color.WHITE);
        defaultStory4.setId("storyButtons");
        defaultStory4.setOnAction(e ->{
            try {
                controller.setActiveFile("paths4.paths");
                game.start(stage);
            } catch (Exception ex) {
                noFile.setText("This slot is empty");
            }
        });
        customStories.add(defaultStory4, 0, 3);
        customStories.setVgap(5);


        //I don't think this will work.
        /*
        for (int i = 0; i < storyTitles.length; i++) {
            Button defaultStory = new Button();
            defaultStory.setText(storyTitles[i]);
            defaultStory.setTextFill(Color.WHITE);
            defaultStory.setId("storyButtons");
            customStories.add(defaultStory, 0, i + 1);
        }
        */


        noFile.setTextFill(Color.WHITE);
        noFile.setAlignment(Pos.CENTER);
        noFile.setId("noFile");

        HBox gameControl = new HBox();
        Button dev = new Button();
        dev.setStyle("-fx-background-color: #FFFFFF");
        dev.setText("PREVIEW");
        dev.setOnAction(e ->{
            try {
                game.start(stage);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        gameControl.getChildren().addAll(continueButton, startGame);
        gameControl.setAlignment(Pos.CENTER);
        gameControl.setSpacing(3);
        importMenu.getChildren().addAll(gameControl, noFile, label, customStories);

        VBox defaultStories = new VBox();
        defaultStories.setStyle("-fx-background-color: rgba(0,0,0,0.7); -fx-background-radius: 40px");
        defaultStories.setPrefSize(400, 400);
        defaultStories.setMaxHeight(400);
        defaultStories.setAlignment(Pos.CENTER);
        defaultStories.setEffect(dropShadow);

        ImageView ourStories = new ImageView("/gruppe/fire/Media/ourgames.png");
        ourStories.setFitWidth(250);
        ourStories.setFitHeight(85);
        defaultStories.getChildren().add(ourStories);


        //Creates graphics for default story buttons.
        for (int i = 1; i <= 4; i++) {
            Button story = new Button();
            ImageView storyImage2 = new ImageView("/gruppe/fire/Media/" + i + ".png");
            story.setGraphic(storyImage2);
            story.setStyle("-fx-background-color: transparent");
            defaultStories.getChildren().add(story);
            int finalI = i;
            story.setOnMouseEntered(e -> story.setStyle("-fx-background-color: rgba(255,255,255,0.27); -fx-background-radius: 20px ; -fx-cursor: HAND "));
            story.setOnMousePressed(e -> story.setStyle("-fx-background-color: rgba(255,255,255,0.56); -fx-background-radius: 20px ; -fx-cursor: HAND "));
            story.setOnMouseReleased(e -> story.setStyle("-fx-background-color: rgba(255,255,255,0.27); -fx-background-radius: 20px ; -fx-cursor: HAND "));
            story.setOnMouseExited(e -> story.setStyle("-fx-background-color: transparent; -fx-background-radius: 20px ; -fx-cursor: HAND "));
        }

        //Menu box
        HBox menuBox = new HBox();
        menuBox.getChildren().addAll(defaultStories, importMenu);
        menuBox.setAlignment(Pos.CENTER);
        menuBox.setSpacing(10);

        root.setCenter(menuBox);

        //About page for the program. Displays Authors and version etc.
        Button about = new Button();
        about.setStyle("-fx-background-color: transparent");
        ImageView info = new ImageView("/gruppe/fire/Media/info.png");
        info.setFitHeight(20);
        info.setFitWidth(20);
        about.setGraphic(info);
        about.setOnMouseClicked(mouseEvent -> {
            //On clicked
            Alert alertDialog = new Alert(Alert.AlertType.INFORMATION);
            ImageView logo = new ImageView("/gruppe/fire/Media/title.png");
            logo.setFitWidth(256);
            logo.setFitHeight(100);
            alertDialog.setGraphic(logo);
            alertDialog.setTitle("About");
            alertDialog.setHeaderText(version);
            alertDialog.setContentText("Created by: Gruppe 4");
            Optional<ButtonType> respons = alertDialog.showAndWait();
        });

        //Version label.
        Label versionLabel = new Label(version);
        versionLabel.setTextFill(Color.WHITE);

        //Bottom bar.
        HBox bottom = new HBox();
        bottom.setMaxHeight(30);
        HBox growBox = new HBox();
        growBox.setHgrow(growBox, Priority.ALWAYS); // set horizontal grow priority
        growBox.setMaxWidth(Double.MAX_VALUE); // set maximum width to a large value
        bottom.getChildren().addAll(versionLabel, growBox, dev, about);
        root.setBottom(bottom);


        //Show stage
        Scene mainScene = new Scene(root, 1300,800);
        stage.setResizable(false);
        stage.setScene(mainScene);
        stage.setTitle("Paths");
        stage.getIcons().add(new Image("/gruppe/fire/Media/icon.png"));
        mainScene.getStylesheets().add(Objects.requireNonNull(this.getClass().getResource("/gruppe/fire/css/main.css")).toExternalForm());
        stage.show();
    }



    /**
     * Launch method.
     * @param args
     */
    public static void appMain(String[] args) {
        launch(args);
    }
}
