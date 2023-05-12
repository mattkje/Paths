package gruppe.fire.ui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class StartUpVideo extends Application {

    private MainMenu mainMenu;

    private MainUiController controller;
    @Override
    public void start(Stage stage)  {

        this.mainMenu = new MainMenu();
        this.controller = new MainUiController();

        MediaView mediaView = controller.getLoadingVideo();
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double screenWidth = screenBounds.getWidth();
        double screenHeight = screenBounds.getHeight();

        BorderPane root = new BorderPane();
        HBox group = new HBox();
        mediaView.fitWidthProperty().bind(group.widthProperty());
        mediaView.fitHeightProperty().bind(group.heightProperty());
        group.setPrefSize(screenWidth, screenHeight);
        group.setAlignment(Pos.CENTER);
        group.getChildren().add(mediaView);
        root.setCenter(group);
        Scene scene = new Scene(root);
        mediaView.getMediaPlayer().setOnEndOfMedia(() -> {
            Platform.runLater(() -> {
                try {
                    mediaView.getMediaPlayer().dispose();
                    mainMenu.start(scene);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        });

        stage.setFullScreen(true);
        stage.setScene(scene);
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
