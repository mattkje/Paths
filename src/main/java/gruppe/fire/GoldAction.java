package gruppe.fire;

public class GoldAction implements Actions{
    private int gold;

    public void GoldAction(int gold){
        this.gold = gold;
    }
    public void execute(Player player){
        player.addGold(gold);
    }
}

