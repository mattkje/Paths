package gruppe.fire.goals;

import gruppe.fire.goals.ScoreGoal;
import gruppe.fire.logic.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ScoreGoalTest {
    @Test
    void testIsFulfilled() {
        Player player = new Player.PlayerBuilder()
            .name("Mathias")
            .health(5)
            .gold(50)
            .score(5)
            .build();
        ScoreGoal goal = new ScoreGoal(0);
        Assertions.assertTrue(goal.isFulfilled(player));
    }

    @Test
    void testRemainingPoints() {
        Player player = new Player.PlayerBuilder()
            .name("Mathias")
            .health(5)
            .gold(15)
            .score(50)
            .build();
        ScoreGoal goal = new ScoreGoal(100);
        Assertions.assertEquals(50, goal.remainingPoints(player));
    }

    @Test
    void testIsNotFulFilled() {
        Player player = new Player.PlayerBuilder()
            .name("Mathias")
            .health(10)
            .gold(15)
            .score(100)
            .build();
        ScoreGoal goal = new ScoreGoal(0);
        Assertions.assertTrue(goal.isFulfilled(player));
    }
}
