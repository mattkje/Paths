package gruppe.fire.ui;




import gruppe.fire.fileHandling.FileToStory;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;

import javafx.scene.Scene;
import javafx.scene.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

public class MainUiController {

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
