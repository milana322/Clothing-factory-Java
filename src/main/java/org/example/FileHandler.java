package org.example;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;
import java.text.*;
import java.util.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FileHandler {
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    public void writeToJSONFile(String filename, ClothingItem item) {
        try {
            // Создание объекта JSONObject
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", item.getId());
            jsonObject.put("model", item.getModel());
            jsonObject.put("price", item.getPrice());
            jsonObject.put("type", item.getType());

            // Запись в файл
            try (FileWriter file = new FileWriter(filename)) {
                file.write(jsonObject.toString(4));  // Сохранение с отступами для красивого вывода
                System.out.println("Successfully written to the JSON file.");
            }

        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }

    // Метод чтения данных из JSON файла с проверкой данных через InputValidator
    public ClothingList readFromJSONFile(String filename) {
        ClothingList clothingList = new ClothingList();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            StringBuilder jsonContent = new StringBuilder();
            String line;

            // Чтение всего файла в одну строку
            while ((line = reader.readLine()) != null) {
                jsonContent.append(line);
            }

            // Парсинг JSON содержимого
            JSONObject jsonObject = new JSONObject(jsonContent.toString());
            JSONArray clothingArray = jsonObject.getJSONArray("ClothingItems");  // Предположим, что данные об одежде находятся в массиве "ClothingItems"

            // Проходим по каждому объекту в массиве
            for (int i = 0; i < clothingArray.length(); i++) {
                JSONObject item = clothingArray.getJSONObject(i);

                // Извлекаем данные для каждого элемента одежды
                String idStr = item.getString("id");
                String type = item.getString("type");
                String model = item.getString("model");
                String priceStr = item.getString("price");
                String releaseDateStr = item.getString("releaseDate");
                String specificField = item.getString("specificField");

                // Проверка данных через InputValidator
                if (!InputValidator.isValidId(idStr)) {
                    System.out.println("Невалидный ID: " + idStr);
                    continue;
                }
                if (!InputValidator.isValidString(type)) {
                    System.out.println("Невалидный тип одежды: " + type);
                    continue;
                }
                if (!InputValidator.isValidString(model)) {
                    System.out.println("Невалидная модель: " + model);
                    continue;
                }
                if (!InputValidator.isValidNumber(priceStr)) {
                    System.out.println("Невалидная цена: " + priceStr);
                    continue;
                }
                if (!InputValidator.isValidDate(releaseDateStr)) {
                    System.out.println("Невалидная дата выпуска: " + releaseDateStr);
                    continue;
                }

                // Преобразование строки в число и дату
                int id = Integer.parseInt(idStr);
                double price = Double.parseDouble(priceStr);
                Date releaseDate = dateFormat.parse(releaseDateStr);

                // Если все данные валидны, создаём объект одежды и добавляем его в список
                switch (type) {
                    case "Dress":
                        if (!InputValidator.isValidString(specificField)) {
                            System.out.println("Невалидный размер для платья: " + specificField);
                            continue;
                        }
                        clothingList.add(new Dress(id, model, price, releaseDate, specificField));
                        break;
                    case "Pants":
                        if (!InputValidator.isValidString(specificField)) {
                            System.out.println("Невалидная посадка для брюк: " + specificField);
                            continue;
                        }
                        clothingList.add(new Pants(id, model, price, releaseDate, specificField));
                        break;
                    case "Skirt":
                        if (!InputValidator.isValidString(specificField)) {
                            System.out.println("Невалидная длина для юбки: " + specificField);
                            continue;
                        }
                        clothingList.add(new Skirt(id, model, price, releaseDate, specificField));
                        break;
                    case "T-Shirt":
                        if (!InputValidator.isValidString(specificField)) {
                            System.out.println("Невалидный размер для футболки: " + specificField);
                            continue;
                        }
                        clothingList.add(new TShirt(id, model, price, releaseDate, specificField));
                        break;
                    default:
                        System.out.println("Неизвестный тип одежды: " + type);
                }
            }
        } catch (IOException | JSONException | ParseException e) {
            e.printStackTrace();
        }
        return clothingList;
    }


    public void writeToJSONFile(String filename, ClothingList clothingList) {
        // Объявим общий try-catch для всех операций с jsonObject

        try {
            JSONObject jsonObject = new JSONObject();
            JSONArray clothingItemsArray = new JSONArray();

            for (ClothingItem item : clothingList) {
                JSONObject clothingItemObject = new JSONObject();
                // Каждое put(...) тоже желательно обернуть
                try {
                    clothingItemObject.put("id", item.getId());
                    clothingItemObject.put("type", item.getType());
                    clothingItemObject.put("model", item.getModel());
                    clothingItemObject.put("price", item.getPrice());
                    clothingItemObject.put("releaseDate", dateFormat.format(item.getReleaseDate()));

                    String specificField = "";
                    if (item instanceof Dress) {
                        specificField = ((Dress) item).getSize();
                    } else if (item instanceof Pants) {
                        specificField = ((Pants) item).getFit();
                    } else if (item instanceof Skirt) {
                        specificField = ((Skirt) item).getLength();
                    } else if (item instanceof TShirt) {
                        specificField = ((TShirt) item).getSize();
                    }

                    clothingItemObject.put("specificField", specificField);

                    // добавляем в массив
                    clothingItemsArray.put(clothingItemObject);
                } catch (Exception e) {
                    // Ловим конкретное исключение ChargebeeJSONException
                    System.out.println("Error constructing JSON for item: " + item.getId());
                    e.printStackTrace();
                }
            }

            // Добавляем массив ClothingItems
            try {
                jsonObject.put("ClothingItems", clothingItemsArray);
            } catch (Exception e) {
                System.out.println("Error putting ClothingItems array into root JSON");
                e.printStackTrace();
            }

            // Запись в файл
            try (FileWriter fileWriter = new FileWriter(filename)) {
                try {
                    // toString(4) тоже может бросать checked-исключение
                    fileWriter.write(jsonObject.toString(4));
                } catch (Exception e) {
                    System.out.println("Error calling toString() on JSON");
                    e.printStackTrace();
                }
                System.out.println("Data successfully written to JSON file: " + filename);
            } catch (IOException e) {
                System.out.println("Error writing to JSON file: " + e.getMessage());
                e.printStackTrace();
            }
        } catch (Exception e) {
            // Если что-то общее ещё вылетело
            System.out.println("General exception in writeToJSONFile");
            e.printStackTrace();
        }
    }

    // Метод записи в текстовый файл
    // Метод записи в текстовый файл с использованием итератора
    public void writeToFile(String filename, ClothingList clothingList) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            // Используем итератор для обхода списка одежды
            Iterator<ClothingItem> iterator = clothingList.iterator();
            while (iterator.hasNext()) {
                ClothingItem item = iterator.next();
                writer.write(item.getId() + "," + item.getType() + "," + item.getModel() +
                        "," + item.getPrice() + "," + dateFormat.format(item.getReleaseDate()) + "," +
                        ((item instanceof Dress) ? ((Dress) item).getSize() :
                                (item instanceof Pants) ? ((Pants) item).getFit() :
                                        (item instanceof Skirt) ? ((Skirt) item).getLength() :
                                                (item instanceof TShirt) ? ((TShirt) item).getSize() : ""));

                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // Метод чтения данных из текстового файла
    public ClothingList readFromFile(String filename) {
        ClothingList clothingList = new ClothingList();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");

                // Проверка на валидность данных
                if (!InputValidator.isValidClothingItemData(parts)) {
                    System.out.println("Невалидные данные в строке: " + line);
                    continue;  // Пропустить строку с неверными данными
                }

                // Если данные валидны, продолжаем обработку
                int id = Integer.parseInt(parts[0].trim());
                String type = parts[1].trim();
                String model = parts[2].trim();
                double price = Double.parseDouble(parts[3].trim());
                Date releaseDate = dateFormat.parse(parts[4].trim());

                switch (type) {
                    case "Dress":
                        clothingList.add(new Dress(id, model, price, releaseDate, parts[5].trim()));
                        break;
                    case "Pants":
                        clothingList.add(new Pants(id, model, price, releaseDate, parts[5].trim()));
                        break;
                    case "Skirt":
                        clothingList.add(new Skirt(id, model, price, releaseDate, parts[5].trim()));
                        break;
                    case "T-Shirt":
                        clothingList.add(new TShirt(id, model, price, releaseDate, parts[5].trim()));
                        break;
                    default:
                        System.out.println("Неизвестный тип одежды: " + type);
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return clothingList;
    }

    // Метод для чтения данных из XML
    public ClothingList readFromXMLFile(String filename) {
        ClothingList clothingList = new ClothingList();
        try {
            File file = new File(filename);
            if (!file.exists()) {
                System.out.println("Файл не найден: " + filename);
                return clothingList;
            }

            // Создание объекта парсера для XML
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(file);
            doc.getDocumentElement().normalize();

            // Получаем список всех элементов ClothingItem в XML
            NodeList itemList = doc.getElementsByTagName("ClothingItem");
            for (int i = 0; i < itemList.getLength(); i++) {
                Node node = itemList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    // Извлекаем данные из XML
                    String idStr = element.getElementsByTagName("ID").item(0).getTextContent().trim();
                    String type = element.getElementsByTagName("Type").item(0).getTextContent().trim();
                    String model = element.getElementsByTagName("Model").item(0).getTextContent().trim();
                    String priceStr = element.getElementsByTagName("Price").item(0).getTextContent().trim();
                    String releaseDateStr = element.getElementsByTagName("ReleaseDate").item(0).getTextContent().trim();
                    String specificField = element.getElementsByTagName("SpecificField").item(0).getTextContent().trim();

                    // Проверка валидности данных с использованием InputValidator
                    String[] data = {idStr, type, model, priceStr, releaseDateStr, specificField};
                    if (!InputValidator.isValidClothingItemData(data)) {
                        System.out.println("Невалидные данные в XML элементе: " + node);
                        continue;  // Пропустить этот элемент, если данные невалидны
                    }

                    // Если данные валидны, продолжаем обработку
                    int id = Integer.parseInt(idStr);
                    double price = Double.parseDouble(priceStr);
                    Date releaseDate = dateFormat.parse(releaseDateStr);

                    // Создание объекта одежды в зависимости от типа
                    switch (type) {
                        case "Dress":
                            clothingList.add(new Dress(id, model, price, releaseDate, specificField));
                            break;
                        case "Pants":
                            clothingList.add(new Pants(id, model, price, releaseDate, specificField));
                            break;
                        case "Skirt":
                            clothingList.add(new Skirt(id, model, price, releaseDate, specificField));
                            break;
                        case "T-Shirt":
                            clothingList.add(new TShirt(id, model, price, releaseDate, specificField));
                            break;
                        default:
                            System.out.println("Неизвестный тип одежды: " + type);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Ошибка при чтении из XML: " + e.getMessage());
            e.printStackTrace();
        }
        return clothingList;
    }

    // Метод записи данных в XML
    public void writeToXMLFile(String filename, ClothingList clothingList) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();
            Element rootElement = doc.createElement("ClothingItems");
            doc.appendChild(rootElement);

            for (ClothingItem item : clothingList) {
                Element clothingElement = doc.createElement("ClothingItem");
                rootElement.appendChild(clothingElement);

                Element id = doc.createElement("ID");
                id.appendChild(doc.createTextNode(String.valueOf(item.getId())));
                clothingElement.appendChild(id);

                Element type = doc.createElement("Type");
                type.appendChild(doc.createTextNode(item.getType()));
                clothingElement.appendChild(type);

                Element model = doc.createElement("Model");
                model.appendChild(doc.createTextNode(item.getModel()));
                clothingElement.appendChild(model);

                Element price = doc.createElement("Price");
                price.appendChild(doc.createTextNode(String.valueOf(item.getPrice())));
                clothingElement.appendChild(price);

                Element releaseDate = doc.createElement("ReleaseDate");
                releaseDate.appendChild(doc.createTextNode(dateFormat.format(item.getReleaseDate())));
                clothingElement.appendChild(releaseDate);

                Element specificField = doc.createElement("SpecificField");
                if (item instanceof Dress) {
                    specificField.appendChild(doc.createTextNode(((Dress) item).getSize()));
                } else if (item instanceof Pants) {
                    specificField.appendChild(doc.createTextNode(((Pants) item).getFit()));
                } else if (item instanceof Skirt) {
                    specificField.appendChild(doc.createTextNode(((Skirt) item).getLength()));
                } else if (item instanceof TShirt) {
                    specificField.appendChild(doc.createTextNode(((TShirt) item).getSize()));
                }
                clothingElement.appendChild(specificField);
            }

            // Запись в файл
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(filename));
            transformer.transform(source, result);

            System.out.println("Data successfully written to XML file: " + filename);

        } catch (Exception e) {
            System.out.println("Error writing to XML: " + e.getMessage());
            e.printStackTrace();
        }
    }
}