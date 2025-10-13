package school.xset.homework4;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Array;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

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

            // Переводим на русский, используем лямбда подобный синтаксис в switch
            String russianDay = switch (dayOfWeek) {
                case MONDAY -> "Понедельник";
                case TUESDAY -> "Вторник";
                case WEDNESDAY -> "Среда";
                case THURSDAY -> "Четверг";
                case FRIDAY -> "Пятница";
                case SATURDAY -> "Суббота";
                case SUNDAY -> "Воскресенье";
            };

            return russianDay;
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
    public String generatePassword(@RequestParam int length) {

        try {
            String symbols = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890#&$%^?!_";
            Random random = new Random();
            StringBuilder randomPassword = new StringBuilder();
            for (int i = 0; i < length; i++) {
                randomPassword.append(symbols.charAt(random.nextInt(symbols.length() - 1)));
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
    public String getFactirial(@RequestParam int number) {

        try {
            int factorial = 1;
            for (int i = number; i > 0; i--) {
                factorial *= i;
            }
            return "Факториал числа " + number + ": " + factorial;
        } catch (NumberFormatException e) {
            // Обработка ошибок, если передано неверное значение для аргумента
            return "Некорректное значение. Должно быть отправлено целое число.";
        }

    }

    // TODO: Реализовать метод для возведения числа в степень
    //       - Метод должен принимать два параметра: число и степень.
    //       - Возвести число в указанную степень.
    //       - Вернуть строку с результатом в формате "[number] в степени [power]: [result]"
    //       - Метод должен быть доступен по GET запросу на "/power"

    @GetMapping("/power")
    public String getPower(@RequestParam double number, @RequestParam double power) {
        try {
            double result = Math.pow(number, power);
            return number + " в степени " + power + ": " + result;
        } catch (NumberFormatException e) {
            // Обработка ошибок, если переданы неверные значения для аргументов
            return "Некорректное значение. Должны быть отправлены два числа.";
        }
    }
    // TODO: Реализовать метод для генерации случайной даты между двумя переданными
    //       - Метод должен принимать два параметра: startDate и endDate (в формате "yyyy-MM-dd").
    //       - Сгенерировать случайную дату в диапазоне между этими двумя датами.
    //       - Вернуть строку с результатом в формате "Случайная дата: [randomDate]"
    //       - Метод должен быть доступен по GET запросу на "/random-date"\

    @GetMapping("/random-date")
    public String getRandomDate(@RequestParam String startDate, @RequestParam String endDate) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            // Парсим начальную и конечную даты
            LocalDate localStartDate = LocalDate.parse(startDate, formatter);
            LocalDate localEndDate = LocalDate.parse(endDate, formatter);
            // Вычисляем количество дней между датами
            long daysBetween = ChronoUnit.DAYS.between(localStartDate, localEndDate);
            Random random = new Random();
            // Генерируем случайное число дней от 0 до daysBetween - 1
            long randomDays = random.nextLong(daysBetween);
            // Добавляем случайное число дней к начальной дате
            LocalDate randomDate = localStartDate.plusDays(randomDays);
            return "Случайная дата: " + randomDate;
        } catch (Exception e) {
            // Обработка ошибок, если даты некорректны
            return "Некорректные даты. Используйте формат yyyy-MM-dd.";
        }
    }
    // TODO: Реализовать метод для сортировки массива с учетом параметра isAsc
    //       - Метод должен принимать массив чисел и параметр "isAsc" (логическое значение).
    //       - Если isAsc = true, отсортировать массив по возрастанию, если false — по убыванию.
    //       - Вернуть строку с отсортированным массивом в формате "Отсортированный массив: [sortedArray]"
    //       - Метод должен быть доступен по GET запросу на "/sort-array"

    @GetMapping("/sort-array")
    public String getSortArray(@RequestParam("numbers") Integer[] numbers, @RequestParam("isAsc") boolean isAsc) {
        try {
            String response;
            Arrays.sort(numbers);
            if (isAsc) {
                response = Arrays.toString(numbers);
            } else {
                // Создание нового массива и расстановка элементов в обратном порядке
                int[] reversedArr = new int[numbers.length];
                for (int i = 0; i < numbers.length; i++) {
                    reversedArr[i] = numbers[numbers.length - 1 - i];
                }
                response = Arrays.toString(reversedArr);
            }
            return "Отсортированный массив: " + response;
        } catch (NumberFormatException e) {
            // Обработка ошибки, если числа некорректные
            return "Ошибка: введите корректные числа через запятую, укажите isAsc как true/false";
        }
    }
//    public String sortArray(@RequestParam String numbers, @RequestParam boolean isAsc) {
//        try {
//            // Парсим строку в список чисел
//            List<Integer> list = Arrays.stream(numbers.split(","))
//                    .map(String::trim)  // Убираем пробелы
//                    .map(Integer::valueOf)
//                    .collect(Collectors.toList());
//
//            // Сортируем: по возрастанию или убыванию
//            if (isAsc) {
//                Collections.sort(list);  // По возрастанию
//            } else {
//                list.sort(Collections.reverseOrder());  // По убыванию
//            }
//
//            // Формируем строку ответа
//            return "Отсортированный массив: " + list.toString();
//        } catch (NumberFormatException e) {
//            // Обработка ошибки, если числа некорректные
//            return "Ошибка: введите корректные числа через запятую";
//        }
//
//}
    // TODO: Реализовать метод для разделения строки по позиции и отправки части строки
    //       - Метод должен принимать строку (str), позицию (position) и флаг (isFirst).
    //       - Если isFirst = true, вернуть первую часть строки до указанной позиции, иначе — вторую часть после позиции.
    //       - Вернуть строку в формате "Часть строки: [substring]"
    //       - Метод должен быть доступен по GET запросу на "/substring"

    @GetMapping("/substring")
    public String getSubstring(@RequestParam String str, @RequestParam int position, @RequestParam boolean isFirst) {
        try {
        String result =  isFirst ? str.substring(0, position) : str.substring(position);
        return result;
        } catch (NumberFormatException e) {
            // Обработка ошибки, если значения некорректные
            return "Ошибка: введите корректные значения";
        }
    }
}

