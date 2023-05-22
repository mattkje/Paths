package gruppe.fire.logic;

import gruppe.fire.goals.Goal;
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


}
