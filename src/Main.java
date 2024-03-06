import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;

public class Main {
    private static final Map<String, Product> inventory = new HashMap<>();
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
        System.out.print("Enter the product code: ");
        String code = scanner.nextLine();
        System.out.print("Enter the product name: ");
        String name = scanner.nextLine();
        System.out.print("Enter the product quantity: ");
        int quantity = scanner.nextInt();
        scanner.nextLine();

        Product product = new Product(code, name, quantity);
        inventory.put(code, product);

        System.out.println("Product added successfully.\n");
    }

    private static void searchItem() {
        System.out.println("\nSearch Item:");
        System.out.print("Enter the product code or name: ");
        String key = scanner.nextLine();

        Product product = inventory.get(key);
        if (product != null) {
            System.out.println("Code: " + product.getCode());
            System.out.println("Name: " + product.getName());
            System.out.println("Quantity: " + product.getQuantity() + "\n");
        } else {
            System.out.println("Product not found.\n");
        }
    }

    private static void removeItem() {
        System.out.println("\nRemove Item:");
        System.out.print("Enter the product code: ");
        String code = scanner.nextLine();

        Product product = inventory.get(code);
        if (product != null) {
            System.out.print("Enter the quantity to remove: ");
            int quantityToRemove = scanner.nextInt();
            scanner.nextLine();

            if (quantityToRemove <= product.getQuantity()) {
                product.subtractQuantity(quantityToRemove);
                recordExit(product, quantityToRemove);
                System.out.println("Removal registered successfully.\n");
            } else {
                System.out.println("The quantity to remove exceeds the inventory quantity.\n");
            }
        } else {
            System.out.println("Product not found.\n");
        }
    }

    private static void showInventory() {
        System.out.println("\nInventory:");
        if (inventory.isEmpty()) {
            System.out.println("The inventory is empty.\n");
        } else {
            for (Product product : inventory.values()) {
                System.out.println("Code: " + product.getCode());
                System.out.println("Name: " + product.getName());
                System.out.println("Quantity: " + product.getQuantity() + "\n");
            }
        }
    }

    private static void viewExitRecords() {
        System.out.println("\nProduct Exit Records:");
        if (exitRecords.isEmpty()) {
            System.out.println("No exit records available.\n");
        } else {
            for (ExitRecord record : exitRecords) {
                System.out.println("Product: " + record.getProduct().getName());
                System.out.println("Quantity: " + record.getQuantity());
                System.out.println("Date & Time: " + dateFormat.format(record.getExitTime()) + "\n");
            }
        }
    }

    private static void recordExit(Product product, int quantity) {
        ExitRecord record = new ExitRecord(product, quantity, new Date());
        exitRecords.add(record);
    }

    static class Product {
        private final String code;
        private final String name;
        private int quantity;

        public Product(String code, String name, int quantity) {
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

    static class ExitRecord {
        private final Product product;
        private final int quantity;
        private final Date exitTime;

        public ExitRecord(Product product, int quantity, Date exitTime) {
            this.product = product;
            this.quantity = quantity;
            this.exitTime = exitTime;
        }

        public Product getProduct() {
            return product;
        }

        public int getQuantity() {
            return quantity;
        }

        public Date getExitTime() {
            return exitTime;
        }
    }
}