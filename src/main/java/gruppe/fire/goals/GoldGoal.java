package gruppe.fire.goals;

import gruppe.fire.logic.Player;

/**
 * This class represents a gold goal that specifies a minimum amount of gold that the player needs to have.
 *
 * @author Matti Kjellstadli
 * @version 2023-05-19
 */
public class GoldGoal implements Goal {
  private int minimumGold;

  /**
   * Creates an instance of GoldGoal with the specified minimum gold amount.
   *
   * @param minimumGold The minimum amount of gold required for the goal to be fulfilled.
   */
  public GoldGoal(int minimumGold) {
    this.minimumGold = minimumGold;
  }

  /**
   * Checks whether the player's gold meets the minimum gold requirement.
   *
   * @param player The player whose gold amount will be checked.
   * @return True if the player's gold is greater than or equal to the minimum gold requirement, false otherwise.
   */
  public boolean isFulfilled(Player player) {
    return player.getGold() > minimumGold;
  }

  /**
   * Calculates the remaining gold needed to fulfill the goal.
   *
   * @param player The player whose gold amount will be checked.
   * @return The remaining gold needed to fulfill the goal.
   */
  public int remainingGold(Player player) {
    return minimumGold - player.getGold();
  }

  public String getGoal() {
    return this.minimumGold + " Gold";
  }
}
