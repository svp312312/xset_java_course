//Необходимо выполнить сравнительный анализ двух коллекций - ArrayList и LinkedList
//        (исходный размер 1 млн элементов) по следующим критериям:
//        1. Добавление 500к элементов в середину списка
//        2. Получение элемента из середины списка
//        3. Удаление элемента из середины списка
//        Сравнительный анализ - сравнение времени выполнения той или иной операции
//        Необходимо прислать ссылку на git с готовым решением

package school.xset.homework5;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Lesson5HW {
        public static final int INITIAL_SIZE = 1_000_000;
        public static final int ADD_COUNT = 500_000;

//        public static final int INITIAL_SIZE = 100000;
//        public static final int ADD_COUNT = 50000;

        public static void main(String[] args) {
            // Тестируем ArrayList
            System.out.println("ArrayList:");
            List<Integer> arrayList = MyList.createList(new ArrayList<>());
            long timeAddArrayList = MyList.addList(arrayList, "ArrayList");
            long timeGetArrayList = MyList.getList(arrayList, "ArrayList");
            long timeRemoveArrayList = MyList.removeList(arrayList, "ArrayList");

            // Тестируем LinkedList
            System.out.println("\nLinkedList:");
            List<Integer> linkedList = MyList.createList(new LinkedList<>());
            long timeAddLinkedList = MyList.addList(linkedList, "LinkedList");
            long timeGetLinkedList = MyList.getList(linkedList, "LinkedList");
            long timeRemoveLinkedList = MyList.removeList(linkedList, "LinkedList");

            // Сравниваем результаты
            System.out.println("\nСравниваем производительность ArrayList и LinkedList по заданным критериям.");

            if (timeAddArrayList < timeAddLinkedList && timeAddArrayList != 0) {
                System.out.println("При добавлении элементов в коллекцию ArrayList отработал быстрее, чем LinkedList ~ в " + (timeAddLinkedList / timeAddArrayList) + " раз(а), или на " + (timeAddLinkedList - timeAddArrayList) + " мс");
            } else if (timeAddArrayList > timeAddLinkedList) {
                System.out.println("При добавлении элементов в коллекцию LinkedList отработал быстрее, чем ArrayList ~ в " + (timeAddArrayList / timeAddLinkedList) + " раз(а), или на " + (timeAddLinkedList - timeAddArrayList) + " мс");
            } else {
                System.out.println("При добавлении элементов время одинаковое");
            }

            if (timeGetArrayList < timeGetLinkedList) {
                System.out.println("При получении элемента из середины коллекции ArrayList отработал быстрее, чем LinkedList на " + (timeGetLinkedList - timeGetArrayList) + " мс");
            } else if (timeGetArrayList > timeGetLinkedList) {
                System.out.println("При получении элемента из середины коллекции LinkedList отработал быстрее, чем ArrayList на " + (timeGetArrayList - timeGetLinkedList) + " мс");
            } else {
                System.out.println("При получении элемента время одинаковое");
            }

            if (timeRemoveArrayList < timeRemoveLinkedList) {
                System.out.println("При удалении элемента из середины коллекции ArrayList отработал быстрее, чем LinkedList на " + (timeRemoveLinkedList - timeRemoveArrayList) + " мс");
            } else if (timeRemoveArrayList > timeRemoveLinkedList) {
                System.out.println("При удалении элемента из середины коллекции LinkedList отработал быстрее, чем ArrayList на " + (timeRemoveArrayList - timeRemoveLinkedList) + " мс");
            } else {
                System.out.println("При удалении элемента время одинаковое ");
            }

        }

}
