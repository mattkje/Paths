package gruppe.fire.ui;


import gruppe.fire.fileHandling.DataBase;
import gruppe.fire.fileHandling.FileToGame;
import gruppe.fire.fileHandling.FileToPlayer;
import gruppe.fire.fileHandling.FileToStory;
import gruppe.fire.logic.Game;
import gruppe.fire.logic.Player;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.*;
import javafx.util.Duration;

import java.awt.event.WindowFocusListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Stack;

/**
 * This class represents a starting point for the GUI (Main menu).
 */
public class MainMenu {

    private PlayerMenu playerMenu;

    private GameDisplay gameDisplay;

    private DataBase dataBase;

    private Map map;

    private MainMenuController controller;

    private File selectedFile;

    private double musicVolume;

    private double fxVolume;

    private MediaPlayer player;
    private MediaPlayer fxPlayer;

    private Boolean ifSaved = false;


    /**
     *
     * @throws Exception
     */

    public void start(Scene mainScene) throws Exception {
        //Universal app version
        String version = "Version: 2023.05.15";


        this.dataBase = new DataBase();
        this.map = dataBase.readSettingsFromFile();
        this.musicVolume = (Double) map.get("vlm");
        this.fxVolume = (Double) map.get("vlm2");

        this.controller = new MainMenuController();
        this.playerMenu = new PlayerMenu();
        this.gameDisplay = new GameDisplay();



        BorderPane root = (BorderPane) mainScene.getRoot();
        controller.changeSettings((Boolean) map.get("fs"), (Boolean) map.get("bg"), musicVolume, musicVolume);
        root.getChildren().clear();

        this.player = controller.getBackgroundMusic(musicVolume);
        player.play();

        this.fxPlayer = controller.getButtonClick(fxVolume);
        mainScene.addEventFilter(ActionEvent.ACTION, event -> {
            fxPlayer.seek(Duration.ZERO);
            fxPlayer.play();
        });



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


        Font font = Font.font("Comfortaa", 24);
        Font titleFont = Font.font("Pacifico", 300);
        Font titleFontSmall = Font.font("Pacifico", 100);
        Font menuFont = Font.font("Pacifico", 34);
        Font menuFontLarge = Font.font("Pacifico", 64);




        Label title1 = new Label("P");
        Label title2 = new Label("aths");
        HBox titleBox = new HBox();
        titleBox.setAlignment(Pos.CENTER);
        titleBox.getChildren().addAll(title1,title2);
        title1.setEffect(solidShadow);
        title2.setEffect(solidShadow);

        //Screen size check
        Stage stage = (Stage) mainScene.getWindow();
        if (stage.getWidth() > 1300){
            title1.setFont(titleFont);
            title2.setFont(titleFont);
            titleBox.setSpacing(-50);
        } else {
            title1.setFont(titleFontSmall);
            title2.setFont(titleFontSmall);
            titleBox.setSpacing(-26);
        }


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

        //Game Starting point. Checks if imported file is valid, and opens the next menu.
        startGame.setOnAction(e -> {
            if(String.valueOf(selectedFile).endsWith(".paths") && selectedFile != null){
                try{
                    if(controller.checkBrokenGame(new Game(new Player("Test", null, 0, 0, 0),new FileToStory(selectedFile).readFile())) == true){
                        playerMenu.start(mainScene);
                    }
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
            controller.openSavedPath("paths1.paths", playerMenu, mainScene, noFile);
        });
        customStories.add(customStory1, 0, 0);

        Button customStory2 = new Button();
        customStory2.setStyle("-fx-font-family: Comfortaa");
        customStory2.setText(storyTitles[1]);
        customStory2.setTextFill(Color.WHITE);
        customStory2.setPrefWidth(300);
        customStory2.setOnAction(e ->{

            controller.openSavedPath("paths2.paths", playerMenu, mainScene, noFile);
        });
        customStories.add(customStory2, 0, 1);

        Button customStory3 = new Button();
        customStory3.setStyle("-fx-font-family: Comfortaa");
        customStory3.setText(storyTitles[2]);
        customStory3.setTextFill(Color.WHITE);
        customStory3.setPrefWidth(300);
        customStory3.setOnAction(e ->{

            controller.openSavedPath("paths3.paths", playerMenu, mainScene, noFile);
        });
        customStories.add(customStory3, 0, 2);

        Button customStory4 = new Button();
        customStory4.setStyle("-fx-font-family: Comfortaa");
        customStory4.setText(storyTitles[3]);
        customStory4.setTextFill(Color.WHITE);
        customStory4.setPrefWidth(300);
        customStory4.setOnAction(e ->{

            controller.openSavedPath("paths4.paths", playerMenu, mainScene, noFile);

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

        VBox savedBox = new VBox();
        Label savedTitle = new Label("Previously played");
        Button continueGame = new Button("Continue");
        continueGame.setFont(font);
        continueGame.setOnAction(e ->{
            this.ifSaved = true;
            gameDisplay.start(mainScene, ifSaved);

        });
        FileToGame fileToGame = new FileToGame();
        Game gamePreview = fileToGame.readFile();

        Label savedStoryTitle = new Label("Game: "+gamePreview.getStory().getTitle());
        savedStoryTitle.setFont(font);
        Label savedStoryPassage = new Label("Room: "+gamePreview.getStory().getOpeningPassage().getTitle());
        savedStoryPassage.setFont(font);
        Label savedStoryPlayer = new Label("Player: "+gamePreview.getPlayer().getName());
        savedStoryPlayer.setFont(font);
        Label savedPlayerGold = new Label("Gold: "+ gamePreview.getPlayer().getGold());
        Label savedPlayerHealth = new Label("Health: "+gamePreview.getPlayer().getHealth());
        Label savedPlayerScore = new Label("Score: "+gamePreview.getPlayer().getScore());

        savedTitle.setFont(menuFont);
        savedTitle.setTextFill(Color.WHITE);
        savedTitle.setAlignment(Pos.CENTER);
        label.setFont(menuFont);
        label.setTextFill(Color.WHITE);
        HBox scoreHBox = new HBox(savedPlayerGold, savedPlayerHealth, savedPlayerScore);
        scoreHBox.setAlignment(Pos.CENTER);
        scoreHBox.setSpacing(10);
        VBox savedVBox = new VBox(savedStoryTitle, savedStoryPassage, savedStoryPlayer, scoreHBox);
        savedVBox.setAlignment(Pos.CENTER);
        savedVBox.setStyle("-fx-background-color: rgba(54,54,54,0.5); -fx-background-radius: 30");
        savedVBox.setMaxWidth(380);
        savedBox.getChildren().addAll(savedTitle, savedVBox, continueGame);
        savedBox.setStyle("-fx-background-color: rgba(0,0,0,0.7); -fx-background-radius: 40px");
        savedBox.setPrefWidth(400);
        savedBox.setMaxHeight(400);
        savedBox.setSpacing(20);
        savedBox.setAlignment(Pos.TOP_CENTER);
        savedBox.setEffect(dropShadow);

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
        settingsTitle.setPadding(new Insets(20));
        settingsTitle.setFont(font);

        Label someLabel = new Label("Display");
        someLabel.setFont(font);
        someLabel.setAlignment(Pos.CENTER);
        Button toggleFullscreen = new Button("Toggle fullscreen");
        toggleFullscreen.setFont(font);
        toggleFullscreen.setAlignment(Pos.CENTER);
        toggleFullscreen.setOnAction(e ->{
            if (stage.isFullScreen()){
                stage.setFullScreen(false);
                stage.setWidth(1380);
                stage.setHeight(800);
                title1.setFont(titleFontSmall);
                title2.setFont(titleFontSmall);
                titleBox.setSpacing(-26);
            } else {
                stage.setFullScreen(true);
                stage.setFullScreenExitHint("");
                stage.setFullScreenExitKeyCombination(null);
                title1.setFont(titleFont);
                title2.setFont(titleFont);
                titleBox.setSpacing(-50);

            }

        });

        //Change Resolution
        Label smallScreen = new Label("Set resolution");
        smallScreen.setFont(font);
        smallScreen.setAlignment(Pos.CENTER);

        ComboBox<String> resolutionComboBox = new ComboBox<>();
        resolutionComboBox.setId("resolutionComboBox");
        resolutionComboBox.getItems().addAll("1920 x 1080", "800 x 600");
        resolutionComboBox.getSelectionModel().selectFirst();
        resolutionComboBox.setOnAction(e -> {
            String selectedResolution = resolutionComboBox.getSelectionModel().getSelectedItem();
            controller.handleResolutionChange(selectedResolution, stage, title1, title2, titleBox, titleFontSmall);
        });

        Label backgroundLabel = new Label("Background");
        backgroundLabel.setFont(font);
        backgroundLabel.setAlignment(Pos.CENTER);
        Button disableAnimation = new Button("Disable background");
        disableAnimation.setFont(font);
        disableAnimation.setAlignment(Pos.CENTER);
        disableAnimation.setOnAction(e ->{
            controller.changeSettings((Boolean) map.get("fs"), false, (Double) map.get("vlm"), (Double) map.get("vlm2"));
            controller.updateBackground(root, false);
        });
        Button enableBackground = new Button("Enable background");
        enableBackground.setFont(font);
        enableBackground.setAlignment(Pos.CENTER);
        enableBackground.setOnAction(e ->{
            controller.changeSettings((Boolean) map.get("fs"), true, (Double) map.get("vlm"), (Double) map.get("vlm2"));
            controller.updateBackground(root, true);
        });

        HBox fsBox = new HBox(someLabel, toggleFullscreen);
        fsBox.setAlignment(Pos.CENTER);
        fsBox.setSpacing(60);

        HBox ssBox = new HBox(smallScreen, resolutionComboBox);
        ssBox.setAlignment(Pos.CENTER);
        ssBox.setSpacing(60);

        HBox bgBox = new HBox(disableAnimation, enableBackground);
        bgBox.setAlignment(Pos.CENTER);
        bgBox.setSpacing(60);

        //Settings containers
        TabPane settingsPane = new TabPane();
        settingsPane.setStyle("-fx-background-color: rgba(0,0,0,0.7); -fx-background-radius: 40px");
        settingsPane.setPrefSize(1000, 800);
        settingsPane.setEffect(dropShadow);

        VBox displayBox = new VBox(fsBox, ssBox, backgroundLabel, bgBox);
        displayBox.setAlignment(Pos.CENTER);
        displayBox.setSpacing(30);
        Tab display = new Tab("Display");
        display.setContent(displayBox);
        display.setClosable(false);
        settingsPane.getTabs().add(display);

        //Volume control

        Slider musicVolumeSlider = new Slider(0, 1, musicVolume);
        Label musicVolumeLabel = new Label("Music volume");
        musicVolumeLabel.setFont(font);
        HBox msBox = new HBox(musicVolumeLabel, musicVolumeSlider);
        msBox.setAlignment(Pos.CENTER);
        msBox.setSpacing(60);

        Slider fxVolumeSlider = new Slider(0, 1, fxVolume);
        Label fxVolumeLabel = new Label("FX volume");
        fxVolumeLabel.setFont(font);
        HBox fxBox = new HBox(fxVolumeLabel, fxVolumeSlider);
        fxBox.setAlignment(Pos.CENTER);
        fxBox.setSpacing(60);

        Button setVol = new Button("Save settings");
        setVol.setFont(font);
        setVol.setAlignment(Pos.CENTER);
        setVol.setOnAction(e ->{
            controller.changeSettings((Boolean) map.get("fs"), (Boolean) map.get("bg"), musicVolumeSlider.getValue(), fxVolumeSlider.getValue());
        });


        VBox audioBox = new VBox(msBox, fxBox, setVol);
        audioBox.setAlignment(Pos.CENTER);
        audioBox.setSpacing(30);
        Tab audio = new Tab("Audio");
        audio.setContent(audioBox);
        audio.setClosable(false);
        settingsPane.getTabs().add(audio);

        VBox settingsBox = new VBox(settingsTitle, settingsPane);
        settingsBox.setAlignment(Pos.CENTER);

        //Start menu
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

        //Story menu button functions
        story.setOnAction(e ->{
            menuBox.getChildren().remove(startMenu);
            menuBox.getChildren().addAll(backButton, defaultStoriesBox, importMenu, savedBox);
        });
        settings.setOnAction(e ->{
            menuBox.getChildren().remove(startMenu);
            menuBox.getChildren().addAll(backButton, settingsBox);
            musicVolumeSlider.valueProperty().bindBidirectional(player.volumeProperty());
            fxVolumeSlider.valueProperty().bindBidirectional(fxPlayer.volumeProperty());
        });
        howToPlay.setOnAction(e ->{
            menuBox.getChildren().remove(startMenu);
            menuBox.getChildren().addAll(backButton, tutorialBox);
        });
        backButton.setOnAction(e ->{
            menuBox.getChildren().clear();
            menuBox.getChildren().add(startMenu);
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

            Label aboutTitle = new Label("About");
            Label team = new Label("Created by G4");
            team.setFont(font);
            Label versionLabel = new Label(version);
            Button dismissButton = new Button("Dismiss");
            dismissButton.setFont(font);
            aboutTitle.setFont(menuFontLarge);
            aboutTitle.setAlignment(Pos.CENTER);
            VBox aboutBox = new VBox(aboutTitle, team, versionLabel, dismissButton);
            aboutBox.setSpacing(20);
            aboutBox.setPrefSize(600, 400);
            aboutBox.setStyle("-fx-background-color: rgba(0,0,0,0.9); -fx-background-radius: 30px");
            aboutBox.setAlignment(Pos.CENTER);
            Popup popup = new Popup();
            popup.getContent().add(aboutBox);
            popup.show(mainScene.getWindow());
            dismissButton.setOnAction(e ->{
                popup.hide();
            });
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

        exit.setOnAction(e -> {


            Label quitTitle = new Label("Exit the game");
            Label confirmationLabel = new Label("Are you sure you want to quit?");
            confirmationLabel.setFont(font);
            Button yesButton = new Button("Yes");
            Button noButton = new Button("No");
            HBox quitButtons = new HBox(yesButton, noButton);
            quitButtons.setSpacing(40);
            quitButtons.setAlignment(Pos.CENTER);
            yesButton.setFont(font);
            noButton.setFont(font);
            quitTitle.setFont(menuFontLarge);
            quitTitle.setAlignment(Pos.CENTER);
            VBox quitBox = new VBox(quitTitle, confirmationLabel, quitButtons);
            quitBox.setSpacing(20);
            quitBox.setMinSize(600, 300);
            quitBox.setStyle("-fx-background-color: rgba(0,0,0,0.9)");
            quitBox.setAlignment(Pos.CENTER);
            root.setTop(null);
            root.setBottom(null);
            root.setCenter(quitBox);


            yesButton.setOnAction(exitEvent ->{
                Platform.exit();
            });
            noButton.setOnAction(exitEvent ->{
                root.setTop(titleBox);
                root.setBottom(bottom);
                root.setCenter(menuBox);
            });

        });


    }

}
