package gruppe.fire.actions;

import gruppe.fire.logic.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * Test class for the GoldAction Class.
 * The following positive tests:
 * - testExecutePositiveGold
 * <p>
 * The following negative tests:
 * - testExecuteNegativeGold
 */
class GoldActionTest {

    @Test
    void testExecutePositiveGold() {
        GoldAction action = new GoldAction(260);
        Player player = new Player.PlayerBuilder()
            .gold(340)
            .build();
        action.execute(player);
        assertEquals(600, player.getGold());
    }

    @Test
    void testExecuteNegativeGold() {
        GoldAction action = new GoldAction(260);
        Player player = new Player.PlayerBuilder()
            .gold(340)
            .build();
        action.execute(player);
        assertNotEquals(500, player.getGold());
    }
}

