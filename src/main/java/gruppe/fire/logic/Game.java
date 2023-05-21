package gruppe.fire.logic;

import gruppe.fire.filehandling.DataBase;
import gruppe.fire.goals.Goal;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * The Game class is responsible for connecting a player to a story, and has
 * handy methods for starting and maneuvering in the game.
 *
 * @author Matti Kjellstadli
 * @version 2023-05-20
 */
public class Game {

  private final Player player;
  private final Story story;
  private List<Goal> goals;

  private Passage currentPassage;

  /**
   * Creates an instance of Game.
   *
   * @param player The player of the game.
   * @param story  The story of the game.
   */
  public Game(Player player, Story story) {
    this.player = player;
    this.story = story;
    this.goals = new ArrayList<>();
    this.currentPassage = story.getOpeningPassage();
  }


  public Player getPlayer() {
    return player;
  }

  public Story getStory() {
    return story;
  }


  public List<Goal> getGoals() {
    return goals;
  }

  public void setGoalsList(List<Goal> goals) {
    this.goals = goals;
  }


  public Passage getCurrentPassage() {
    return currentPassage;
  }

  public void setCurrentPassage(Passage currentPassage) {
    this.currentPassage = currentPassage;
  }

  /**
   * This method should simply return the first passage in the story for
   * this game.
   *
   * @param openingPassage The first passage in the story.
   * @return The openingPassage
   */
  public Passage begin(Passage openingPassage) {
    return openingPassage;
  }

  /**
   * This method should return the passage that matches the provided the link.
   *
   * @param link The link provided by the user.
   * @return The passage which matches the provided Link.
   */
  public Passage go(Link link) {
    currentPassage = story.getPassageByLink(link);
    return currentPassage;
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
   * This method is responsible for saving story progress to a file.
   *
   * @param game           Current game.
   * @param currentPassage Current passage.
   */
  public void gameToFile(Game game, Passage currentPassage) {
    DataBase dataBase = new DataBase();

    String gameState = dataBase.getActiveStoryPath() + "\n"
        + currentPassage.getTitle() + "\n"
        + game.getPlayer().getName() + "\n"
        + game.getPlayer().getGold() + "\n"
        + game.getPlayer().getHealth() + "\n"
        + game.getPlayer().getScore() + "\n"
        + game.getGoals().get(0).getGoal().replace(" Gold", "") + "\n"
        + game.getGoals().get(1).getGoal().replace(" Health", "") + "\n"
        + game.getGoals().get(2).getGoal().replace(" Points", "") + "\n"
        + dataBase.readGoalsFromFile().get(3).getGoal() + "\n"
        + game.getPlayer().getInventory();

    dataBase.writeStateToFile(gameState);
  }

}
