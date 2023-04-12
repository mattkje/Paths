package gruppe.fire.goals;

import gruppe.fire.logic.Player;

/**
 * This class represents a target value or a desired result related to the player's condition.
 * While actions change the state of the player along the way, goals make it possible to check
 * whether the player has achieved the expected result.
 */
public interface Goal {
    public boolean isFulfilled(Player player);
}
