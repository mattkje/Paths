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

    //@Test
    //public void testExecuteNegativeGold() {
        //Player player = new Player("Per", 500, 200, 100);
        //GoldAction action = new GoldAction(-130);

        //Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            //action.execute(player);
        //});

        //String expectedMessage = "Gold cannot be less than 0";
        //String actualMessage = exception.getMessage();

        //assertEquals(expectedMessage, actualMessage);
    //}

}

