package gruppe.fire.ui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.media.MediaView;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * Represents the game startup phase.
 * The class should play an intro video
 * before changing the scene root to the main menu.
 *
 * @author Matti Kjellstadli
 * @version 2023-05-18
 */
public class PathsGame extends Application {

  /**
   * This method should start the game after playing an intro video.
   *
   * @param stage The Game stage.
   */
  @Override
  public void start(Stage stage) {

    MainMenuController controller = new MainMenuController();

    MediaView mediaView = controller.getLoadingVideo();
    mediaView.getMediaPlayer().play();

    BorderPane root = new BorderPane();
    root.setPrefSize(1920, 1080);
    root.setStyle("-fx-background-color: linear-gradient(#6746a9, #3829cd)");
    HBox group = new HBox();
    mediaView.fitWidthProperty().bind(group.widthProperty());
    mediaView.fitHeightProperty().bind(group.heightProperty());
    Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
    double screenWidth = screenBounds.getWidth();
    double screenHeight = screenBounds.getHeight();
    group.setPrefSize(screenWidth, screenHeight);
    group.setAlignment(Pos.CENTER);
    group.getChildren().add(mediaView);
    root.setCenter(group);
    Scene scene = new Scene(root, 1920, 1080);

    MainMenu mainMenu = new MainMenu();
    mediaView.getMediaPlayer().setOnEndOfMedia(() -> Platform.runLater(() -> {
      mediaView.getMediaPlayer().dispose();
      mainMenu.startMain(scene);
    }));


    stage.setFullScreen(true);
    stage.setScene(scene);
    stage.setTitle("Paths");
    stage.getIcons().add(new Image("/gruppe/fire/Media/icon.png"));
    stage.setFullScreenExitHint("");
    stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
    stage.show();
  }

  /**
   * Responsible for launching the game.
   *
   * @param args Launch args.
   */
  public static void appMain(String[] args) {
    launch(args);
  }

}
