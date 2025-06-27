package game;

import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static GameSettings settings = new GameSettings();
    private static FileManager fileManager = new FileManager();
    private static StatisticsManager statisticsManager = new StatisticsManager(fileManager);

    public static void main(String[] args) {
        settings = fileManager.loadConfiguration();
        mainGameLoop();
        scanner.close();
        System.out.println("Вихід");
    }

    private static void mainGameLoop() {
        boolean codeisrunning = true;
        while (codeisrunning) {
            displayMainMenu();
            if (!scanner.hasNextLine()) {
                System.out.println("Спробуйте ще раз.");
                continue;
            }

            String input = scanner.nextLine();
            if (input.isEmpty()) {
                System.out.println("Спробуйте ще раз.");
                continue;
            }

            char choice = input.charAt(0);
            switch (choice) {
                case '1' -> handleGameMenu();
                case '2' -> handleSettingsMenu();
                case '3' -> statisticsManager.showStatistics();
                case '4' -> codeisrunning = handleExitMenu();
                default -> System.out.println("Спробуйте ще раз.");
            }
        }
    }

    private static void displayMainMenu() {
        System.out.println("""
                Головне меню
                1. Почати гру
                2. Налаштування
                3. Статистика
                4. Вихід""");
    }

    private static void handleGameMenu() {
        boolean inGameMenu = true;
        while (inGameMenu) {
            System.out.println(": Розмір дошки: " + settings.getBoardSize() + "x" + settings.getBoardSize());
            System.out.println("Гравці: " + settings.getPlayer1Name() + " (X) проти " + settings.getPlayer2Name() + " (O)");
            System.out.println("Готові? (1) Так! (2) Головне меню");

            String input = scanner.nextLine();
            if (input.isEmpty()) {
                System.out.println("Спробуйте ще раз.");
                continue;
            }

            char choice = input.charAt(0);
            if (choice == '2') {
                inGameMenu = false;
            } else if (choice == '1') {
                TicTacToeGame game = new TicTacToeGame(settings, scanner, statisticsManager);
                game.playGame();
                inGameMenu = false;
            } else {
                System.out.println("Спробуйте ще раз.");
            }
        }
    }

    private static void handleSettingsMenu() {
        boolean inSettingsMenu = true;
        while (inSettingsMenu) {
            System.out.println("""
                Меню налаштувань
                1. Розмір дошки
                2. Ім'я гравця
                0. Головне меню""");

            String input = scanner.nextLine();
            if (input.isEmpty()) {
                System.out.println("Спробуйте ще раз.");
                continue;
            }

            char choice = input.charAt(0);
            switch (choice) {
                case '1' -> changeBoardSize();
                case '2' -> changePlayerNames();
                case '0' -> inSettingsMenu = false;
                default -> System.out.println("Спробуйте ще раз.");
            }

            if (choice == '1' || choice == '2') {
                fileManager.saveConfiguration(settings);
            }
        }
    }

    private static void changeBoardSize() {
        System.out.println("""
            Розмір дошки:
            1. 3x3
            2. 5x5
            3. 7x7
            4. 9x9
            0. Скасувати""");

        String input = scanner.nextLine();
        if (input.isEmpty()) {
            System.out.println("Спробуйте ще раз.");
            return;
        }

        char choice = input.charAt(0);
        switch (choice) {
            case '1' -> settings.setBoardSize(3);
            case '2' -> settings.setBoardSize(5);
            case '3' -> settings.setBoardSize(7);
            case '4' -> settings.setBoardSize(9);
            case '0' -> { return; }
            default -> {
                System.out.println("Спробуйте ще раз.");
                return;
            }
        }
        System.out.println("Встановлено розмір " + settings.getBoardSize() + "x" + settings.getBoardSize());
    }

    private static void changePlayerNames() {
        System.out.println("Введіть ім'я для гравця 1 (X): ");
        String input = scanner.nextLine();
        if (!input.isEmpty()) {
            settings.setPlayer1Name(input);
        }

        System.out.println("Введіть ім'я для гравця 2 (O): ");
        input = scanner.nextLine();
        if (!input.isEmpty()) {
            settings.setPlayer2Name(input);
        }

        System.out.println("Імена гравців оновлено: " + settings.getPlayer1Name() + " (X) проти " + settings.getPlayer2Name() + " (O)");
    }

    private static boolean handleExitMenu() {
        System.out.println("Впевнені? (1(Так) або 2(Ні)");
        String input = scanner.nextLine();
        if (input.isEmpty()) {
            System.out.println("Спробуйте ще раз.");
            return true;
        }

        char choice = input.charAt(0);
        if (choice == '1') {
            return false;
        } else if (choice == '2') {
            System.out.println("Повернення в головне меню");
            return true;
        } else {
            System.out.println("Спробуйте ще раз");
            return true;
        }
    }
}