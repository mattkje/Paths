package gruppe.fire;

/**
 * This class is responsible for changing the player's inventory.
 */
public class InventoryAction implements Actions {
    private String item;

    public InventoryAction(String item){
        this.item = item;
    }
    public void execute(Player player){
        player.addToInventory(item);
    }
}
