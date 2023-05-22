package gruppe.fire.ui;


import gruppe.fire.filehandling.DataBase;
import gruppe.fire.goals.Goal;
import gruppe.fire.logic.Game;
import gruppe.fire.logic.JukeBox;
import gruppe.fire.logic.Passage;
import gruppe.fire.logic.Player;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.util.List;
import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

/**
 * Represents the game menu GUI.
 * This class lets the user play a story file.
 *
 * @author Matti Kjellstadli
 * @version 2023-05-18
 */
public class GameDisplay {

  private HBox actionBar;

  private static final String GOAL = "Goal: ";

  /**
   * This method is responsible for building and displaying the game menu.
   *
   * @param scene   The game scene.
   * @param ifSaved True if game is saved, false otherwise.
   */
  public void start(Scene scene, Boolean ifSaved) {


    JukeBox jukeBox = new JukeBox();
    DataBase dataBase = new DataBase();
    File gameFile = new File(dataBase.getActiveStoryPath());
    File playerFile = new File(dataBase.getActivePlayerPath());

    //Add goals.
    Game game = dataBase.createGame(playerFile, gameFile);
    List<Goal> goalList = dataBase.readGoalsFromFile();
    game.setGoalsList(goalList);


    MediaPlayer mediaPlayer = jukeBox.getGameplayMusic();
    mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.seek(javafx.util.Duration.ZERO));
    mediaPlayer.play();

    //Background
    BorderPane root = (BorderPane) scene.getRoot();
    root.getChildren().clear();
    root.setStyle("-fx-background-color: linear-gradient(#6746a9, #3829cd)");
    MainMenuController mainMenuController = new MainMenuController();
    mainMenuController.getBackground(root);


    //Shadows and fonts
    DropShadow dropShadow = new DropShadow();
    dropShadow.setOffsetY(5.0);
    dropShadow.setColor(Color.color(0, 0, 0, 0.5));
    DropShadow hopShadow = new DropShadow();
    hopShadow.setOffsetY(-5.0);
    hopShadow.setColor(Color.color(0, 0, 0, 0.5));
    DropShadow glow = new DropShadow();
    glow.setColor(Color.WHITE);
    glow.setSpread(1);
    glow.setRadius(2);


    //logo
    Label title = new Label("Paths");
    Font menuFont = Font.font("Pacifico", 44);
    title.setFont(menuFont);

    HBox growBox = new HBox();
    HBox.setHgrow(growBox, Priority.ALWAYS); // set horizontal grow priority
    growBox.setMaxWidth(Double.MAX_VALUE); // set maximum width to a large value

    //Back to main menu
    Button mainMenuButton = new Button();
    mainMenuButton.setId("topButton");
    ImageView mainMenuImage = new ImageView("/gruppe/fire/Media/home.png");
    mainMenuImage.setFitWidth(50);
    mainMenuImage.setFitHeight(50);
    mainMenuButton.setGraphic(mainMenuImage);
    mainMenuButton.setEffect(dropShadow);
    MainMenu mainMenu = new MainMenu();
    mainMenuButton.setOnAction(e -> {
      mainMenu.startMain(scene);
      mediaPlayer.dispose();
    });

    //Options
    VBox optionsBox = new VBox();
    optionsBox.setSpacing(30);
    optionsBox.setStyle("-fx-background-color: rgba(0,0,0,0.7)");
    optionsBox.setAlignment(Pos.CENTER);
    Font pauseFont = Font.font("Comfortaa", 34);
    Button resume = new Button("Resume game");
    resume.setFont(pauseFont);
    resume.setPrefSize(400, 100);
    Button restart = new Button("Restart game");
    restart.setFont(pauseFont);
    restart.setPrefSize(400, 100);
    Button tutorial = new Button("Help");
    tutorial.setFont(pauseFont);
    tutorial.setPrefSize(400, 100);
    Button settings = new Button("Settings");
    settings.setFont(pauseFont);
    settings.setPrefSize(400, 100);
    Button back = new Button("Exit to main menu");
    back.setFont(pauseFont);
    back.setPrefSize(400, 100);
    optionsBox.getChildren().addAll(resume, restart, tutorial, settings, back);

