package gruppe.fire.goals;

import gruppe.fire.logic.Player;

/**
 * This class represents a gold goal that specifies a minimum amount of gold that the player needs to have.
 */
public class GoldGoal implements Goal {
    private int minimumGold;

    /**
     * Creates an instance of GoldGoal with the specified minimum gold amount.
     * @param minimumGold The minimum amount of gold required for the goal to be fulfilled.
     */
    public GoldGoal(int minimumGold){
        this.minimumGold = minimumGold;
    }

    /**
     * Checks whether the player's gold meets the minimum gold requirement.
     * @param player The player whose gold amount will be checked.
     * @return True if the player's gold is greater than or equal to the minimum gold requirement, false otherwise.
     */
    public boolean isFulfilled(Player player){
        if(player.getGold() > minimumGold){
            return true;
        }else{
            return false;
        }
    }

    /**
     * Calculates the remaining gold needed to fulfill the goal.
     * @param player The player whose gold amount will be checked.
     * @return The remaining gold needed to fulfill the goal.
     */
    public int remainingGold(Player player){
        return minimumGold - player.getGold();
    }
}
