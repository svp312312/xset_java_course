package school.xset.homework4;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@RestController
public class Lesson4HWController {
    // TODO: Реализовать метод для вычисления дня недели по переданной дате
    //       - Метод должен принимать строку с датой в формате "yyyy-MM-dd".
    //       - Преобразовать строку в LocalDate и вернуть день недели (например, Понедельник, Вторник и т.д.)
    //       - Метод должен быть доступен по GET запросу на "/day-of-week"

    @GetMapping("/day-of-week")
    public String getDayOfWeek(@RequestParam String date) {
        try {
            // Парсим строку в LocalDate
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(date, formatter);

            // Получаем день недели
            DayOfWeek dayOfWeek = localDate.getDayOfWeek();

            // Переводим на русский
            String russianDay = switch (dayOfWeek) {
                case MONDAY -> "понедельник";
                case TUESDAY -> "вторник";
                case WEDNESDAY -> "среда";
                case THURSDAY -> "четверг";
                case FRIDAY -> "пятница";
                case SATURDAY -> "суббота";
                case SUNDAY -> "воскресенье";
            };

            return "День " + localDate + " - это " + russianDay;
        } catch (Exception e) {
            // Обработка ошибок, если дата некорректная
            return "Некорректная дата. Используйте формат yyyy-MM-dd.";
        }
    }

    // TODO: Реализовать метод для генерации случайного пароля переданной длины
    //       - Метод должен принимать параметр "length" (длина пароля).
    //       - Сгенерировать случайный пароль из букв (верхний и нижний регистр), цифр и специальных символов.
    //       - Вернуть строку с результатом в формате "Случайный пароль: [randomPassword]"
    //       - Метод должен быть доступен по GET запросу на "/generate-password"

    @GetMapping("/generate-password")
    public String generatePassword(String length){

        try {
            int numLength = Integer.parseInt(length);
            String symbols = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890#&$%^?!_";
            Random random = new Random();
            StringBuilder randomPassword = new StringBuilder();
            for (int i = 0; i < numLength; i++) {
                randomPassword.append(symbols.charAt(random.nextInt(symbols.length()-1)));
            }
            return "Случайный пароль: " + randomPassword;
        } catch (NumberFormatException e) {
            // Обработка ошибок, если длина пароля некорректная
            return "Некорректное значение. Должно быть отправлено целое число.";
        }

    }
    // TODO: Реализовать метод для вычисления факториала от числа
    //       - Метод должен принимать параметр "number" (целое число).
    //       - Рассчитать факториал числа по формуле: factorial(n) = n * (n-1) * (n-2) * ... * 1.
    //       - Вернуть строку с результатом в формате "Факториал числа [number]: [factorial]"
    //       - Метод должен быть доступен по GET запросу на "/factorial"

    @GetMapping("/factorial")
    public String getFactirial(String number){

        try {
            int n = Integer.parseInt(number);
            int factorial = 1;
            for (int i = n; i > 0; i--) {
                factorial *= i;
            }
            return "Факториал числа " + number + ": " + factorial;
        } catch (NumberFormatException e) {
            // Обработка ошибок, если длина пароля некорректная
            return "Некорректное значение. Должно быть отправлено целое число.";
        }

    }

    // TODO: Реализовать метод для возведения числа в степень
    //       - Метод должен принимать два параметра: число и степень.
    //       - Возвести число в указанную степень.
    //       - Вернуть строку с результатом в формате "[number] в степени [power]: [result]"
    //       - Метод должен быть доступен по GET запросу на "/power"

    // TODO: Реализовать метод для генерации случайной даты между двумя переданными
    //       - Метод должен принимать два параметра: startDate и endDate (в формате "yyyy-MM-dd").
    //       - Сгенерировать случайную дату в диапазоне между этими двумя датами.
    //       - Вернуть строку с результатом в формате "Случайная дата: [randomDate]"
    //       - Метод должен быть доступен по GET запросу на "/random-date"\

    // TODO: Реализовать метод для сортировки массива с учетом параметра isAsc
    //       - Метод должен принимать массив чисел и параметр "isAsc" (логическое значение).
    //       - Если isAsc = true, отсортировать массив по возрастанию, если false — по убыванию.
    //       - Вернуть строку с отсортированным массивом в формате "Отсортированный массив: [sortedArray]"
    //       - Метод должен быть доступен по GET запросу на "/sort-array"

    // TODO: Реализовать метод для разделения строки по позиции и отправки части строки
    //       - Метод должен принимать строку (str), позицию (position) и флаг (isFirst).
    //       - Если isFirst = true, вернуть первую часть строки до указанной позиции, иначе — вторую часть после позиции.
    //       - Вернуть строку в формате "Часть строки: [substring]"
    //       - Метод должен быть доступен по GET запросу на "/substring"

}
