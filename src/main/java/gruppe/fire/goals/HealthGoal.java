package gruppe.fire.goals;

import gruppe.fire.logic.Player;

/**
 * This class represents a health goal that specifies a minimum amount of health that the player needs to have.
 */
public class HealthGoal implements Goal {
  private int minimumHealth;

  /**
   * Creates an instance of HealthGoal with the specified minimum health amount.
   *
   * @param minimumHealth The minimum amount of health required for the goal to be fulfilled.
   */
  public HealthGoal(int minimumHealth) {
    this.minimumHealth = minimumHealth;
  }

  /**
   * Checks whether the player's health meets the minimum health requirement.
   *
   * @param player The player whose health amount will be checked.
   * @return True if the player's health is greater than or equal to the minimum health requirement, false otherwise.
   */
  public boolean isFulfilled(Player player) {
    if (player.getHealth() > minimumHealth) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * Calculates the remaining health needed to fulfill the goal.
   *
   * @param player The player whose health amount will be checked.
   * @return The remaining health needed to fulfill the goal.
   */
  public int remainingHealth(Player player) {
    return minimumHealth - player.getHealth();
  }

  public String getGoal() {
    return this.minimumHealth + "Health";
  }
}
