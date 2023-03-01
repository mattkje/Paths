package gruppe.fire;

/**
 * This interface is responsible for changes in the player's score,
 * health, gold inventory or inventory.
 */
public interface Actions {
    public void execute(Player player);
}
