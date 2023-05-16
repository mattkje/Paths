package gruppe.fire.ui;


import gruppe.fire.fileHandling.DataBase;
import gruppe.fire.fileHandling.FileToStory;
import gruppe.fire.logic.Game;
import gruppe.fire.logic.Player;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.Objects;

public class MainMenuController {


    private ImageView citySkyline;
    private ImageView citySkyline2;
    private ImageView citySkyline3;
    private ImageView citySkyline4;
    private ImageView citySkyline5;
    private ImageView citySkyline6;
    private static final String PATH = "Data/SavedPaths/";


    /**
     * Responsible for writing the active ".paths" filepath to a file.
     * @param filename Name of the file which is being written.
     */
    public void setActiveFile(String filename){
        Path savedPaths = Path.of(PATH + filename);
        try {
            FileWriter writer;
            writer = new FileWriter("Data/currentPathsFile.cfg");
            writer.write(String.valueOf(savedPaths));
            writer.close();

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Responsible for opening saved paths slots, and checking if the slots are empty.
     * @param fileName File name of the slot.
     * @param playerMenu The player menu.
     * @param scene Game scene.
     * @param noFile Feedback label.
     */
    public void openSavedPath(String fileName, PlayerMenu playerMenu, Scene scene, Label noFile){
        setActiveFile(fileName);
        try{
            if(checkBrokenGame(new Game(new Player("Test", null, 0, 0, 0), new FileToStory(new File(PATH+fileName)).readFile()))){
                playerMenu.start(scene);
            }
        } catch (Exception ex) {
            noFile.setText("This slot is empty");
        }
    }

    /**
     * Sets one of the default paths as the active path.
     * @param filename Default path file name.
     */
    public void setDefaultPath(String filename){
        Path savedPaths = Path.of("src/main/resources/gruppe/fire/Paths/" + filename);
        try {
            FileWriter writer;
            writer = new FileWriter("Data/currentPathsFile.cfg");
            writer.write(String.valueOf(savedPaths));
            writer.close();

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Responsible for the animated background of the game.
     * @param root Root of the game scene.
     */
    public void getBackground(BorderPane root){

        DataBase dataBase = new DataBase();
        Map map = dataBase.readSettingsFromFile();
        boolean bg = (Boolean) map.get("bg");
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(0);
        Image cityImage = new Image("/gruppe/fire/Media/gameBG.png");
        Image cityImage2 = new Image("/gruppe/fire/Media/gameBGC.png");
        this.citySkyline = new ImageView(cityImage);
        this.citySkyline2 = new ImageView(cityImage);
        this.citySkyline3 = new ImageView(cityImage);
        this.citySkyline4 = new ImageView(cityImage2);
        this.citySkyline5 = new ImageView(cityImage2);
        this.citySkyline6 = new ImageView(cityImage2);

        TranslateTransition translateTransition =
                new TranslateTransition(Duration.millis(20000), citySkyline);
        translateTransition.setFromX(0);
        translateTransition.setToX(-1 * 1300);
        translateTransition.setInterpolator(Interpolator.LINEAR);

        TranslateTransition translateTransition2 =
                new TranslateTransition(Duration.millis(20000), citySkyline2);
        translateTransition2.setFromX(1300);
        translateTransition2.setToX(0);
        translateTransition2.setInterpolator(Interpolator.LINEAR);

        TranslateTransition translateTransition3 =
                new TranslateTransition(Duration.millis(20000), citySkyline3);
        translateTransition3.setFromX(2600);
        translateTransition3.setToX(1300);
        translateTransition3.setInterpolator(Interpolator.LINEAR);

        TranslateTransition translateTransition4 =
                new TranslateTransition(Duration.millis(10000), citySkyline4);
        translateTransition4.setFromX(0);
        translateTransition4.setToX(-1 * 1300);
        translateTransition4.setInterpolator(Interpolator.LINEAR);

        TranslateTransition translateTransition5 =
                new TranslateTransition(Duration.millis(10000), citySkyline5);
        translateTransition5.setFromX(1300);
        translateTransition5.setToX(0);
        translateTransition5.setInterpolator(Interpolator.LINEAR);

        TranslateTransition translateTransition6 =
                new TranslateTransition(Duration.millis(10000), citySkyline6);
        translateTransition6.setFromX(2600);
        translateTransition6.setToX(1300);
        translateTransition6.setInterpolator(Interpolator.LINEAR);

        //Need two parallelTransitions as it stops if all are in one.
        ParallelTransition parallelTransition = new ParallelTransition(translateTransition, translateTransition2, translateTransition3);
        parallelTransition.setCycleCount(Animation.INDEFINITE);
        ParallelTransition parallelTransition2 = new ParallelTransition(translateTransition4, translateTransition5, translateTransition6 );
        parallelTransition2.setCycleCount(Animation.INDEFINITE);
        parallelTransition2.play();
        parallelTransition.play();

        citySkyline.fitHeightProperty().bind(root.heightProperty());
        citySkyline2.fitHeightProperty().bind(root.heightProperty());
        citySkyline3.fitHeightProperty().bind(root.heightProperty());
        citySkyline4.fitHeightProperty().bind(root.heightProperty());
        citySkyline5.fitHeightProperty().bind(root.heightProperty());
        citySkyline6.fitHeightProperty().bind(root.heightProperty());

        citySkyline4.setLayoutY(200);
        citySkyline5.setLayoutY(200);
        citySkyline6.setLayoutY(200);

        citySkyline.setStyle("-fx-opacity: 0.1");
        citySkyline2.setStyle("-fx-opacity: 0.1");
        citySkyline3.setStyle("-fx-opacity: 0.1");
        citySkyline4.setEffect(colorAdjust);
        citySkyline5.setEffect(colorAdjust);
        citySkyline6.setEffect(colorAdjust);
        if(bg){
            root.getChildren().addAll(citySkyline, citySkyline2, citySkyline3, citySkyline4, citySkyline5, citySkyline6);
        }
    }

    /**
     * Responsible for handling open file action.
     * @param selectedFile The selected file.
     * @param fileChooser File chooser which is used to get file.
     * @param scene The game scene.
     * @param noFile Feedback label.
     */
    public void openFileButton(File selectedFile, FileChooser fileChooser, Scene scene, Label noFile){
        selectedFile = fileChooser.showOpenDialog(scene.getWindow());

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
    }

    /**
     * TODO: Write here
     * @param selectedFile
     * @param playerMenu
     * @param scene
     * @param noFile
     */
    public void startGameButton(File selectedFile, PlayerMenu playerMenu, Scene scene, Label noFile){
        if(String.valueOf(selectedFile).endsWith(".paths") && selectedFile != null){
            try{
                if(checkBrokenGame(new Game(new Player("Test", null, 0, 0, 0),new FileToStory(selectedFile).readFile())) == true){
                    playerMenu.start(scene);
                }
            } catch (Exception ex) {
                noFile.setText("Could not load file. Wrong format?");
            }
        } else {
            noFile.setText("You have to select a file first.");
        }
    }

    public void fullscreenButton(Stage stage, Label title1, Label title2, HBox titleBox, Font titleFontSmall, Font titleFont){
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
    }

    public void exitButton(BorderPane root, Font font, Font menuFontLarge, HBox titleBox, HBox bottom, HBox menuBox, MediaPlayer player){
        player.setVolume(player.getVolume()-0.4);
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
        quitBox.setId("quitBox");
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
            player.setVolume(player.getVolume()+0.4);

        });
    }

    /**
     * Responsible for handling user input from settings.
     * @param fs Fullscreen
     * @param bg Background
     * @param vlm Music volume
     * @param vlm2 FX volume
     */
    public void changeSettings(boolean fs, boolean bg, double vlm, double vlm2){
        DataBase dataBase = new DataBase();
        dataBase.writeSettingsToFile(fs, bg, vlm,vlm2);
    }

    /**
     * Responsible for giving the player instant feedback when disabling/enabling the background animation.
     * @param root The game root node.
     * @param bg Background boolean
     */
    public void updateBackground(BorderPane root, boolean bg){
        if (!bg){
            root.getChildren().removeAll(citySkyline, citySkyline2, citySkyline3, citySkyline4, citySkyline5, citySkyline6);
        }
        if (bg){
            root.getChildren().addAll(citySkyline, citySkyline2, citySkyline3, citySkyline4, citySkyline5, citySkyline6);
            citySkyline6.toBack();
            citySkyline5.toBack();
            citySkyline4.toBack();
            citySkyline3.toBack();
            citySkyline2.toBack();
            citySkyline.toBack();
        }
    }

    /**
     * Responsible for changing the game resolution.
     * @param resolution Selected resolution as string.
     * @param stage Game stage
     * @param title1 Part one of title text.
     * @param title2 Part two of title text.
     * @param titleBox Box containing oth title parts.
     * @param titleFontSmall Smaller font for title text.
     */
    public void handleResolutionChange(String resolution, Stage stage, Label title1, Label title2, HBox titleBox, Font titleFontSmall) {
        if (stage.isFullScreen()) {
            stage.setFullScreen(false);
        }

        switch (resolution) {
            case "1920 x 1080" -> {
                stage.setWidth(1920);
                stage.setHeight(1080);
            }
            case "800 x 600" -> {
                title1.setFont(titleFontSmall);
                title2.setFont(titleFontSmall);
                titleBox.setSpacing(-26);
                stage.setWidth(800);
                stage.setHeight(600);
            }
            // Handle unknown resolution
        }
    }

    /**
     * Validates the imported paths file by creating a game object and checking if crucial content is null.
     * @param game The created Game object.
     * @return True if paths file is valid, false otherwise.
     */
    public boolean checkBrokenGame(Game game){
        String one = game.getStory().getOpeningPassage().toString();
        String two = game.getStory().getTitle();
        String three = game.getStory().getPassages().toString();
        String four = game.begin(game.getStory().getOpeningPassage()).toString();
        return one != null && two != null && three != null && four != null;
    }

    /**
     * Responsible for returning the startup animation.
     * @return Startup animation
     */
    public MediaView getLoadingVideo()   {

        Media media = new Media (Objects.requireNonNull(getClass().getResource("/gruppe/fire/Media/PathsLoad.mp4")).toString());
        MediaPlayer player = new MediaPlayer (media);
        MediaView view = new MediaView (player);
        //player.play();

        return view;
    }


    /**
     * Responsible for returning the button noise.
     * @param fxVolume FX volume set in settings.
     * @return FX noises.
     */
    public MediaPlayer getButtonClick(double fxVolume){
        Media sound = new Media(Objects.requireNonNull(getClass().getResource("/gruppe/fire/Media/button.wav")).toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setVolume(fxVolume);

        return mediaPlayer;

    }

}
