package gruppe.fire;
import gruppe.fire.actions.GoldAction;
import gruppe.fire.actions.HealthAction;
import gruppe.fire.logic.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HealthActionTest {

    @Test
    void testExecutePositiveGold() {
        HealthAction action = new HealthAction(260);
        Player player = new Player.PlayerBuilder()
            .name("Mathias")
            .health(500)
            .gold(340)
            .score(400)
            .build();
        action.execute(player);
        assertEquals(760, player.getHealth());
    }
}

