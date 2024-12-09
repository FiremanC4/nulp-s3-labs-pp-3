package game;

import droids.Droid;
import droids.Medic;
import droids.Sniper;
import droids.Tank;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static ArrayList<Droid> droids = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    private static BattleManager battleManager = new BattleManager();

    public static void main(String[] args) {
        droids.add(new Sniper("Andriy1"));
        droids.add(new Tank  ("Valera1"));
        droids.add(new Medic ("Ivan1"  ));

        droids.add(new Sniper("Andriy2"));
        droids.add(new Tank  ("Valera2"));
        droids.add(new Medic ("Ivan2"  ));

        while (true) {
            System.out.println("\nГоловне меню:");
            System.out.println("1. Створити дроїда");
            System.out.println("2. Показати список дроїдів");
            System.out.println("3. Бій 1 на 1");
            System.out.println("4. Бій команда на команду");
            System.out.println("5. Прочитати бої з файла");
            System.out.println("0. Вийти");
            System.out.print("Ваш вибір: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> createDroid();
                case 2 -> showDroids();
                case 3 -> startOneOnOneBattle();
                case 4 -> startTeamBattle();
                case 5 -> BattleLogger.readFromFile();
                case 0 -> {
                    System.out.println("Дякуємо за гру!");
                    return;
                }
                default -> System.out.println("Невірний вибір, спробуйте ще раз.");
            }
        }

    }
    private static void startTeamBattle() {
        if (droids.size() < 4) {
            System.out.println("Недостатньо дроїдів для командного бою. Створіть більше.");
            return;
        }

        System.out.println("Формуємо команди. Командний бій потребує хоча б 4 дроїдів.");
        System.out.print("Скільки дроїдів буде в кожній команді? ");
        int teamSize = scanner.nextInt();
        scanner.nextLine();

        if (teamSize * 2 > droids.size()) {
            System.out.println("Недостатньо дроїдів для обраного розміру команд.");
            return;
        }

        System.out.println("Список доступних дроїдів:");
        showDroids();

        ArrayList<Droid> team1 = new ArrayList<>();
        ArrayList<Droid> team2 = new ArrayList<>();

        System.out.println("Формуємо першу команду:");
        for (int i = 0; i < teamSize; i++) {
            System.out.print("Оберіть дроїда для команди 1 (номер): ");
            int index = scanner.nextInt() - 1;
            scanner.nextLine();
            if (index >= 0 && index < droids.size() && !team1.contains(droids.get(index)) && !team2.contains(droids.get(index))) {
                team1.add(droids.get(index));
            } else {
                System.out.println("Невірний вибір або дроїд вже в команді. Спробуйте ще раз.");
                i--;
            }
        }

        System.out.println("Формуємо другу команду:");
        for (int i = 0; i < teamSize; i++) {
            System.out.print("Оберіть дроїда для команди 2 (номер): ");
            int index = scanner.nextInt() - 1;
            scanner.nextLine();
            if (index >= 0 && index < droids.size() && !team1.contains(droids.get(index)) && !team2.contains(droids.get(index))) {
                team2.add(droids.get(index));
            } else {
                System.out.println("Невірний вибір або дроїд вже в команді. Спробуйте ще раз.");
                i--;
            }
        }

        battleManager.fightTeamVsTeam(team1, team2);
    }


    private static void createDroid() {
        System.out.println("Оберіть тип дроїда:");
        System.out.println("1. Снайпер");
        System.out.println("2. Танк");
        System.out.println("3. Медик");
        System.out.print("Ваш вибір: ");
        int type = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Введіть ім'я дроїда: ");
        String name = scanner.nextLine();

        Droid droid = switch (type) {
            case 1 -> new Sniper(name);
            case 2 -> new Tank(name);
            case 3 -> new Medic(name);
            default -> null;
        };

        if (droid != null) {
            droids.add(droid);
            System.out.println("Дроїд створений: " + droid);
        } else {
            System.out.println("Невірний вибір типу дроїда.");
        }
    }

    private static void showDroids() {
        if (droids.isEmpty()) {
            System.out.println("Немає створених дроїдів.");
        } else {
            System.out.println("Список дроїдів:");
            for (int i = 0; i < droids.size(); i++) {
                System.out.println((i + 1) + ". " + droids.get(i));
            }
        }
    }

    private static void startOneOnOneBattle() {
        if (droids.size() < 2) {
            System.out.println("Недостатньо дроїдів для бою. Створіть більше.");
            return;
        }
        showDroids();
        System.out.print("Оберіть першого дроїда (номер): ");
        int droid1Index = scanner.nextInt() - 1;
        System.out.print("Оберіть другого дроїда (номер): ");
        int droid2Index = scanner.nextInt() - 1;

        if (droid1Index != droid2Index && droid1Index >= 0 && droid1Index < droids.size() &&
                droid2Index >= 0 && droid2Index < droids.size()) {
            battleManager.fight1v1(droids.get(droid1Index), droids.get(droid2Index));
        } else {
            System.out.println("Невірний вибір дроїдів.");
        }
    }
}
