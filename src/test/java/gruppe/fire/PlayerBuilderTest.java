package gruppe.fire;

import gruppe.fire.logic.Player;
import gruppe.fire.logic.PlayerBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class PlayerBuilderTest {

    @Test
    void testPlayerBuilderClass() {
        PlayerBuilder builder = new PlayerBuilder();
        builder.setName("Mathias");
        builder.setHealth(200);
        Player player = builder.getPlayer();
        assertEquals("Mathias", player.getName());
        assertEquals(200, player.getHealth());
        assertEquals(0, player.getGold());
    }

    @Test
    void testPlayerBuilderClassWithNoName() {
        PlayerBuilder builder = new PlayerBuilder();
        builder.setGold(20);
        builder.setScore(30);
        Player player = builder.getPlayer();
        assertEquals(20, player.getGold());
        assertEquals(30, player.getScore());
        assertNull(null, player.getName());
    }
}
