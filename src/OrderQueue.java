import java.util.LinkedList;
import java.util.Queue;
import java.util.List;
import java.util.ArrayList;

public class OrderQueue {
    private Queue<Order> pendingOrders;

    public OrderQueue() {
        this.pendingOrders = new LinkedList<>();
    }

    public void enqueue(Order order) {
        pendingOrders.offer(order);
    }

    public Order dequeue() {
        return pendingOrders.poll();
    }

    public Order peek() {
        return pendingOrders.peek();
    }

    public List<Order> getAllPendingOrders() {
        return new ArrayList<>(pendingOrders);
    }

    public int getQueueSize() {
        return pendingOrders.size();
    }

    public boolean isEmpty() {
        return pendingOrders.isEmpty();
    }

    public String displayKitchenView() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append("=======================================================");
        sb.append("|           厨房显示 KITCHEN DISPLAY                   |\n");
        sb.append("=======================================================\n");
        sb.append(String.format("|  等待订单 Pending Orders: %-3d                        |\n",
                pendingOrders.size()));
        sb.append("=======================================================\n\n");

        if (pendingOrders.isEmpty()) {
            sb.append("  ✓ 没有订单 No pending orders\n");
        } else {
            int position = 1;
            for (Order order : pendingOrders) {
                sb.append(String.format("{%d} ", position++));
                sb.append(order.toKitchenDisplay());
                sb.append("\n");
            }
        }

        return sb.toString();
    }

    public String displayStatus() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Kitchen Queue: %d orders pending\n", pendingOrders.size()));

        if (!pendingOrders.isEmpty()) {
            sb.append("Next up: ").append(pendingOrders.peek().toString()).append("\n");
        }

        return sb.toString();
    }
}
