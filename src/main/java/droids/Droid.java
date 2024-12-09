package droids;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Droid {
    private final Logger logger = LogManager.getLogger();

    protected String name;
    protected int health;
    protected int damage;

    public Droid(String name, int health, int damage) {
        this.name = name;
        this.health = health;
        this.damage = damage;
    }

    public int attack(Droid droidToAttack) {
        return droidToAttack.takeDamage(this.damage);
    }

    public int takeDamage(int damage) {
        this.health -= damage;
        if (health <= 0) {
            int takenDamage = this.health + damage;
            health = 0;
            logger.info("{} отримав {} шкоди і знищений.", name, takenDamage);
            return takenDamage;
        } else {
            logger.info("{} отримав {} шкоди, залишилося {} здоров'я.", name, damage, health);
            return damage;
        }
    }

    public String getType(){
        return "Дроїд";
    }

    public boolean isAlive() {
        return health > 0;
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    @Override
    public String toString() {
        String status = isAlive() ? "живий" : "знищений";
        return "(" + getType() + ") " + name + " Здоров'я: " + health + ", Статус: " + status;
    }

}
