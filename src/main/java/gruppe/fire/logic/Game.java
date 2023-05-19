package gruppe.fire.logic;

import gruppe.fire.fileHandling.DataBase;
import gruppe.fire.goals.Goal;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * The Game class is responsible for connecting a player to a story, and has
 * handy methods for starting and maneuvering in the game.
 */
public class Game {

  private Player player;
  private Story story;
  private ArrayList<Goal> goals;

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

  /**
   * This method returns the player.
   *
   * @return The player.
   */
  public Player getPlayer() {
    return player;
  }

  /**
   * This method returns the story.
   *
   * @return The story.
   */
  public Story getStory() {
    return story;
  }

  /**
   * This method returns the goals.
   *
   * @return The goals.
   */
  public ArrayList<Goal> getGoals() {
    return goals;
  }

  public void setGoalsList(ArrayList goals) {
    this.goals = goals;
  }

  public void addGoals(Goal goal) {
    this.goals.add(goal);
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
   * @param link
   * @return The passage which matches the provided Link.
   */
  public Passage go(Link link) {
    Passage currentPassage = story.getPassageByLink(link);
    return currentPassage;
  }

  public String[] readPlayers() {
    File folder = new File("Data/PlayerData/Players");
    File[] listOfFiles = folder.listFiles();
    List<String> paths = new ArrayList<>();

    for (File file : listOfFiles) {
      if (file.isFile()) {
        paths.add(file.getPath());
      }
    }

    String[] result = paths.toArray(new String[0]);
    return result;
  }

  public void gameToFile(Game game, Passage currentPassage) {
    DataBase dataBase = new DataBase();
    String gameState = dataBase.getActiveStoryPath() + "\n" +
        //game.getStory().getPassage(link).getTitle() + "\n" +
        //game.getStory().getPassage(link).getContent() + "\n" +
        currentPassage.getTitle() + "\n" +
        game.getPlayer().getName() + "\n" +
        game.getPlayer().getGold() + "\n" +
        game.getPlayer().getHealth() + "\n" +
        game.getPlayer().getScore() + "\n" +
        game.getGoals() + "\n" +
        game.getPlayer().getInventory();

    dataBase.writeStateToFile(gameState);
  }

}
