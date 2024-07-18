package com.app.zafiro.util;

public class ProductUtil {

    public static String generateUniqueCode(String name, String color, int sequenceNumber) {
        return name.substring(0, 3).toUpperCase()
                .concat(color.substring(0, 3).toUpperCase())
                .concat(String.format("%03d", sequenceNumber));
    }}
