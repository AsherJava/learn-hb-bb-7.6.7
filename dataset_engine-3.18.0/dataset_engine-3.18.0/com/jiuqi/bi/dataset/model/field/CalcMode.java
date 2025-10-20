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

public enum CalcMode {
    CALC_THEN_AGGR(4001, "\u5148\u8ba1\u7b97\u540e\u6c47\u603b"),
    AGGR_THEN_CALC(4002, "\u5148\u6c47\u603b\u540e\u8ba1\u7b97");

    private int value;
    private String title;
    private static final int DEPRECATED_CALC_THEN_AGGR = 1;
    private static final int DEPRECATED_AGGR_THEN_CALC = 2;
    private static Map<Integer, CalcMode> finder;
    public static final String TAG_VALUE = "value";
    public static final String TAG_TITLE = "title";

    private CalcMode(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int value() {
        return this.value;
    }

    public String title() {
        return this.title;
    }

    public static CalcMode valueOf(int value) {
        return CalcMode.valueOf(new Integer(value));
    }

    public static CalcMode valueOf(Integer value) {
        return finder.get(value);
    }

    public static CalcMode parse(String s) {
        if (s == null || s.length() == 0) {
            return null;
        }
        for (CalcMode item : CalcMode.values()) {
            if (!item.title().equalsIgnoreCase(s)) continue;
            return item;
        }
        return null;
    }

    public static String[] titles() {
        CalcMode[] types = CalcMode.values();
        String[] titles = new String[types.length];
        for (int i = 0; i < types.length; ++i) {
            titles[i] = types[i].title;
        }
        return titles;
    }

    public static JSONArray toJSON() throws JSONException {
        CalcMode[] types;
        JSONArray array = new JSONArray();
        for (CalcMode type : types = CalcMode.values()) {
            JSONObject json = new JSONObject();
            json.put(TAG_VALUE, type.value);
            json.put(TAG_TITLE, (Object)type.title);
            array.put((Object)json);
        }
        return array;
    }

    static {
        finder = new HashMap<Integer, CalcMode>();
        for (CalcMode item : CalcMode.values()) {
            finder.put(new Integer(item.value()), item);
        }
        finder.put(1, CALC_THEN_AGGR);
        finder.put(2, AGGR_THEN_CALC);
    }
}

