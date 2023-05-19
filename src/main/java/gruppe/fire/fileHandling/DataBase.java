package gruppe.fire.fileHandling;

import gruppe.fire.goals.*;
import gruppe.fire.logic.Game;
import gruppe.fire.logic.Link;
import gruppe.fire.logic.Passage;
import gruppe.fire.logic.Player;
import gruppe.fire.logic.Story;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javafx.scene.image.Image;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


/**
 * Represents reading from and writing to the database.
 *
 * @author Matti Kjellstadli
 * @version 2023-05-19
 */
public class DataBase {
  private Path targetPath;

  public String getActivePlayerPath() {
    String activePlayer = null;
    try (BufferedReader reader = new BufferedReader(new FileReader("Data/currentPlayerFile.cfg"))) {
      activePlayer = reader.readLine();
    } catch (IOException e) {
      String exceptionString = "Something went wrong while reading active player file" + e;
      System.getLogger(exceptionString);
    }
    return activePlayer;
  }

  public String getActiveStoryName() {
    String storyTitle = null;
    File currentStoryFile = new File((getActiveStoryPath()));
    try (Scanner scanner = new Scanner(currentStoryFile)) {
      storyTitle = scanner.nextLine();

    } catch (IOException e) {
      String exceptionString = "Something went wrong while reading active story file" + e;
      System.getLogger(exceptionString);
    }
    return storyTitle;
  }

  public String getActiveStoryPassages() {
    File currentStoryFile = new File((getActiveStoryPath()));
    FileToStory fileToStory = new FileToStory(currentStoryFile);
    Story story = fileToStory.readFile();
    return String.valueOf(story.getPassages().size());
  }

  public String getBrokenStoryLinks() {
    File currentStoryFile = new File((getActiveStoryPath()));
    FileToStory fileToStory = new FileToStory(currentStoryFile);
    Story story = fileToStory.readFile();
    return String.valueOf(story.getBrokenLinks().size());
  }

  public String getBrokenStoryPassages() {
    File currentStoryFile = new File((getActiveStoryPath()));
    FileToStory fileToStory = new FileToStory(currentStoryFile);
    Story story = fileToStory.readFile();
    return String.valueOf(story.getBrokenPassage().size());
  }


  public String getActiveStoryPath() {
    String activeStory = null;
    try (BufferedReader reader = new BufferedReader(new FileReader("Data/currentPathsFile.cfg"))) {
      activeStory = reader.readLine();
    } catch (IOException e) {
      String exceptionString = "Something went wrong while reading active story path from file" + e;
      System.getLogger(exceptionString);
    }
    return activeStory;

  }

  public Game createGame(File playerFile, File storyFile) {
    FileToPlayer fileToPlayer = new FileToPlayer(playerFile);
    Player player = fileToPlayer.readFile();

    FileToStory fileToStory = new FileToStory(storyFile);
    Story story = fileToStory.readFile();


    return new Game(player, story);
  }

