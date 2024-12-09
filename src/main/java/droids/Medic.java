package droids;

import game.BattleLogger;

public class Medic extends Droid {
    private int healsLeft;
    private final int healScore;
    public Medic(String name) {
        super(name, 40, 5); // Середнє здоров'я, низький урон
        healsLeft = 20;
        healScore = 20;
    }

    @Override
    public String getType(){
        return "Медик";
    }

    public void heal(Droid ally) {
        if (healsLeft<=0) {
            throw new IllegalCallerException(name + " can't heal because he left no heals");
        }

        BattleLogger.healAction(this, ally, healScore);
        ally.health += healScore;
        healsLeft -= 1;
    }
    public boolean canHeal() {
        return healsLeft > 0;
    }

    @Override
    public String toString() {
        return super.toString() + ", зцілювань: " + healsLeft;
    }
}
