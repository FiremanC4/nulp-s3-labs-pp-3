package droids;


import game.BattleLogger;

public class Sniper extends Droid {
    protected double camouflageLevel;

    @Override
    public String getType(){
        return "Снайпер";
    }

    public Sniper(String name) {
        super(name, 35, 30);
        this.camouflageLevel = 0.6;
    }

    @Override
    public int takeDamage(int damage) {
        if (Math.random() < camouflageLevel) {
            BattleLogger.camouflageAction(this);
            return 0;
        }
        return super.takeDamage(damage);
    }

}
