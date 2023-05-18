package gruppe.fire.actions;


import gruppe.fire.logic.Player;

/**
 * This interface is responsible for changes in the player's score,
 * health, gold inventory or inventory.
 */
public interface Action {
  public void execute(Player player);
}
