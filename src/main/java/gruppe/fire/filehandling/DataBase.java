package gruppe.fire.filehandling;

import gruppe.fire.goals.Goal;
import gruppe.fire.goals.GoldGoal;
import gruppe.fire.goals.HealthGoal;
import gruppe.fire.goals.InventoryGoal;
import gruppe.fire.goals.ScoreGoal;
import gruppe.fire.logic.Game;
import gruppe.fire.logic.Link;
import gruppe.fire.logic.Passage;
import gruppe.fire.logic.Player;
import gruppe.fire.logic.Story;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javafx.scene.image.Image;


/**
 * Represents reading from and writing to the database.
 *
 * @author Matti Kjellstadli
 * @version 2023-05-19
 */
public class DataBase {

  private static final String GPATHS = ".Gpaths";
  private static final String CURRENT_GPATHS = "Data/currentGpaths/story.paths";
  private static final String PP_FILE = "Data/PlayerData/Images/pp";
  private static final String PLAYER_FILE = "Data/PlayerData/Players/player";
  private static final String ACTIVE_GOALS = "Data/activeGoals.csv";

  private boolean gameState = true;

  /**
   * Responsible for returning the active player path as String.
   *
   * @return The active player path as String.
   */
  public String getActivePlayerPath() {
    String activePlayer = null;
    try (BufferedReader reader = new BufferedReader(new FileReader("Data/currentPlayerFile.csv"))) {
      activePlayer = reader.readLine();
      if (!new File(activePlayer).exists()) {
        //Replaces selected player with default if player does not exist
        activePlayer = "Data/PlayerData/Players/player1.dat";
      }
    } catch (IOException e) {
      String exceptionString = "Something went wrong while reading active player file" + e;
      System.getLogger(exceptionString);
    }
    return activePlayer;
  }

  /**
   * This method should return the active story name as String.
   *
   * @return The active story name as String.
   */
  public String getActiveStoryName() {
    String storyTitle = null;
    File currentStoryFile = new File(getActiveStoryPath());
    if (String.valueOf(currentStoryFile).endsWith(GPATHS)) {
      currentStoryFile = new File(CURRENT_GPATHS);
    }
    try (Scanner scanner = new Scanner(currentStoryFile)) {
      storyTitle = scanner.nextLine();

    } catch (IOException e) {
      String exceptionString = "Something went wrong while reading active story file" + e;
      System.getLogger(exceptionString);
    }

    return storyTitle;
  }

  /**
   * This method should return active story passages as String.
   *
   * @return Active story passages as String
   */
  public String getActiveStoryPassages() {
    File currentStoryFile = new File((getActiveStoryPath()));
    if (String.valueOf(currentStoryFile).endsWith(GPATHS)) {
      currentStoryFile = new File(CURRENT_GPATHS);
    }
    String returnString = "0";
    if (!String.valueOf(currentStoryFile).endsWith(GPATHS)) {
      FileToStory fileToStory = new FileToStory(currentStoryFile);
      Story story = fileToStory.readFile();
      returnString = String.valueOf(story.getPassages().size());
    }
    return returnString;
  }

  /**
   * This method should return broken story links as String.
   *
   * @return Broken story links as String.
   */
  public String getBrokenStoryLinks() {
    File currentStoryFile = new File((getActiveStoryPath()));
    if (String.valueOf(currentStoryFile).endsWith(GPATHS)) {
      currentStoryFile = new File(CURRENT_GPATHS);
    }
    String returnString = "0";
    if (!String.valueOf(currentStoryFile).endsWith(GPATHS)) {
      try {
        FileToStory fileToStory = new FileToStory(currentStoryFile);
        Story story = fileToStory.readFile();
        returnString = String.valueOf(story.getBrokenLinks().size());
      } catch (NoSuchElementException e) {
        returnString = "0";
      }

    }
    return returnString;
  }

  /**
   * Responsible for returning the active story path as String.
   *
   * @return The active story path as String.
   */
  public String getActiveStoryPath() {
    String activeStory = null;
    try (BufferedReader reader = new BufferedReader(new FileReader("Data/currentPathsFile.csv"))) {
      activeStory = reader.readLine();
    } catch (IOException e) {
      String exceptionString = "Something went wrong while reading active story path from file" + e;
      System.getLogger(exceptionString);
    }
    return activeStory;

  }

  /**
   * Creates a game object from a player and a story file.
   *
   * @param playerFile File containing a saved player.
   * @param storyFile  File containing a saved story.
   * @return Game object.
   */
  public Game createGame(File playerFile, File storyFile) {
    FileToPlayer fileToPlayer = new FileToPlayer(playerFile);
    Player player = fileToPlayer.readFile();

    FileToStory fileToStory = new FileToStory(storyFile);
    Story story = fileToStory.readFile();
    story.addPassage(story.getOpeningPassage());


    return new Game(player, story);
  }

