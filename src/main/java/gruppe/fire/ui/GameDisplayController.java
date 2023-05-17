package gruppe.fire.ui;

import gruppe.fire.actions.Action;
import gruppe.fire.fileHandling.DataBase;
import gruppe.fire.fileHandling.FileToStory;
import gruppe.fire.logic.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Objects;

public class GameDisplayController {
    private ArrayList inventoryList;

    private Player player;

    public VBox saveStory() {

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
            gridPane.add(slot, 0, i - 1);
        }

        gridPane.add(saveLabel1, 1, 0);
        gridPane.add(saveLabel2, 1, 1);
        gridPane.add(saveLabel3, 1, 2);
        gridPane.add(saveLabel4, 1, 3);
        gridPane.add(saveButton1, 2, 0);
        gridPane.add(saveButton2, 2, 1);
        gridPane.add(saveButton3, 2, 2);
        gridPane.add(saveButton4, 2, 3);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setMaxHeight(400);
        gridPane.setMinWidth(400);


        //String activeStory = handler.getStoryTitle();

        Story story = handler.readFile();
        String activeStory = story.getTitle();

        Label activeFile = new Label("   Active story: " + activeStory);
        activeFile.setFont(Font.font("Segoe UI", FontWeight.BOLD, 15));
        activeFile.setStyle("-fx-background-color: transparent; -fx-padding: 10; -fx-text-fill: rgba(255,255,255,0.84); -fx-min-width: 100");
        activeFile.setMaxHeight(400);
        activeFile.setMinWidth(400);

        VBox vbox = new VBox();
        vbox.setMaxHeight(400);
        vbox.setMinWidth(400);
        vbox.setAlignment(Pos.CENTER);
        vbox.setStyle("-fx-background-color: rgb(14,0,44); -fx-background-radius: 30px");
        vbox.getChildren().addAll(infoLabel, activeFile, gridPane);

