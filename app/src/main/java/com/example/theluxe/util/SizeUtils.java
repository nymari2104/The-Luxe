package com.example.theluxe.util;

public class SizeUtils {
    public static String suggestSize(String gender, double height, double weight) {
        if (gender == null || height <= 0 || weight <= 0) {
            return null; // Not enough info
        }

        if ("Male".equalsIgnoreCase(gender)) {
            if (height < 165) {
                return "S";
            } else if (height < 175) {
                return weight < 70 ? "M" : "L";
            } else {
                return weight < 80 ? "L" : "XL";
            }
        } else if ("Female".equalsIgnoreCase(gender)) {
            if (height < 160) {
                return "S";
            } else if (height < 170) {
                return weight < 60 ? "M" : "L";
            } else {
                return weight < 70 ? "L" : "XL";
            }
        }
        return null; // For "Other" or unexpected values
    }
}
