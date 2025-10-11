package school.xset.homework3;

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
