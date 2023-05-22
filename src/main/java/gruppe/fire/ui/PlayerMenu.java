package gruppe.fire.ui;

import gruppe.fire.filehandling.DataBase;
import gruppe.fire.filehandling.FileToPlayer;
import gruppe.fire.logic.JukeBox;
import gruppe.fire.logic.Player;
import java.io.File;
import java.nio.file.Paths;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;

/**
 * Represents the player select screen.
 * At this menu, the user should be able to create a player and goals
 *
 * @author Matti Kjellstadli
 * @version 2023-05-20
 */
public class PlayerMenu {


  private Image newProfileImage;

  /**
   * This method is responsible for building and displaying the player select screen.
   *
   * @param scene The game scene
   */
  public void start(Scene scene) {


    PlayerMenuController controller = new PlayerMenuController();
    JukeBox jukeBox = new JukeBox();

    MediaPlayer selectMusic = jukeBox.getPlayerMenuMusic();
    selectMusic.setOnEndOfMedia(() -> selectMusic.seek(javafx.util.Duration.ZERO));
    selectMusic.play();


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


    Font font = Font.font("Comfortaa", 24);





    //logo
    ImageView title = new ImageView("/gruppe/fire/Media/engineLogo.png");
    title.setFitWidth(107);
    title.setFitHeight(50);

    Font menuFont = Font.font("Pacifico", 44);
    Label playerTitle = new Label("Select player");
    playerTitle.setAlignment(Pos.CENTER);
    playerTitle.setTextFill(Color.WHITE);
    playerTitle.setFont(menuFont);


    FlowPane ppImageBox = new FlowPane();
    ppImageBox.setHgap(50);
    ppImageBox.setHgap(50);
    ppImageBox.setAlignment(Pos.CENTER);
    Label selectedPlayer = new Label();
    selectedPlayer.setFont(font);
    selectedPlayer.setAlignment(Pos.CENTER);

    DataBase dataBase = new DataBase();
    String[] players = dataBase.readPlayers();


    ImageView[] ppImages = new ImageView[players.length];
    Label[] ppLabels = new Label[players.length];

    //Reads playerfolder, and display all saved users.
    for (int i = 1; i <= players.length; i++) {

      File file = new File(players[i - 1]);
      //Extracts the number from the file path.
      String playerNumber = file.toPath().toString().replaceAll("\\D+", "");
      FileToPlayer fileToPlayer = new FileToPlayer(file);
      Player currentPlayer = fileToPlayer.readFile();
      Button deletePlayer = new Button("Delete");
      deletePlayer.setOnAction(event -> {
        controller.setActivePlayer("player1.dat");
        //Deletes the player with matching number.
        dataBase.deletePlayer(Integer.parseInt(playerNumber));
        selectMusic.dispose();
        start(scene);
      });
      ppImages[i - 1] = new ImageView(currentPlayer.getImage());
      ppImages[i - 1].setFitHeight(100);
      ppImages[i - 1].setFitWidth(100);
      ppLabels[i - 1] = new Label(currentPlayer.getName());
      ppLabels[i - 1].setTextFill(Color.WHITE);
      ppLabels[i - 1].setAlignment(Pos.CENTER);
      ppLabels[i - 1].setFont(font);
      Rectangle clip = new Rectangle();
      clip.setWidth(100);
      clip.setHeight(100);
      clip.setArcHeight(20);
      clip.setArcWidth(20);
      ppImages[i - 1].setClip(clip);
      VBox playerSelectBox = new VBox();
      playerSelectBox.setAlignment(Pos.CENTER);
      Button playerButton = new Button();
      playerButton.setId("ppButton");
      playerButton.setGraphic(ppImages[i - 1]);
      playerButton.setPadding(new Insets(30));
      playerButton.setStyle("-fx-background-radius: 10");
      playerSelectBox.getChildren().addAll(playerButton, ppLabels[i - 1]);
      if (!playerNumber.equals("1")) {
        playerSelectBox.getChildren().add(deletePlayer);
      } else {
        Button invisButton = new Button();
        invisButton.setOpacity(0);
        playerSelectBox.getChildren().add(invisButton); //To make icons line up
      }

      //Sets active player
      String playerName = ppLabels[i - 1].getText();
      String activePlayerString = "player" + playerNumber + ".txt";
      playerButton.setOnAction(event -> {
        controller.setActivePlayer(activePlayerString);
        selectedPlayer.setText("Selected Player: " + playerName);
        selectMusic.dispose();
      });
      ppImageBox.getChildren().add(playerSelectBox);
    }

    //Create new user button
    Button newPlayerButton = new Button();
    newPlayerButton.setId("ppButton");
    String imagePath = Paths.get("").toAbsolutePath() + "/Data/PlayerData/Images/pp.png";
    Image image = new Image(new File(imagePath).toURI().toString());
    ImageView newPlayer = new ImageView(image);
    newPlayer.setFitHeight(100);
    newPlayer.setFitWidth(100);
    Label createPlayer = new Label("New player");
    createPlayer.setTextFill(Color.WHITE);
    createPlayer.setAlignment(Pos.CENTER);
    createPlayer.setFont(font);
    Label createPlayerStatus = new Label();
    VBox newPlayerBox = new VBox();
    newPlayerBox.setAlignment(Pos.CENTER);
    newPlayerBox.setStyle(
        "-fx-background-color: rgba(255,255,255,0.14);-fx-background-radius: 20; -fx-padding: 20");
    newPlayerBox.getChildren().addAll(newPlayer, createPlayer, createPlayerStatus);
    newPlayerButton.setGraphic(newPlayerBox);
    newPlayerButton.setPadding(new Insets(30));

    ppImageBox.getChildren().add(newPlayerButton);


    //Create new user
    HBox newUserHbox = new HBox();
    newUserHbox.setAlignment(Pos.CENTER);
    newUserHbox.setSpacing(20);
    VBox newUserVbox = new VBox();
    newUserVbox.setAlignment(Pos.CENTER);
    newUserVbox.setSpacing(20);
    VBox imageSelectBox = new VBox();
    imageSelectBox.setAlignment(Pos.CENTER);
    imageSelectBox.setSpacing(20);
    VBox goalsListBox = new VBox();
    goalsListBox.setAlignment(Pos.CENTER);
    goalsListBox.setSpacing(20);
    GridPane newUserOptions = new GridPane();
    newUserOptions.setVgap(20);

    Label createPlayerTitle = new Label("Create player");
    createPlayerTitle.setFont(menuFont);
    createPlayerTitle.setTextFill(Color.WHITE);


    SpinnerValueFactory<Integer> healthFactory =
        new IntegerSpinnerValueFactory(1, 10);
    IntegerSpinnerValueFactory goldFactory =
        new IntegerSpinnerValueFactory(0, 15000);
    goldFactory.setAmountToStepBy(100);
    Spinner<Integer> healthSpinner = new Spinner<>(healthFactory);
    healthSpinner.setPromptText("Health");
    healthSpinner.getEditor().setFont(font);
    Spinner<Integer> goldSpinner = new Spinner<>(goldFactory);
    goldSpinner.setPromptText("Gold");
    goldSpinner.getEditor().setFont(font);


    Button createPlayerButton = new Button("Create player");
    createPlayerButton.setFont(font);
    Label playerNameLabel = new Label("Player name:");
    playerNameLabel.setFont(font);
    Label playerHealthLabel = new Label("Set health:");
    playerHealthLabel.setFont(font);
    Label playerGoldLabel = new Label("Set gold:");
    playerGoldLabel.setFont(font);
    TextField playerTextField = new TextField();
    playerTextField.setFont(font);

    newUserOptions.add(playerNameLabel, 0, 0);
    newUserOptions.add(playerTextField, 1, 0);
    newUserOptions.add(playerHealthLabel, 0, 1);
    newUserOptions.add(healthSpinner, 1, 1);
    newUserOptions.add(playerGoldLabel, 0, 2);
    newUserOptions.add(goldSpinner, 1, 2);
    newUserOptions.add(createPlayerButton, 0, 3);


    ImageView imageDisplay = new ImageView("/gruppe/fire/Media/noSelect.png");
    imageDisplay.setFitHeight(100);
    imageDisplay.setFitWidth(100);
    Button uploadImage = new Button("Upload image");
    uploadImage.setFont(font);
    uploadImage.setOnAction(event -> {
      FileChooser fileChooser = new FileChooser();
      FileChooser.ExtensionFilter extFilter =
          new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png");
      fileChooser.getExtensionFilters().add(extFilter);
      File newProfileImageFile = fileChooser.showOpenDialog(scene.getWindow());
      if (newProfileImageFile != null) {
        this.newProfileImage = new Image(newProfileImageFile.toURI().toString());
        imageDisplay.setImage(newProfileImage);
      }
    });

    ImageView backImage = new ImageView("/gruppe/fire/Media/back.png");
    Button backButton = new Button();
    backButton.setGraphic(backImage);
    backButton.setId("backButton");
    backButton.setPrefSize(100, 100);
    backButton.setAlignment(Pos.CENTER);
    backButton.setEffect(dropShadow);

    createPlayerButton.setOnAction(event -> {
      String playerName = playerTextField.getText();
      Image profileImage = newProfileImage;
      int playerHealth = healthSpinner.getValue();
      int playerGold = goldSpinner.getValue();

      Player addedPlayer = new Player.PlayerBuilder()
          .name(playerName)
          .image(profileImage)
          .health(playerHealth)
          .gold(playerGold)
          .build();
      dataBase.writeNewPlayerFile(addedPlayer);
      selectMusic.dispose();
      start(scene);
    });

    imageSelectBox.getChildren().addAll(imageDisplay, uploadImage);
    imageSelectBox.setStyle("-fx-background-color: rgba(0,0,0,0.7); -fx-background-radius: 40px; -fx-padding: 30");
    newUserVbox.getChildren().addAll(createPlayerTitle, newUserOptions);
    newUserVbox.setStyle("-fx-background-color: rgba(0,0,0,0.7); -fx-background-radius: 40px; -fx-padding: 30");
    newUserHbox.getChildren().addAll(backButton, newUserVbox, imageSelectBox);

    //Goal Menu
    var scoreFactory =
        new IntegerSpinnerValueFactory(0, 15000);
    scoreFactory.setAmountToStepBy(50);
    GridPane goalSetMenu = new GridPane();
    goalSetMenu.setAlignment(Pos.CENTER);
    goalSetMenu.setVgap(20);
    goalSetMenu.setHgap(20);
    goalSetMenu.setPadding(new Insets(70));
    goalSetMenu.setId("backButton");
    Label goldGoal = new Label("Gold goal:");
    goldGoal.setFont(font);
    Label healthGoal = new Label("Health goal:");
    healthGoal.setFont(font);
    Label scoreGoal = new Label("Score goal:");
    scoreGoal.setFont(font);
    Label inventoryGoal = new Label("Inventory goal:");
    inventoryGoal.setFont(font);
    Spinner<Integer> setGoldSpinner = new Spinner<>(goldFactory);
    setGoldSpinner.getEditor().setFont(font);
    Spinner<Integer> setHealthSpinner = new Spinner<>(healthFactory);
    setHealthSpinner.getEditor().setFont(font);
    Spinner<Integer> setScoreSpinner = new Spinner<>(scoreFactory);
    setScoreSpinner.getEditor().setFont(font);
    TextField inventoryField = new TextField();
    inventoryField.setFont(font);
    inventoryField.setPromptText("Mandatory items (,)");
    goalSetMenu.add(goldGoal, 0, 0);
    goalSetMenu.add(healthGoal, 0, 1);
    goalSetMenu.add(scoreGoal, 0, 2);
    goalSetMenu.add(inventoryGoal, 0, 3);
    goalSetMenu.add(setGoldSpinner, 1, 0);
    goalSetMenu.add(setHealthSpinner, 1, 1);
    goalSetMenu.add(setScoreSpinner, 1, 2);
    goalSetMenu.add(inventoryField, 1, 3);
    Button setGoals = new Button("Set goals");
    Button cancelGoals = new Button("Cancel");
    setGoals.setFont(font);
    cancelGoals.setFont(font);
    HBox buttonHbox = new HBox(cancelGoals, setGoals);
    buttonHbox.setSpacing(20);
    buttonHbox.setAlignment(Pos.CENTER);
    VBox goalSetBox = new VBox(goalSetMenu, buttonHbox);
    goalSetBox.setSpacing(20);
    goalSetBox.setAlignment(Pos.CENTER);


    VBox goalMenu = new VBox();
    goalMenu.setStyle("-fx-background-color: rgba(0,0,0,0.7); -fx-background-radius: 40px");
    goalMenu.setAlignment(Pos.CENTER);
    goalMenu.setEffect(dropShadow);
    goalMenu.setSpacing(30);
    ListView<String> goalsList = new ListView<>();
    String goalsString = dataBase.readGoalsFromFileList();
    goalsList.getItems().add(goalsString);
    Label goalStatus = new Label();
    goalStatus.setFont(font);
    goalStatus.setId(("goalStatus"));
    if (dataBase.checkGoalsEmpty()) {
      goalStatus.setText("No goals set!");
    }
    Label goalsTitle = new Label("Active goals");
    goalsTitle.setFont(font);
    Button addGoalsButton = new Button("Add goal");
    addGoalsButton.setFont(font);


    goalMenu.getChildren().addAll(goalsTitle, goalStatus, goalsList, addGoalsButton);
    goalMenu.setSpacing(10);
    goalMenu.setPadding(new Insets(20));

    HBox playerHbox = new HBox();
    playerHbox.setAlignment(Pos.CENTER);
    playerHbox.setSpacing(50);

    MainMenu mainMenu = new MainMenu();
    Font buttonFont = Font.font("Comfortaa", 34);
    Button cancelButton = new Button("Cancel");
    cancelButton.setPrefSize(400, 100);
    cancelButton.setFont(buttonFont);
    cancelButton.setTextFill(Color.WHITE);
    cancelButton.setOnAction(e -> {
      selectMusic.dispose();
      mainMenu.startMain(scene);
    });
    GameDisplay gameDisplay = new GameDisplay();
    Font menuFontLarge = Font.font("Pacifico", 64);
    Button startButton = new Button("Start Game");
    startButton.setPrefSize(1000, 100);
    startButton.setFont(buttonFont);
    startButton.setTextFill(Color.WHITE);
    startButton.setOnAction(e -> {
      if (dataBase.checkGoalsEmpty()) {
        controller.noGoalsPopUp(gameDisplay, font, menuFontLarge, scene, selectMusic);
      } else {
        selectMusic.dispose();
        gameDisplay.start(scene, false);
      }

    });


    HBox buttonBox = new HBox();
    buttonBox.setAlignment(Pos.CENTER);
    buttonBox.setSpacing(50);
    buttonBox.setMaxWidth(1440);
    buttonBox.getChildren().addAll(cancelButton, startButton);
    buttonBox.setEffect(dropShadow);
    buttonBox.setId("playerButtonBox");


    VBox playerVbox = new VBox();
    playerVbox.setMinWidth(400);
    VBox playerContainer = new VBox(playerVbox, buttonBox);
    playerContainer.setAlignment(Pos.CENTER);
    playerContainer.setSpacing(20);
    playerHbox.getChildren().addAll(playerContainer, goalMenu);
    playerVbox.getChildren().addAll(playerTitle, selectedPlayer, ppImageBox);
    newPlayerButton.setOnAction(e -> {
      if (players.length < 11) {
        playerHbox.getChildren().removeAll(goalMenu);
        playerContainer.getChildren().clear();
        playerContainer.getChildren().add(newUserHbox);
      } else {
        createPlayerStatus.setText("Player limit reached");
      }
    });
    backButton.setOnAction(e -> {
      playerContainer.getChildren().clear();
      playerContainer.getChildren().addAll(playerVbox, buttonBox);
      playerHbox.getChildren().add(goalMenu);
    });
    addGoalsButton.setOnAction(event -> {
      playerHbox.getChildren().removeAll(goalMenu);
      playerContainer.getChildren().clear();
      playerContainer.getChildren().addAll(goalSetBox);
    });
    cancelGoals.setOnAction(e -> {
      playerContainer.getChildren().clear();
      playerContainer.getChildren().addAll(playerVbox, buttonBox);
      playerHbox.getChildren().addAll(goalMenu);
    });
    setGoals.setOnAction(e -> {
      int goldInt = setGoldSpinner.getValue();
      int healthInt = setHealthSpinner.getValue();
      int scoreInt = setScoreSpinner.getValue();
      String inventoryString = inventoryField.getText();
      dataBase.writeGoalsToFile(goldInt, healthInt, scoreInt, inventoryString);
      selectMusic.dispose();
      start(scene);
    });


    playerVbox.setStyle("-fx-background-color: rgba(0,0,0,0.7); -fx-background-radius: 40px");
    playerVbox.setPrefSize(1100, 400);

    playerVbox.setAlignment(Pos.CENTER);
    playerVbox.setEffect(dropShadow);
    playerVbox.setSpacing(30);


    VBox menuBox = new VBox();
    menuBox.setAlignment(Pos.CENTER);
    menuBox.setSpacing(20);

    menuBox.getChildren().addAll(playerHbox);


    root.setCenter(menuBox);


  }

}
