package gruppe.fire.ui;

import gruppe.fire.actions.Action;
import gruppe.fire.fileHandling.DataBase;
import gruppe.fire.logic.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;

/**
 * Represents a controller for the game menu class.
 * This class should handle events triggered by the GameDisplay.
 *
 * @author Matti Kjellstadli
 * @version 2023-05-18
 */
public class GameDisplayController {

  private Player player;


  /**
   * This method determines if a paths file is new.
   *
   * @return true if active path is already saved or if it is default, false otherwise.
   * @throws IOException Exception
   */
  public boolean checkIfDefault() {
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

  public boolean checkIfGPaths() {
    DataBase dataBase = new DataBase();
    if (dataBase.getActiveStoryPath().contains("currentGpaths")) {
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
            roomContent, healthAmount, goldAmount, scoreAmount, font, dropShadow,
            finalPassageImage);
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
      passageImage.setImage(getPassageImage(game.getStory().getPassageByLink(link)));

      for (String item : game.getPlayer().getInventory()) {
        if (!flowPaneContainsItem(inventoryPane, item)) {
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
          checkGoals(game);
          writePassage(game, finalLink, actionBar, finalInventoryPane, nextPassage, roomTitle,
              roomContent, healthAmount, goldAmount, scoreAmount, font, dropShadow,
              finalPassageImage);
        });
        nextPath.setFont(font);
        nextPath.setEffect(dropShadow);
        actionBar.getChildren().add(nextPath);

      }

    }

  }

  /**
   * This method is responsible for displaying inventory items in the form of an image
   * with a text.
   *
   * @param item     Item name
   * @param flowPane FlowPane for items in inventory.
   * @param font     Font used for inventory items.
   * @return FlowPane containing all items in inventory.
   */
  public FlowPane handleInventory(String item, FlowPane flowPane, Font font) {
    ImageView itemImage = new ImageView("gruppe/fire/Media/noInv.png");
    itemImage.setFitHeight(100);
    itemImage.setFitWidth(100);
    Label itemName = new Label(item);
    itemName.setFont(font);
    itemName.setAlignment(Pos.CENTER);
    System.out.println(item);
    if (item.equals("Knife")) {
      Image image = new Image(("gruppe/fire/Media/2.png"));
      itemImage.setImage(image);
    }
    if (item.equals("TokenOfDeath")) {
      Image image = new Image(("gruppe/fire/Media/bones.png"));
      itemImage.setImage(image);
    }
    if (item.contains("Clue")) {
      Image image = new Image(("gruppe/fire/Media/footprint.png"));
      itemImage.setImage(image);
    }
    VBox itemBox = new VBox(itemImage, itemName);
    itemBox.setPadding(new Insets(20));
    itemBox.setAlignment(Pos.CENTER);
    flowPane.getChildren().add(itemBox);
    return flowPane;
  }

  /**
   * Responsible for handling duplicate inventory items by searching if
   * the flowPane contains a label with the same name as the item.
   * Duplicate items should be ignored.
   *
   * @param flowPane FlowPane for items in inventory.
   * @param item     Item name.
   * @return True if flowPane contains item, false otherwise;
   */
  public static boolean flowPaneContainsItem(FlowPane flowPane, String item) {
    return flowPane.getChildren().stream()
        .filter(VBox.class::isInstance)
        .map(VBox.class::cast)
        .flatMap(itemBox -> itemBox.getChildren().stream())
        .filter(Label.class::isInstance)
        .map(Label.class::cast)
        .anyMatch(label -> label.getText().equals(item));
  }


  /**
   * This method is responsible for get the image which corresponds to the passage.
   *
   * @param passage Current passage.
   * @return The image which corresponds to the passage, null if passage does not have
   * a corresponding image.
   */
  public Image getPassageImage(Passage passage) {
    if (!checkIfDefault() && checkFileExistence(
        "/gruppe/fire/Media/defaultPathsImages/MurderMysteryImages/" + passage.getTitle().strip() +
            ".png")) {
      return new Image("/gruppe/fire/Media/defaultPathsImages/MurderMysteryImages/" +
          passage.getTitle().strip() + ".png");

    } else if (checkFileExistence("Data/currentGpaths/Images/" + passage.getTitle() + ".png")) {
      String passageImageString = ("Data/currentGpaths/Images/" + passage.getTitle() + ".png");
      File file = new File(passageImageString);
      String absolutePath = file.toURI().toString();
      System.out.println(absolutePath);
      return new Image(absolutePath);

    } else {
      return null;
    }

  }

  /**
   * This method is responsible for checking if a file exists.
   *
   * @param filePath Filepath to check.
   * @return True if the file exists, false otherwise;
   */
  public boolean checkFileExistence(String filePath) {
    if (!checkIfDefault()) {
      URL resourceUrl = getClass().getResource(filePath);
      return resourceUrl != null;
    } else {
      File file = new File(filePath);
      return file.exists();
    }
  }

  /**
   * Responsible for checking if goals are fulfilled.
   *
   * @param game Current game.
   */
  public void checkGoals(Game game) {
    game.getGoals().stream()
        .filter(g -> g.isFulfilled(game.getPlayer()))
        .forEach(g -> System.out.println(g.getGoal()));
  }

}