package gruppe.fire;

import java.util.ArrayList;

public class InventoryGoal {
    private ArrayList<String> mandatoryItems;

    public InventoryGoal(ArrayList<String> mandatoryItems){
        this.mandatoryItems = mandatoryItems;
    }
    public boolean isFulfilled(Player player){
        if(player.get() > minimumGold){
            return true;
        }else{
            return false;
        }
    }
    public int remainingHealth(Player player){
        return minimumGold - player.getGold();
    }
}
