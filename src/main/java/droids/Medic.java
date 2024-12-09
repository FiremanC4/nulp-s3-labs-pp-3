package droids;

public class Medic extends Droid {
    private int healsLeft;
    public Medic(String name) {
        super(name, 40, 5); // Середнє здоров'я, низький урон
        healsLeft = 20;
    }

    @Override
    public String getType(){
        return "Медик";
    }

    public void heal(Droid ally) {
        if (!isAlive()) {
            System.out.println(name + " не може лікувати. Він знищений!");
            return;
        }

        if (healsLeft<=0) {
            throw new IllegalCallerException(name + " can't heal because he left no heals");
        }

        System.out.println(name + " лікує " + ally.getName() + " на 20 одиниць.");
        ally.health += 20;
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
