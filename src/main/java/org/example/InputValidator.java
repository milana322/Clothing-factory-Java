//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.example;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputValidator {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static final Pattern NUMBER_PATTERN = Pattern.compile("^[0-9]+(\\.[0-9]+)?$");

    public InputValidator() {
    }

    public static boolean isValidNumber(String value) {
        if (value != null && !value.isEmpty()) {
            Matcher matcher = NUMBER_PATTERN.matcher(value.trim());
            return matcher.matches();
        } else {
            return false;
        }
    }

    public static boolean isValidDate(String value) {
        if (value != null && !value.isEmpty()) {
            try {
                DATE_FORMAT.setLenient(false);
                Date date = DATE_FORMAT.parse(value.trim());
                return date != null;
            } catch (ParseException var2) {
                return false;
            }
        } else {
            return false;
        }
    }

    public static boolean isValidId(String value) {
        return value != null && !value.isEmpty() ? value.matches("\\d+") : false;
    }

    public static boolean isValidString(String value) {
        return value != null && !value.trim().isEmpty();
    }

    public static boolean isValidClothingItemData(String[] data) {
        if (data != null && data.length == 6) {
            String id = data[0].trim();
            String type = data[1].trim();
            String model = data[2].trim();
            String price = data[3].trim();
            String releaseDate = data[4].trim();
            String specificField = data[5].trim();
            return isValidId(id) && isValidString(type) && isValidString(model) && isValidNumber(price) && isValidDate(releaseDate) && isValidString(specificField);
        } else {
            return false;
        }
    }
}
