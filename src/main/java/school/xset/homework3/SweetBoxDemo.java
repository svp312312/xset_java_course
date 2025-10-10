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
import java.util.ArrayList;

// 1. Базовый абстрактный класс для сладостей
abstract class Sweet {
    private String name;    // Название сладости
    private double weight;  // Вес в граммах
    private double price;   // Цена в условных единицах

    public Sweet(String name, double weight, double price) {
        this.name = name;
        this.weight = weight;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getWeight() {
        return weight;
    }

    public double getPrice() {
        return price;
    }

    // Абстрактный метод для уникального параметра каждой сладости
    public abstract String getUniqueParameter();

    @Override
    public String toString() {
        return String.format("Название: %s, Вес: %.2f г, Цена: %.2f, %s",
                name, weight, price, getUniqueParameter());
    }

    // 2. Интерфейс для коробки сладостей
    static interface SweetBox {
        void addSweet(Sweet sweet);           // Добавить сладость
        void removeSweetByIndex(int index);   // Удалить сладость по индексу
        void removeLastSweet();               // Удалить последнюю сладость
        double getTotalWeight();              // Получить общий вес коробки
        double getTotalPrice();               // Получить общую стоимость коробки
        void printAllSweets();                // Вывести информацию о всех сладостях

        // 4. Методы оптимизации: удаление по весу и цене до заданного предела
        void optimizeByWeight(double maxWeight);  // Удалять минимальный вес, пока общий вес > maxWeight
        void optimizeByPrice(double maxPrice);    // Удалять минимальную цену, пока общая цена > maxPrice
    }
}

// Конкретные сладости, наследующиеся от базового класса
class Chocolate extends Sweet {
    private int cocoaPercentage;  // Процент какао (уникальный параметр)

    public Chocolate(String name, double weight, double price, int cocoaPercentage) {
        super(name, weight, price);
        this.cocoaPercentage = cocoaPercentage;
    }

    @Override
    public String getUniqueParameter() {
        return "Процент какао: " + cocoaPercentage + "%";
    }
}

class Candy extends Sweet {
    private String type;  // Вид конфеты (уникальный параметр)

    public Candy(String name, double weight, double price, String type) {
        super(name, weight, price);
        this.type = type;
    }

    @Override
    public String getUniqueParameter() {
        return "Вид конфеты: " + type;
    }
}

class Cookie extends Sweet {
    private boolean isGlutenFree;  // Наличие глютена (уникальный параметр)

    public Cookie(String name, double weight, double price, boolean isGlutenFree) {
        super(name, weight, price);
        this.isGlutenFree = isGlutenFree;
    }

    @Override
    public String getUniqueParameter() {
        return "Без глютена: " + (isGlutenFree ? "да" : "нет");
    }
}

// 3. Конкретная реализация интерфейса коробки (с ArrayList внутри, не доступным снаружи)
class SweetBoxImpl implements Sweet.SweetBox {
    private ArrayList<Sweet> sweets;  // Внутренний список сладостей

    public SweetBoxImpl() {
        this.sweets = new ArrayList<>();
    }

    @Override
    public void addSweet(Sweet sweet) {
        sweets.add(sweet);
    }

    @Override
    public void removeSweetByIndex(int index) {
        if (index >= 0 && index < sweets.size()) {
            sweets.remove(index);
        } else {
            System.out.println("Ошибка: индекс вне диапазона!");
        }
    }

    @Override
    public void removeLastSweet() {
        if (!sweets.isEmpty()) {
            sweets.remove(sweets.size() - 1);
            System.out.println("Сладость удалена");
        } else {
            System.out.println("Коробка пуста, нечего удалять.");
        }
    }

    @Override
    public double getTotalWeight() {
        double total = 0;
        for (Sweet s : sweets) {
            total += s.getWeight();
        }
        return total;
    }

    @Override
    public double getTotalPrice() {
        double total = 0;
        for (Sweet s : sweets) {
            total += s.getPrice();
        }
        return total;
    }

    @Override
    public void printAllSweets() {
        if (sweets.isEmpty()) {
            System.out.println("Коробка пуста.");
            return;
        }
        for (int i = 0; i < sweets.size(); i++) {
            System.out.println((i + 1) + ". " + sweets.get(i).toString());
        }
    }

    @Override
    public void optimizeByWeight(double maxWeight) {
        while (getTotalWeight() > maxWeight && !sweets.isEmpty()) {
            // Найти сладость с минимальным весом и удалить её
            Sweet minWeight = sweets.get(0);
            for (Sweet s : sweets) {
                if (s.getWeight() < minWeight.getWeight()) {
                    minWeight = s;
                }
            }
            sweets.remove(minWeight);
        }
    }

    @Override
    public void optimizeByPrice(double maxPrice) {
        while (getTotalPrice() > maxPrice && !sweets.isEmpty()) {
            // Найти сладость с минимальной ценой и удалить её
            Sweet minPrice = sweets.get(0);
            for (Sweet s : sweets) {
                if (s.getPrice() < minPrice.getPrice()) {
                    minPrice = s;
                }
            }
            sweets.remove(minPrice);
        }
    }
}

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
        System.out.println("Все сладости в коробке:");
        box.printAllSweets();
        System.out.println(String.format("Общий вес: %.2f г", box.getTotalWeight()));
        System.out.println(String.format("Общая цена: %.2f руб", box.getTotalPrice()));

        Boolean endAction = false;
        while (!endAction) {

            System.out.println("\nТы доволен подарком? Введи нужный номер:");
            System.out.println("1 - да, доволен");
            System.out.println("2 - нет, слишком большой вес");
            System.out.println("3 - нет, слишком высокая цена");
            System.out.println("4 - нет, хочу удалить лишнюю сладость");

            String num = reader.readLine();

            switch (num) {
                case "1":
                    endAction = true;
                    break;
                case "2":
                    System.out.println("Введи максимально возможный вес:");
                    String maxWeight = reader.readLine();
                    int numMaxWeight = Integer.parseInt(maxWeight);
                    // Оптимизация по весу
                    System.out.println("\nОптимизация по весу (макс " + numMaxWeight + "г):");
                    box.optimizeByWeight(numMaxWeight);
                    box.printAllSweets();
                    System.out.println(String.format("Общий вес после оптимизации: %.2f г", box.getTotalWeight()));
                    System.out.println(String.format("Общая цена: %.2f руб", box.getTotalPrice()));
                    break;
                case "3":
                    System.out.println("Введи максимально возможную цену:");
                    String maxPrice = reader.readLine();
                    int numMaxPrice = Integer.parseInt(maxPrice);
                    // Оптимизация по цене
                    System.out.println("\nОптимизация по цене (макс " + numMaxPrice + "):");
                    box.optimizeByPrice(numMaxPrice);
                    box.printAllSweets();
                    System.out.println(String.format("Общий вес: %.2f г", box.getTotalWeight()));
                    System.out.println(String.format("Общая цена после оптимизации: %.2f руб", box.getTotalPrice()));
                    break;
                case "4":
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
