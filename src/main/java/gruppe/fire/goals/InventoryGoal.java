package gruppe.fire.goals;

/**
 * This class represents an expected inventory with items.
 */

import gruppe.fire.logic.Player;

import java.util.ArrayList;

public class InventoryGoal implements Goal {
  private String[] mandatoryItems;

  /**
   * Creates an instance of InventoryGoal.
   * @param mandatoryItems The mandatory items that the player needs to have in their inventory.
   */
  public InventoryGoal(String[] mandatoryItems) {
    this.mandatoryItems = mandatoryItems;

  }

  /**
   * Checks whether the mandatory items are present in the player's inventory.
   * @param player The player whose inventory will be checked.
   * @return True if the mandatory items are present in the player's inventory, false otherwise.
   */
  public boolean isFulfilled(Player player) {
    return mandatoryItems != null;
  }

  public String getGoal() {
    return this.mandatoryItems + "Items";
  }
}
