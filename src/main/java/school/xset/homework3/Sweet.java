package school.xset.homework3;

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
