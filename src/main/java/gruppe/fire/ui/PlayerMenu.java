package gruppe.fire.ui;

import gruppe.fire.fileHandling.DataBase;
import gruppe.fire.fileHandling.FileToPlayer;
import gruppe.fire.logic.GameBuilder;
import gruppe.fire.logic.Player;
import javafx.animation.ParallelTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import java.io.File;
import java.nio.file.Paths;

public class PlayerMenu {

    private GameDisplay gameScreen;

    private MainMenu mainMenu;

    private ParallelTransition parallelTransition;

    private PlayerMenuController controller;

    private Image newProfileImage;


    public void start(Scene scene) {


        //Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        this.gameScreen =  new GameDisplay();
        this.mainMenu = new MainMenu();
        this.controller = new PlayerMenuController();

        DataBase dataBase = new DataBase();
        GameBuilder gameBuilder = new GameBuilder(new File(dataBase.getActivePlayerPath()), new File(dataBase.getActiveStoryPath()));

        //Background

        BorderPane root = (BorderPane) scene.getRoot();
        root.getChildren().clear();
        root.setStyle("-fx-background-color: linear-gradient(#6746a9, #3829cd)");
        MainMenuController mainMenuController = new MainMenuController();
        mainMenuController.getBackground(root, false);


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

        //Font font = Font.loadFont(MainMenu.class.getResource("/gruppe/fire/fonts/Comfortaa-Regular.ttf").toExternalForm(), 24);
        //Font buttonFont = Font.loadFont(MainMenu.class.getResource("/gruppe/fire/fonts/Comfortaa-Regular.ttf").toExternalForm(), 34);
        //Font menuFont = Font.loadFont(MainMenu.class.getResource("/gruppe/fire/fonts/Pacifico-Regular.ttf").toExternalForm(), 44);

        Font font = Font.font("Comfortaa", 24);
        Font buttonFont = Font.font("Comfortaa", 34);
        Font menuFont = Font.font("Pacifico", 44);


        //logo
        ImageView title = new ImageView("/gruppe/fire/Media/logo.png");
        title.setFitWidth(107);
        title.setFitHeight(50);


        Label playerTitle = new Label("Select player");
        playerTitle.setAlignment(Pos.CENTER);
        playerTitle.setTextFill(Color.WHITE);
        playerTitle.setFont(menuFont);




        FlowPane ppImageBox = new FlowPane();
        //ppImageBox.setSpacing(100);
        ppImageBox.setHgap(50);
        ppImageBox.setHgap(50);
        ppImageBox.setAlignment(Pos.CENTER);



        String[] players = gameBuilder.createGame().readPlayers();


        ImageView[] ppImages = new ImageView[players.length];
        Label[] ppLabels = new Label[players.length];

        //Reads playerfolder, and display all saved users.
        for (int i = 1; i <= players.length; i++) {
            File file = new File(players[i-1]);
            FileToPlayer fileToPlayer = new FileToPlayer(file);
            Player currentPlayer = fileToPlayer.readFile();

            ppImages[i-1] = new ImageView(currentPlayer.getImage());
            ppImages[i-1].setFitHeight(100);
            ppImages[i-1].setFitWidth(100);
            ppLabels[i-1] = new Label(currentPlayer.getName());
            ppLabels[i-1].setTextFill(Color.WHITE);
            ppLabels[i-1].setAlignment(Pos.CENTER);
            ppLabels[i-1].setFont(font);
            VBox vBox =new VBox();
            vBox.setAlignment(Pos.CENTER);
            vBox.getChildren().addAll(ppImages[i-1], ppLabels[i-1]);
            Button playerButton = new Button();
            playerButton.setId("ppButton");
            playerButton.setGraphic(vBox);
            playerButton.setPadding(new Insets(30));
            playerButton.setStyle("-fx-background-radius: 10");

            //Sets active player
            String activePlayerString = "player"+i+".txt";
            playerButton.setOnAction(event -> {
                controller.setActivePlayer(activePlayerString);
                gameScreen.start(scene, false);
            });
            ppImageBox.getChildren().add(playerButton);
        }

        //Create new user button
        Button newPlayerButton = new Button();
        newPlayerButton.setId("ppButton");
        String imagePath = Paths.get("").toAbsolutePath().toString() + "/Data/PlayerData/Images/pp.png";
        Image image = new Image(new File(imagePath).toURI().toString());
        ImageView newPlayer = new ImageView(image);
        newPlayer.setFitHeight(100);
        newPlayer.setFitWidth(100);
        Label createPlayer = new Label("New player");
        createPlayer.setTextFill(Color.WHITE);
        createPlayer.setAlignment(Pos.CENTER);
        createPlayer.setFont(font);
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setStyle("-fx-background-color: rgba(255,255,255,0.14);-fx-background-radius: 20; -fx-padding: 20");
        vBox.getChildren().addAll(newPlayer, createPlayer);
        newPlayerButton.setGraphic(vBox);
        newPlayerButton.setPadding(new Insets(30));

        ppImageBox.getChildren().add(newPlayerButton);


        //Create new user
        HBox newUserHBox = new HBox();
        newUserHBox.setAlignment(Pos.CENTER);
        newUserHBox.setSpacing(20);
        VBox newUserVBox = new VBox();
        newUserVBox.setAlignment(Pos.CENTER);
        newUserVBox.setSpacing(20);
        VBox imageSelectBox = new VBox();
        imageSelectBox.setAlignment(Pos.CENTER);
        imageSelectBox.setSpacing(20);
        VBox goalsListBox = new VBox();
        goalsListBox.setAlignment(Pos.CENTER);
        goalsListBox.setSpacing(20);
        GridPane newUserOptions = new GridPane();
        newUserOptions.setVgap(20);

        Label createPlayerTitle = new Label("Create player");
        createPlayerTitle.setFont(font);
        createPlayerTitle.setTextFill(Color.WHITE);

        Label playerNameLabel = new Label("Player name:");
        Label playerHealthLabel = new Label("Set health:");
        Label playerGoldLabel = new Label("Set gold:");
        Label playerGoalsLabel = new Label("Set custom goals:");
        TextField playerTextField = new TextField();

        SpinnerValueFactory<Integer> healthFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10);
        SpinnerValueFactory<Integer> goldFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 15000);
        ((SpinnerValueFactory.IntegerSpinnerValueFactory) goldFactory).setAmountToStepBy(100);
        Spinner healthSpinner = new Spinner(healthFactory);
        Spinner goldSpinner = new Spinner(goldFactory);


        Button createPlayerButton = new Button("Create player");

        newUserOptions.add(playerNameLabel,0,0);
        newUserOptions.add(playerTextField,1,0);
        newUserOptions.add(playerHealthLabel, 0,1);
        newUserOptions.add(healthSpinner,1,1);
        newUserOptions.add(playerGoldLabel,0,2);
        newUserOptions.add(goldSpinner,1,2);
        //newUserOptions.add(playerGoalsLabel,0,3);
        //newUserOptions.add(addGoalsButton,1,3);
        newUserOptions.add(createPlayerButton,0,3);



        ImageView imageDisplay = new ImageView("/gruppe/fire/Media/noSelect.png");
        imageDisplay.setFitHeight(100);
        imageDisplay.setFitWidth(100);
        Button uploadImage = new Button("Upload image");
        uploadImage.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png");
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
        backButton.setStyle("-fx-background-color: rgba(0,0,0,0.7); -fx-background-radius: 40px");
        backButton.setPrefSize(100, 100);
        backButton.setAlignment(Pos.CENTER);
        backButton.setEffect(dropShadow);

        createPlayerButton.setOnAction(event -> {
            String name = playerTextField.getText();
            Image profileImage = newProfileImage;
            int health = (int) healthSpinner.getValue();
            int gold = (int) goldSpinner.getValue();

            Player addedPlayer = new Player(name, profileImage, health,0,gold);
            dataBase.writeFile(addedPlayer);
        });

        imageSelectBox.getChildren().addAll(imageDisplay, uploadImage);
        //goalsListBox.getChildren().addAll(goalsTitle, goalsList);
        newUserVBox.getChildren().addAll(createPlayerTitle, newUserOptions);
        newUserHBox.getChildren().addAll(backButton, newUserVBox, imageSelectBox);

        //Goal Menu
        VBox goalMenu = new VBox();
        goalMenu.setStyle("-fx-background-color: rgba(0,0,0,0.7); -fx-background-radius: 40px");
        //goalMenu.setPrefSize(400, 400);
        //goalMenu.setMinWidth(400);
        goalMenu.setAlignment(Pos.CENTER);
        goalMenu.setEffect(dropShadow);
        goalMenu.setSpacing(30);
        ListView goalsList = new ListView();
        Label goalsTitle = new Label("Active goals");
        goalsTitle.setFont(font);
        Button addGoalsButton = new Button("Add goal");
        addGoalsButton.setOnAction(event -> {
            //TODO: Implement goals.
        });

        goalMenu.getChildren().addAll(goalsTitle, goalsList, addGoalsButton);
        goalMenu.setSpacing(10);
        goalMenu.setPadding(new Insets(20));

        HBox playerHBox = new HBox();
        playerHBox.setAlignment(Pos.CENTER);
        playerHBox.setSpacing(50);

        VBox playerVBox = new VBox();
        playerVBox.setMinWidth(400);
        playerHBox.getChildren().addAll(playerVBox, goalMenu);
        playerVBox.getChildren().addAll(playerTitle, ppImageBox);
        newPlayerButton.setOnAction(e ->{
            playerHBox.getChildren().removeAll(goalMenu);
            playerVBox.getChildren().clear();
            playerVBox.getChildren().add(newUserHBox);
        });
        backButton.setOnAction(e ->{
            playerVBox.getChildren().clear();
            playerVBox.getChildren().addAll(playerTitle, ppImageBox);
            playerHBox.getChildren().add(goalMenu);
        });



        playerVBox.setStyle("-fx-background-color: rgba(0,0,0,0.7); -fx-background-radius: 40px");
        playerVBox.setPrefSize(1100, 400);

        playerVBox.setAlignment(Pos.CENTER);
        playerVBox.setEffect(dropShadow);
        playerVBox.setSpacing(30);

        Button cancelButton = new Button("Cancel");
        cancelButton.setPrefSize(200,100);
        cancelButton.setFont(buttonFont);
        cancelButton.setTextFill(Color.WHITE);
        cancelButton.setOnAction(e ->{
            try {
                mainMenu.start(scene);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        Button startButton = new Button("Start Game");
        startButton.setPrefSize(600,100);
        startButton.setFont(buttonFont);
        startButton.setTextFill(Color.WHITE);
        HBox buttonBox = new HBox();
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setSpacing(50);
        buttonBox.getChildren().addAll(cancelButton, startButton);

        VBox menuBox = new VBox();
        menuBox.setAlignment(Pos.CENTER);
        menuBox.getChildren().add(playerHBox);
        root.setBottom(buttonBox);
        root.setCenter(menuBox);



    }

}
