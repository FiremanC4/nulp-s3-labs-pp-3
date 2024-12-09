package droids;

public class Tank extends Droid {
    private int maxTookDamage;
    @Override
    public String getType(){
        return "Танк";
    }
    public Tank(String name) {
        super(name, 100, 10); // Високе здоров'я, низький урон
        maxTookDamage = 7;
    }

    @Override
    public int takeDamage(int damage) {
        return super.takeDamage(Math.min(damage, maxTookDamage));
    }
}
