package gruppe.fire.actions;

import gruppe.fire.logic.Player;

/**
 * This class represents an action that changes the player's health.
 *
 * @author Matti Kjellstadli
 * @version 2023-05-21
 */
public class HealthAction implements Action {
  private int health;

  /**
   * Creates an instance of HealthAction with the specified health amount.
   *
   * @param health The amount of health to be added to the player's health.
   */
  public HealthAction(int health) {
    this.health = health;
  }

  /**
   * Executes the health action by adding the specified health amount to the player's health.
   *
   * @param player The player whose health will be changed.
   */
  public void execute(Player player) {
    player.addHealth(health);
  }
}

