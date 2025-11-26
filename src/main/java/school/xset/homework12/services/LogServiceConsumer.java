package school.xset.homework12.services;


import lombok.Data;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Data

@RestController
public class LogServiceConsumer {
    private static final String LOG_FILE = "consumer.log"; // Имя файла для логов
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); // Формат даты

    // Метод для записи лога
    public void writeLog(String infoMessage, String sendObject) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE, true))) { // true - для добавления, без перезаписи
            String timestamp = LocalDateTime.now().format(FORMATTER);
            writer.write(timestamp + ": " + infoMessage + ": " + sendObject);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace(); // Обработка ошибок ввода-вывода
        }
    }

    // Метод для чтения логов за промежуток времени
    public static List<String> readLog(String startDateTime, String endDateTime) {
        List<String> logs = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(LOG_FILE))) {
            String line;
            LocalDateTime start = LocalDateTime.parse(startDateTime, FORMATTER);
            LocalDateTime end = LocalDateTime.parse(endDateTime, FORMATTER);
            while ((line = reader.readLine()) != null) {
                // Парсим временную метку из строки лога
                int colonIndex = line.indexOf(": ");
                if (colonIndex > 0) {
                    String timestampStr = line.substring(0, colonIndex);
                    LocalDateTime timestamp = LocalDateTime.parse(timestampStr, FORMATTER);
                    // Проверяем, попадает ли время в диапазон
                    if ((timestamp.isEqual(start) || timestamp.isAfter(start)) && timestamp.isBefore(end)) {
                        logs.add(line);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace(); // Обработка ошибок ввода-вывода
        }
        return logs;
    }
}


