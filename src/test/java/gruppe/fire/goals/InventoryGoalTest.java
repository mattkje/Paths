package gruppe.fire.goals;

import gruppe.fire.logic.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;


class InventoryGoalTest {

    @Test
    void testIsFulfilled() {
        Player player = new Player.PlayerBuilder()
            .name("Mathias")
            .health(50)
            .gold(50)
            .score(50)
            .build();
        List<String> mandatoryItems = new ArrayList<>();
        mandatoryItems.add("knife");
        mandatoryItems.add("shotgun");

        InventoryGoal goal = new InventoryGoal(mandatoryItems);

        player.addToInventory("knife");
        player.addToInventory("shotgun");
        Assertions.assertTrue(goal.isFulfilled(player));
    }

    @Test
    void testIsFulfilledWithSomeItemsMissing() {
        Player player = new Player.PlayerBuilder()
            .name("Mathias")
            .health(50)
            .gold(50)
            .score(50)
            .build();
        List<String> mandatoryItems = new ArrayList<>();
        mandatoryItems.add("shotgun");
        mandatoryItems.add("shotgun pellets");
        mandatoryItems.add("shells");

        InventoryGoal goal = new InventoryGoal(mandatoryItems);

        player.addToInventory("shotgun");
        player.addToInventory("shotgun pellets");
        Assertions.assertFalse(goal.isFulfilled(player));
    }

    @Test
    void testIsFulfilledWithNoItems() {
        Player player = new Player.PlayerBuilder()
            .name("Mathias")
            .health(50)
            .gold(50)
            .score(50)
            .build();
        List<String> mandatoryItems = new ArrayList<>();
        mandatoryItems.add("torch");
        mandatoryItems.add("torch");
        mandatoryItems.add("sword");
        InventoryGoal goal = new InventoryGoal(mandatoryItems);

        Assertions.assertFalse(goal.isFulfilled(player));
    }
}
