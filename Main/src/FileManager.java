package game;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileManager {
    private static final String CONFIG_FILE = "config.txt";

    public void saveConfiguration(GameSettings settings) {
        try {
            Path path = Paths.get(CONFIG_FILE);
            String content = "boardSize=" + settings.getBoardSize() + "\n" +
                    "player1=" + settings.getPlayer1Name() + "\n" +
                    "player2=" + settings.getPlayer2Name() + "\n";
            Files.writeString(path, content);
            System.out.println("Конфігурація збережена");
        } catch (IOException e) {
            System.out.println("Помилка збереження конфігурації: " + e.getMessage());
        }
    }

    public GameSettings loadConfiguration() {
        Path path = Paths.get(CONFIG_FILE);
        GameSettings settings = new GameSettings();

        if (!Files.exists(path)) {
            System.out.println("Файл конфігурації не знайдено.");
            return settings;
        }

        try {
            BufferedReader reader = Files.newBufferedReader(path);
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("=", 2);
                if (parts.length == 2) {
                    String key = parts[0];
                    String value = parts[1];

                    switch (key) {
                        case "boardSize" -> settings.setBoardSize(Integer.parseInt(value));
                        case "player1" -> settings.setPlayer1Name(value);
                        case "player2" -> settings.setPlayer2Name(value);
                    }
                }
            }
            reader.close();
            System.out.println("Конфігурацію успішно завантажено.");
        } catch (IOException e) {
            System.out.println("Помилка завантаження конфігурації:" + e.getMessage());
        }

        return settings;
    }
}