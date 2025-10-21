//Задание:
//        На входе дан файл file_l6.txt, содержащий текст (слова, числа, знаки препинания).
//        Необходимо:
//        Считать все слова из файла, при этом не учитывать регистр (то есть считать слова «Дом» и «дом» одинаковыми).
//        Удалить из слов все знаки препинания.
//        Отсортировать полученные слова в порядке убывания длины, а при равной длине — в алфавитном порядке.
//        Вывести на экран отсортированный список слов.
//        Подсчитать, сколько раз встречается каждое слово, и вывести статистику в формате:
//        слово — количество_повторений
//        Найти самое длинное слово в файле и вывести его на экран (если таких несколько — вывести все).
//        Замечания:
//        Разделителями слов считаются пробелы, табуляции, переносы строк, а также любые знаки пунктуации.
//        Программа должна корректно работать даже при пустом файле или при отсутствии слов.

package school.xset.homework6;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lesson6HW {
    public static void main(String[] args) {
        String filePath = "file_l6.txt";
//        String filePath = "text";

        try {
            // Читаем файл и записываем всё в одну строку
            String content = new String(Files.readAllBytes(Paths.get(filePath)));
            // Ищем слова, используя паттерн - конструктор рег. выражений (кириллица, латиница, \b - граница слова)
            Pattern wordPattern = Pattern.compile("\\b[а-яА-Яa-zA-Z]+\\b", Pattern.UNICODE_CHARACTER_CLASS);
            //Ищем соответсвия шаблону
            Matcher matcher = wordPattern.matcher(content);
            // Создаем список для хранения слов
            List<String> words = new ArrayList<>();

            // В цикле находим совпадения, приводим к нижнему регистру и фильтруем от небуквенных символов
            while (matcher.find()) {

                // Находим все совпадения, приводим к нижнему регистру
                String word = matcher.group().toLowerCase();
                // Удаляем все знаки препинания из слова (используем replaceAll для удаления)
                word = word.replaceAll("[^а-яa-z]", "");
                // Включаем слова в коллекцию words
                if (!word.isEmpty()) {
                    words.add(word);
                }
            }

            // Если коллекция не пуста, выполняем последующие действия, иначе печатаем, что слова не найдены
            if (!words.isEmpty()) {

                // Сортируем сначала по длине (по убыванию), затем вторичная сортировка по алфавиту
                words.sort(Comparator.comparingInt(String::length).reversed().thenComparing(Comparator.naturalOrder()));

                // Выводим список слов
                System.out.println("Отсортированный список слов:\n\n" + String.join("\n", words) );

                // Подсчитываем частоту слов в Map
                Map<String, Integer> wordCount = new HashMap<>();
                for (String word : words) {
                    wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
                }

                // Убираем повторяющиеся слова, используя Set
                Set<String> set = new HashSet<>(words);
                List<String> uniqueWords = new ArrayList<>(set);

                // Сортируем список уникальных слов
                uniqueWords.sort(Comparator.comparingInt(String::length).reversed().thenComparing(Comparator.naturalOrder()));

                // Вывод статистики на экран
                System.out.println("\nВывод статистики");
                System.out.println("cлово\t / количество_повторений");
                for (String word : uniqueWords) {
                    System.out.printf("\n%-16s - %-10d", word, wordCount.get(word));
//                    System.out.println(word + " — " + wordCount.get(word));
                }

                // Находим самое длинное слово (или слова)
                int maxLength = words.get(0).length();
                List<String> longestWords = new ArrayList<>();
                for (String word : words) {
                    if (word.length() == maxLength) {
                        // Включаем слово в лист
                        longestWords.add(word);
                    } else {
                        break;// Поскольку список отсортирован, дальше не проверяем
                    }
                }

                // Выводим список самых длинных слов
                System.out.println("\n\nСамое длинное слово/слова:\n" + String.join("\n", longestWords));

            } else {
                System.out.println("Слова не найдены.");
            }

        } catch (IOException e) {
            System.err.println("Ошибка чтения файла: " + e.getMessage());
        }

    }
}
