package gruppe.fire;

import gruppe.fire.actions.ScoreAction;
import gruppe.fire.logic.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ScoreActionTest {

    @Test
    void testExecutePositiveGold() {
        ScoreAction action = new ScoreAction(260);
        Player player = new Player("Mathias", null,500, 400, 340);
        action.execute(player);
        assertEquals(560, player.getScore());
    }
}