  public void writeFile(Player player) {
    DataBase dataBase = new DataBase();
    String[] players = createGame(new File(dataBase.getActivePlayerPath()),
        new File(dataBase.getActiveStoryPath())).readPlayers();
    int i = players.length + 1;

    // Get the player's image
    if (player.getImage() != null) {
      Image playerImage = player.getImage();
      String imagePath = playerImage.getUrl();
      String filePathStr = imagePath.replace("file:/", "");
      Path sourcePath = Paths.get(filePathStr);
      this.targetPath = Paths.get("Data/PlayerData/Images/pp" + i + ".png");
      try {
        Files.copy(sourcePath, targetPath);
      } catch (IOException e) {
        String exceptionString = "Something went wrong while copying player image to file" + e;
        System.getLogger(exceptionString);
      }
    } else {
      this.targetPath = Paths.get("Data/PlayerData/Images/noSelect.png");
    }


    try (FileWriter fileWriter = new FileWriter("Data/PlayerData/Players/player" + i + ".txt")) {
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


  public void writeStateToFile(String gameState) {
    try (FileWriter fileWriter = new FileWriter("Data/GameStates/state1.txt");) {
      fileWriter.write(gameState);
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
    try (Scanner scanner = new Scanner(new File("Data/GameStates/state1.txt"))) {

      // Sets the first line as the title of the story
      Path path = Path.of(scanner.nextLine());
      FileToStory fileToStory = new FileToStory(path.toFile());
      Story story = fileToStory.readFile();

      String passageTitle = scanner.nextLine();
      player.setName(scanner.nextLine());
      player.setGold(scanner.nextInt());
      player.setHealth(scanner.nextInt());
      player.setScore(scanner.nextInt());
      String goals = scanner.nextLine();
      String inventory = scanner.nextLine();

      Link link = new Link(passageTitle, passageTitle);

      Passage passage = story.getPassageByLink(link);
      story.setOpeningPassage(passage);
      player.addToInventory(inventory.strip());
      game = new Game(player, story);

    } catch (FileNotFoundException e) {
      String exceptionString = "Something went wrong while reading state file" + e;
      System.getLogger(exceptionString);
    }
    return game;

  }

  public void writeGoalsToFile(int gold, int health, int score, String inventory) {
    try (FileWriter fileWriter = new FileWriter("Data/activeGoals.txt")) {
      fileWriter.write(gold + "\n");
      fileWriter.write(health + "\n");
      fileWriter.write(score + "\n");
      fileWriter.write(inventory + "\n");
    } catch (IOException e) {
      String exceptionString = "Something went wrong while writing goals" + e;
      System.getLogger(exceptionString);
    }
  }

  public ArrayList<Goal> readGoalsFromFile() {
    ArrayList<Goal> goalArray = new ArrayList<>();
    File goalsFile = new File("Data/activeGoals.txt");
    try (Scanner scanner = new Scanner(goalsFile)) {
      int gold = scanner.nextInt();
      int health = scanner.nextInt();
      int score = scanner.nextInt();
      String inventory = scanner.nextLine();

      GoldGoal gGoal = new GoldGoal(gold);
      HealthGoal hGoal = new HealthGoal(health);
      ScoreGoal sGoal = new ScoreGoal(score);
      List<String> inventoryStringList = Arrays.asList(inventory.split(","));
      InventoryGoal iGoal = new InventoryGoal(inventoryStringList);

      goalArray.add(gGoal);
      goalArray.add(hGoal);
      goalArray.add(sGoal);
      goalArray.add(iGoal);

    } catch (FileNotFoundException e) {
      String exceptionString = "Something went wrong while reading goals" + e;
      System.getLogger(exceptionString);
    }
    return goalArray;
  }

  public String readGoalsFromFileList() {
    String goalsString = null;
    File goalsFile = new File("Data/activeGoals.txt");
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

  public boolean checkGoalsEmpty() {
    File goalsFile = new File("Data/activeGoals.txt");
    boolean goalsEmpty = false;
    try (Scanner scanner = new Scanner(goalsFile)) {
      String gold = scanner.nextLine();
      String health = scanner.nextLine();
      String score = scanner.nextLine();
      String inventory = scanner.nextLine();
      goalsEmpty = (gold.equals("0") && health.equals("0") && score.equals("0") && inventory.equals(""));
    } catch (FileNotFoundException e) {
      String exceptionString = "Something went wrong while checking if goals are empty" + e;
      System.getLogger(exceptionString);
    }
    return goalsEmpty;
  }

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

  public Map readSettingsFromFile() {
    Map map = new HashMap<>();
    File settingsFile = new File("Data/settings.cfg");
    try (Scanner scanner = new Scanner(settingsFile)) {
      String fs = scanner.nextLine();
      boolean fullscreen = Boolean.parseBoolean(fs.replace("fullscreen=", ""));
      String bg = scanner.nextLine();
      boolean background = Boolean.parseBoolean(bg.replace("background=", ""));
      String vlm = scanner.nextLine();
      double volume = Double.parseDouble(vlm.replace("music=", ""));
      String vlm2 = scanner.nextLine();
      double volume2 = Double.parseDouble(vlm2.replace("fx=", ""));


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

  public String readTutorial() {
    String tutorial = "";
    try (InputStream inputStream = getClass().getResourceAsStream("/gruppe/fire/tutorial.txt")) {
      assert inputStream != null;
      try (Scanner scanner = new Scanner(inputStream)) {
        while (scanner.hasNextLine()) {
          tutorial += scanner.nextLine() + "\n";
        }
      }
    } catch (IOException e) {
      String exceptionString = "Something went wrong while reading tutorial from file" + e;
      System.getLogger(exceptionString);
    }
    return tutorial;
  }
}
