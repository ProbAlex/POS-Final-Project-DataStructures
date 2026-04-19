import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Menu {
    private ArrayList<MenuItem> allItems;
    private HashMap<String, MenuItem> englishLookup;
    private HashMap<String, MenuItem> mandarinLookup;
    private HashMap<String, MenuCategoryBST> categoryTrees;

    public static final String[] CATEGORIES = {"Appetizers", "Entrees", "Desserts", "Drinks"};

    public Menu() {
        this.allItems = new ArrayList<>();
        this.englishLookup = new HashMap<>();
        this.mandarinLookup = new HashMap<>();
        this.categoryTrees = new HashMap<>();

        for (String category : CATEGORIES) {
            categoryTrees.put(category, new MenuCategoryBST(category));
        }
    }

    public void addItem(MenuItem item) {
        allItems.add(item);

        englishLookup.put(item.getEnglishName().toLowerCase(), item);
        mandarinLookup.put(item.getMandarinName(), item);

        MenuCategoryBST categoryTree = categoryTrees.get(item.getCategory());
        if (categoryTree != null) {
            categoryTree.insert(item);
        }
    }

    public MenuItem findByEnglish(String englishName) {
        return englishLookup.get(englishName.toLowerCase());
    }

    public MenuItem findByMandarin(String mandarinName) {
        return mandarinLookup.get(mandarinName);
    }

    public List<MenuItem> searchByEnglish(String partial) {
        List<MenuItem> results = new ArrayList<>();
        String searchTerm = partial.toLowerCase();
        for (MenuItem item : allItems) {
            if (item.getEnglishName().toLowerCase().contains(searchTerm)) {
                results.add(item);
            }
        }
        return results;
    }

    public List<MenuItem> getCategory(String category) {
        MenuCategoryBST tree = categoryTrees.get(category);
        if (tree != null) {
            return tree.getAllItemsSorted();
        }
        return new ArrayList<>();
    }

    public MenuCategoryBST getCategoryTree(String category) {
        return categoryTrees.get(category);
    }

    public ArrayList<MenuItem> getAllItems() {
        return new ArrayList<>(allItems);
    }

    public int getItemCount() {
        return allItems.size();
    }

    public String displayFullMenu() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append("==========================================================");
        sb.append("|                    MENU 菜单                           |\n");
        sb.append("=========================================================+\n\n");

        for (String category : CATEGORIES) {
            MenuCategoryBST tree = categoryTrees.get(category);
            if (tree != null && !tree.isEmpty()) {
                sb.append(tree.displayCategory());
                sb.append("\n");
            }
        }

        return sb.toString();
    }

    public String displayNumberedMenu() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n=== SELECT ITEM BY NUMBER ===\n\n");

        int index = 1;
        for (String category : CATEGORIES) {
            List<MenuItem> items = getCategory(category);
            if (!items.isEmpty()) {
                sb.append(String.format("--- %s ---\n", category));
                for (MenuItem item : items) {
                    sb.append(String.format("[%2d] %s\n", index++, item.toEnglishDisplay()));
                }
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    public MenuItem getItemByIndex(int index) {
        int current = 1;
        for (String category : CATEGORIES) {
            List<MenuItem> items = getCategory(category);
            for (MenuItem item : items) {
                if (current == index) {
                    return item;
                }
                current++;
            }
        }
        return null;
    }
}
