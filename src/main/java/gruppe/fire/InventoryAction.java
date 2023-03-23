package gruppe.fire;

/**
 * This class represents an action that adds an item to the player's inventory.
 */
public class InventoryAction implements Actions {
    private String item;

    /**
     * Creates an instance of InventoryAction with the specified item to be added to the player's inventory.
     * @param item The item to be added to the player's inventory.
     */
    public InventoryAction(String item){
        this.item = item;
    }

    /**
     * Executes the inventory action by adding the specified item to the player's inventory.
     * @param player The player whose inventory will be changed.
     */
    public void execute(Player player){
        player.addToInventory(item);
    }
}
