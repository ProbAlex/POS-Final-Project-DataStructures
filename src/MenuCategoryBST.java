import java.util.ArrayList;
import java.util.List;

public class MenuCategoryBST {

    private class BSTNode {
        MenuItem item;
        BSTNode left;
        BSTNode right;

        BSTNode(MenuItem item) {
            this.item = item;
            this.left = null;
            this.right = null;
        }
    }

    private BSTNode root;
    private String category;
    private int size;

    public MenuCategoryBST(String category) {
        this.category = category;
        this.root = null;
        this.size = 0;
    }

    public void insert(MenuItem item) {
        root = insertRecursive(root, item);
        size++;
    }

    private BSTNode insertRecursive(BSTNode node, MenuItem item) {
        if (node == null) {
            return new BSTNode(item);
        }

        int comparison = item.compareTo(node.item);
        if (comparison < 0) {
            node.left = insertRecursive(node.left, item);
        } else if (comparison > 0) {
            node.right = insertRecursive(node.right, item);
        }
        return node;
    }

    public MenuItem search(String englishName) {
        return searchRecursive(root, englishName);
    }

    private MenuItem searchRecursive(BSTNode node, String englishName) {
        if (node == null) {
            return null;
        }

        int comparison = englishName.compareToIgnoreCase(node.item.getEnglishName());
        if (comparison == 0) {
            return node.item;
        } else if (comparison < 0) {
            return searchRecursive(node.left, englishName);
        } else {
            return searchRecursive(node.right, englishName);
        }
    }

    public List<MenuItem> getAllItemsSorted() {
        List<MenuItem> items = new ArrayList<>();
        inOrderTraversal(root, items);
        return items;
    }

    private void inOrderTraversal(BSTNode node, List<MenuItem> items) {
        if (node != null) {
            inOrderTraversal(node.left, items);
            items.add(node.item);
            inOrderTraversal(node.right, items);
        }
    }

    public String displayCategory() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("=== %s ===\n", category));
        sb.append("-".repeat(50)).append("\n");

        List<MenuItem> items = getAllItemsSorted();
        int index = 1;
        for (MenuItem item : items) {
            sb.append(String.format("%d. %s\n", index++, item.toEnglishDisplay()));
        }

        if (items.isEmpty()) {
            sb.append("  (No items in this category)\n");
        }
        return sb.toString();
    }

    public String getCategory() { return category; }

    public int getSize() { return size; }

    public boolean isEmpty() { return root == null; }
}
