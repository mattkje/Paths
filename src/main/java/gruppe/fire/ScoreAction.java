package gruppe.fire;

/**
 * This class is responsible for changing the player's score.
 */
public class ScoreAction implements Actions{
    private int score;

    public void ScoreAction(int score){
        this.score = score;
    }
    public void execute(Player player){
        player.addScore(score);
    }
}
