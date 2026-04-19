import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Order {
    private int tableNumber;
    private Stack<MenuItem> itemStack;
    private LocalDateTime timestamp;
    private static int orderCounter = 0;
    private int orderId;

    public Order(int tableNumber) {
        this.tableNumber = tableNumber;
        this.itemStack = new Stack<>();
        this.timestamp = LocalDateTime.now();
        this.orderId = ++orderCounter;
    }

    public void addItem(MenuItem item) {
        itemStack.push(item);
    }

    public MenuItem undoLastItem() {
        if (itemStack.isEmpty()) {
            return null;
        }
        return itemStack.pop();
    }

    public List<MenuItem> getItems() {
        return new ArrayList<>(itemStack);
    }

    public double getTotal() {
        double total = 0;
        for (MenuItem item : itemStack) {
            total += item.getPrice();
        }
        return total;
    }

    public int getTotalPrepTime() {
        int total = 0;
        for (MenuItem item : itemStack) {
            total += item.getPrepTimeMinutes();
        }
        return total;
    }

    public boolean isEmpty() {
        return itemStack.isEmpty();
    }

    public int getItemCount() {
        return itemStack.size();
    }

    public int getTableNumber() { return tableNumber; }
    public int getOrderId() { return orderId; }
    public LocalDateTime getTimestamp() { return timestamp; }

    public String toCashierDisplay() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("=== Order #%d | Table %d ===\n", orderId, tableNumber));
        sb.append(String.format("Time: %s\n",
                timestamp.format(DateTimeFormatter.ofPattern("HH:mm:ss"))));
        sb.append("-".repeat(40)).append("\n");

        int index = 1;
        for (MenuItem item : itemStack) {
            sb.append(String.format("%d. %s\n", index++, item.toEnglishDisplay()));
        }

        sb.append("-".repeat(40)).append("\n");
        sb.append(String.format("Items: %d | Total: $%.2f\n", getItemCount(), getTotal()));
        return sb.toString();
    }

    public String toKitchenDisplay() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("=== 订单 Order #%d | 桌号 Table %d ===\n", orderId, tableNumber));
        sb.append(String.format("时间 Time: %s\n",
                timestamp.format(DateTimeFormatter.ofPattern("HH:mm:ss"))));
        sb.append("-".repeat(45)).append("\n");

        for (MenuItem item : itemStack) {
            sb.append("  • ").append(item.toKitchenDisplay()).append("\n");
        }

        sb.append("-".repeat(45)).append("\n");
        sb.append(String.format("总共 Total: %d items | ~%d min\n", getItemCount(), getTotalPrepTime()));
        return sb.toString();
    }

    public String toString() {
        return String.format("Order #%d | Table %d | %d items | $%.2f",
                orderId, tableNumber, getItemCount(), getTotal());
    }
}
