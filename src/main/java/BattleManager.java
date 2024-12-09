import droids.Droid;
import droids.Medic;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class BattleManager {
    private final Scanner scanner = new Scanner(System.in);
    private final Random random = new Random();

    void perform1v1Action(Droid attacker, Droid defender) {
        switch (attacker.getType()) {
            case "Медик":
                if (    ((Medic) attacker).canHeal()
                        && attacker.getHealth() <= 30
                        && !defender.getType().equals("Снайпер")) {
                    ((Medic)attacker).heal(attacker);
                } else {
                    attacker.attack(defender);
                }
                break;
            case "Танк":
            case "Снайпер":
                attacker.attack(defender);
                break;
        }
    }

    public void fight1v1(Droid droid1, Droid droid2) {
        System.out.println("Бій розпочинається між " + droid1.getName() + " і " + droid2.getName() + "!");
        while (droid1.isAlive() && droid2.isAlive()) {
            perform1v1Action(droid1, droid2);

            if (droid2.isAlive()) {
                perform1v1Action(droid2, droid1);
            }

            System.out.println(droid1);
            System.out.println(droid2);
        }
        if (droid1.isAlive()) {
            System.out.println(droid1.getName() + " переміг!");
        } else if (droid2.isAlive()) {
            System.out.println(droid2.getName() + " переміг!");
        } else {
            System.out.println("Обидва дроїди померли в жорстокій битві");

        }
    }


    void performTeamVsTeamAction(Droid attacker, List<Droid> attackingTeam, List<Droid> opponentTeam) {
        switch (attacker.getType()) {
            case "Медик":
                Medic med = (Medic) attacker;
                if (med.canHeal()) {
                    Droid ally = attackingTeam.stream().filter(droid -> droid.getHealth() < 100 && droid != med).findFirst().orElse(null);
                    if (ally != null) {
                        med.heal(ally);
                        break;
                    } else if (med.getHealth() < 100) {
                        med.heal(med);
                        break;
                    }
                }
                med.attack(opponentTeam.get(random.nextInt(opponentTeam.size())));
                break;
            case "Танк":
            case "Снайпер":
                attacker.attack(opponentTeam.get(random.nextInt(opponentTeam.size())));
                break;
        }
    }

    public void fightTeamVsTeam(List<Droid> team1, List<Droid> team2) {
        System.out.println("Бій між командами починається!");

        while (team1.stream().anyMatch(Droid::isAlive) && team2.stream().anyMatch(Droid::isAlive)) {
            List<Droid> attackingTeam = random.nextBoolean() ? team1 : team2;
            List<Droid> opponentTeam = (attackingTeam == team1) ? team2 : team1;
            Droid attacker = attackingTeam.get(random.nextInt(attackingTeam.size()));

            performTeamVsTeamAction(attacker, attackingTeam, opponentTeam);

            team1.removeIf(droid -> !droid.isAlive());
            team2.removeIf(droid -> !droid.isAlive());

            System.out.println("Стан команд:");
            System.out.println("Команда 1:");
            team1.forEach(System.out::println);
            System.out.println("Команда 2:");
            team2.forEach(System.out::println);
        }

        if (team1.stream().anyMatch(Droid::isAlive)) {
            System.out.println("Команда 1 перемогла!");
        } else if (team2.stream().anyMatch(Droid::isAlive)) {
            System.out.println("Команда 2 перемогла!");
        } else {
            System.out.println("Обидві команди знищені.");
        }
    }
}
