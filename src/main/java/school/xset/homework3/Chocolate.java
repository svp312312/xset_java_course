package school.xset.homework3;

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
