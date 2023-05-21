package gruppe.fire.actions;

import gruppe.fire.logic.Player;

/**
 * This class represents an action that adds an item to the player's inventory.
 *
 * @author Matti Kjellstadli
 * @version 2023-05-21
 */
public class InventoryAction implements Action {
  private final String item;

  /**
   * Creates an instance of InventoryAction with the specified item to be added to the player's inventory.
   *
   * @param item The item to be added to the player's inventory.
   */
  public InventoryAction(String item) {
    this.item = item;
  }

  /**
   * Executes the inventory action by adding the specified item to the player's inventory.
   *
   * @param player The player whose inventory will be changed.
   */
  public void execute(Player player) {
    player.addToInventory(item);
  }
}
