package gruppe.fire;

/**
 * This class represents an action that changes the player's health.
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

