package gruppe.fire.ui;

import gruppe.fire.fileHandling.FileToStory;
import javafx.animation.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
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
import javafx.util.Duration;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Optional;

public class StartUp extends Application{

    private MainUI mainUI;
    @Override
    public void start(Stage stage) throws Exception {
        //Universal app version
        StackPane root = new StackPane();
        Scene mainScene = new Scene(root, 1300,800);

        mainScene.getStylesheets().add("https://fonts.googleapis.com/css2?family=Pacifico");
        mainScene.getStylesheets().add("https://fonts.googleapis.com/css2?family=Comfortaa");
        mainScene.getStylesheets().add(Objects.requireNonNull(this.getClass().getResource("/gruppe/fire/css/main.css")).toExternalForm());


        Media sound = new Media(getClass().getResource("/gruppe/fire/Media/button.wav").toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mainScene.addEventFilter(ActionEvent.ACTION, event -> {
            mediaPlayer.seek(Duration.ZERO);
            mediaPlayer.play();
        });


        stage.setResizable(true);
        stage.setFullScreen(true);
        stage.setScene(mainScene);
        stage.setTitle("Paths");
        stage.getIcons().add(new Image("/gruppe/fire/Media/icon.png"));
        stage.setFullScreenExitHint("");
        stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        stage.show();

    }
    public static void appMain(String[] args) {
        launch(args);
    }

}
