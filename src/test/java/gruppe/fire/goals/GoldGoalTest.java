package gruppe.fire.goals;

import gruppe.fire.logic.Player;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GoldGoalTest {

    @Test
    void testIsFulfilled() {
        GoldGoal goal = new GoldGoal(1000);
        Player player1 = new Player.PlayerBuilder()
            .name("Mathias")
            .health(500)
            .gold(300)
            .score(200)
            .build();
        Player player2 = new Player.PlayerBuilder()
            .name("Mathias")
            .health(1500)
            .gold(5025)
            .score(450)
            .build();
        assertFalse(goal.isFulfilled(player1));
        assertTrue(goal.isFulfilled(player2));
    }


    }


