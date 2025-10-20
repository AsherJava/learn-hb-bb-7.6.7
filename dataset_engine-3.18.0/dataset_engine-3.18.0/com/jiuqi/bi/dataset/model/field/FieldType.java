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

public enum FieldType {
    GENERAL_DIM(1, "\u666e\u901a\u7ef4\u5ea6"),
    TIME_DIM(2, "\u65f6\u95f4\u7ef4\u5ea6"),
    MEASURE(3, "\u5ea6\u91cf"),
    DESCRIPTION(4, "\u63cf\u8ff0\u4fe1\u606f");

    private int value;
    private String title;
    private static Map<Integer, FieldType> finder;
    public static final String TAG_VALUE = "value";
    public static final String TAG_TITLE = "title";

    private FieldType(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int value() {
        return this.value;
    }

    public String title() {
        return this.title;
    }

    public static FieldType valueOf(int value) {
        return FieldType.valueOf(new Integer(value));
    }

    public static FieldType valueOf(Integer value) {
        return finder.get(value);
    }

    public static FieldType parse(String s) {
        if (s == null || s.length() == 0) {
            return null;
        }
        for (FieldType item : FieldType.values()) {
            if (!item.title().equalsIgnoreCase(s)) continue;
            return item;
        }
        return null;
    }

    public static boolean isDimField(FieldType type) {
        return type == GENERAL_DIM || type == TIME_DIM;
    }

    public boolean isDimField() {
        return this.value == FieldType.GENERAL_DIM.value || this.value == FieldType.TIME_DIM.value;
    }

    public boolean isTimeDimField() {
        return this.value == FieldType.TIME_DIM.value;
    }

    public boolean isMeasureField() {
        return this.value == FieldType.MEASURE.value;
    }

    public static String[] titles() {
        FieldType[] types = FieldType.values();
        String[] titles = new String[types.length];
        for (int i = 0; i < types.length; ++i) {
            titles[i] = types[i].title;
        }
        return titles;
    }

    public static JSONArray toJSON() throws JSONException {
        FieldType[] types;
        JSONArray array = new JSONArray();
        for (FieldType type : types = FieldType.values()) {
            JSONObject json = new JSONObject();
            json.put(TAG_VALUE, type.value);
            json.put(TAG_TITLE, (Object)type.title);
            array.put((Object)json);
        }
        return array;
    }

    static {
        finder = new HashMap<Integer, FieldType>();
        for (FieldType item : FieldType.values()) {
            finder.put(new Integer(item.value()), item);
        }
    }
}

