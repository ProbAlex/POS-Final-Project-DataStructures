import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TransactionHistory {

    private final LinkedList<Order> transactions;

    public TransactionHistory() {
        this.transactions = new LinkedList<>();
    }

    public void addTransaction(Order order) {
        transactions.addLast(order);
    }

    public List<Order> getAllTransactions() {
        return new ArrayList<>(transactions);
    }

    public List<Order> getRecentTransactions(int count) {
        int start = Math.max(0, transactions.size() - count);
        return new ArrayList<>(transactions.subList(start, transactions.size()));
    }

    public double getTotalRevenue() {
        double total = 0;
        for (Order order : transactions) {
            total += order.getTotal();
        }
        return total;
    }

    public boolean isEmpty() {
        return transactions.isEmpty();
    }

    public String displaySummary() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== Transaction History Summary ===\n");
        sb.append("-".repeat(40)).append("\n");
        sb.append(String.format("Total Transactions: %d\n", transactions.size()));
        sb.append(String.format("Total Revenue: $%.2f\n", getTotalRevenue()));
        sb.append("-".repeat(40)).append("\n");
        return sb.toString();
    }


    public String displayAll() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== All Transactions ===\n");
        sb.append("=".repeat(50)).append("\n\n");

        for (Order order : transactions) {
            sb.append(order.toCashierDisplay());
            sb.append("\n");
        }

        if (transactions.isEmpty()) {
            sb.append("  (No transactions yet)\n");
        }

        sb.append(displaySummary());
        return sb.toString();
    }
}
