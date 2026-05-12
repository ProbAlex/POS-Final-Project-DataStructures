import java.util.Scanner;
import java.util.List;

public class Main {
    private static POSSystem pos;
    private static Scanner scan;

    public static void main(String[] args) {
        pos = new POSSystem();
        scan = new Scanner(System.in);

        System.out.println("==========================================================");
        System.out.println("|     BILINGUAL POS SYSTEM 双语收银系统                     |");
        System.out.println("|     Chinese Restaurant 中餐厅                            |");
        System.out.println("==========================================================");

        boolean running = true;
        while (running) {
            running = displayMainMenu();
        }

        System.out.println("\nThank you for using the POS System. Goodbye! 再见！");
        scan.close();
    }

    private static boolean displayMainMenu() {
        System.out.println("\n=== MAIN MENU 主菜单 ===");
        System.out.println("[1] Cashier Mode 收银模式");
        System.out.println("[2] Kitchen Mode 厨房模式");
        System.out.println("[3] Manager Mode 经理模式");
        System.out.println("[0] Exit 退出");
        System.out.print("\nSelect option: ");

        String choice = scan.nextLine().trim();
        switch (choice) {
            case "1": handleCashierMode(); break;
            case "2": handleKitchenMode(); break;
            case "3": handleManagerMode(); break;
            case "0": return false;
            default: System.out.println("Invalid option. Please try again.");
        }
        return true;
    }

    private static void handleCashierMode() {
        boolean inCashierMode = true;
        while (inCashierMode) {
            System.out.println("\n=== CASHIER MODE 收银模式 ===");
            System.out.println("[1] Start New Order 开始新订单");
            System.out.println("[2] View Menu 查看菜单");
            System.out.println("[3] View Current Order 查看当前订单");
            System.out.println("[4] Add Item to Order 添加菜品");
            System.out.println("[5] Undo Last Item 撤销上一项");
            System.out.println("[6] Submit Order 提交订单");
            System.out.println("[7] Cancel Order 取消订单");
            System.out.println("[8] Search Menu 搜索菜单");
            System.out.println("[0] Back to Main Menu 返回主菜单");
            System.out.print("\nSelect option: ");

            String choice = scan.nextLine().trim();
            switch (choice) {
                case "1": startNewOrder(); break;
                case "2": System.out.println(pos.getMenu().displayFullMenu()); break;
                case "3": viewCurrentOrder(); break;
                case "4": addItemToOrder(); break;
                case "5": undoLastItem(); break;
                case "6": submitOrder(); break;
                case "7": cancelOrder(); break;
                case "8": searchMenu(); break;
                case "0": inCashierMode = false; break;
                default: System.out.println("Invalid option.");
            }
        }
    }

    private static void startNewOrder() {
        if (pos.hasCurrentOrder()) {
            System.out.println("⚠ There's already an active order. Submit or cancel it first.");
            return;
        }
        System.out.print("Enter table number: ");
        try {
            int tableNum = scan.nextInt();
            pos.startNewOrder(tableNum);
            System.out.println("✓ New order started for Table " + tableNum);
        } catch (NumberFormatException e) {
            System.out.println("Invalid table number.");
        }
    }

    private static void viewCurrentOrder() {
        if (!pos.hasCurrentOrder()) {
            System.out.println("No active order. Start a new order first.");
            return;
        }
        System.out.println(pos.getCurrentOrder().toCashierDisplay());
    }

