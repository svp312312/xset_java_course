package school.xset.homework2;

// TODO: Создать класс Lesson2HWController с аннотацией @RestController для обработки HTTP запросов

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;

@RestController
public class Lesson2HWController {

    // TODO: Реализовать метод currentTime():
    //       - Используя LocalDateTime.now(), получить текущее время
    //       - Вернуть строку с текущим временем в формате "Текущее время: [currentTime]"
    //       - Метод должен быть доступен по GET запросу на "/current-datetime"

    @GetMapping("/current-datetime")
    public String currentTime(){
        int hour = LocalDateTime.now().getHour();
        int minute = LocalDateTime.now().getMinute();
        int second = LocalDateTime.now().getSecond();
//            return new StringBuilder().append("Текущее время: ").append(hour).append(":").append(minute).append(":").append(second).toString();
        return String.format("Текущее время: %02d:%02d:%02d", hour, minute, second);
    }

    // TODO: Реализовать метод currentSeason():
    //       - Получить текущий месяц через LocalDate.now().getMonthValue()
    //       - В зависимости от месяца вернуть сезон:
    //         - Зима (декабрь, январь, февраль)
    //         - Весна (март, апрель, май)
    //         - Лето (июнь, июль, август)
    //         - Осень (сентябрь, октябрь, ноябрь)
    //       - Метод должен быть доступен по GET запросу на "/current-season"
    @GetMapping("/current-season")
    public String currentSeason(){
        String season = "";
        int month = LocalDate.now().getMonthValue();
        switch (month) {
            case 1,2,12: season = "Зима";
                break;
            case 3,4,5: season = "Весна";
                break;
            case 6,7,8: season = "Лето";
                break;
            case 9,10,11: season = "Осень";
                break;
            default:
                break;
        }
        return season;
    }
    // TODO: Реализовать метод futureDate():
    //       - Получить текущую дату через LocalDate.now()
    //       - Сгенерировать случайное количество дней в пределах 30 (с использованием Random)
    //       - Добавить эти дни к текущей дате для получения будущей даты
    //       - Вернуть строку с будущей датой в формате "Случайная дата в будущем: [futureDate]"
    //       - Метод должен быть доступен по GET запросу на "/future-date"

    @GetMapping("/future-date")
    public String futureDate(){
        Random random = new Random();
        int number = random.nextInt(29) + 1;
        LocalDate date = LocalDate.now();
        String randomDate = "Случайная дата в будущем: " + date.plusDays(number);
        return randomDate;
    }
}


