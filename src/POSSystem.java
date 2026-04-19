import java.util.ArrayList;
import java.util.List;

public class POSSystem {
    private Menu menu;
    private OrderQueue kitchenQueue;
    private TransactionHistory transactionHistory;
    private Order currentOrder;

    public POSSystem() {
        this.menu = new Menu();
        this.kitchenQueue = new OrderQueue();
        this.transactionHistory = new TransactionHistory();
        this.currentOrder = null;
        
        initializeMenu();
    }
    
    private void initializeMenu() {
        menu.addItem(new MenuItem("Spring Rolls", "春卷", 6.99, "Appetizers", 8));
        menu.addItem(new MenuItem("Dumplings", "饺子", 8.99, "Appetizers", 12));
        menu.addItem(new MenuItem("Wonton Soup", "馄饨汤", 5.99, "Appetizers", 6));
        menu.addItem(new MenuItem("Egg Drop Soup", "蛋花汤", 4.99, "Appetizers", 5));
        menu.addItem(new MenuItem("Hot and Sour Soup", "酸辣汤", 5.99, "Appetizers", 7));
        menu.addItem(new MenuItem("Crab Rangoon", "蟹角", 7.99, "Appetizers", 10));

        menu.addItem(new MenuItem("Kung Pao Chicken", "宫保鸡丁", 14.99, "Entrees", 15));
        menu.addItem(new MenuItem("Sweet and Sour Pork", "糖醋里脊", 13.99, "Entrees", 18));
        menu.addItem(new MenuItem("Beef with Broccoli", "西兰花牛肉", 15.99, "Entrees", 14));
        menu.addItem(new MenuItem("Mapo Tofu", "麻婆豆腐", 12.99, "Entrees", 12));
        menu.addItem(new MenuItem("General Tso Chicken", "左宗棠鸡", 14.99, "Entrees", 16));
        menu.addItem(new MenuItem("Orange Chicken", "陈皮鸡", 14.99, "Entrees", 15));
        menu.addItem(new MenuItem("Mongolian Beef", "蒙古牛肉", 16.99, "Entrees", 14));
        menu.addItem(new MenuItem("Fried Rice", "炒饭", 10.99, "Entrees", 10));
        menu.addItem(new MenuItem("Lo Mein", "捞面", 11.99, "Entrees", 12));
        menu.addItem(new MenuItem("Chow Mein", "炒面", 11.99, "Entrees", 11));

        menu.addItem(new MenuItem("Fortune Cookies", "幸运饼干", 2.99, "Desserts", 1));
        menu.addItem(new MenuItem("Mango Pudding", "芒果布丁", 5.99, "Desserts", 3));
        menu.addItem(new MenuItem("Red Bean Soup", "红豆汤", 4.99, "Desserts", 5));
        menu.addItem(new MenuItem("Sesame Balls", "芝麻球", 5.99, "Desserts", 8));

        menu.addItem(new MenuItem("Green Tea", "绿茶", 2.99, "Drinks", 2));
        menu.addItem(new MenuItem("Jasmine Tea", "茉莉花茶", 2.99, "Drinks", 2));
        menu.addItem(new MenuItem("Bubble Tea", "珍珠奶茶", 5.99, "Drinks", 4));
        menu.addItem(new MenuItem("Plum Juice", "酸梅汤", 3.99, "Drinks", 1));
    }

    public void startNewOrder(int tableNumber) {
        this.currentOrder = new Order(tableNumber);
    }

    public boolean hasCurrentOrder() {
        return currentOrder != null;
    }

    public Order getCurrentOrder() {
        return currentOrder;
    }

    public boolean addToOrder(MenuItem item) {
        if (currentOrder == null) return false;
        currentOrder.addItem(item);
        return true;
    }

    public MenuItem undoLastItem() {
        if (currentOrder == null) return null;
        return currentOrder.undoLastItem();
    }

    public void cancelCurrentOrder() {
        currentOrder = null;
    }

    public boolean submitOrder() {
        if (currentOrder == null || currentOrder.isEmpty()) return false;

        List<MenuItem> sortedItems = sortByPrepTime(currentOrder.getItems());

        Order sortedOrder = new Order(currentOrder.getTableNumber());
        for (MenuItem item : sortedItems) {
            sortedOrder.addItem(item);
        }

        kitchenQueue.enqueue(currentOrder);
        transactionHistory.addTransaction(currentOrder);
        currentOrder = null;
        return true;
    }
    
    public List<MenuItem> sortByPrepTime(List<MenuItem> items) {
        List<MenuItem> sorted = new ArrayList<>(items);
        int n = sorted.size();

        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;
            for (int j = 0; j < n - 1 - i; j++) {
                if (sorted.get(j).getPrepTimeMinutes() > sorted.get(j + 1).getPrepTimeMinutes()) {
                    MenuItem temp = sorted.get(j);
                    sorted.set(j, sorted.get(j + 1));
                    sorted.set(j + 1, temp);
                    swapped = true;
                }
            }
            if (!swapped) break;
        }

        return sorted;
    }
    
    public Menu getMenu() { return menu; }
    public OrderQueue getKitchenQueue() { return kitchenQueue; }
    public TransactionHistory getTransactionHistory() { return transactionHistory; }
    
    public Order completeKitchenOrder() {
        return kitchenQueue.dequeue();
    }
}
