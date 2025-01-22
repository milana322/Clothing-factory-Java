package org.example;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ClothingManager manager = ClothingManager.getInstance(); // Singleton
        FileHandler fileHandler = new FileHandler();

        while (true) {
            System.out.println("Menu");
            System.out.println("1. Display Clothing Items");
            System.out.println("2. Add New Clothing Item");
            System.out.println("3. Import Data");
            System.out.println("4. Export Data");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            int choice = 0;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1:
                    manager.displayClothingItems();
                    break;
                case 2:
                    manager.addNewClothingItem(scanner);
                    break;
                case 3:
                    importData(scanner, manager, fileHandler);
                    break;
                case 4:
                    exportData(scanner, manager, fileHandler);
                    break;
                case 5:
                    System.out.println("Goodbye!");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    /**
     * Метод для импорта данных (из текстового файла, JSON или XML).
     */
    private static void importData(Scanner scanner, ClothingManager manager, FileHandler fileHandler) {
        System.out.println("\n=== IMPORT MENU ===");
        System.out.println("1. Import from TEXT file");
        System.out.println("2. Import from JSON file");
        System.out.println("3. Import from XML file");
        System.out.print("Choose an option: ");

        int importChoice;
        try {
            importChoice = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
            return;
        }

        System.out.print("Enter filename/path to import: ");
        String filename = scanner.nextLine().trim();

        ClothingList tempList = null;
        switch (importChoice) {
            case 1:
                // Импорт из текстового файла
                tempList = fileHandler.readFromFile(filename);
                break;
            case 2:
                // Импорт из JSON
                tempList = fileHandler.readFromJSONFile(filename);
                break;
            case 3:
                // Импорт из XML
                tempList = fileHandler.readFromXMLFile(filename);
                break;
            default:
                System.out.println("Invalid choice for import.");
                return;
        }

        if (tempList == null || tempList.isEmpty()) {
            System.out.println("No items imported (list is empty or null).");
        } else {
            // Добавляем все предметы из tempList в ClothingManager
            for (ClothingItem item : tempList) {
                manager.addClothingItem(item);
            }
            System.out.println("Imported " + tempList.size() + " items into the manager.");
        }
    }

    /**
     * Метод для экспорта данных (в текстовый файл, JSON или XML).
     */
    private static void exportData(Scanner scanner, ClothingManager manager, FileHandler fileHandler) {
        System.out.println("\n=== EXPORT MENU ===");
        System.out.println("1. Export to TEXT file");
        System.out.println("2. Export to JSON file");
        System.out.println("3. Export to XML file");
        System.out.print("Choose an option: ");

        int exportChoice;
        try {
            exportChoice = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
            return;
        }

        System.out.print("Enter filename/path to export: ");
        String filename = scanner.nextLine().trim();

        // Берём все предметы из ClothingManager
        ClothingList tempList = new ClothingList();
        // Добавляем туда всё, что есть у менеджера
        List<ClothingItem> managerItems = manager.getClothingItems();
        tempList.addAll(managerItems);

        switch (exportChoice) {
            case 1:
                // Экспорт в текстовый файл
                fileHandler.writeToFile(filename, tempList);
                break;
            case 2:
                // Экспорт в JSON
                fileHandler.writeToJSONFile(filename, tempList);
                break;
            case 3:
                // Экспорт в XML
                fileHandler.writeToXMLFile(filename, tempList);
                break;
            default:
                System.out.println("Invalid choice for export.");
                return;
        }
        System.out.println("Export successful!");
    }
}
