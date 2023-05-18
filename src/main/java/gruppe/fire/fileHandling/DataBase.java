package gruppe.fire.fileHandling;

import gruppe.fire.goals.*;
import gruppe.fire.logic.GameBuilder;
import gruppe.fire.logic.Player;
import javafx.scene.image.Image;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


public class DataBase {
  private Path targetPath;

  public String getActivePlayerPath() {
    BufferedReader reader;
    try {
      reader = new BufferedReader(new FileReader("Data/currentPlayerFile.cfg"));
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
    String activePlayer;
    try {
      activePlayer = reader.readLine();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return activePlayer;
  }


  public String getActiveStoryPath() {
    BufferedReader reader;
    try {
      reader = new BufferedReader(new FileReader("Data/currentPathsFile.cfg"));
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
    String activeStory;
    try {
      activeStory = reader.readLine();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return activeStory;

  }

  public void writeFile(Player player) {
    DataBase dataBase = new DataBase();
    GameBuilder gameBuilder = new GameBuilder(new File(dataBase.getActivePlayerPath()),
        new File(dataBase.getActiveStoryPath()));
    String[] players = gameBuilder.createGame().readPlayers();
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
        throw new RuntimeException(e);
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
      System.out.println("Something went wrong");
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
      throw new RuntimeException("Failed to delete files in destination directory", e);
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
      throw new RuntimeException(e);
    }
  }


  public void writeStateToFile(String gameState) {
    FileWriter fileWriter = null;
    try {
      fileWriter = new FileWriter("Data/GameStates/state1.txt");
      fileWriter.write(gameState);
      fileWriter.close();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public void writeGoalsToFile(int gold, int health, int score, String inventory) {
    try (FileWriter fileWriter = new FileWriter("Data/activeGoals.txt")) {
      fileWriter.write(gold + "\n");
      fileWriter.write(health + "\n");
      fileWriter.write(score + "\n");
      fileWriter.write(inventory + "\n");
    } catch (IOException e) {
      System.out.println("Something went wrong");
    }
  }

  public ArrayList readGoalsFromFile() {
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
      String[] inventoryStringList = (inventory.split(","));
      InventoryGoal iGoal = new InventoryGoal(inventoryStringList);

      goalArray.add(gGoal);
      goalArray.add(hGoal);
      goalArray.add(sGoal);
      goalArray.add(iGoal);

    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
    return goalArray;
  }

  public String readGoalsFromFileList() {
    String goalsString;
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
      throw new RuntimeException(e);
    }
    return goalsString;
  }

  public boolean checkGoalsEmpty() {
    File goalsFile = new File("Data/activeGoals.txt");
    try (Scanner scanner = new Scanner(goalsFile)) {
      String gold = scanner.nextLine();
      String health = scanner.nextLine();
      String score = scanner.nextLine();
      String inventory = scanner.nextLine();
      if (gold.equals("0") && health.equals("0") && score.equals("0") && inventory.equals("")) {
        return false;
      } else {
        return true;
      }
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  public void writeSettingsToFile(Boolean fs, Boolean bg, double vlm, double vlm2) {
    try (FileWriter fileWriter = new FileWriter("Data/settings.cfg")) {
      fileWriter.write("fullscreen=" + fs + "\n");
      fileWriter.write("background=" + bg + "\n");
      fileWriter.write("music=" + vlm + "\n");
      fileWriter.write("fx=" + vlm2 + "\n");
    } catch (IOException e) {
      System.out.println("Something went wrong");
    }
  }

  public HashMap readSettingsFromFile() {
    HashMap map = new HashMap<>();
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
      throw new RuntimeException(e);
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
      e.printStackTrace();
    }
    return tutorial;
  }
}
