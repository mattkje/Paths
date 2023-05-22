package gruppe.fire.actions;

import gruppe.fire.logic.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for the ScoreAction Class.
 * The following positive tests:
 * - testExecutePositiveScore
 * - testExecuteNegativeScore
 * <p>
 * No negative tests.
 */
class ScoreActionTest {

    @Test
    void testExecutePositiveScore() {
        ScoreAction action = new ScoreAction(1);
        Player player = new Player.PlayerBuilder()
            .score(5)
            .build();
        action.execute(player);
        assertEquals(6, player.getScore());
    }

    @Test
    void testExecuteNegativeScore() {
        ScoreAction action = new ScoreAction(-1);
        Player player = new Player.PlayerBuilder()
            .score(5)
            .build();
        action.execute(player);
        assertEquals(4, player.getScore());
    }
}

