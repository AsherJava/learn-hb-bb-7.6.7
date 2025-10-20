/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.bi.dataset.model.field;

import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public enum AggregationType {
    MAX(1003, "\u6700\u5927\u503c"),
    MIN(1004, "\u6700\u5c0f\u503c"),
    SUM(1001, "\u6c42\u548c"),
    COUNT(1002, "\u8ba1\u6570"),
    AVG(1005, "\u6c42\u5e73\u5747");

    private int value;
    private String title;
    private static final int DEPRECATED_VALUE_MAX = 0;
    private static final int DEPRECATED_VALUE_MIN = 1;
    private static final int DEPRECATED_VALUE_SUM = 2;
    private static final int DEPRECATED_VALUE_COUNT = 3;
    private static final int DEPRECATED_VALUE_AVG = 4;
    private static Map<Integer, AggregationType> finder;
    public static final String TAG_VALUE = "value";
    public static final String TAG_TITLE = "title";

    private AggregationType(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int value() {
        return this.value;
    }

    public String title() {
        return this.title;
    }

    public static AggregationType valueOf(int value) {
        return AggregationType.valueOf(new Integer(value));
    }

    public static AggregationType valueOf(Integer value) {
        return finder.get(value);
    }

    public static AggregationType parse(String s) {
        if (s == null || s.length() == 0) {
            return null;
        }
        for (AggregationType item : AggregationType.values()) {
            if (!item.title().equalsIgnoreCase(s)) continue;
            return item;
        }
        return null;
    }

    public static String[] titles() {
        AggregationType[] types = AggregationType.values();
        String[] titles = new String[types.length];
        for (int i = 0; i < types.length; ++i) {
            titles[i] = types[i].title;
        }
        return titles;
    }

    public static JSONArray toJSON() throws JSONException {
        AggregationType[] types;
        JSONArray array = new JSONArray();
        for (AggregationType type : types = AggregationType.values()) {
            JSONObject json = new JSONObject();
            json.put(TAG_VALUE, type.value);
            json.put(TAG_TITLE, (Object)type.title);
            array.put((Object)json);
        }
        return array;
    }

    static {
        finder = new HashMap<Integer, AggregationType>();
        for (AggregationType item : AggregationType.values()) {
            finder.put(new Integer(item.value()), item);
        }
        finder.put(0, MAX);
        finder.put(1, MIN);
        finder.put(2, SUM);
        finder.put(3, COUNT);
        finder.put(4, AVG);
    }
}