  /**
   * This method is responsible for saving a player in a text file.
   * It should also save an image if one is provided.
   *
   * @param player Current player
   */
  public void writeNewPlayerFile(Player player) {
    Path targetPath;
    String[] players = readPlayers();
    int i = players.length + 1;

    // Get the player's image
    if (player.getImage() != null) {
      Image playerImage = player.getImage();
      String imagePath = playerImage.getUrl();
      String filePathStr = imagePath.replace("file:/", "");
      Path sourcePath = Paths.get(filePathStr);
      while (new File(PP_FILE + i + ".png").exists()) {
        i++;
      }
      targetPath = Paths.get(PP_FILE + i + ".png");
      try {
        Files.copy(sourcePath, targetPath);
      } catch (IOException e) {
        String exceptionString = "Something went wrong while copying player image to file" + e;
        System.getLogger(exceptionString);
      }
    } else {
      targetPath = Paths.get("Data/PlayerData/Images/noSelect.png");
    }

    while (new File(PLAYER_FILE + i + ".dat").exists()) {
      i++;
    }
    try (FileWriter fileWriter = new FileWriter(PLAYER_FILE + i + ".dat")) {
      fileWriter.write(player.getName() + "\n");
      fileWriter.write(targetPath + "\n");
      fileWriter.write(player.getHealth() + "\n");
      fileWriter.write(player.getScore() + "\n");
      fileWriter.write(player.getGold() + "\n");
    } catch (IOException e) {
      String exceptionString = "Something went wrong while writing player info to file" + e;
      System.getLogger(exceptionString);
    }
  }

  /**
   * This method is responsible for reading all players and returning.
   *
   * @return Array of players.
   */
  public String[] readPlayers() {
    File folder = new File("Data/PlayerData/Players");
    File[] listOfFiles = folder.listFiles();
    List<String> paths = new ArrayList<>();

    if (listOfFiles != null) {
      for (File file : listOfFiles) {
        if (file.isFile()) {
          paths.add(file.getPath());
        }
      }
    }
    return paths.toArray(new String[0]);
  }

