package ru.nikkorejz.Engine;

import java.util.HashMap;
import java.util.Map;

public class Variables {

    private static final Map<String, Double> _Variables;

    static {
        _Variables = new HashMap<>();
    }

    private static boolean isExists(String key) {
        return _Variables.containsKey(key);
    }

    public static Double get(String key) {
        key = key.trim();
        if (isExists(key)) {
            return _Variables.get(key);
        }

        throw new RuntimeException("Can't resolve symbol \"" + key + "\"");
    }

    public static void add(String key, Double value) {
        key = key.trim();
        _Variables.put(key, value);
    }

}
