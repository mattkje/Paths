package gruppe.fire;

import java.util.ArrayList;

public class InventoryAction implements Actions {
    private String item;

    public InventoryAction(String item){
        this.item = item;
    }
    public void execute(Player player){
        player.addToInventory(item);
    }
}
