package gruppe.fire;
import gruppe.fire.actions.GoldAction;
import gruppe.fire.logic.Player;
import javafx.scene.image.Image;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
class GoldActionTest {

    @Test
    void testExecutePositiveGold() {
        GoldAction action = new GoldAction(260);
        Player player = new Player.PlayerBuilder()
            .name("Mathias")
            .health(500)
            .gold(340)
            .score(400)
            .build();
        action.execute(player);
        assertEquals(600, player.getGold());
    }
}

