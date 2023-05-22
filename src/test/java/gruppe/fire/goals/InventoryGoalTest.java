package gruppe.fire.goals;

import gruppe.fire.logic.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;


/**
 * Test class for the InventoryGoal Class.
 * The following positive tests:
 * - testIsFulfilled
 * <p>
 * The following negative tests:
 * - testIsNotFulfilledItemsMissing
 * - testIsFulfilledWithNoItems
 */
class InventoryGoalTest {

    @Test
    void testIsFulfilled() {
        Player player = new Player.PlayerBuilder()
            .build();
        List<String> mandatoryItems = new ArrayList<>();
        mandatoryItems.add("item1");
        mandatoryItems.add("item2");

        InventoryGoal goal = new InventoryGoal(mandatoryItems);

        player.addToInventory("item1");
        player.addToInventory("item2");
        Assertions.assertTrue(goal.isFulfilled(player));
    }

    @Test
    void testIsNotFulfilledItemsMissing() {
        Player player = new Player.PlayerBuilder()
            .build();
        List<String> mandatoryItems = new ArrayList<>();
        mandatoryItems.add("item1");
        mandatoryItems.add("item2");
        mandatoryItems.add("item3");

        InventoryGoal goal = new InventoryGoal(mandatoryItems);

        player.addToInventory("item1");
        player.addToInventory("item2");
        Assertions.assertFalse(goal.isFulfilled(player));
    }

    @Test
    void testIsFulfilledWithNoItems() {
        Player player = new Player.PlayerBuilder()
            .build();
        List<String> mandatoryItems = new ArrayList<>();
        mandatoryItems.add("item1");
        mandatoryItems.add("item2");
        mandatoryItems.add("item3");
        InventoryGoal goal = new InventoryGoal(mandatoryItems);

        Assertions.assertFalse(goal.isFulfilled(player));
    }
}
