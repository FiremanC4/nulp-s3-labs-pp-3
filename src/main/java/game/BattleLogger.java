package game;

import droids.Droid;
import droids.Sniper;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.util.List;

public class BattleLogger {
    private static final String LOG_FILE = "battle_log.txt";
    private static PrintWriter fileWriter;
    private static int round;

    static {
        try {
            fileWriter = new PrintWriter(new FileWriter(LOG_FILE, true)); // Append mode
        } catch (IOException e) {
            throw new RuntimeException("Не вдалося відкрити файл для запису логів: ", e);
        }
    }

    public static void readFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(LOG_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
        }
    }

    private static void log(String message) {
        String logMessage;
        if (message != null)
            logMessage = String.format("[%3s] %s", round, message);
        else
            logMessage = "";

        // Print to console
        System.out.println(logMessage);

        // Write to file
        if (fileWriter != null) {
            fileWriter.println(logMessage);
            fileWriter.flush();
        }
    }

    // 1v1 Battle Logs
    public static void start1v1(Droid droid1, Droid droid2) {
        round = 1;
        log("Бій розпочинається між " + droid1.getName() + " і " + droid2.getName() + "!");
    }

    public static void Round1v1Summary(Droid droid1, Droid droid2) {
        log("Стан дроїдів після раунду:");
        log(String.valueOf(droid1));
        log(String.valueOf(droid2));
        log(null);
        round += 1;

    }

    public static void result1v1(Droid winner) {
        if (winner.isAlive()) {
            log(winner.getName() + " переміг!");
        } else {
            log("Обидва дроїди померли в жорстокій битві.");
        }
    }

    // Team Battle Logs
    public static void startTeamBattle(List<Droid> team1, List<Droid> team2) {
        round = 1;
        log("Бій між командами починається!");
        log("Команда 1: " + team1);
        log("Команда 2: " + team2);
    }

    public static void teamRoundSummary(List<Droid> team1, List<Droid> team2) {
        log("Стан команд після раунду:");
        log("Команда 1: " + team1);
        log("Команда 2: " + team2);
        log(null);
        round += 1;
    }

    public static void teamBattleResult(List<Droid> team1, List<Droid> team2) {
        if (team1.stream().anyMatch(Droid::isAlive)) {
            log("Команда 1 перемогла!");
        } else if (team2.stream().anyMatch(Droid::isAlive)) {
            log("Команда 2 перемогла!");
        } else {
            log("Обидві команди знищені.");
        }
    }

    public static void healAction(Droid medic, Droid ally, int healScore) {
        log(medic + " лікує " + ally + " на " + healScore + " одиниць.");

    }

    public static void camouflageAction(Sniper sniper) {
        log(sniper + " уникає удару завдяки маскуванню!");
    }

    public static void attackAction(Droid attacker, Droid target) {
        log(attacker.getName() + " атакує " + target.getName());
    }

    public static void takeDamageAction(Droid droid, int takenDamage) {
        log(droid + ", отримав " + takenDamage + " шкоди.");
    }
    public static void takeDamageAndDieAction(Droid droid, int takenDamage) {
        log(droid + ", отримав " + takenDamage + " шкоди і був знищений.");
    }

    public static void close() {
        if (fileWriter != null) {
            fileWriter.close();
        }
    }
}
