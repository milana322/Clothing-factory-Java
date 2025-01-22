package org.example;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class ClothingManager {
    private static ClothingManager instance;
    private List<ClothingItem> clothingItems;

    private ClothingManager() {
        clothingItems = new ArrayList<>();
    }

    public static ClothingManager getInstance() {
        if (instance == null) {
            synchronized (ClothingManager.class) {
                if (instance == null) {
                    instance = new ClothingManager();
                }
            }
        }
        return instance;
    }

    public void addNewClothingItem(Scanner scanner) {
        System.out.println("Enter the type of clothing (T-Shirt, Pants, Dress, Skirt): ");
        String type = scanner.nextLine().trim();

        System.out.print("Enter the ID of the clothing item: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // consume newline

        System.out.print("Enter the model: ");
        String model = scanner.nextLine().trim();

        System.out.print("Enter the price: ");
        double price = scanner.nextDouble();
        scanner.nextLine(); // consume newline

        System.out.print("Enter release date (yyyy-MM-dd): ");
        String dateStr = scanner.nextLine().trim();

        Date releaseDate;
        try {
            releaseDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
        } catch (Exception e) {
            System.out.println("Invalid date format. Please enter the date in yyyy-MM-dd format.");
            return;
        }

        System.out.print("Enter specific attribute (size/fit/length/etc.): ");
        String specificAttribute = scanner.nextLine().trim();

        ClothingItem newItem;
        switch (type.toLowerCase()) {
            case "t-shirt":
                newItem = new TShirt(id, model, price, releaseDate, specificAttribute);
                break;
            case "pants":
                newItem = new Pants(id, model, price, releaseDate, specificAttribute);
                break;
            case "dress":
                newItem = new Dress(id, model, price, releaseDate, specificAttribute);
                break;
            case "skirt":
                newItem = new Skirt(id, model, price, releaseDate, specificAttribute);
                break;
            default:
                System.out.println("Invalid clothing type: " + type);
                return;
        }

        addClothingItem(newItem);
        System.out.println("Clothing item added successfully!");
    }

    public void addClothingItem(ClothingItem item) {
        clothingItems.add(item);
    }

    // ВАЖНО: геттер, чтобы Main мог получить все предметы
    public List<ClothingItem> getClothingItems() {
        return clothingItems;
    }

    public void displayClothingItems() {
        if (clothingItems.isEmpty()) {
            System.out.println("No clothing items available.");
        } else {
            for (ClothingItem item : clothingItems) {
                System.out.println(item);
            }
        }
    }
}
