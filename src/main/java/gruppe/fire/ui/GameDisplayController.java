package gruppe.fire.ui;

import gruppe.fire.fileHandling.DataBase;
import gruppe.fire.fileHandling.FileToStory;
import gruppe.fire.logic.GameBuilder;
import gruppe.fire.logic.Story;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.*;
import java.util.Objects;

public class GameDisplayController {
    public void saveStory() {

        // Create a GridPane to hold the controls
        GridPane gridPane = new GridPane();

        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10));
        gridPane.setStyle("-fx-padding: 20px");

        //Add an error message
        Label infoLabel = new Label("Select slot to save/overwrite");
        infoLabel.setStyle("-fx-background-color: transparent");
        infoLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        //gridPane.add(infoLabel, 0, 0);

        Button saveButton1 = new Button("Save");
        saveButton1.setOnAction(event1 -> {
            ((Stage) saveButton1.getScene().getWindow()).close();
        });

        Button saveButton2 = new Button("Save");
        saveButton2.setOnAction(event1 -> {
            ((Stage) saveButton2.getScene().getWindow()).close();
        });

        Button saveButton3 = new Button("Save");
        saveButton3.setOnAction(event1 -> {
            ((Stage) saveButton3.getScene().getWindow()).close();
        });

        Button saveButton4 = new Button("Save");
        saveButton4.setOnAction(event1 -> {
            ((Stage) saveButton4.getScene().getWindow()).close();
        });

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(event1 -> {
            ((Stage) cancelButton.getScene().getWindow()).close();
        });

        DataBase dataBase = new DataBase();
        File gameFile = new File(dataBase.getActiveStoryPath());
        FileToStory handler = new FileToStory(gameFile);

        //Sets slot titles as story title.
        String[] storyTitles;
        try {
            storyTitles = handler.readSavedStories();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Label saveLabel1 = new Label();
        saveLabel1.setText(storyTitles[0]);
        Label saveLabel2 = new Label();
        saveLabel2.setText(storyTitles[1]);
        Label saveLabel3 = new Label();
        saveLabel3.setText(storyTitles[2]);
        Label saveLabel4 = new Label();
        saveLabel4.setText(storyTitles[3]);

        for (int i = 1; i <= 4; i++) {
            Label slot = new Label("SLOT " + i);
            slot.setFont(Font.font("Segoe UI", FontWeight.BOLD, 30));
            slot.setStyle("-fx-background-color: transparent; -fx-padding: 0; -fx-text-fill: rgba(255,255,255,0.84); -fx-pref-width: 100; -fx-min-width: 100; -fx-effect: dropshadow(gaussian, rgb(29, 3, 89), 0, 6.8025, 0, 5);");
            gridPane.add(slot, 0, i-1);
        }

        gridPane.add(saveLabel1, 1, 0);
        gridPane.add(saveLabel2, 1, 1);
        gridPane.add(saveLabel3, 1, 2);
        gridPane.add(saveLabel4, 1, 3);
        gridPane.add(saveButton1, 2, 0);
        gridPane.add(saveButton2, 2, 1);
        gridPane.add(saveButton3, 2, 2);
        gridPane.add(saveButton4, 2, 3);
        gridPane.add(cancelButton,0,4);


        //String activeStory = handler.getStoryTitle();

        Story story = handler.readFile();
        String activeStory = story.getTitle();

        Label activeFile = new Label("   Active story: " + activeStory);
        activeFile.setFont(Font.font("Segoe UI", FontWeight.BOLD, 15));
        activeFile.setStyle("-fx-background-color: transparent; -fx-padding: 10; -fx-text-fill: rgba(255,255,255,0.84); -fx-min-width: 100");

        VBox vbox = new VBox();
        vbox.setStyle("-fx-background-color: rgb(14,0,44); -fx-border-color: #33029d; -fx-border-width: 10px");
        vbox.getChildren().addAll(infoLabel,activeFile, gridPane);

        // Create a new Scene with the GridPane as the root node
        Scene scene = new Scene(vbox);
        scene.getStylesheets().add(Objects.requireNonNull(this.getClass().getResource("/gruppe/fire/css/popUp.css")).toExternalForm());

        // Create a new Stage to hold the Scene
        Stage popupStage = new Stage();
        popupStage.setScene(scene);
        popupStage.initStyle(StageStyle.UNDECORATED);
        popupStage.initModality(Modality.APPLICATION_MODAL);
        // Set the title of the Stage and show it
        popupStage.setTitle("Save story");
        popupStage.setResizable(false);
        popupStage.getIcons().add(new Image("/gruppe/fire/Media/icon.png"));
        popupStage.show();
    }

    /**
     * This method determines if a paths file is new.
     * @return true if active path is already saved or if it is default, false otherwise.
     * @throws IOException Exception
     */
    public boolean checkIfDefault() throws IOException {
        DataBase dataBase = new DataBase();
        String activeStory = dataBase.getActiveStoryPath();
        if(activeStory.contains("paths1.paths") || activeStory.contains("paths2.paths") ||
                activeStory.contains("paths3.paths") || activeStory.contains("paths4.paths") ||
                activeStory.contains("Castle.paths") || activeStory.contains("HauntedHouse.paths") ||
                activeStory.contains("MurderMystery.paths") || activeStory.contains("SpaceShip.paths")){
            return false;
        } else {
            return true;
        }

    }

}
