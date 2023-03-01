package gruppe.fire;

/**
 * This class represents an expected inventory with items.
 */
import java.util.ArrayList;

public class InventoryGoal {
    private ArrayList<String> mandatoryItems;

    public InventoryGoal(ArrayList<String> mandatoryItems){
        this.mandatoryItems = mandatoryItems;

    }

    public boolean isFulfilled(Player player){
        return mandatoryItems != null;
    }
}