    Button optionsButton = new Button();
    ImageView playerMenuImage = new ImageView("/gruppe/fire/Media/menu.png");
    optionsButton.setId("topButton");
    playerMenuImage.setFitWidth(50);
    playerMenuImage.setFitHeight(50);
    optionsButton.setGraphic(playerMenuImage);
    optionsButton.setEffect(dropShadow);
    StackPane gameWindowPane = new StackPane();
    optionsButton.setOnAction(e -> {
      gameWindowPane.getChildren().add(optionsBox);
      root.setBottom(null);
    });
    resume.setOnAction(e -> {
      gameWindowPane.getChildren().remove(optionsBox);
      root.setBottom(actionBar);
    });
    restart.setOnAction(e -> {
      start(scene, ifSaved);
      mediaPlayer.dispose();
    });
    back.setOnAction(e -> {
      mainMenu.startMain(scene);
      mediaPlayer.dispose();
    });

    ImageView saveImage = new ImageView("/gruppe/fire/Media/diskette.png");

    Button saveStateButton = new Button();
    saveStateButton.setId("topButton");
    saveStateButton.setGraphic(saveImage);


    saveImage.setFitWidth(50);
    saveImage.setFitHeight(50);

    HBox menuBar = new HBox();
    menuBar.setStyle("-fx-background-color: rgba(0,0,0,0.7); -fx-padding: 10px");
    GameDisplayController controller = new GameDisplayController();

    //Disable saving for gpaths
    if (controller.checkIfGpaths()) {
      menuBar.getChildren()
          .addAll(title, growBox, optionsButton, mainMenuButton);
    } else {
      menuBar.getChildren().addAll(title, growBox, saveStateButton, optionsButton, mainMenuButton);
    }

    menuBar.setEffect(dropShadow);
    menuBar.setSpacing(10);
    root.setTop(menuBar);

    BorderPane gameWindow = new BorderPane();
    gameWindow.setStyle(
        "-fx-background-color: rgba(0,0,0,0.7); -fx-background-radius: 20px; -fx-spacing: 20px");
    gameWindow.setMaxWidth(700);
    gameWindow.setMaxHeight(500);
    gameWindow.setEffect(dropShadow);


    gameWindowPane.getChildren().add(gameWindow);
    root.setCenter(gameWindowPane);


    //Stats
    GridPane inventory = new GridPane();
    inventory.setAlignment(Pos.CENTER_LEFT);
    inventory.setVgap(40);
    inventory.setHgap(50);

    Player player = game.getPlayer();
    Image profileImage = player.getImage();
    ImageView playerPicture = new ImageView(profileImage);
    playerPicture.setFitWidth(50);
    playerPicture.setFitHeight(50);
    String playerString = player.getName();
    Label playerName = new Label(playerString);


    FlowPane inventoryPane = new FlowPane();
    inventoryPane.setMaxHeight(600);

    inventoryPane.setId("inventoryPane");

    playerName.setTextFill(Color.WHITE);
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    double width = screenSize.getWidth();
    Font font = Font.font("Comfortaa", width / 130);


    playerName.setFont(font);
    Label info = new Label("Stats");
    info.setFont(menuFont);
    Label health = new Label("Health:");
    health.setFont(font);
    Label gold = new Label("Gold:");
    gold.setFont(font);
    Label score = new Label("Score:");
    score.setFont(font);

    Label healthGoalLabel = new Label(GOAL);
    healthGoalLabel.setFont(font);
    Label goldGoalLabel = new Label(GOAL);
    goldGoalLabel.setFont(font);
    Label scoreGoalLabel = new Label(GOAL);
    scoreGoalLabel.setFont(font);
    Label inventoryTitle = new Label("Inventory");
    inventoryTitle.setFont(menuFont);
    inventory.add(playerPicture, 1, 0);
    inventory.add(playerName, 0, 0);
    inventory.add(info, 0, 1);
    inventory.add(health, 0, 2);
    inventory.add(gold, 0, 3);
    inventory.add(score, 0, 4);
    VBox inventoryBox = new VBox();
    inventoryBox.setStyle("-fx-background-color: rgba(0,0,0,0.7); -fx-padding: 50px");
    inventoryBox.getChildren().addAll(inventory, inventoryTitle, inventoryPane);
    inventoryBox.prefWidthProperty().bind(root.widthProperty().multiply(0.3));

    ImageView showImage = new ImageView("/gruppe/fire/Media/back.png");
    TranslateTransition translate = new TranslateTransition();
    translate.setNode(showImage);
    translate.setDuration(Duration.millis(1500));
    translate.setCycleCount(Animation.INDEFINITE);
    translate.setFromX(-5);
    translate.setToX(5);
    translate.setAutoReverse(true);
    translate.play();

