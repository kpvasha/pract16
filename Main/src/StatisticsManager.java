package game;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class StatisticsManager {
    private static final String STATS_FILE = "statistics.txt";
    private FileManager fileManager;

    public StatisticsManager(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    public void saveGameStatistics(GameSettings settings, String winner) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String timestamp = LocalDateTime.now().format(formatter);

            Path path = Paths.get(STATS_FILE);
            String content = "Дата: " + timestamp + "\n" +
                    "Розмір дошки: " + settings.getBoardSize() + "x" + settings.getBoardSize() + "\n" +
                    "Гравець 1: " + settings.getPlayer1Name() + " (X)\n" +
                    "Гравець 2: " + settings.getPlayer2Name() + " (O)\n" +
                    "Переможець: " + winner + "\n\n";

            if (!Files.exists(path)) {
                Files.writeString(path, content);
            } else {
                Files.writeString(path, content, StandardOpenOption.APPEND);
            }

            System.out.println("Статистика гри збережена.");
        } catch (IOException e) {
            System.out.println("Помилка: " + e.getMessage());
        }
    }

    public void showStatistics() {
        Path path = Paths.get(STATS_FILE);
        if (!Files.exists(path)) {
            System.out.println("Статистики не знайдено");
            return;
        }

        System.out.println("\nІгрова статистика");
        try {
            String content = Files.readString(path);
            System.out.println(content);

            System.out.println("\nНатисність Enter");
            Scanner scanner = new Scanner(System.in);
            scanner.nextLine();
        } catch (IOException e) {
            System.out.println("Помилка: " + e.getMessage());
        }
    }
}