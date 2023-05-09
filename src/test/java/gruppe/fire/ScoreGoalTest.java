package gruppe.fire;

import gruppe.fire.goals.ScoreGoal;
import gruppe.fire.logic.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ScoreGoalTest {
    @Test
    void testIsFulfilled() {
        Player player = new Player("Adrian", null,5,50,5);
        ScoreGoal goal = new ScoreGoal(50);
        Assertions.assertTrue(goal.isFulfilled(player));
    }

    @Test
    void testRemainingPoints() {
        Player player = new Player("Adrian", null, 5, 55, 15);
        ScoreGoal goal = new ScoreGoal(105);
        Assertions.assertEquals(50, goal.remainingPoints(player));
    }

    @Test
    void testIsNotFulFilled() {
        Player player = new Player("Adrian", null, 10, 100, 15);
        ScoreGoal goal = new ScoreGoal(110);
        Assertions.assertFalse(goal.isFulfilled(player));
    }
}
