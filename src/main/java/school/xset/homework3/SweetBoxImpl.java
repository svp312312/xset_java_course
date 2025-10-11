package school.xset.homework3;

import java.util.ArrayList;

//Конкретная реализация интерфейса коробки (с ArrayList внутри, не доступным снаружи)
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
        System.out.println("Все сладости в коробке:");
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
    public void optimizeByPrice(double maxWeight) {
        while (getTotalWeight() > maxWeight && !sweets.isEmpty()) {
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