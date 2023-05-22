package gruppe.fire.actions;

import gruppe.fire.logic.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for the HealthAction Class.
 * The following positive tests:
 * - testExecutePositiveGold
 * - testExecuteNegativeHealth
 * <p>
 * No negative tests.
 */
class HealthActionTest {

    @Test
    void testExecutePositiveHealth() {
        HealthAction action = new HealthAction(1);
        Player player = new Player.PlayerBuilder()
            .health(5)
            .build();
        action.execute(player);
        assertEquals(6, player.getHealth());
    }

    @Test
    void testExecuteNegativeHealth() {
        HealthAction action = new HealthAction(-1);
        Player player = new Player.PlayerBuilder()
            .health(5)
            .build();
        action.execute(player);
        assertEquals(4, player.getHealth());
    }
}

