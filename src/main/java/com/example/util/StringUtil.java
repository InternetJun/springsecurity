package com.example.util;

public class StringUtil {
    public static boolean isEmpty(Object e) {
        return e == null || "".equals(e.toString());
    }

    public static boolean isNotEmpty(Object e) {
        return !isEmpty(e);
    }

    public static boolean isEmpty(String e) {
        return e == null || "".equals(e);
    }

    public static boolean isNotEmpty(String  e) {
        return !isEmpty(e);
    }
}
