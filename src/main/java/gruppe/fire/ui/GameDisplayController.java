package gruppe.fire.ui;

import gruppe.fire.actions.Action;
import gruppe.fire.filehandling.DataBase;
import gruppe.fire.logic.Game;
import gruppe.fire.logic.Link;
import gruppe.fire.logic.Passage;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
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
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Represents a controller for the game menu class.
 * This class should handle events triggered by the GameDisplay.
 *
 * @author Matti Kjellstadli
 * @version 2023-05-18
 */
public class GameDisplayController {

  private ArrayList<String> winList;

  private Label roomTitleLabel;
  private Text roomContentLabel;
  private Label healthAmountLabel;
  private Label goldAmountLabel;
  private Label scoreAmountLabel;
  private ImageView passageImage;


  /**
   * This method determines if a paths file is new.
   *
   * @return true if active path is already saved or if it is default, false otherwise.
   */
  public boolean checkIfDefault() {
    DataBase dataBase = new DataBase();
    String activeStory = dataBase.getActiveStoryPath();
    return !(activeStory.contains("Castle.paths") || activeStory.contains("HauntedHouse.paths")
        || activeStory.contains("MurderMystery.paths") || activeStory.contains("SpaceShip.paths"));
  }

  /**
   * This method is responsible for acquiring and displaying the opening passage
   * to the user.
   *
   * @param game          The current game.
   * @param actionBar     The HBox containing the link buttons.
   * @param inventoryPane The flow pane containing the inventory items.
   * @param gameTitle     The game Title.
   * @param font          Default font.
   * @param dropShadow    Default shadow.
   */
  public void writeOpeningStringPassage(Game game, HBox actionBar, FlowPane inventoryPane,
                                        Label gameTitle, Font font, DropShadow dropShadow) {
    ArrayList<Link> links = game.getStory().getOpeningPassage().getLinks();

    for (Link link : links) {
      Button nextPath = new Button("");
      Passage passage = game.go(link);
      game.setCurrentPassage(passage);
      nextPath.setText(link.getText());
      if (checkGoals(game)) {
        goalsAccomplished();
        return;
      }
      nextPath.setOnAction(e ->
          writeStringPassage(game, link, actionBar, inventoryPane, passage, font, dropShadow));

      nextPath.setFont(font);
      nextPath.setEffect(dropShadow);
      actionBar.getChildren().add(nextPath);
    }

    gameTitle.setText(game.getStory().getTitle());
    setRoomTitleLabel(game.getStory().getOpeningPassage().getTitle());
    setRoomContentLabel(game.getStory().getOpeningPassage().getContent());
    setPassageImage(getPassageImage(game.getStory().getOpeningPassage().getTitle()));
    if (passageImage.getImage() != null) {
      passageImage.setFitHeight(500);
      passageImage.setFitWidth(500);
    } else {
      passageImage.setFitHeight(1);
      passageImage.setFitWidth(1);
    }

    setHealthAmountLabel(String.valueOf(game.getPlayer().getHealth()));
    setGoldAmountLabel(String.valueOf(game.getPlayer().getGold()));
    setScoreAmountLabel(String.valueOf(game.getPlayer().getScore()));
  }

  /**
   * This method is responsible for acquiring and displaying the opening passage
   * to the user.
   *
   * @param game          The current game.
   * @param actionBar     The HBox containing the link buttons.
   * @param inventoryPane The flow pane containing the inventory items.
   * @param passage       The current Title.
   * @param font          Default font.
   * @param dropShadow    Default shadow.
   */
  public void writeStringPassage(Game game, Link link, HBox actionBar, FlowPane inventoryPane,
                                 Passage passage, Font font, DropShadow dropShadow) {


    //Refresh text
    setRoomTitleLabel(passage.getTitle());
    setRoomContentLabel(passage.getContent());


    //Refresh buttons
    actionBar.getChildren().clear();

    ArrayList<Action> actionArrayList = link.getActions();
    for (Action value : actionArrayList) {

      if (value != null) {
        value.execute(game.getPlayer());
      }

      setHealthAmountLabel(String.valueOf(game.getPlayer().getHealth()));
      setGoldAmountLabel(String.valueOf(game.getPlayer().getGold()));
      setScoreAmountLabel(String.valueOf(game.getPlayer().getScore()));
      setPassageImage(getPassageImage(game.getStory().getPassageByLink(link).getTitle()));
      if (passageImage.getImage() != null) {
        passageImage.setFitHeight(500);
        passageImage.setFitWidth(500);
      } else {
        passageImage.setFitHeight(1);
        passageImage.setFitWidth(1);
      }

      for (String item : game.getPlayer().getInventory()) {
        if (!flowPaneContainsItem(inventoryPane, item)) {
          inventoryPane = handleInventory(item, inventoryPane, font);
        }
      }

    }
    ArrayList<Link> links2 = passage.getLinks();

    for (Link value : links2) {
      link = value;
      Button nextPath = new Button("");
      game.setCurrentPassage(passage);
      nextPath.setText(link.getText());
      Link finalLink = link;
      Passage nextPassage = game.go(link);
      FlowPane finalInventoryPane = inventoryPane;
      if (checkGoals(game)) {
        goalsAccomplished();
        Button restartButton = new Button("Restart");
        restartButton.setFont(font);
        restartButton.setEffect(dropShadow);
        restartButton.setOnAction(e -> {
          GameDisplay gameDisplay = new GameDisplay();
          gameDisplay.start(restartButton.getScene(), false);
        });
        Button mainMenuButton = new Button("Main menu");
        mainMenuButton.setFont(font);
        mainMenuButton.setEffect(dropShadow);
        mainMenuButton.setOnAction(e -> {
          MainMenu mainMenu = new MainMenu();
          mainMenu.startMain(mainMenuButton.getScene());
        });
        actionBar.getChildren().addAll(restartButton, mainMenuButton);
        return;
      }
      nextPath.setOnAction(e ->
          writeStringPassage(game, finalLink, actionBar, finalInventoryPane, nextPassage, font,
              dropShadow));
      nextPath.setFont(font);
      nextPath.setEffect(dropShadow);
      actionBar.getChildren().add(nextPath);
    }

  }

