package school.xset.homework8;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import school.xset.homework7.models.*;


import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RestController
public class HomeWork8Controller {

    LoggerService loggerService = new LoggerService();

    // TODO: Вернуть случайную дату между двумя переданными (передавать JSON)
    // 1. Принять JSON с полями "startDate" и "endDate" (формат YYYY-MM-DD)
    // 2. Сгенерировать случайную дату в этом диапазоне
    // 3. Вернуть JSON с ключом "randomDate"
    // обработка входящего тела запроса через Substring -> отдаем хардкод с нужными данными
    // ------------------------------------------------------------------------------------
    // Ожидаемый json для запроса:
    //    {
    //        "startDate" : "1992-10-01",
    //        "endDate" : "2002-01-10"
    //    }
    //  Ответ возвращается в формате:
    //    {
    //        "randomDate": "1996-10-13"
    //    }

    @PostMapping("/random-date")
    public RandomDateResponse postRandomDate(@RequestBody String data) throws IOException {
//    public String postRandomDate(@RequestBody String data) {
        // Из json формируем строку, исключая пробелы (\s) и кавычки (\")
//            String str = data.trim().replaceAll("\\s", "").replaceAll("\"","");
        String str = data.trim().replaceAll("[\"\\s]", "");

        // Извлекаем позицию начала "startDate:"
        int startIndex = str.indexOf("startDate:");
        // Извлекаем текст даты
        String startDate = str.substring(startIndex + 10, startIndex + 20); // "2015-01-01".length() = 10, но с учётом кавычек

        // Извлекаем позицию начала "endDate:"
        int endIndex = str.indexOf("endDate:");
        // Извлекаем текст даты
        String endDate = str.substring(endIndex + 8, endIndex + 18);

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
        RandomDateResponse result =new RandomDateResponse();
        result.setRandomDate(randomDate);
        loggerService.writeLog("Result for random-date:", result.toString());
        return result;

    }


    // TODO: Вернуть отсортированный массив, учитывая параметр isAsc (передавать JSON)
    // 1. Принять JSON с массивом "numbers" в Body и булевым флагом "isAsc" в параметре
    // 2. Отсортировать массив по возрастанию/убыванию в зависимости от isAsc
    // 3. Вернуть JSON с ключом "sortedNumbers"
    // ---------------------------------------------------------------------------------
    // Ожидаемый json для запроса:
    //    {
    //        "numbers" : [ 10, 9, 8, 7 ],
    //     }
    // Для Query Parameters ожидается ключ isAsc со значением true или false
    // Возвращается отсортированный массив в формате:
    // {
    //    "sortedNumbers": [
    //        7,
    //        8,
    //        ...
    //    ]
    // }

    @PostMapping("/sort-array")
    public NumbersResponse postSortArray(@RequestBody NumbersRequest numbersRequest, @RequestParam Boolean isAsc) throws IOException {

        List<Integer> numbers = numbersRequest.getNumbers();

//            if (numbers == null || numbers.isEmpty()) {
//                return new NumbersResponse(List.of()); // Вернём пустой список, если ничего нет
//            }

        List<Integer> sorted;
        if (isAsc) {
            sorted = numbers.stream().sorted().collect(Collectors.toList());
        } else {
            sorted = numbers.stream().sorted(Collections.reverseOrder()).collect(Collectors.toList());
        }
        NumbersResponse result = new NumbersResponse();
        result.setSortedNumbers(sorted);
        loggerService.writeLog("Result for sort-array: ", result.toString());
        return result;
    }

    // TODO: Вернуть частоту символов в переданной строке (отсортировано по убыванию)
    // 1. Принять JSON с ключом "text"
    // 2. Подсчитать количество вхождений каждого символа (игнорировать пробелы)
    // 3. Вернуть JSON с отсортированным списком символов и их частот
    // -------------------------------------------------------------------------
    // Ожидаемый json для запроса:
    //    {
    //        "text" : "Hello, World!"
    //    }
    //    Возвращается отсортированный список символов и их частот в формате:
    //    {
    //        "charCounts": {
    //          "l": 3,
    //          "o": 2,
    //          ...
    //          }
    //    }

    @PostMapping("/char-counts")

