package gruppe.fire.ui;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * This class represents the GUI for the game itself.
 */
public class GameDisplay extends Application {

    /**
     * Starting point for the game.
     * @param stage
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        BorderPane root = new BorderPane();

        //TODO Create HUD for game.

        //Show stage
        Scene mainScene = new Scene(root, 1300,800);
        stage.setResizable(true);
        stage.setScene(mainScene);
        stage.setTitle("Paths");
        stage.getIcons().add(new Image("/gruppe/fire/Media/icon.png"));
        stage.show();
    }
}
