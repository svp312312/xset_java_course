package school.xset.homework3;

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
