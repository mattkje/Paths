package gruppe.fire;

/**
 * This class is responsible for changing the player's health.
 */
public class HealthAction implements Actions{
    private int health;

    public void HealthAction(int health){
        this.health = health;
    }
    public void execute(Player player){
        player.addHealth(health);
    }
}