    showImage.setScaleX(-1);
    Button showInventory = new Button();
    showInventory.setGraphic(showImage);
    showInventory.setStyle(
        "-fx-background-color: rgba(0,0,0,0.7); -fx-background-radius: 0px; -fx-translate-y: 0px;");
    showInventory.setPrefSize(80, 2000);
    HBox sideBox = new HBox();
    sideBox.getChildren().addAll(showInventory, inventoryBox);
    root.setRight(sideBox);
    showInventory.setOnAction(e -> {
      if (!sideBox.getChildren().contains(inventoryBox)) {
        sideBox.getChildren().add(inventoryBox);
        showImage.setScaleX(-1);
      } else {
        sideBox.getChildren().remove(inventoryBox);
        showInventory.setGraphic(showImage);
        showImage.setScaleX(1);
      }

    });

    //The Game
    Text roomContent = new Text();
    roomContent.setWrappingWidth(400);
    ImageView passageImage = new ImageView();

    Button cancelButton = new Button("Cancel");
    cancelButton.setOnAction(event1 ->
        root.setCenter(gameWindow));
    Label gameTitle = new Label();
    gameTitle.setAlignment(Pos.CENTER);
    gameTitle.setFont(menuFont);
    gameTitle.setTextFill(Color.WHITE);
    Label roomTitle = new Label();
    roomTitle.setAlignment(Pos.CENTER);
    roomTitle.setFont(font);
    roomTitle.setTextFill(Color.WHITE);
    roomContent.setFont(font);
    roomContent.setFill(Color.WHITE);
    Text gameRoom = new Text();
    gameRoom.setTextAlignment(TextAlignment.CENTER);
    gameRoom.setFont(font);
    gameRoom.setFill(Color.WHITE);
    Label healthAmount = new Label();
    Label goldAmount = new Label();
    Label scoreAmount = new Label();
    inventory.add(healthAmount, 1, 2);
    inventory.add(goldAmount, 1, 3);
    inventory.add(scoreAmount, 1, 4);
    inventory.add(healthGoalLabel, 2, 2);
    inventory.add(goldGoalLabel, 2, 3);
    inventory.add(scoreGoalLabel, 2, 4);
    Label healthGoal = new Label(game.getGoals().get(1).getGoal());
    Label goldGoal = new Label(game.getGoals().get(0).getGoal());
    Label scoreGoal = new Label(game.getGoals().get(2).getGoal());
    healthGoal.setFont(font);
    goldGoal.setFont(font);
    scoreGoal.setFont(font);
    inventory.add(healthGoal, 3, 2);
    inventory.add(goldGoal, 3, 3);
    inventory.add(scoreGoal, 3, 4);

    this.actionBar = new HBox();
    actionBar.setEffect(hopShadow);
    actionBar.setSpacing(30);
    actionBar.setAlignment(Pos.CENTER);
    actionBar.setStyle("-fx-background-color: rgba(0,0,0,0.7); -fx-padding: 10px");


    //Import text
    saveStateButton.setOnAction(e -> {
      dataBase.gameToFile(game);
    });

    Game savedGame = dataBase.readFile();
    if (Boolean.TRUE.equals(ifSaved)) {
      controller.setLabels(roomTitle, roomContent, healthAmount,
          goldAmount, scoreAmount, passageImage);
      controller.writeOpeningStringPassage(savedGame, actionBar, inventoryPane, gameTitle, font,
          dropShadow);
    } else {
      controller.setLabels(roomTitle, roomContent, healthAmount,
          goldAmount, scoreAmount, passageImage);
      controller.writeOpeningStringPassage(game, actionBar, inventoryPane, gameTitle, font,
          dropShadow);
    }

    HBox titleBox = new HBox();
    titleBox.getChildren().add(gameTitle);
    titleBox.setAlignment(Pos.CENTER);

    healthAmount.setFont(font);
    healthAmount.setTextFill(Color.WHITE);
    goldAmount.setFont(font);
    goldAmount.setTextFill(Color.WHITE);
    scoreAmount.setFont(font);
    scoreAmount.setTextFill(Color.WHITE);
    gameWindow.setTop(titleBox);
    VBox gameBox = new VBox();
    gameBox.setSpacing(50);
    gameBox.setAlignment(Pos.CENTER);
    gameBox.getChildren().addAll(roomTitle, gameRoom, passageImage, roomContent);
    gameBox.setPadding(new Insets(20));
    gameWindow.setCenter(gameBox);
    root.setBottom(actionBar);


  }
}
