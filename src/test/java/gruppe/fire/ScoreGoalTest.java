package gruppe.fire;

import gruppe.fire.goals.ScoreGoal;
import gruppe.fire.logic.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ScoreGoalTest {
    @Test
    void testIsFulfilled() {
        Player player = new Player("Mathias", null,5,50,5);
        ScoreGoal goal = new ScoreGoal();
        Assertions.assertTrue(goal.isFulfilled(player));
    }

    @Test
    void testRemainingPoints() {
        Player player = new Player("Mathias", null, 5, 55, 15);
        ScoreGoal goal = new ScoreGoal();
        Assertions.assertEquals(50, goal.remainingPoints(player));
    }

    @Test
    void testIsNotFulFilled() {
        Player player = new Player("Mathias", null, 10, 100, 15);
        ScoreGoal goal = new ScoreGoal();
        Assertions.assertFalse(goal.isFulfilled(player));
    }
}
