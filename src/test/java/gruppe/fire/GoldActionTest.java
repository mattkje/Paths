package gruppe.fire;
import gruppe.fire.actions.GoldAction;
import gruppe.fire.logic.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
class GoldActionTest {

    @Test
    void testExecutePositiveGold() {
        GoldAction action = new GoldAction(260);
        Player player = new Player("Alice", 500, 400, 340);
        action.execute(player);
        assertEquals(600, player.getGold());
    }
}

