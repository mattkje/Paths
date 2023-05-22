package gruppe.fire.actions;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import gruppe.fire.logic.Player;
import org.junit.jupiter.api.Test;

/**
 * Test class for the InventoryAction Class.
 * The following positive tests:
 * - testExecutePositiveInventory
 * - testExecutePositiveMultiInventory
 * <p>
 * The following negative tests:
 * - testExecuteNegativeInventory
 * - testExecuteNegativeMultiInventory
 */
class InventoryActionTest {

    @Test
    void testExecutePositiveInventory() {
        InventoryAction action = new InventoryAction("Item");
        Player player = new Player.PlayerBuilder()
            .build();
        action.execute(player);
        assertTrue(player.getInventory().contains("Item"));
    }

    @Test
    void testExecutePositiveMultiInventory() {
        InventoryAction action = new InventoryAction("Item1");
        InventoryAction action2 = new InventoryAction("Item2");
        Player player = new Player.PlayerBuilder()
            .name("Mathias")
            .build();
        action.execute(player);
        action2.execute(player);
        assertTrue(player.getInventory().contains("Item1") && player.getInventory().contains("Item2"));
    }

    @Test
    void testExecuteNegativeInventory() {
        InventoryAction action = new InventoryAction("Item1");
        Player player = new Player.PlayerBuilder()
            .build();
        action.execute(player);
        assertFalse(player.getInventory().contains("Item2"));
    }

    @Test
    void testExecuteNegativeMultiInventory() {
        InventoryAction action = new InventoryAction("Item1");
        InventoryAction action2 = new InventoryAction("Item2");
        Player player = new Player.PlayerBuilder()
            .build();
        action.execute(player);
        action2.execute(player);
        assertFalse(player.getInventory().contains("Item4") && player.getInventory().contains("Item3"));
    }
}