  /**
   * Responsible for unpacking .Gpaths files.
   */
  public void gpathHandler() {
    String fileZip = getActiveStoryPath();
    Path destDir = Paths.get("Data/currentGpaths");

    try {
      Files.walk(destDir)
          .filter(Files::isRegularFile)
          .map(Path::toFile)
          .forEach(File::delete);
    } catch (IOException e) {
      String exceptionString = "Failed to delete previous gpaths files" + e;
      System.getLogger(exceptionString);
    }

    try (ZipInputStream zis = new ZipInputStream(new FileInputStream(fileZip))) {
      ZipEntry zipEntry = zis.getNextEntry();
      while (zipEntry != null) {
        Path newFilePath = destDir.resolve(zipEntry.getName());

        if (!newFilePath.normalize().startsWith(destDir)) {
          throw new IOException("Entry is outside of the target directory: " + zipEntry.getName());
        }

        if (zipEntry.isDirectory()) {
          Files.createDirectories(newFilePath);
        } else {
          Path parentDir = newFilePath.getParent();
          if (parentDir != null) {
            Files.createDirectories(parentDir);
          }

          try (OutputStream fos = Files.newOutputStream(newFilePath)) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = zis.read(buffer)) > 0) {
              fos.write(buffer, 0, len);
            }
          }
        }
        zipEntry = zis.getNextEntry();
      }
    } catch (IOException e) {
      String exceptionString = "Something went wrong" + e;
      System.getLogger(exceptionString);
    }
  }


  /**
   * This method is responsible for saving story progress to a file.
   *
   * @param game Current game.
   */
  public void gameToFile(Game game) {
    DataBase dataBase = new DataBase();

    String gameStateString = dataBase.getActiveStoryPath() + "\n"
        + game.getCurrentPassage().getTitle() + "\n"
        + game.getPlayer().getName() + "\n"
        + game.getPlayer().getGold() + "\n"
        + game.getPlayer().getHealth() + "\n"
        + game.getPlayer().getScore() + "\n"
        + game.getGoals().get(0).getGoal().replace(" Gold", "") + "\n"
        + game.getGoals().get(1).getGoal().replace(" Health", "") + "\n"
        + game.getGoals().get(2).getGoal().replace(" Points", "") + "\n"
        + game.getPlayer().getInventory() + "\n"
        + currentGoalsList().get(3);

    try (FileWriter fileWriter = new FileWriter("Data/gameState.csv")) {
      fileWriter.write(gameStateString);
    } catch (IOException e) {
      String exceptionString = "Something went wrong" + e;
      System.getLogger(exceptionString);
    }
  }


  /**
   * This method is responsible for reading a saved game state and returning a game object.
   *
   * @return Game object from file.
   */
  public Game readFile() {
    Player player = new Player.PlayerBuilder()
        .build();

    Game game = null;
    try (Scanner scanner = new Scanner(new File("Data/gameState.csv"))) {

      // Sets the first line as the title of the story
      if (scanner.hasNextLine()) {
        Path path = Path.of(scanner.nextLine());
        FileToStory fileToStory = new FileToStory(path.toFile());
        Story story = fileToStory.readFile();

        String passageTitle = scanner.nextLine();
        player.setName(scanner.nextLine());
        player.setGold(scanner.nextInt());
        player.setHealth(scanner.nextInt());
        player.setScore(scanner.nextInt());
        int goldGoal = scanner.nextInt();
        int healthGoal = scanner.nextInt();
        int scoreGoal = scanner.nextInt();
        String inventory = scanner.nextLine();
        String inventoryGoal = scanner.nextLine();
        Link link = new Link(passageTitle, passageTitle);

        Passage passage = story.getPassageByLink(link);
        story.setOpeningPassage(passage);
        List<Goal> goals = new ArrayList<>();
        goals.add(new GoldGoal(goldGoal));
        goals.add(new HealthGoal(healthGoal));
        goals.add(new ScoreGoal(scoreGoal));
        String[] inventorylist1 = (inventory.split(","));
        List<String> invList1 = new ArrayList<>(Arrays.asList(inventorylist1));
        goals.add(new InventoryGoal(invList1));
        game = new Game(player, story);
        game.setGoalsList(goals);
        String[] inventoryList =
            (inventoryGoal.replace("[", "").replace("]", "").strip().split(","));
        List<String> invList = new ArrayList<>(Arrays.asList(inventoryList));
        if (!invList.isEmpty()) {
          for (String s : invList) {
            game.getPlayer().addToInventory(s);
          }
        }
        this.gameState = true;
      } else {
        this.gameState = false;
      }

    } catch (FileNotFoundException e) {
      this.gameState = false;
    }
    return game;

  }

  public boolean getGameState() {
    return this.gameState;
  }

  /**
   * This method is responsible for writing goals to a file.
   *
   * @param gold      Current gold goal.
   * @param health    Current health goal.
   * @param score     Current score goal.
   * @param inventory Current inventory goal.
   */
  public void writeGoalsToFile(int gold, int health, int score, String inventory) {
    try (FileWriter fileWriter = new FileWriter(ACTIVE_GOALS)) {
      fileWriter.write(gold + "\n");
      fileWriter.write(health + "\n");
      fileWriter.write(score + "\n");
      fileWriter.write(inventory + "\n");
    } catch (IOException e) {
      String exceptionString = "Something went wrong while writing goals" + e;
      System.getLogger(exceptionString);
    }
  }

  /**
   * This method is responsible for reading goals from a file.
   *
   * @return List containing goals.
   */
  public List<Goal> readGoalsFromFile() {
    List<Goal> goalArray = new ArrayList<>();
    File goalsFile = new File(ACTIVE_GOALS);
    try (Scanner scanner = new Scanner(goalsFile)) {
      int gold = scanner.nextInt();
      int health = scanner.nextInt();
      int score = scanner.nextInt();
      String inventory = scanner.nextLine();

      GoldGoal newGoldGoal = new GoldGoal(gold);
      HealthGoal newHealthGoal = new HealthGoal(health);
      ScoreGoal newScoreGoal = new ScoreGoal(score);
      String[] inventoryList = (inventory.replace("[" + "]", "")).split(",");
      List<String> inventoryStringList = new ArrayList<>(Arrays.asList(inventoryList));
      InventoryGoal newInventoryGoal = new InventoryGoal(inventoryStringList);

      goalArray.add(newGoldGoal);
      goalArray.add(newHealthGoal);
      goalArray.add(newScoreGoal);
      goalArray.add(newInventoryGoal);

    } catch (FileNotFoundException e) {
      String exceptionString = "Something went wrong while reading goals" + e;
      System.getLogger(exceptionString);
    }
    return goalArray;
  }

  /**
   * This method is responsible for reading current goals from a file.
   *
   * @return Current goals.
   */
  public List<String> currentGoalsList() {
    List<String> goalsList = new ArrayList<>();
    File goalsFile = new File(ACTIVE_GOALS);
    try (Scanner scanner = new Scanner(goalsFile)) {
      goalsList.add(scanner.nextLine());
      goalsList.add(scanner.nextLine());
      goalsList.add(scanner.nextLine());
      goalsList.add(scanner.nextLine());
    } catch (FileNotFoundException e) {
      String exceptionString = "Something went wrong while reading goals from list" + e;
      System.getLogger(exceptionString);
    }
    return goalsList;
  }

  /**
   * This method is responsible for reading goals from a file and returning
   * a string which is representable for a user.
   *
   * @return Goals as String.
   */
  public String readGoalsFromFileList() {
    String goalsString = null;
    File goalsFile = new File(ACTIVE_GOALS);
    try (Scanner scanner = new Scanner(goalsFile)) {
      String gold = scanner.nextLine();
      String health = scanner.nextLine();
      String score = scanner.nextLine();
      String inventory = scanner.nextLine();
      goalsString = "Gold Goal:      " + gold + "\n"
          + "Health Goal:    " + health + "\n"
          + "Score Goal:     " + score + "\n"
          + "Inventory Goal: " + inventory;
    } catch (FileNotFoundException e) {
      String exceptionString = "Something went wrong while reading goals from list" + e;
      System.getLogger(exceptionString);
    }
    return goalsString;
  }

  /**
   * This method is responsible for checking if goals are empty to
   * prevent goals from being 0.
   *
   * @return True if goals are empty, false otherwise.
   */
  public boolean checkGoalsEmpty() {
    File goalsFile = new File(ACTIVE_GOALS);
    boolean goalsEmpty = false;
    try (Scanner scanner = new Scanner(goalsFile)) {
      String gold = scanner.nextLine();
      String health = scanner.nextLine();
      String score = scanner.nextLine();
      String inventory = scanner.nextLine();
      goalsEmpty =
          (gold.equals("0") && health.equals("0") && score.equals("0") && inventory.equals(""));
    } catch (FileNotFoundException e) {
      String exceptionString = "Something went wrong while checking if goals are empty" + e;
      System.getLogger(exceptionString);
    }
    return goalsEmpty;
  }

  /**
   * This method is responsible for writing settings to file.
   *
   * @param fs   Fullscreen status.
   * @param bg   Background status.
   * @param vlm  Music volume status.
   * @param vlm2 FX volume status.
   */
  public void writeSettingsToFile(Boolean fs, Boolean bg, double vlm, double vlm2) {
    try (FileWriter fileWriter = new FileWriter("Data/settings.cfg")) {
      fileWriter.write("fullscreen=" + fs + "\n");
      fileWriter.write("background=" + bg + "\n");
      fileWriter.write("music=" + vlm + "\n");
      fileWriter.write("fx=" + vlm2 + "\n");
    } catch (IOException e) {
      String exceptionString = "Something went wrong while writing settings to file" + e;
      System.getLogger(exceptionString);
    }
  }

  /**
   * This method is responsible for reading setting file and returning
   * a map containing the status of the setting.
   *
   * @return Map containing settings status.
   */
  public Map<String, String> readSettingsFromFile() {
    Map<String, String> map = new HashMap<>();
    File settingsFile = new File("Data/settings.cfg");
    try (Scanner scanner = new Scanner(settingsFile)) {
      String fs = scanner.nextLine();
      String fullscreen = (fs.replace("fullscreen=", ""));
      String bg = scanner.nextLine();
      String background = (bg.replace("background=", ""));
      String vlm = scanner.nextLine();
      String volume = (vlm.replace("music=", ""));
      String vlm2 = scanner.nextLine();
      String volume2 = (vlm2.replace("fx=", ""));


      map.put("fs", fullscreen);
      map.put("bg", background);
      map.put("vlm", volume);
      map.put("vlm2", volume2);

    } catch (FileNotFoundException e) {
      String exceptionString = "Something went wrong while reading settings from file" + e;
      System.getLogger(exceptionString);
    }
    return map;
  }

  /**
   * This method is responsible for handling delete player requests based
   * on the player id.
   *
   * @param i Player ID.
   */
  public void deletePlayer(int i) {
    File playerFile = new File(PLAYER_FILE + i + ".dat");
    File playerImageFile = new File(PP_FILE + i + ".png");
    if (playerFile.delete() && playerImageFile.delete()) {
      System.getLogger("Deleted the file player");
    } else {
      System.getLogger("Failed to delete the player.");
    }
  }

  /**
   * This method is responsible for reading tutorial file and returning it as a string.
   *
   * @return Tutorial as String.
   */
  public String readTutorial() {
    StringBuilder tutorial = new StringBuilder();
    try (InputStream inputStream = getClass().getResourceAsStream("/gruppe/fire/tutorial.txt")) {
      assert inputStream != null;
      try (Scanner scanner = new Scanner(inputStream)) {
        while (scanner.hasNextLine()) {
          tutorial.append(scanner.nextLine()).append("\n");
        }
      }
    } catch (IOException e) {
      String exceptionString = "Something went wrong while reading tutorial from file" + e;
      System.getLogger(exceptionString);
    }
    return tutorial.toString();
  }
}
