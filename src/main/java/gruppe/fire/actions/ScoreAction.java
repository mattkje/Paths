package gruppe.fire.actions;

import gruppe.fire.logic.Player;

/**
 * This class is responsible for changing the player's score.
 */
public class ScoreAction implements Action {
  private int score;

  public ScoreAction(int score) {
    this.score = score;
  }

  public void execute(Player player) {
    player.addScore(score);
  }
}
