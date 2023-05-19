package gruppe.fire.ui;

import gruppe.fire.fileHandling.DataBase;
import gruppe.fire.logic.JukeBox;
import java.awt.Dimension;
import java.awt.Toolkit;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import java.util.Objects;

/**
 * Represents the File editor menu. This menu should let the user write
 * their own story and save it as a ".paths" file.
 * @author Matti Kjellstadli
 * @version 2023-05-18
 */
public class FileEditorMenu {

  /**
   * This method is responsible for building and displaying the file editor menu.
   * @param scene The game scene.
   */
  public void start(Scene scene, String string) {


    MainMenu mainMenu = new MainMenu();
    FileEditorMenuController controller = new FileEditorMenuController();
    MainMenuController mainController = new MainMenuController();
    JukeBox jukeBox = new JukeBox();

    MediaPlayer selectMusic = jukeBox.getPlayerMenuMusic();
    selectMusic.setOnEndOfMedia(() -> selectMusic.seek(javafx.util.Duration.ZERO));
    selectMusic.play();


    //Background
    BorderPane root = (BorderPane) scene.getRoot();
    root.getChildren().clear();
    root.setStyle("-fx-background-color: linear-gradient(#6746a9, #3829cd)");

    //Styling.
    scene.getStylesheets().add("https://fonts.googleapis.com/css2?family=Pacifico");
    scene.getStylesheets().add("https://fonts.googleapis.com/css2?family=Comfortaa");
    scene.getStylesheets().add("https://fonts.googleapis.com/css2?family=JetBrains+Mono");
    scene.getStylesheets().add(
        Objects.requireNonNull(this.getClass().getResource("/gruppe/fire/css/main.css"))
            .toExternalForm());
    root.setId("mainRoot");
    mainController.getBackground(root);


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
    titleShadow.setColor(Color.color(1, 1, 1));
    titleShadow.setSpread(1);
    titleShadow.setRadius(3);
    DropShadow glow = new DropShadow();
    glow.setColor(Color.WHITE);
    glow.setSpread(1);
    glow.setRadius(2);

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    double width = screenSize.getWidth();
    Font font = Font.font("Comfortaa", width/100);
    Font titleFontSmall = Font.font("Pacifico", width/27);
    Font textFont = Font.font("JetBrains Mono", 24);
    Font textFontSmall = Font.font("JetBrains Mono", 14);

    TextArea editArea = new TextArea();
    editArea.setMaxSize(1400, 1000);
    editArea.setFont(textFont);
    editArea.setId("fileEditArea");
    editArea.setPromptText("Start with title");
    VBox editContainer = new VBox(editArea);
    editContainer.setMaxSize(1400, 1000);
    editContainer.setPrefSize(1400, 1000);
    editContainer.setPadding(new Insets(50));
    editContainer.setId("editContainer");
    editContainer.setEffect(dropShadow);
    editContainer.setAlignment(Pos.CENTER);


    Label fileTitle = new Label("File Editor");
    fileTitle.setEffect(solidShadow);
    fileTitle.setFont(titleFontSmall);
    fileTitle.setAlignment(Pos.CENTER);
    HBox topBar = new HBox(fileTitle);
    topBar.setAlignment(Pos.CENTER);
    root.setTop(topBar);

    Button saveButton = new Button("Save file");
    saveButton.setFont(font);
    Button uploadButton = new Button("Upload file");
    uploadButton.setFont(font);
    Button backButton = new Button("Main menu");
    backButton.setFont(font);
    Label noFile = new Label();
    noFile.setFont(font);
    noFile.setAlignment(Pos.CENTER_LEFT);
    HBox menuBar = new HBox(noFile, uploadButton, saveButton, backButton);
    menuBar.setAlignment(Pos.CENTER_RIGHT);
    menuBar.setSpacing(20);
    menuBar.setPrefHeight(100);
    menuBar.setId("fileEditMenuBar");
    FileChooser.ExtensionFilter extFilter =
        new FileChooser.ExtensionFilter("Paths files (*.paths)", "*.paths");
    FileChooser fileChooser = new FileChooser();
    fileChooser.getExtensionFilters().add(extFilter);


    uploadButton.setOnAction(e -> {
      String selectedFile = controller.openFileButton(fileChooser, scene, noFile);
      editArea.setText(selectedFile);
    });
    saveButton.setOnAction(e -> {
      controller.saveFileButton(fileChooser, scene, editArea.getText(), noFile);
    });
    backButton.setOnAction(e -> {
      try {
        mainMenu.startMain(scene);
      } catch (Exception ex) {
        throw new RuntimeException(ex);
      }
    });
    root.setBottom(menuBar);
    DataBase dataBase = new DataBase();
    TextArea tutorialArea = new TextArea(dataBase.readTutorial());
    tutorialArea.setEditable(false);
    tutorialArea.setOpacity(1);
    tutorialArea.setFont(textFontSmall);
    tutorialArea.setPadding(new Insets(30));
    tutorialArea.setId("tutorialArea");
    tutorialArea.setPrefWidth(root.getWidth() * 0.3);
    HBox centerBox = new HBox(editContainer, tutorialArea);
    centerBox.setAlignment(Pos.CENTER);
    centerBox.setSpacing(70);
    root.setCenter(centerBox);
    editArea.setMinSize(editContainer.getWidth(), root.getHeight() * 0.7);

    if (!string.isEmpty()){
      editArea.setText(string);
    }
  }
}
