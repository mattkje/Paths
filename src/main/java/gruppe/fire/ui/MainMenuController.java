package gruppe.fire.ui;




import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Effect;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

public class MainMenuController {

    private File selectedFile;
    private static final String PATH = "Data/SavedPaths/";


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

    public void getBackground(BorderPane root){

        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(0);

        ImageView citySkyline = new ImageView("/gruppe/fire/Media/gameBG.png");
        ImageView citySkyline2 = new ImageView("/gruppe/fire/Media/gameBG.png");
        ImageView citySkyline3 = new ImageView("/gruppe/fire/Media/gameBG.png");
        ImageView citySkyline4 = new ImageView("/gruppe/fire/Media/gameBGC.png");
        ImageView citySkyline5 = new ImageView("/gruppe/fire/Media/gameBGC.png");
        ImageView citySkyline6 = new ImageView("/gruppe/fire/Media/gameBGC.png");

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

        ParallelTransition parallelTransition = new ParallelTransition(translateTransition4, translateTransition5, translateTransition6 );
        parallelTransition.setCycleCount(Animation.INDEFINITE);

        ParallelTransition parallelTransition2 = new ParallelTransition(translateTransition, translateTransition2, translateTransition3);
        parallelTransition2.setCycleCount(Animation.INDEFINITE);

        parallelTransition.play();
        parallelTransition2.play();

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
        root.getChildren().addAll(citySkyline, citySkyline2, citySkyline3, citySkyline4, citySkyline5, citySkyline6);
    }

    public MediaView getLoadingVideo()   {


        Media media = new Media (getClass().getResource("/gruppe/fire/Media/PathsLoad.mp4").toString());
        MediaPlayer player = new MediaPlayer (media);
        MediaView view = new MediaView (player);
        player.play();

        return view;
    }

    public MediaPlayer getBackgroundMusic(){
        Media sound = new Media(getClass().getResource("/gruppe/fire/Media/backgroundMusic.mp3").toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        //mediaPlayer.play();
        return mediaPlayer;
    }

}
