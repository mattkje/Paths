package gruppe.fire.actions;


import gruppe.fire.logic.Player;

/**
 * This interface is responsible for changes in the player's score,
 * health, gold inventory or inventory.
 *
 * @author Matti Kjellstadli
 * @version 2023-05-21
 */
public interface Action {
   void execute(Player player);
}
