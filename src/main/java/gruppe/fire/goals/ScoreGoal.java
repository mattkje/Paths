package gruppe.fire.goals;

import gruppe.fire.logic.Player;

/**
 * This class represents an expected minimum score.
 *
 * @author Matti Kjellstadli
 * @version 2023-05-19
 */
public class ScoreGoal implements Goal {
  private int minimumPoints;

  public ScoreGoal(int minimumPoints) {
    this.minimumPoints = minimumPoints;
  }

  public boolean isFulfilled(Player player) {
    if (player.getScore() >= minimumPoints) {
      return true;
    } else {
      return false;
    }
  }

  public int remainingPoints(Player player) {
    return minimumPoints - player.getScore();
  }

  public String getGoal() {
    return this.minimumPoints + " Points";
  }
}
