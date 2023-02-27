package gruppe.fire;

public class ScoreGoal implements Goal{
    private int minimumPoints;

    public void ScoreGoal(int minimumPoints){
        this.minimumPoints = minimumPoints;
    }
    public boolean isFulfilled(Player player){
        if(player.getScore() >= minimumPoints){
            return true;
        }else{
            return false;
        }
    }
    public int remainingPoints(Player player){
        return minimumPoints - player.getScore();
    }
}
