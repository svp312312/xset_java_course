package school.xset.homework5;

import java.util.List;
import static school.xset.homework5.Lesson5HW.INITIAL_SIZE;
import static school.xset.homework5.Lesson5HW.ADD_COUNT;


public class MyList {

    private static long startTime;

    public static List<Integer> createList(List<Integer> list) {
        for (int i = 0; i < INITIAL_SIZE; i++) {
            list.add(i);
        }
        System.out.println("Элементов в коллекции: " + list.size());
        return list;
    }

    public static long addList(List<Integer> list, String listType) {
        // Добавление 500к элементов в середину
        startTime = System.currentTimeMillis();
        for (int i = 0; i < ADD_COUNT; i++) {
            int midIndex = list.size() / 2;
            list.add(midIndex, INITIAL_SIZE + i);  // Вставляем новый элемент в середину
        }
        long addTime = System.currentTimeMillis() - startTime;
        System.out.println("Добавление 500к элементов: " + addTime + " мс");
        System.out.println("Элементов в коллекции после добавления: " + list.size());
        return addTime;
    }

    public static long getList(List<Integer> list, String listType) {
        // Получение элемента из середины
        int midIndex = list.size() / 2;
        startTime = System.currentTimeMillis();
        int element = list.get(midIndex);
        long getTime = System.currentTimeMillis() - startTime;
        System.out.println("Получение элемента из середины: " + getTime + " мс");
        return getTime;
    }

    public static long removeList(List<Integer> list, String listType) {
        // Удаление элемента из середины
        int midIndex = list.size() / 2;
        startTime = System.currentTimeMillis();
        list.remove(midIndex);
        long removeTime = System.currentTimeMillis() - startTime;
        System.out.println("Удаление элемента из середины: " + removeTime + " мс");
        System.out.println("Элементов в коллекции после удаления: " + list.size());
        return removeTime;
    }

}
