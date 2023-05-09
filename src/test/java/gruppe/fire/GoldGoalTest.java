package gruppe.fire;

import gruppe.fire.goals.GoldGoal;
import gruppe.fire.logic.Player;
import javafx.scene.image.Image;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GoldGoalTest {

    @Test
    void testIsFulfilled() {
        GoldGoal goal = new GoldGoal(1000);
        Player player1 = new Player("Mathias", null,500,200,300);
        Player player2 = new Player("Mathias", null,1500,450,5025);
        assertFalse(goal.isFulfilled(player1));
        assertTrue(goal.isFulfilled(player2));
    }

    @Test
    void testRemainingGold() {
        GoldGoal goal = new GoldGoal(1000);
        Player player1 = new Player("Mathias", null,500,200, 700);
        Player player2 = new Player("Mathias", null,1500, 250, 750 );
        assertEquals(300, goal.remainingGold(player1));
        assertEquals(250, goal.remainingGold(player2));
    }

}

