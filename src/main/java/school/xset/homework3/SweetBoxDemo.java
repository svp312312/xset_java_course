//1. Cформировать коробку со сладостями. Она может включать в себя разные сладости, у каждой есть название, вес, цена и уникальный параметр
//        2. Найти общий вес подарка, общую стоимость подарка и вывести на консоль информацию о всех сладостях в коробке.
//        Сущности:
//        1. Базовый абстрактный класс для всех видов сладостей и конкретные сладости, наследующиеся от базового.
//        2. Интерфейс для коробки сладостей с методами: добавить, удалить (по индексу/либо последний) сладость, вывести вес, стоимость коробки, вывести всю информацию обо всех сладостях.
//        3. Конкретные реализации интерфейса коробки. Внутри коробка содержит ArrayList/массив сладостей и работает с ним. Вне коробки ArrayList/массив недоступен.
//        4. Два метода для умной оптимизации подарка, для удаления сладостей с меньшим весом и с меньшей ценой из коробки до тех пор, пока вес коробки не станет меньше входного параметра

package school.xset.homework3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

// Пользовательcкий интерфейс
public class SweetBoxDemo {
    public static void main(String[] args) throws IOException {

        Sweet.SweetBox box = new SweetBoxImpl();
        // Добавляем сладости в коробку
        boolean giftCollected = false;

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Приветствуем в нашем магазине 'ВКУСНЯШКА'!");
        System.out.println("Собери подарочную коробку со сладостями.");
        System.out.println("Чтобы добавить сладость в коробку, введи ее номер:");
        System.out.println("1 - " + "Конфеты 'Mонпасье'");
        System.out.println("2 - " + "Конфета 'Гулливер'");
        System.out.println("3 - " + "Печенье 'Чоко Пай'");
        System.out.println("4 - " + "Шоколадка 'Чёрный трюфель'");
        System.out.println("5 - " + "Конфеты 'Фруктовый микс'");
        System.out.println("6 - " + "Печенье 'Овсяное'");
        System.out.println("7 - " + "Шоколадка 'Алёнка'");
        for(int i = 0; i < 39; i++){
            System.out.print("=");
        }
        System.out.println("\n8 - удалить последний добавленный товар");
        System.out.println("9 - коробка собрана");

        while (!giftCollected) {

            String num = reader.readLine();

            switch (num) {
                case "1":
                    box.addSweet(new Candy("Конфеты 'Монпасье'", 150, 150, "Леденцы"));
                    System.out.println("Конфеты 'Монпасье' добавлены");
                    break;
                case "2":
                    box.addSweet(new Candy("Конфета 'Гулливер'", 78, 210, "Вафельная"));
                    System.out.println("Конфета 'Гулливер' добавлена");
                    break;
                case "3":
                    box.addSweet(new Cookie("Печенье 'Чоко-пай'", 50, 150, false));
                    System.out.println("Печенье 'Чоко Пай' добавлено");
                    break;
                case "4":
                    box.addSweet(new Chocolate("Шоколадка 'Чёрный трюфель'", 100, 50, 85));
                    System.out.println("Шоколадка 'Чёрный трюфель' добавлена");
                    break;
                case "5":
                    box.addSweet(new Candy("Конфеты 'Фруктовый микс'", 150, 30, "Леденцы"));
                    System.out.println("Конфеты 'Фруктовый микс' добавлены");
                    break;
                case "6":
                    box.addSweet(new Cookie("Печенье 'Овсяное'", 200, 40, true));
                    System.out.println("Печенье 'Овсяное' добавлено");
                    break;
                case "7":
                    box.addSweet(new Chocolate("Шоколадка 'Алёнка'", 120, 60, 30));
                    System.out.println("Шоколадка 'Алёнка' добавлена");
                    break;
                case "8":
                    box.removeLastSweet();
                    break;
                case "9":
                    giftCollected = true;
                    break;
                default:
                    System.out.println("Неправильно введен номер");
                    break;
            }
        }
        // Выводим информацию

        box.printAllSweets();
        System.out.println(String.format("Общий вес: %.2f г", box.getTotalWeight()));
        System.out.println(String.format("Общая цена: %.2f руб", box.getTotalPrice()));

        Boolean endAction = false;
        while (!endAction) {

            System.out.println("\nТы доволен подарком? Введи нужный номер:");
            System.out.println("1 - да, доволен");
            System.out.println("2 - нет, слишком большой вес");
            System.out.println("3 - нет, хочу удалить лишнюю сладость");

            String num = reader.readLine();

            switch (num) {
                case "1":
                    endAction = true;
                    break;
                case "2":
                    System.out.println("Умный помощник составит для тебя оптимальный подарок!;)");
                    System.out.println("Введи максимально возможный вес:");
                    String maxWeight = reader.readLine();
                    int numMaxWeight = Integer.parseInt(maxWeight);
                    boolean optimization = false;
                    while (!optimization){
                        System.out.println("Введи ключ оптимизации: 1 - по весу, 2 - по цене");
                        String key = reader.readLine();
                        if (key.equals("1")) {
                            // Оптимизация по весу
                            box.optimizeByWeight(numMaxWeight);
                            optimization = true;
                        } else if (key.equals("2")) {
                            // Оптимизация по цене
                            box.optimizeByPrice(numMaxWeight);
                            optimization = true;
                        } else {
                            System.out.println("Неверный ключ оптимизации, попробуй снова.");
                        }
                    }
                    box.printAllSweets();
                    System.out.println(String.format("Общий вес после оптимизации: %.2f г", box.getTotalWeight()));
                    System.out.println(String.format("Общая цена после оптимизации: %.2f руб", box.getTotalPrice()));
                    break;
                case "3":
                    System.out.println("Введи номер удаляемой сладости (см. содержимое коробки выше):");
                    String removeSweet = reader.readLine();
                    int numRemoveSweet = Integer.parseInt(removeSweet) - 1;
                    box.removeSweetByIndex(numRemoveSweet);
                    box.printAllSweets();
                    System.out.println(String.format("Общий вес после удаления сладости: %.2f г", box.getTotalWeight()));
                    System.out.println(String.format("Общая цена после удаления сладости: %.2f руб", box.getTotalPrice()));
                    break;
                default:
                    System.out.println("Неправильно введен номер");
                    break;
            }
        }
        System.out.println("\nПоздравляем: подарок собран!");
        System.out.println(".....................");
        System.out.println(".....@O@.....@O@.....");
        System.out.println("....@O::O@.@O::O@....");
        System.out.println("......@O::O::O@......");
        System.out.println(".@@@@@@@@@@@@@@@@@@@.");
        System.out.println(".@::::::@@@@@::::::@.");
        System.out.println(".@::::::@@@@@::::::@.");
        System.out.println(".@@@@@@@@@@@@@@@@@@@.");
        System.out.println("..@:::::@@@@@:::::@..");
        System.out.println("..@:::::@@@@@:::::@..");
        System.out.println("..@@@@@@@@@@@@@@@@@..");
        System.out.println(".....................");
        System.out.println("\nКурьер доставит тебе коробку со сладостями в течение часа. Оплата при получении.");
    }

}
