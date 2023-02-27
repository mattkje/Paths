package gruppe.fire;

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
