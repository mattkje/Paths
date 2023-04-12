package gruppe.fire.ui;

import javafx.animation.*;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import java.io.File;
import java.util.Random;

/**
 * This class represents a starting point for the GUI (Main menu).
 */
public class MainUI extends Application {

    /**
     *
     * @param stage
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {

        BorderPane root = new BorderPane();

        //Background Image
        Image backgroundImage = new Image("/gruppe/fire/Media/titleBackground.png");
        ImageView anim1 = new ImageView("/gruppe/fire/Media/anim1.png");
        ImageView anim2 = new ImageView("/gruppe/fire/Media/anim2.png");
        ImageView anim3 = new ImageView("/gruppe/fire/Media/anim3.png");
        ImageView anim4 = new ImageView("/gruppe/fire/Media/anim4.png");
        ImageView anim5 = new ImageView("/gruppe/fire/Media/anim5.png");
        BackgroundImage background = new BackgroundImage(backgroundImage, null, null, null, null);
        Background backgroundObject = new Background(background);
        root.setBackground(backgroundObject);

        //Animated background letters.
        Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);

        Random random = new Random();
        for (int i = 0; i < 1000; i++) {
            int x1 = 100;
            int y1 = random.nextInt(300);
            int x2 = 300;
            int y2 = random.nextInt(300);
            int x3 = 600;
            int y3 = random.nextInt(300);
            int x4 = 900;
            int y4 = random.nextInt(300);
            int x5 = 1100;
            int y5 = random.nextInt(300);
            KeyFrame keyFrame1 = new KeyFrame(Duration.seconds(i * 10), new KeyValue(anim1.translateXProperty(), x1), new KeyValue(anim1.translateYProperty(), y1));
            KeyFrame keyFrame2 = new KeyFrame(Duration.seconds(i * 10), new KeyValue(anim2.translateXProperty(), x2), new KeyValue(anim2.translateYProperty(), y2));
            KeyFrame keyFrame3 = new KeyFrame(Duration.seconds(i * 10), new KeyValue(anim3.translateXProperty(), x3), new KeyValue(anim3.translateYProperty(), y3));
            KeyFrame keyFrame4 = new KeyFrame(Duration.seconds(i * 10), new KeyValue(anim4.translateXProperty(), x4), new KeyValue(anim4.translateYProperty(), y4));
            KeyFrame keyFrame5 = new KeyFrame(Duration.seconds(i * 10), new KeyValue(anim5.translateXProperty(), x5), new KeyValue(anim5.translateYProperty(), y5));
            timeline.getKeyFrames().addAll(keyFrame1, keyFrame2, keyFrame3, keyFrame4, keyFrame5);
        }
        timeline.play();
        Pane animPane = new Pane();
        animPane.getChildren().addAll(anim1, anim2, anim3, anim4, anim5);
        animPane.setLayoutX(-200);
        animPane.setLayoutY(100);
        animPane.setOpacity(0.2);
        root.getChildren().addAll(animPane);


        //Main menu title
        BorderPane titlePane = new BorderPane();
        ImageView title = new ImageView("/gruppe/fire/Media/title.png");
        title.setFitHeight(200);
        title.setFitWidth(512);
        titlePane.setCenter(title);
        root.setTop(titlePane);

        //Shadow and font
        DropShadow dropShadow = new DropShadow();
        dropShadow.setOffsetY(5.0);
        dropShadow.setColor(Color.color(0, 0, 0, 0.5));
        Font font = Font.font("Arial",FontWeight.BOLD, 24);

        //Import file menu.
        VBox importMenu = new VBox();
        Label label = new Label("Saved stories:");
        ImageView selectGame = new ImageView("/gruppe/fire/Media/GameSelect.png");
        selectGame.setFitHeight(90);
        selectGame.setFitWidth(250);
        label.setFont(font);
        label.setTextFill(Color.WHITE);
        importMenu.getChildren().add(selectGame);
        importMenu.setStyle("-fx-background-color: rgba(0,0,0,0.7); -fx-background-radius: 40px");
        importMenu.setPrefWidth(400);
        importMenu.setMaxHeight(400);
        importMenu.setSpacing(20);
        importMenu.setAlignment(Pos.TOP_CENTER);
        importMenu.setEffect(dropShadow);
        FileChooser fileChooser = new FileChooser();

        //Open file button.
        Button continueButton = new Button("Open paths file");
        continueButton.setOnAction(e -> {
            File selectedFile = fileChooser.showOpenDialog(stage);
        });

        continueButton.setOnMouseEntered(e -> {
            continueButton.setStyle("-fx-background-color: #d1d1d1; -fx-background-radius: 20px ; -fx-cursor: HAND ");
        });

        continueButton.setOnMouseClicked(e -> {
            continueButton.setStyle("-fx-background-color: GREY; -fx-background-radius: 20px ; -fx-cursor: HAND ");
        });

        continueButton.setOnMouseExited(e -> {
            continueButton.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 20px ; -fx-cursor: HAND ");
        });

        continueButton.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 20px ; -fx-cursor: HAND ");
        continueButton.setFont(font);

        //Buttons to open saved stories.
        GridPane customStories = new GridPane();
        customStories.setPrefSize(400, 400);
        customStories.setMaxHeight(400);
        customStories.setAlignment(Pos.CENTER);

        String[] storyTitles = {"Slot 1", "Slot 2", "Slot 3", "Slot 4"};
        ImageView[] storyImages = {new ImageView("/gruppe/fire/Media/custom.png"), new ImageView("/gruppe/fire/Media/custom.png"), new ImageView("/gruppe/fire/Media/custom.png"), new ImageView("/gruppe/fire/Media/custom.png")};

        for (int i = 0; i < storyTitles.length; i++) {
            Button story = new Button();
            Label storyTitle = new Label(storyTitles[i]);
            storyTitle.setTextFill(Color.WHITE);
            storyTitle.setFont(font);
            story.setGraphic(storyImages[i]);
            story.setStyle("-fx-background-color: rgba(255,255,255,0.24); -fx-background-radius: 10px");
            customStories.add(story, 0, i + 1);
            customStories.add(storyTitle, 1, i + 1);
        }
        importMenu.getChildren().addAll(continueButton, label, customStories);

        GridPane defaultStories = new GridPane();
        defaultStories.setStyle("-fx-background-color: rgba(0,0,0,0.7); -fx-background-radius: 40px");
        defaultStories.setPrefSize(400, 400);
        defaultStories.setMaxHeight(400);
        defaultStories.setVgap(20);
        defaultStories.setAlignment(Pos.CENTER);
        defaultStories.setEffect(dropShadow);

        Label ourStories = new Label("Our stories:");
        ourStories.setFont(font);
        ourStories.setTextFill(Color.WHITE);
        defaultStories.add(ourStories, 0, 0);

        String[] storyTitles2 = {"Haunted House", "Murder Mystery", "Castle", "Space Ship"};
        ImageView[] storyImages2 = {new ImageView("/gruppe/fire/Media/1.png"), new ImageView("/gruppe/fire/Media/2.png"), new ImageView("/gruppe/fire/Media/3.png"), new ImageView("/gruppe/fire/Media/4.png")};

        for (int i = 0; i < storyTitles.length; i++) {
            Button story = new Button();
            Label storyTitle2 = new Label(storyTitles2[i]);
            storyTitle2.setTextFill(Color.WHITE);
            storyTitle2.setFont(font);
            story.setGraphic(storyImages2[i]);
            story.setStyle("-fx-background-color: transparent");
            defaultStories.add(story, 0, i + 1);
            defaultStories.add(storyTitle2, 1, i + 1);
        }


        //Default stories.
        /*
        GridPane defaultStories = new GridPane();
        defaultStories.setStyle("-fx-background-color: rgba(0,0,0,0.7); -fx-background-radius: 40px");
        defaultStories.setPrefSize(400, 400);
        defaultStories.setMaxHeight(400);
        defaultStories.setVgap(20);
        defaultStories.setAlignment(Pos.CENTER);
        defaultStories.setEffect(dropShadow);

        Label ourStories = new Label("Our stories:");
        ourStories.setFont(font);
        ourStories.setTextFill(Color.WHITE);
        defaultStories.add(ourStories, 0, 0);

        String[] storyTitles2 = {"Haunted House", "Murder Mystery", "Castle", "Space Ship"};
        ImageView[] storyImages2 = {new ImageView("/gruppe/fire/Media/1.png"), new ImageView("/gruppe/fire/Media/2.png"), new ImageView("/gruppe/fire/Media/3.png"), new ImageView("/gruppe/fire/Media/4.png")};

        for (int i = 0; i < storyTitles.length; i++) {
            Button story = new Button();
            Label storyTitle2 = new Label(storyTitles2[i]);
            storyTitle2.setTextFill(Color.WHITE);
            storyTitle2.setFont(font);
            story.setGraphic(storyImages2[i]);
            story.setStyle("-fx-background-color: rgba(255,255,255,0.24); -fx-background-radius: 10px");
            defaultStories.add(story, 0, i + 1);
            defaultStories.add(storyTitle2, 1, i + 1);
        }
         */



        //Menu box
        HBox menuBox = new HBox();
        menuBox.getChildren().addAll(importMenu, defaultStories);
        menuBox.setAlignment(Pos.CENTER);
        menuBox.setSpacing(10);

        root.setCenter(menuBox);

        //Version label.
        Label version = new Label("Version: pre-Aplha 0.1");
        version.setTextFill(Color.WHITE);
        root.setBottom(version);

        //Show stage
        Scene mainScene = new Scene(root, 1300,800);
        stage.setResizable(false);
        stage.setScene(mainScene);
        stage.setTitle("Paths");
        stage.getIcons().add(new Image("/gruppe/fire/Media/icon.png"));
        stage.show();
    }

    /**
     * Launch method.
     * @param args
     */
    public static void appMain(String[] args) {
        launch(args);
    }
}
