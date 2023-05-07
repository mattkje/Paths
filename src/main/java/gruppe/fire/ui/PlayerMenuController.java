package gruppe.fire.ui;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.ResourceBundle;

public class PlayerMenuController {

    public void setActivePlayer(String filename){
        Path savedPlayers = Path.of("Data/PlayerData/Players/" + filename);
        try {
            FileWriter writer;
            writer = new FileWriter("Data/currentPlayerFile.cfg");
            writer.write(String.valueOf(savedPlayers));
            writer.close();

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
