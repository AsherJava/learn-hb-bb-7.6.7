/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.bi.dataset;

import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public enum DataType {
    BOOLEAN(1, "\u5e03\u5c14"),
    DATETIME(2, "\u65e5\u671f"),
    DOUBLE(3, "\u6d6e\u70b9"),
    INTEGER(5, "\u6574\u578b"),
    STRING(6, "\u5b57\u7b26\u4e32"),
    UNKNOWN(0, "\u672a\u77e5\u7c7b\u578b");

    private String title;
    private int value;
    private static Map<Integer, DataType> finder;
    public static final String TAG_VALUE = "value";
    public static final String TAG_TITLE = "title";

    private DataType(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int value() {
        return this.value;
    }

    public String title() {
        return this.title;
    }

    public static DataType valueOf(int value) {
        DataType v = finder.get(value);
        return v == null ? UNKNOWN : v;
    }

    public static DataType parse(String s) {
        if (s == null || s.length() == 0) {
            return null;
        }
        try {
            int val = Integer.parseInt(s);
            DataType ret = DataType.valueOf(val);
            if (ret != null) {
                return ret;
            }
        }
        catch (NumberFormatException numberFormatException) {
            // empty catch block
        }
        for (DataType item : DataType.values()) {
            if (!item.title().equalsIgnoreCase(s) && !item.name().equalsIgnoreCase(s)) continue;
            return item;
        }
        return UNKNOWN;
    }

    public static int translateToSyntaxType(DataType type) {
        switch (type) {
            case BOOLEAN: {
                return 1;
            }
            case DATETIME: {
                return 2;
            }
            case INTEGER: 
            case DOUBLE: {
                return 3;
            }
            case STRING: {
                return 6;
            }
        }
        return -1;
    }

    public static int translateToSyntaxType(int type) {
        if (type == DataType.INTEGER.value) {
            return 3;
        }
        return type;
    }

    public static String[] getAllTypeTitle() {
        DataType[] dtypes = DataType.values();
        String[] titles = new String[dtypes.length];
        for (int i = 0; i < dtypes.length; ++i) {
            titles[i] = dtypes[i].title();
        }
        return titles;
    }

    public static int[] getAllTypes() {
        DataType[] dtypes = DataType.values();
        int[] values = new int[dtypes.length];
        for (int i = 0; i < dtypes.length; ++i) {
            values[i] = dtypes[i].value();
        }
        return values;
    }

    public static JSONArray toJSON() throws JSONException {
        DataType[] types;
        JSONArray array = new JSONArray();
        for (DataType type : types = DataType.values()) {
            JSONObject json = new JSONObject();
            json.put(TAG_VALUE, type.value);
            json.put(TAG_TITLE, (Object)type.title);
            array.put((Object)json);
        }
        return array;
    }

    static {
        finder = new HashMap<Integer, DataType>();
        for (DataType item : DataType.values()) {
            finder.put(item.value(), item);
        }
        finder.put(8, INTEGER);
    }
}

