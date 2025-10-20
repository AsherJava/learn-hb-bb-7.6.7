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

public enum ApplyType {
    PERIOD(2001, "\u65f6\u671f\u6570"),
    TIMEPOINT(2002, "\u65f6\u70b9\u6570"),
    TOTAL(2003, "\u7d2f\u8ba1\u6570"),
    PERIOD_OPENNING_BLANCE(2004, "\u671f\u521d\u6570"),
    PERIOD_CLOSING_BLANCE(2005, "\u671f\u672b\u6570");

    private static final int DEPRECATED_VALUE_PERIOD = 1;
    private static final int DEPRECATED_VALUE_TIMEPOINT = 2;
    private static final int DEPRECATED_VALUE_TOTAL = 3;
    private int value;
    private String title;
    private static Map<Integer, ApplyType> finder;
    public static final String TAG_VALUE = "value";
    public static final String TAG_TITLE = "title";

    private ApplyType(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int value() {
        return this.value;
    }

    public String title() {
        return this.title;
    }

    public static ApplyType valueOf(int value) {
        return ApplyType.valueOf(new Integer(value));
    }

    public static ApplyType valueOf(Integer value) {
        return finder.get(value);
    }

    public static ApplyType parse(String s) {
        if (s == null || s.length() == 0) {
            return null;
        }
        for (ApplyType item : ApplyType.values()) {
            if (!item.title().equalsIgnoreCase(s)) continue;
            return item;
        }
        return null;
    }

    public static String[] titles() {
        ApplyType[] types = ApplyType.values();
        String[] titles = new String[types.length];
        for (int i = 0; i < types.length; ++i) {
            titles[i] = types[i].title;
        }
        return titles;
    }

    public static JSONArray toJSON() throws JSONException {
        ApplyType[] types;
        JSONArray array = new JSONArray();
        for (ApplyType type : types = ApplyType.values()) {
            JSONObject json = new JSONObject();
            json.put(TAG_VALUE, type.value);
            json.put(TAG_TITLE, (Object)type.title);
            array.put((Object)json);
        }
        return array;
    }

    static {
        finder = new HashMap<Integer, ApplyType>();
        for (ApplyType item : ApplyType.values()) {
            finder.put(new Integer(item.value()), item);
        }
        finder.put(1, PERIOD);
        finder.put(2, TIMEPOINT);
        finder.put(3, TOTAL);
    }
}

