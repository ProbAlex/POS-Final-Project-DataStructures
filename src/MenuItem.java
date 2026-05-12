public class MenuItem implements Comparable<MenuItem> {
    private String englishName;
    private String mandarinName;
    private double price;
    private String category;
    private int prepTimeMinutes;

    public MenuItem(String englishName, String mandarinName, double price,
                    String category, int prepTimeMinutes) {
        this.englishName = englishName;
        this.mandarinName = mandarinName;
        this.price = price;
        this.category = category;
        this.prepTimeMinutes = prepTimeMinutes;
    }

    public String getEnglishName() { return englishName; }
    public String getMandarinName() { return mandarinName; }
    public double getPrice() { return price; }
    public String getCategory() { return category; }
    public int getPrepTimeMinutes() { return prepTimeMinutes; }

    public int compareTo(MenuItem other) {
        return this.englishName.compareToIgnoreCase(other.englishName);
    }

    public String toEnglishDisplay() {
        return String.format("%-25s $%.2f (%d min)", englishName, price, prepTimeMinutes);
    }

    public String toKitchenDisplay() {
        return String.format("%s / %s (%d min)", mandarinName, englishName, prepTimeMinutes);
    }

    public String toString() {
        return String.format("%s | %s | $%.2f | %s | %d min",
                englishName, mandarinName, price, category, prepTimeMinutes);
    }
}
