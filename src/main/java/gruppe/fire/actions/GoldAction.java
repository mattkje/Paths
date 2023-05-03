package gruppe.fire.actions;

import gruppe.fire.logic.Player;

/**
 * This class responsible for changing the player's gold.
 */
public class GoldAction implements Action {
    private int gold;

    /**
     * Creates an instance of GoldAction with the specified gold amount.
     * @param gold The amount of gold to be added to the player's gold.
     */
    public GoldAction(int gold){
        this.gold = gold;
    }

    /**
     * Executes the gold action by adding the specified gold amount to the player's gold.
     * @param player The player whose gold will be changed.
     */
    public void execute(Player player){
        player.addGold(gold);
    }
}