        // Create a new Scene with the GridPane as the root node
        return vbox;
    }

    /**
     * This method determines if a paths file is new.
     *
     * @return true if active path is already saved or if it is default, false otherwise.
     * @throws IOException Exception
     */
    public boolean checkIfDefault() throws IOException {
        DataBase dataBase = new DataBase();
        String activeStory = dataBase.getActiveStoryPath();
        if (activeStory.contains("paths1.paths") || activeStory.contains("paths2.paths") ||
                activeStory.contains("paths3.paths") || activeStory.contains("paths4.paths") ||
                activeStory.contains("Castle.paths") || activeStory.contains("HauntedHouse.paths") ||
                activeStory.contains("MurderMystery.paths") || activeStory.contains("SpaceShip.paths")) {
            return false;
        } else {
            return true;
        }

    }

    public boolean checkIfGPaths(){
        DataBase dataBase = new DataBase();
        if (dataBase.getActiveStoryPath().contains("currentGpaths")){
            return true;
        } else {
            return false;
        }
    }


    /**
     * Displays the opening passage to user
     *
     * @param game
     * @param actionBar
     * @param inventoryPane
     * @param gameTitle
     * @param roomTitle
     * @param roomContent
     * @param healthAmount
     * @param goldAmount
     * @param scoreAmount
     * @param font
     * @param dropShadow
     */
    public void writeOpeningPassage(Game game, HBox actionBar, FlowPane inventoryPane,
                                    Label gameTitle, Label roomTitle, Text roomContent,
                                    Label healthAmount, Label goldAmount, Label scoreAmount,
                                    Font font, DropShadow dropShadow, ImageView passageImage) {
        ArrayList links = game.getStory().getOpeningPassage().getLinks();
        int linkCount = links.size();

        for (int i = 0; i < linkCount; i++) {
            Link link = (Link) links.get(i);
            Button nextPath = new Button("");
            Passage passage = game.go(link);
            game.setCurrentPassage(passage);
            nextPath.setTextFill(Color.WHITE);
            nextPath.setText(link.getText());
            ImageView finalPassageImage = passageImage;
            nextPath.setOnAction(e -> {
                writePassage(game, link, actionBar, inventoryPane, passage, roomTitle,
                        roomContent, healthAmount, goldAmount, scoreAmount, font, dropShadow, finalPassageImage);
            });
            nextPath.setFont(font);
            nextPath.setEffect(dropShadow);
            actionBar.getChildren().add(nextPath);
        }

        gameTitle.setText(game.getStory().getTitle());
        roomTitle.setText(game.getStory().getOpeningPassage().getTitle());
        roomContent.setText(game.getStory().getOpeningPassage().getContent());
        passageImage.setImage(getPassageImage(game.getCurrentPassage()));

        healthAmount.setText(String.valueOf(game.getPlayer().getHealth()));
        goldAmount.setText(String.valueOf(game.getPlayer().getGold()));
        scoreAmount.setText(String.valueOf(game.getPlayer().getScore()));
    }

    /**
     * Displays the nest passage.
     *
     * @param game
     * @param link
     * @param actionBar
     * @param inventoryPane
     * @param passage
     * @param roomTitle
     * @param roomContent
     * @param healthAmount
     * @param goldAmount
     * @param scoreAmount
     * @param font
     * @param dropShadow
     */
    public void writePassage(Game game, Link link, HBox actionBar, FlowPane inventoryPane,
                             Passage passage, Label roomTitle, Text roomContent,
                             Label healthAmount, Label goldAmount, Label scoreAmount,
                             Font font, DropShadow dropShadow, ImageView passageImage) {

        //Refresh text
        roomTitle.setText(passage.getTitle());
        roomContent.setText(passage.getContent());


        //Refresh buttons
        actionBar.getChildren().clear();

        ArrayList<Action> actionArrayList = link.getActions();
        for (int j = 0; j < actionArrayList.size(); j++) {

            Action action = actionArrayList.get(j);
            action.execute(game.getPlayer());

            healthAmount.setText(String.valueOf(game.getPlayer().getHealth()));
            goldAmount.setText(String.valueOf(game.getPlayer().getGold()));
            scoreAmount.setText(String.valueOf(game.getPlayer().getScore()));
            passageImage.setImage(getPassageImage(game.getStory().getPassage(link)));
            for (String item : game.getPlayer().getInventory()) {
                if (flowPaneContainsItem(inventoryPane, item) == false){
                    inventoryPane = handleInventory(item, inventoryPane, font);
                }

            }

            ArrayList links2 = passage.getLinks();
            int linkCount = links2.size();

            for (int i = 0; i < linkCount; i++) {
                link = (Link) links2.get(i);
                Button nextPath = new Button("");
                Passage nextPassage = game.go(link);
                game.setCurrentPassage(passage);
                nextPath.setTextFill(Color.WHITE);
                nextPath.setText(link.getText());
                Link finalLink = link;
                FlowPane finalInventoryPane = inventoryPane;
                ImageView finalPassageImage = passageImage;
                nextPath.setOnAction(e -> {
                    writePassage(game, finalLink, actionBar, finalInventoryPane, nextPassage, roomTitle,
                            roomContent, healthAmount, goldAmount, scoreAmount, font, dropShadow, finalPassageImage);
                });
                nextPath.setFont(font);
                nextPath.setEffect(dropShadow);
                actionBar.getChildren().add(nextPath);

            }

        }

    }

    public FlowPane handleInventory(String item, FlowPane flowPane, Font font){
        ImageView itemImage = new ImageView("gruppe/fire/Media/noInv.png");
        itemImage.setFitHeight(100);
        itemImage.setFitWidth(100);
        Label itemName = new Label(item);
        itemName.setFont(font);
        itemName.setAlignment(Pos.CENTER);
        System.out.println(item);
        if (item.equals("Knife")){
            Image image = new Image(("gruppe/fire/Media/2 - Copy.png"));
            itemImage.setImage(image);
        }
        if (item.equals("TokenOfDeath")){
            Image image = new Image(("gruppe/fire/Media/bones.png"));
            itemImage.setImage(image);
        }
        if (item.contains("Clue")){
            Image image = new Image(("gruppe/fire/Media/footprint.png"));
            itemImage.setImage(image);
        }
        VBox itemBox = new VBox(itemImage, itemName);
        itemBox.setPadding(new Insets(20));
        itemBox.setAlignment(Pos.CENTER);
        flowPane.getChildren().add(itemBox);
        return flowPane;
    }

    public static boolean flowPaneContainsItem(FlowPane flowPane, String item) {
        for (Node child : flowPane.getChildren()) {
            if (child instanceof VBox) {
                VBox itemBox = (VBox) child;
                for (Node itemNode : itemBox.getChildren()) {
                    if (itemNode instanceof Label) {
                        Label itemName = (Label) itemNode;
                        if (itemName.getText().equals(item)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public Image getPassageImage(Passage passage){
        String passageImageString = ("Data/currentGpaths/Images/" + passage.getTitle() + ".png");
        File file = new File(passageImageString);
        String absolutePath = file.toURI().toString();
        System.out.println(absolutePath);
        return new Image(absolutePath);
    }

}