    private static void addItemToOrder() {
        if (!pos.hasCurrentOrder()) {
            System.out.println("No active order. Start a new order first.");
            return;
        }
        System.out.println(pos.getMenu().displayNumberedMenu());
        System.out.print("Enter item number (or 0 to cancel): ");
        try {
            int itemNum = scan.nextInt();
            if (itemNum == 0) return;

            MenuItem item = pos.getMenu().getItemByIndex(itemNum);
            if (item != null) {
                pos.addToOrder(item);
                System.out.println("✓ Added: " + item.getEnglishName() + " / " + item.getMandarinName());
            } else {
                System.out.println("Invalid item number.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
        }
    }

    private static void undoLastItem() {
        if (!pos.hasCurrentOrder()) {
            System.out.println("No active order.");
            return;
        }
        MenuItem removed = pos.undoLastItem();
        if (removed != null) {
            System.out.println("✓ Removed: " + removed.getEnglishName());
        } else {
            System.out.println("Order is empty, nothing to undo.");
        }
    }

    private static void submitOrder() {
        if (!pos.hasCurrentOrder()) {
            System.out.println("No active order to submit.");
            return;
        }
        if (pos.getCurrentOrder().isEmpty()) {
            System.out.println("Cannot submit empty order. Add items first.");
            return;
        }

        System.out.println("\n--- Order Summary ---");
        System.out.println(pos.getCurrentOrder().toCashierDisplay());
        System.out.print("Confirm submission? (y/n): ");

        if (scan.nextLine().trim().equalsIgnoreCase("y")) {
            pos.submitOrder();
            System.out.println("✓ Order submitted to kitchen!");
        } else {
            System.out.println("Submission cancelled.");
        }
    }

    private static void cancelOrder() {
        if (!pos.hasCurrentOrder()) {
            System.out.println("No active order to cancel.");
            return;
        }
        System.out.print("Are you sure you want to cancel this order? (y/n): ");
        if (scan.nextLine().trim().equalsIgnoreCase("y")) {
            pos.cancelCurrentOrder();
            System.out.println("✓ Order cancelled.");
        }
    }

    private static void searchMenu() {
        System.out.print("Enter search term (English): ");
        String term = scan.nextLine().trim();
        List<MenuItem> results = pos.getMenu().searchByEnglish(term);

        if (results.isEmpty()) {
            System.out.println("No items found matching '" + term + "'");
        } else {
            System.out.println("\n--- Search Results ---");
            for (MenuItem item : results) {
                System.out.println("  • " + item.toString());
            }
        }
    }

    private static void handleKitchenMode() {
        boolean inKitchenMode = true;
        while (inKitchenMode) {
            System.out.println("\n=== KITCHEN MODE 厨房模式 ===");
            System.out.println("[1] View All Pending Orders 查看所有待处理订单");
            System.out.println("[2] View Next Order 查看下一个订单");
            System.out.println("[3] Complete Order 完成订单");
            System.out.println("[4] Queue Status 队列状态");
            System.out.println("[0] Back to Main Menu 返回主菜单");
            System.out.print("\nSelect option: ");

            String choice = scan.nextLine().trim();
            switch (choice) {
                case "1":
                    System.out.println(pos.getKitchenQueue().displayKitchenView());
                    break;
                case "2":
                    viewNextOrder();
                    break;
                case "3":
                    completeOrder();
                    break;
                case "4":
                    System.out.println(pos.getKitchenQueue().displayStatus());
                    break;
                case "0":
                    inKitchenMode = false;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private static void viewNextOrder() {
        Order next = pos.getKitchenQueue().peek();
        if (next == null) {
            System.out.println("✓ 没有订单 No pending orders");
        } else {
            System.out.println("\n--- NEXT ORDER 下一个订单 ---");
            System.out.println(next.toKitchenDisplay());

            System.out.println("Sorted by prep time (fastest first):");
            List<MenuItem> sorted = pos.sortByPrepTime(next.getItems());
            for (MenuItem item : sorted) {
                System.out.println("  → " + item.toKitchenDisplay());
            }
        }
    }

    private static void completeOrder() {
        if (pos.getKitchenQueue().isEmpty()) {
            System.out.println("No orders to complete.");
            return;
        }

        Order next = pos.getKitchenQueue().peek();
        System.out.println("Complete this order?");
        System.out.println(next.toKitchenDisplay());
        System.out.print("Mark as complete? (y/n): ");

        if (scan.nextLine().trim().equalsIgnoreCase("y")) {
            Order completed = pos.completeKitchenOrder();
            System.out.println("✓ 完成 Order #" + completed.getOrderId() + " completed!");
        }
    }

    private static void handleManagerMode() {
        boolean inManagerMode = true;
        while (inManagerMode) {
            System.out.println("\n=== MANAGER MODE 经理模式 ===");
            System.out.println("[1] View Transaction History 查看交易历史");
            System.out.println("[2] View Revenue Summary 查看收入汇总");
            System.out.println("[3] View Recent Transactions 查看最近交易");
            System.out.println("[4] View Full Menu 查看完整菜单");
            System.out.println("[5] View Kitchen Queue Status 查看厨房队列状态");
            System.out.println("[0] Back to Main Menu 返回主菜单");
            System.out.print("\nSelect option: ");

            String choice = scan.nextLine().trim();
            switch (choice) {
                case "1":
                    System.out.println(pos.getTransactionHistory().displayAll());
                    break;
                case "2":
                    System.out.println(pos.getTransactionHistory().displaySummary());
                    break;
                case "3":
                    viewRecentTransactions();
                    break;
                case "4":
                    System.out.println(pos.getMenu().displayFullMenu());
                    break;
                case "5":
                    System.out.println(pos.getKitchenQueue().displayStatus());
                    break;
                case "0":
                    inManagerMode = false;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private static void viewRecentTransactions() {
        System.out.print("How many recent transactions to view? ");
        try {
            int count = scan.nextInt();
            List<Order> recent = pos.getTransactionHistory().getRecentTransactions(count);

            if (recent.isEmpty()) {
                System.out.println("No transactions found.");
            } else {
                System.out.println("\n--- Recent " + recent.size() + " Transactions ---");
                for (Order order : recent) {
                    System.out.println(order.toCashierDisplay());
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
        }
    }
}