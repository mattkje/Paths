package gruppe.fire.ui;


import gruppe.fire.fileHandling.FileToStory;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Optional;

/**
 * This class represents a starting point for the GUI (Main menu).
 */
public class MainMenu {

    private PlayerMenu playerMenu;

    private MainMenuController controller;

    private File selectedFile;

    private MediaPlayer player;

    private StackPane rootContainer;

    /**
     *
     * @throws Exception
     */

    public void start(Scene mainScene) throws Exception {
        //Universal app version
        String version = "Version: 2023.05.03";



        this.controller = new MainMenuController();

        this.playerMenu = new PlayerMenu();




        BorderPane root = (BorderPane) mainScene.getRoot();
        root.getChildren().clear();

        this.player = controller.getBackgroundMusic();
        player.setVolume(0.5);
        player.play();




        mainScene.getStylesheets().add("https://fonts.googleapis.com/css2?family=Pacifico");
        mainScene.getStylesheets().add("https://fonts.googleapis.com/css2?family=Comfortaa");


        mainScene.getStylesheets().add(Objects.requireNonNull(this.getClass().getResource("/gruppe/fire/css/main.css")).toExternalForm());

        //Style background
        root.setStyle("-fx-background-color: linear-gradient(#6746a9, #3829cd)");
        controller.getBackground(root);


        //Main menu title
        BorderPane titlePane = new BorderPane();


        //Shadows and fonts
        DropShadow dropShadow = new DropShadow();
        dropShadow.setOffsetY(5.0);
        dropShadow.setColor(Color.color(0, 0, 0, 0.5));
        DropShadow solidShadow = new DropShadow();
        solidShadow.setHeight(0.0);
        solidShadow.setOffsetY(10.0);
        solidShadow.setRadius(1.4475);
        solidShadow.setSpread(1.0);
        solidShadow.setWidth(15.79);
        solidShadow.setColor(Color.color(0, 0, 0, 0.5));
        DropShadow titleShadow = new DropShadow();
        titleShadow.setColor(Color.color(1,1,1));
        titleShadow.setSpread(1);
        titleShadow.setRadius(3);
        DropShadow glow = new DropShadow();
        glow.setColor(Color.WHITE);
        glow.setSpread(1);
        glow.setRadius(2);

        Font font = Font.font("Comfortaa",FontWeight.BOLD, 24);
        Font titleFont2 = Font.font("Pacifico",FontWeight.BOLD, 300);
        Font menuFont = Font.font("Pacifico",FontWeight.BOLD, 34);
        Font menuFontLarge = Font.font("Pacifico",FontWeight.BOLD, 64);

        Font titleFont = Font.loadFont(MainMenu.class.getResource("/gruppe/fire/fonts/Pacifico-Regular.ttf").toExternalForm(), 300);

        Label title1 = new Label("P");
        Label title2 = new Label("aths");
        HBox titleBox = new HBox();
        titleBox.setSpacing(-50);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.getChildren().addAll(title1,title2);
        title1.setEffect(solidShadow);
        title1.setFont(titleFont);
        title2.setEffect(solidShadow);
        title2.setFont(titleFont);

        TranslateTransition translate = new TranslateTransition();
        translate.setNode(titleBox);
        translate.setDuration(Duration.millis(2500));
        translate.setCycleCount(Animation.INDEFINITE);
        translate.setByY(-25);
        translate.setAutoReverse(true);
        translate.play();
        FadeTransition fade = new FadeTransition();
        fade.setNode(titleBox);
        fade.setDuration(Duration.millis(2000));
        fade.setInterpolator(Interpolator.LINEAR);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.play();
        titlePane.setCenter(titleBox);
        root.setTop(titlePane);

        //Import file menu.
        VBox importMenu = new VBox();
        Label label = new Label("Saved stories:");
        //ImageView selectGame = new ImageView("/gruppe/fire/Media/GameSelect.png");
        //selectGame.setFitHeight(90);
        //selectGame.setFitWidth(250);
        Label selectGame = new Label("Upload story");
        selectGame.setFont(menuFont);
        selectGame.setTextFill(Color.WHITE);
        label.setFont(menuFont);
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
        noFile.setStyle("-fx-font-family: Comfortaa");
        continueButton.setOnAction(e -> {
            this.selectedFile = fileChooser.showOpenDialog(mainScene.getWindow());

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
                    playerMenu.start(mainScene);
                } catch (Exception ex) {
                    noFile.setText("Could not load file. Wrong format?");
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
        FileToStory handler = new FileToStory(selectedFile);

        //Sets slot titles as story title.
        String[] storyTitles = handler.readSavedStories();

        Button customStory1 = new Button();
        customStory1.setText(storyTitles[0]);
        customStory1.setStyle("-fx-font-family: Comfortaa");
        customStory1.setTextFill(Color.WHITE);
        customStory1.setPrefWidth(300);
        customStory1.setOnAction(e ->{
            //controller.setActiveFile("paths1.paths");
            //playerMenu.start(mainScene);

            noFile.setText("This slot is empty");
        });
        customStories.add(customStory1, 0, 0);

        Button customStory2 = new Button();
        customStory2.setStyle("-fx-font-family: Comfortaa");
        customStory2.setText(storyTitles[1]);
        customStory2.setTextFill(Color.WHITE);
        customStory2.setPrefWidth(300);
        customStory2.setOnAction(e ->{

            //    controller.setActiveFile("paths2.paths");
            //    playerMenu.start(mainScene);

            noFile.setText("This slot is empty");
        });
        customStories.add(customStory2, 0, 1);

        Button customStory3 = new Button();
        customStory3.setStyle("-fx-font-family: Comfortaa");
        customStory3.setText(storyTitles[2]);
        customStory3.setTextFill(Color.WHITE);
        customStory3.setPrefWidth(300);
        customStory3.setOnAction(e ->{

            //controller.setActiveFile("paths3.paths");
            //playerMenu.start(mainScene);
            noFile.setText("This slot is empty");

        });
        customStories.add(customStory3, 0, 2);

        Button customStory4 = new Button();
        customStory4.setStyle("-fx-font-family: Comfortaa");
        customStory4.setText(storyTitles[3]);
        customStory4.setTextFill(Color.WHITE);
        customStory4.setPrefWidth(300);
        customStory4.setOnAction(e ->{

            //controller.setActiveFile("paths4.paths");
            //playerMenu.start(mainScene);

            noFile.setText("This slot is empty");

        });
        customStories.add(customStory4, 0, 3);
        customStories.setVgap(5);

        noFile.setTextFill(Color.WHITE);
        noFile.setAlignment(Pos.CENTER);
        noFile.setId("noFile");

        HBox gameControl = new HBox();

        gameControl.getChildren().addAll(continueButton, startGame);
        gameControl.setAlignment(Pos.CENTER);
        gameControl.setSpacing(3);
        importMenu.getChildren().addAll(gameControl, noFile, label, customStories);


        //Creates graphics for default story buttons.
        GridPane defaultStories = new GridPane();
        defaultStories.setAlignment(Pos.CENTER);
        defaultStories.setVgap(4);


        Button defaultStory1 = new Button("Haunted House");
        ImageView storyImage1 = new ImageView("/gruppe/fire/Media/1 - Copy.png");
        HBox defaultStoryBox1 = new HBox();
        defaultStoryBox1.setAlignment(Pos.CENTER);
        defaultStoryBox1.getChildren().add(storyImage1);
        defaultStory1.setGraphic(defaultStoryBox1);
        defaultStory1.setPrefWidth(350);
        defaultStory1.setFont(font);
        defaultStory1.setTextFill(Color.WHITE);
        defaultStory1.setOnAction(e ->{
            try {
                controller.setDefaultPath("HauntedHouse.paths");
                playerMenu.start(mainScene);
            } catch (Exception ex) {
                noFile.setText("Something went wrong");
            }
        });

        Button defaultStory2 = new Button("Murder Mystery");
        ImageView storyImage2 = new ImageView("/gruppe/fire/Media/2 - Copy.png");
        HBox defaultStoryBox2 = new HBox();
        defaultStoryBox2.setAlignment(Pos.CENTER);
        defaultStoryBox2.getChildren().add(storyImage2);
        defaultStory2.setGraphic(defaultStoryBox2);
        defaultStory2.setPrefWidth(350);
        defaultStory2.setFont(font);
        defaultStory2.setTextFill(Color.WHITE);
        defaultStory2.setOnAction(e ->{
            try {
                controller.setDefaultPath("MurderMystery.paths");
                PlayerMenu playerScreen = new PlayerMenu();
                playerScreen.start(mainScene);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        Button defaultStory3 = new Button("Ancient Castle");
        ImageView storyImage3 = new ImageView("/gruppe/fire/Media/3 - Copy.png");
        HBox defaultStoryBox3 = new HBox();
        defaultStoryBox3.setAlignment(Pos.CENTER);
        defaultStoryBox3.getChildren().add(storyImage3);
        defaultStory3.setGraphic(defaultStoryBox3);
        defaultStory3.setPrefWidth(350);
        defaultStory3.setFont(font);
        defaultStory3.setTextFill(Color.WHITE);
        defaultStory3.setOnAction(e ->{
            try {
                controller.setDefaultPath("Castle.paths");
                playerMenu.start(mainScene);
            } catch (Exception ex) {
                noFile.setText("Something went wrong");
            }
        });

        Button defaultStory4 = new Button("Space Ship");
        ImageView storyImage4 = new ImageView("/gruppe/fire/Media/4 - Copy.png");
        HBox defaultStoryBox4 = new HBox();
        defaultStoryBox4.setAlignment(Pos.CENTER);
        defaultStoryBox4.getChildren().add(storyImage4);
        defaultStory4.setGraphic(defaultStoryBox4);
        defaultStory4.setPrefWidth(350);
        defaultStory4.setFont(font);
        defaultStory4.setTextFill(Color.WHITE);
        defaultStory4.setOnAction(e ->{
            try {
                controller.setDefaultPath("SpaceShip.paths");
                playerMenu.start(mainScene);
            } catch (Exception ex) {
                noFile.setText("Something went wrong");
            }
        });

        //defaultStory2.setDisable(true);
        //defaultStory3.setDisable(true);
        //defaultStory4.setDisable(true);

        defaultStories.add(defaultStory1, 0, 0);
        defaultStories.add(defaultStory2, 0, 1);
        defaultStories.add(defaultStory3, 0, 2);
        defaultStories.add(defaultStory4, 0, 3);

        VBox defaultStoriesBox = new VBox();
        defaultStoriesBox.setStyle("-fx-background-color: rgba(0,0,0,0.7); -fx-background-radius: 40px");
        defaultStoriesBox.setPrefSize(400, 400);
        defaultStoriesBox.setMaxHeight(400);
        defaultStoriesBox.setAlignment(Pos.CENTER);
        defaultStoriesBox.setEffect(dropShadow);


        Label ourStories = new Label("Our stories");
        ourStories.setTextFill(Color.WHITE);
        ourStories.setFont(menuFont);
        defaultStoriesBox.getChildren().addAll(ourStories, defaultStories);

        //Back Button
        ImageView backImage = new ImageView("/gruppe/fire/Media/back.png");
        Button backButton = new Button();
        backButton.setGraphic(backImage);
        backButton.setStyle("-fx-background-color: rgba(0,0,0,0.7); -fx-background-radius: 40px");
        backButton.setPrefSize(100, 100);
        //backButton.setMaxHeight(400);
        backButton.setAlignment(Pos.CENTER);
        backButton.setEffect(dropShadow);


        HBox menuBox = new HBox();
        menuBox.setAlignment(Pos.CENTER);
        menuBox.setSpacing(10);

        root.setCenter(menuBox);

        //Tutorial screen
        Label tutorialTitle = new Label("How to play");
        tutorialTitle.setTextFill(Color.WHITE);
        tutorialTitle.setFont(font);
        Text tutorialText = new Text("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce gravida turpis id velit hendrerit, \n" +
                "sed imperdiet dui ullamcorper. Vestibulum at mi justo. Fusce ultricies neque vel turpis finibus, ac efficitur ex convallis. \n" +
                "Nullam dignissim nisi id enim tincidunt, vel convallis ex sagittis. Fusce scelerisque purus quis sapien malesuada, \n\n" +


                "non bibendum eros rutrum. Integer euismod, quam vel pulvinar vestibulum, libero ante auctor eros, a vulputate velit \n" +
                "velit in lorem. Sed ullamcorper, leo ac efficitur sollicitudin, velit mauris finibus nisi, ut consequat mauris ipsum a \n" +
                "ex. Nunc congue aliquam sapien, vitae ultrices nulla hendrerit vel. Nunc id blandit mi. Sed fermentum nisi quis faucibus \n" +
                "suscipit. Aenean quis sem ac nibh vehicula ullamcorper.");
        tutorialText.setFill(Color.WHITE);
        VBox tutorialBox = new VBox();
        tutorialBox.setStyle("-fx-background-color: rgba(0,0,0,0.7); -fx-background-radius: 40px");
        tutorialBox.setPrefSize(1000, 800);
        tutorialBox.setAlignment(Pos.CENTER);
        tutorialBox.setEffect(dropShadow);
        tutorialBox.getChildren().addAll(tutorialTitle, tutorialText);

        //Settings screen
        Label settingsTitle = new Label("Settings");
        settingsTitle.setTextFill(Color.WHITE);
        settingsTitle.setFont(font);

        Label someLabel = new Label("Display");
        Button toggleFullscreen = new Button("Toggle fullscreen");
        toggleFullscreen.setOnAction(e ->{
            Node source = (Node) e.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            if (stage.isFullScreen()){
                stage.setFullScreen(false);
            } else {
                stage.setFullScreen(true);
                stage.setFullScreenExitHint("");
                stage.setFullScreenExitKeyCombination(null);
            }

        });

        HBox fsBox = new HBox(someLabel, toggleFullscreen);

        VBox settingsBox = new VBox();
        settingsBox.setStyle("-fx-background-color: rgba(0,0,0,0.7); -fx-background-radius: 40px");
        settingsBox.setPrefSize(1000, 800);
        settingsBox.setAlignment(Pos.CENTER);
        settingsBox.setEffect(dropShadow);
        settingsBox.getChildren().addAll(settingsTitle, fsBox);

        VBox startMenu = new VBox();
        startMenu.setStyle("-fx-background-color: rgba(0,0,0,0); -fx-background-radius: 40px");

        startMenu.setAlignment(Pos.CENTER);
        startMenu.setEffect(dropShadow);
        startMenu.setSpacing(30);

        Button story = new Button("Start Game");
        Button settings = new Button("Settings");
        Button howToPlay = new Button("Tutorial");
        Button exit = new Button("Exit Game");
        story.setFont(menuFontLarge);
        story.setTextFill(Color.WHITE);
        story.setId("startButton");
        settings.setFont(menuFont);
        settings.setTextFill(Color.WHITE);
        settings.setId("settingsButton");
        howToPlay.setFont(menuFont);
        howToPlay.setTextFill(Color.WHITE);
        howToPlay.setId("howToPlayButton");
        exit.setFont(menuFont);
        exit.setTextFill(Color.WHITE);
        exit.setId("exitButton");
        //defaultStories.getChildren().add(story);

        story.setOnAction(e ->{
            menuBox.getChildren().remove(startMenu);
            menuBox.getChildren().addAll(backButton, defaultStoriesBox, importMenu);
        });
        settings.setOnAction(e ->{
            menuBox.getChildren().remove(startMenu);
            menuBox.getChildren().addAll(backButton, settingsBox);
        });
        howToPlay.setOnAction(e ->{
            menuBox.getChildren().remove(startMenu);
            menuBox.getChildren().addAll(backButton, tutorialBox);
        });
        backButton.setOnAction(e ->{
            menuBox.getChildren().clear();
            menuBox.getChildren().add(startMenu);
        });

        exit.setOnAction(e -> {
            Platform.exit();
        });
        menuBox.getChildren().add(startMenu);
        startMenu.getChildren().addAll(story, settings, howToPlay, exit);
        //Menu box



        //About page for the program. Displays Authors and version etc.
        Button about = new Button();
        about.setStyle("-fx-background-color: transparent");
        ImageView info = new ImageView("/gruppe/fire/Media/info.png");
        info.setFitHeight(20);
        info.setFitWidth(20);
        about.setGraphic(info);
        about.setOnMouseClicked(mouseEvent -> {
            // Create a new stage for the alert dialog
            Stage alertStage = new Stage();
            alertStage.initOwner(mainScene.getWindow()); // Set the owner to the current full-screen stage
            alertStage.initModality(Modality.APPLICATION_MODAL); // Make the alert dialog modal

            // Create the alert dialog
            Alert alertDialog = new Alert(Alert.AlertType.INFORMATION);
            ImageView logo = new ImageView("/gruppe/fire/Media/title.png");
            logo.setFitWidth(256);
            logo.setFitHeight(100);
            alertDialog.setGraphic(logo);
            alertDialog.setTitle("About");
            alertDialog.setHeaderText(version);
            alertDialog.setContentText("Created by: Gruppe 4");

            // Show the alert dialog
            Optional<ButtonType> response = alertDialog.showAndWait();
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
        bottom.getChildren().addAll(versionLabel, growBox,  about);
        root.setBottom(bottom);
        //Show stage

        // Create a Media object for the sound file
        Media sound = new Media(getClass().getResource("/gruppe/fire/Media/button.wav").toString());

        MediaPlayer mediaPlayer = new MediaPlayer(sound);

        mainScene.addEventFilter(ActionEvent.ACTION, event -> {
            mediaPlayer.seek(Duration.ZERO);
            mediaPlayer.setVolume(0.5);
            mediaPlayer.play();
        });


    }

}
