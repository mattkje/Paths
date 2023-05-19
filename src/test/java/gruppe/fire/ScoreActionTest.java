package gruppe.fire;

import gruppe.fire.actions.ScoreAction;
import gruppe.fire.logic.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ScoreActionTest {

    @Test
    void testExecutePositiveGold() {
        ScoreAction action = new ScoreAction(260);
        Player player = new Player.PlayerBuilder()
            .name("Mathias")
            .health(500)
            .gold(340)
            .score(400)
            .build();
        action.execute(player);
        assertEquals(560, player.getScore());
    }
}

