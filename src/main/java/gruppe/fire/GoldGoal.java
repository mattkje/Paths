package gruppe.fire;

/**
 * This class represents a gold goal that specifies a minimum amount of gold that the player needs to have.
 */
public class GoldGoal implements Goal{
    private int minimumGold;

    public GoldGoal(int minimumGold){
        this.minimumGold = minimumGold;
    }
    public boolean isFulfilled(Player player){
        if(player.getGold() > minimumGold){
            return true;
        }else{
            return false;
        }
    }
    public int remainingHealth(Player player){
        return minimumGold - player.getGold();
    }
}
