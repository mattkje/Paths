package gruppe.fire.goals;

import gruppe.fire.logic.Player;
import java.util.HashSet;
import java.util.List;

/**
 * This class represents an expected inventory with items.
 *
 * @author Matti Kjellstadli
 * @version 2023-05-19
 */
public class InventoryGoal implements Goal {
  private final List<String> mandatoryItems;

  /**
   * Creates an instance of InventoryGoal.
   *
   * @param mandatoryItems The mandatory items that the player needs to have in their inventory.
   */
  public InventoryGoal(List<String> mandatoryItems) {
    this.mandatoryItems = mandatoryItems;

  }

  /**
   * Checks whether the mandatory items are present in the player's inventory.
   *
   * @param player The player whose inventory will be checked.
   * @return True if the mandatory items are present in the player's inventory, false otherwise.
   */
  public boolean isFulfilled(Player player) {
    return new HashSet<>(player.getInventory()).containsAll(this.mandatoryItems);
  }

  /**
   * Returns all goals as a string.
   *
   * @return All goals as string
   */
  public String getGoal() {
    StringBuilder stringBuilder = new StringBuilder();
    for (String s : mandatoryItems) {
      stringBuilder.append(s).append(",");
    }
    return stringBuilder.toString();
  }
}
