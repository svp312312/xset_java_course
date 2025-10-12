package school.xset.homework3;

// 2. Интерфейс для коробки сладостей
public interface SweetBox {
    void addSweet(Sweet sweet);           // Добавить сладость
    void removeSweetByIndex(int index);   // Удалить сладость по индексу
    void removeLastSweet();               // Удалить последнюю сладость
    double getTotalWeight();              // Получить общий вес коробки
    double getTotalPrice();               // Получить общую стоимость коробки
    void printAllSweets();                // Вывести информацию о всех сладостях

    // 4. Методы оптимизации: удаление по весу и цене до заданного предела
    void optimizeByWeight(double maxWeight);  // Удалять минимальный вес, пока общий вес > maxWeight
    void optimizeByPrice(double maxPrice);    // Удалять минимальную цену, пока общий вес > maxWeight
}
