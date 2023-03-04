package gruppe.fire;

/**
 * This class represents a health goal that specifies a minimum amount of health that the player needs to have.
 */
public class HealthGoal implements Goal{
    private int minimumHealth;

    public HealthGoal(int minimumHealth){
        this.minimumHealth = minimumHealth;
    }
    public boolean isFulfilled(Player player){
        if(player.getHealth() > minimumHealth){
            return true;
        }else{
            return false;
        }
    }
    public int remainingHealth(Player player){
        return minimumHealth - player.getHealth();
    }
}
