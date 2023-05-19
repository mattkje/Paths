package gruppe.fire;

import gruppe.fire.goals.InventoryGoal;
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
        Assertions.assertTrue(goal.isFulfilled(player));
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

        for (String item : mandatoryItems) {
            System.out.println(item);
        }

        InventoryGoal goal = new InventoryGoal(mandatoryItems);

        Assertions.assertTrue(goal.isFulfilled(player));
    }
}
