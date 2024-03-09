import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;

public class Main {
    private static final Map<String, Item> inventory = new HashMap<>();
    private static final Scanner scanner = new Scanner(System.in);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final ArrayList<ExitRecord> exitRecords = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("Welcome to the Inventory Registry\n");

        int option;
        do {
            System.out.println("Menu:");
            System.out.println("1. Add item");
            System.out.println("2. Search item");
            System.out.println("3. Remove item");
            System.out.println("4. Show inventory");
            System.out.println("5. View product exits");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    addItem();
                    break;
                case 2:
                    searchItem();
                    break;
                case 3:
                    removeItem();
                    break;
                case 4:
                    showInventory();
                    break;
                case 5:
                    viewExitRecords();
                    break;
                case 6:
                    System.out.println("Exiting the program...");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        } while (option != 6);

        scanner.close();
    }

    private static void addItem() {
        System.out.println("\nAdd Item:");
        System.out.print("Enter the item code: ");
        String code = scanner.nextLine();
        System.out.print("Enter the item name: ");
        String name = scanner.nextLine();
        System.out.print("Enter the item quantity: ");
        int quantity = scanner.nextInt();
        scanner.nextLine();

        // Creating a Product object
        Product product = new Product(code, name, quantity);
        inventory.put(code, product);

        System.out.println("Product added successfully.\n");
    }

    private static void searchItem() {
        System.out.println("\nSearch Item:");
        System.out.print("Enter the item code or name: ");
        String key = scanner.nextLine();

        Item item = inventory.get(key);
        if (item != null) {
            System.out.println("Code: " + item.getCode());
            System.out.println("Name: " + item.getName());
            System.out.println("Quantity: " + item.getQuantity() + "\n");
        } else {
            System.out.println("Item not found.\n");
        }
    }

    private static void removeItem() {
        System.out.println("\nRemove Item:");
        System.out.print("Enter the item code: ");
        String code = scanner.nextLine();

        Item item = inventory.get(code);
        if (item != null) {
            System.out.print("Enter the quantity to remove: ");
            int quantityToRemove = scanner.nextInt();
            scanner.nextLine();

            if (quantityToRemove <= item.getQuantity()) {
                item.subtractQuantity(quantityToRemove);
                recordExit(item, quantityToRemove);
                System.out.println("Removal registered successfully.\n");
            } else {
                System.out.println("The quantity to remove exceeds the inventory quantity.\n");
            }
        } else {
            System.out.println("Item not found.\n");
        }
    }

    private static void showInventory() {
        System.out.println("\nInventory:");
        if (inventory.isEmpty()) {
            System.out.println("The inventory is empty.\n");
        } else {
            for (Item item : inventory.values()) {
                System.out.println("Code: " + item.getCode());
                System.out.println("Name: " + item.getName());
                System.out.println("Quantity: " + item.getQuantity() + "\n");
            }
        }
    }

    private static void viewExitRecords() {
        System.out.println("\nProduct Exit Records:");
        if (exitRecords.isEmpty()) {
            System.out.println("No exit records available.\n");
        } else {
            for (ExitRecord record : exitRecords) {
                System.out.println("Product: " + record.getItem().getName());
                System.out.println("Quantity: " + record.getQuantity());
                System.out.println("Date & Time: " + dateFormat.format(record.getExitTime()) + "\n");
            }
        }
    }

    private static void recordExit(Item item, int quantity) {
        ExitRecord record = new ExitRecord(item, quantity, new Date());
        exitRecords.add(record);
    }

    // Abstract class representing an item in the inventory.
    abstract static class Item {
        private final String code;
        private final String name;
        private int quantity;

        public Item(String code, String name, int quantity) {
            this.code = code;
            this.name = name;
            this.quantity = quantity;
        }

        public String getCode() {
            return code;
        }

        public String getName() {
            return name;
        }

        public int getQuantity() {
            return quantity;
        }

        public void subtractQuantity(int quantity) {
            this.quantity -= quantity;
        }
    }

    // Concrete subclass representing a product.
    static class Product extends Item {
        public Product(String code, String name, int quantity) {
            super(code, name, quantity);
        }
    }

    // Class representing an exit record of an item.
    static class ExitRecord {
        private final Item item;
        private final int quantity;
        private final Date exitTime;

        public ExitRecord(Item item, int quantity, Date exitTime) {
            this.item = item;
            this.quantity = quantity;
            this.exitTime = exitTime;
        }

        public Item getItem() {
            return item;
        }

        public int getQuantity() {
            return quantity;
        }

        public Date getExitTime() {
            return exitTime;
        }
    }
}
