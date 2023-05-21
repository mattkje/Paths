package gruppe.fire.logic;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class PlayerTest {

    private Player player;

    @BeforeEach
    void setUp() {
        player = new Player.PlayerBuilder()
            .name("Hans")
            .image(null)
            .health(100)
            .score(10)
            .gold(50)
            .build();
    }

    @Test
    void testAddHealth() {
        int newHealth = player.addHealth(50);
        Assertions.assertEquals(150, newHealth);
        Assertions.assertEquals(150, player.getHealth());
    }

    @Test
    void testAddGoldWithPositiveAmount() {
        int newGold = player.addGold(100);
        Assertions.assertEquals(150, newGold);
        Assertions.assertEquals(150, player.getGold());
    }

    @Test
    void testAddGoldWithNegativeAmount() {
        int newGold = player.addGold(-30);
        Assertions.assertEquals(20, newGold);
        Assertions.assertEquals(20, player.getGold());
    }


    @Test
    void testAddToInventory() {
        player.addToInventory("Sword");
        Assertions.assertTrue(player.getInventory().contains("Sword"));
    }
}