  public void setRoomTitleLabel(String roomTitle) {
    this.roomTitleLabel.setText(roomTitle);
  }

  public void setRoomContentLabel(String roomContent) {
    this.roomContentLabel.setText(roomContent);
  }

  public void setHealthAmountLabel(String healthAmount) {
    this.healthAmountLabel.setText(healthAmount);
  }

  public void setGoldAmountLabel(String goldAmount) {
    this.goldAmountLabel.setText(goldAmount);
  }

  public void setScoreAmountLabel(String scoreAmount) {
    this.scoreAmountLabel.setText(scoreAmount);
  }

  public void setPassageImage(Image currentImage) {
    this.passageImage.setImage(currentImage);
  }

  /**
   * This method is responsible setting all display nodes to the ones from gameDisplay.
   *
   * @param roomTitleLabel    Room name label.
   * @param roomContentLabel  Room content label.
   * @param healthAmountLabel Health amount label.
   * @param goldAmountLabel   Gold amount label.
   * @param scoreAmountLabel  Score amount label.
   * @param currentImage      Current passage image;
   */
  public void setLabels(Label roomTitleLabel, Text roomContentLabel, Label healthAmountLabel,
                        Label goldAmountLabel, Label scoreAmountLabel, ImageView currentImage) {
    this.roomTitleLabel = roomTitleLabel;
    this.roomContentLabel = roomContentLabel;
    this.healthAmountLabel = healthAmountLabel;
    this.goldAmountLabel = goldAmountLabel;
    this.scoreAmountLabel = scoreAmountLabel;
    this.passageImage = currentImage;
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
   * @param passageTitle Current passage title.
   * @return The image which corresponds to the passage, null otherwise.
   */
  public Image getPassageImage(String passageTitle) {
    if (!checkIfDefault() && checkFileExistence(
        "/gruppe/fire/Media/defaultPathsImages/MurderMysteryImages/" + passageTitle
            + ".png")) {
      return new Image("/gruppe/fire/Media/defaultPathsImages/MurderMysteryImages/"
          + passageTitle + ".png");

    } else if (checkFileExistence("Data/currentGpaths/Images/" + passageTitle + ".png")) {
      String passageImageString = ("Data/currentGpaths/Images/" + passageTitle + ".png");
      File file = new File(passageImageString);
      String absolutePath = file.toURI().toString();
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
  public boolean checkGoals(Game game) {
    this.winList = new ArrayList<>();
    game.getGoals().stream()
        .filter(g -> g.isFulfilled(game.getPlayer()))
        .forEach(g -> this.winList.add(g.getGoal()));
    if (this.winList.contains("0 Gold") && this.winList.contains("0 Health")
        && this.winList.contains("0 Points")) {
      return false;
    } else {
      return this.winList.get(0).contains("Gold") && this.winList.get(1).contains("Health")
          && this.winList.get(2).contains("Points");
    }
  }

  /**
   * This method is responsible for creating the goal-accomplished screen.
   */
  public void goalsAccomplished() {
    setRoomTitleLabel("Goal accomplished!");
    setRoomContentLabel("You reached all goals: " + winList);
    passageImage.setImage(new Image("/gruppe/fire/Media/scoreGoal.png"));
  }

}