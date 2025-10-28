//Реализовать отдельный класс LogerService, в котором должны быть два метода:
// Требования для writeLog(Stirng infoMessage, String sendObject)
// 1. infoMessage - информативное сообщение (хардкод)
// 2. sendObject - отправляемый ответ на запросы
// 3. Метод writeLog должен писать в текстовый файл сообщение вида -  "LocalDateTime.now(): infoMessage: sendObject"
// 4. Лог не должен удаляться при перезапуске программы
// Требования для readLog()
// 1. Данный метод вызывается по Get-request /getLogs
// 2. Метод должен возвращать все логи, записанные в текстовом файле
package school.xset.homework8;

import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.logging.*;

@Data
@RestController

public class LoggerService {
    Logger logger = Logger.getLogger(LoggerService.class.getName());

    public void writeLog(String infoMessage, String sendObject) throws IOException {
        FileHandler fileHandler = new FileHandler("app.log", true);
        logger.setLevel(Level.INFO);

        fileHandler.setFormatter(new SimpleFormatter());
        logger.addHandler(fileHandler);
        String message = LocalDateTime.now() + ": " + infoMessage + ": " + sendObject;
        logger.info(message);
        fileHandler.close();

    }

    @GetMapping("/getLogs")
    private String readLog() {
        try {
            // Читаем файл app.log из корневой директории
            String content = new String(Files.readAllBytes(Paths.get("app.log")));
            return content; // Возвращаем всё содержимое как строку
        } catch (IOException e) {
            // Обработка ошибки, если файл не найден или ошибка чтения
            return "Ошибка при чтении файла app.log: " + e.getMessage();
        }

    }

}