    public CharCountsResponse postCharCounts(@RequestBody CharCountsRequest text) throws IOException {

        String inputString = text.getText();

        Map<Character, Integer> charCounts = new HashMap<>();

        for (char c : inputString.toCharArray()) {
            if (c != ' ') {  // Игнорируем пробелы
                charCounts.put(c, charCounts.getOrDefault(c, 0) + 1);
            }
        }
        // Сортировка по частоте по убыванию, затем по символу

        Map<Character, Integer> sortedMap = charCounts.entrySet() // Получаем набор entries (пар) из charCounts. Entry содержит ключ (символ) и значение (частоту).
                .stream() //Преобразуем набор в поток (stream), чтобы выполнять цепочки операций, такие как сортировка
                .sorted(Map.Entry.<Character, Integer>comparingByValue().reversed()// Сортируем поток: сначала по значению (частоте) по убыванию,
                        .thenComparing(Map.Entry.comparingByKey())) // затем по ключу (символу) по возрастанию
                .collect(Collectors.toMap( // Собираем отсортированный поток обратно в Map
                        Map.Entry::getKey, // Функция для извлечения ключа (символа) из entry
                        Map.Entry::getValue, // Функция для извлечения значения (частоты) из entry
                        (e1, e2) -> e1, // Функция слияния, если ключи дублируются (у нас не должно быть дублей, т.о. просто берётся первый элемент e1)
                        LinkedHashMap::new// Тип мапы, в которую собираем - LinkedHashMap, она сохраняет порядок вставки (т.е. порядок после сортировки), в отличие от HashMap, которая не гарантирует порядка
                ));

        CharCountsResponse result = new CharCountsResponse();
        result.setCharCounts(sortedMap);
        loggerService.writeLog("Result for char-counts:", result.toString());
        return result;

    }

    // TODO: Реализовать метод sum
    // 1. Принять JSON с массивом "numbers"
    // 2. Вычислить сумму всех элементов массива
    // 3. Вернуть JSON с ключом "sum"
    // ---------------------------------------
    // Ожидаемый json для запроса:
    //    {
    //        "numbers" : [ 1, 2, 3, 4 ]
    //    }
    // Возвращается сумма в формате:
    //    {
    //        "sum": 10
    //    }

    @PostMapping("/sum")
    public SumResponse postSum(@RequestBody SumRequest numbers) throws IOException {
        List<Integer> arr = numbers.getNumbers();
        int sum = 0;
        for (Integer num : arr) {
            sum += num;
        }
        SumResponse result = new SumResponse();
        result.setSum(sum);
        loggerService.writeLog("Result for sum:", result.toString());
        return result;
    }

    // TODO: Реализовать метод sumif
    // 1. Принять JSON с массивами "numbers" и "conditions" (boolean)
    // 2. Просуммировать только те элементы "numbers", где "conditions" равно true
    // 3. Вернуть JSON с ключом "sum"
    // ---------------------------------------------------------------------------
    // Ожидаемый json для запроса:
    //    {
    //        "sumifNumbers" :
    //      [
    //        {"numbers" : 1, "conditions" : true},
    //        {"numbers" : 2, "conditions" : false},
    //        ...
    //      ]
    //    }
    // Возвращается сумма в формате:
    //    {
    //        "sum": 10
    //    }


    @PostMapping("/sumif")

    public SumifResponse sumif(@RequestBody SumifRequest numbers) throws IOException {

        SumifNumbers[] arr = numbers.getSumifNumbers();

        int sum = 0;

        for (SumifNumbers num : arr) {
            if(num.getConditions())//
                sum += num.getNumbers();
        }

        SumifResponse result = new SumifResponse();
        result.setSum(sum);
        loggerService.writeLog("Result for sumif:", result.toString());
        return result;

    }
    //Реализовать отдельный класс LogerService, в котором должны быть два метода:

    // Требования для writeLog(Stirng infoMessage, String sendObject)
    // 1. infoMessage - информативное сообщение (хардкод)
    // 2. sendObject - отправляемый ответ на запросы
    // 3. Метод writeLog должен писать в текстовый файл сообщение вида -  "LocalDateTime.now(): infoMessage: sendObject"
    // 4. Лог не должен удаляться при перезапуске программы

    // Требования для readLog()
    // 1. Данный метод вызывается по Get-request /getLogs
    // 2. Метод должен возвращать все логи, записанные в текстовом файле
}
