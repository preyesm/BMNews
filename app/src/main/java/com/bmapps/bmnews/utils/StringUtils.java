package com.bmapps.bmnews.utils;

import java.util.ArrayList;

public class StringUtils {

    public boolean isEmpty(String value) {
        return value == null || value.trim().length() == 0 || value.equals("null");
    }

    public static String unEscapeEmojiString(String message) {
        return org.apache.commons.lang3.StringEscapeUtils.unescapeJava(message);
    }

    public static boolean isNullOrEmpty(String str) {
        return str == null || str.length() == 0 || str.equalsIgnoreCase("null");
    }

    public static StringBuilder join(ArrayList<String> list, String delimiter) {
        ArrayList<String> copiedList = list;
        StringBuilder stringBuilder = new StringBuilder();
        if (copiedList != null) {
            for (String string : copiedList) {
                stringBuilder.append(string).append(delimiter);
            }
        }
        return stringBuilder;
    }

    public static String capitalizeFirstLetter(String original) {
        if (original == null || original.length() == 0) {
            return original;
        }
        return original.substring(0, 1).toUpperCase() + original.substring(1);
    }

}

