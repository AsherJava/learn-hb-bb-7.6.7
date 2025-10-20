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

public enum TimeGranularity {
    YEAR(0, "\u5e74", 360, new String[]{"1990\u5e74", "1990"}, Integer.MAX_VALUE),
    HALFYEAR(1, "\u534a\u5e74", 180, new String[]{"1990\u5e74\u4e0a\u534a\u5e74|\u4e0b\u534a\u5e74"}, 2),
    QUARTER(2, "\u5b63\u5ea6", 90, new String[]{"1990\u5e741\u5b63\u5ea6", "1990\u5e74\u7b2c1\u5b63\u5ea6"}, 4),
    MONTH(3, "\u6708\u4efd", 30, new String[]{"1990\u5e741\u6708", "1990\u5e7401\u6708", "1990\u5e74\u4e00\u6708"}, 12),
    XUN(4, "\u65ec", 10, new String[]{"1990\u5e741\u6708\u4e0a\u65ec|\u4e2d\u65ec|\u4e0b\u65ec"}, 3),
    DAY(5, "\u65e5", 1, new String[]{"1990\u5e741\u67081\u65e5", "1990-01-01", "1990-1-1"}, 365),
    WEEK(6, "\u5468", 7, new String[]{"\u7b2c1\u5468", "\u7b2c1\u5468(1.1~1.7)"}, 53);

    private int value;
    private String title;
    private int days;
    private String[] defaultShowPatterns;
    private int max;
    private static final Map<Integer, TimeGranularity> valueFinder;
    private static final Map<String, TimeGranularity> nameFinder;
    public static final String TAG_VALUE = "value";
    public static final String TAG_TITLE = "title";
    public static final String TAG_DAYS = "days";
    public static final String TAG_DEFAULTSHOWPATTERNS = "defaultShowPatterns";

    private TimeGranularity(int value, String title, int days, String[] defaultShowPatterns, int max) {
        this.value = value;
        this.title = title;
        this.days = days;
        this.defaultShowPatterns = defaultShowPatterns;
        this.max = max;
    }

    public int value() {
        return this.value;
    }

    public String title() {
        return this.title;
    }

    public int days() {
        return this.days;
    }

    public int getMax() {
        return this.max;
    }

    public String[] getDefaultShowPatterns() {
        return this.defaultShowPatterns;
    }

    public static TimeGranularity valueOf(int value) {
        return TimeGranularity.valueOf(new Integer(value));
    }

    public static TimeGranularity valueOf(Integer value) {
        return valueFinder.get(value);
    }

    public static TimeGranularity parse(String s) {
        if (s == null || s.length() == 0) {
            return null;
        }
        return nameFinder.get(s.toUpperCase());
    }

    public static String[] titles() {
        TimeGranularity[] types = TimeGranularity.values();
        String[] titles = new String[types.length];
        for (int i = 0; i < types.length; ++i) {
            titles[i] = types[i].title;
        }
        return titles;
    }

    public static JSONArray toJSON() throws JSONException {
        TimeGranularity[] types;
        JSONArray array = new JSONArray();
        for (TimeGranularity type : types = TimeGranularity.values()) {
            JSONObject json = new JSONObject();
            json.put(TAG_VALUE, type.value);
            json.put(TAG_TITLE, (Object)type.title);
            json.put(TAG_DAYS, type.days);
            JSONArray showPattersArray = new JSONArray();
            for (String s : type.getDefaultShowPatterns()) {
                showPattersArray.put((Object)s);
            }
            json.put(TAG_DEFAULTSHOWPATTERNS, (Object)showPattersArray);
            array.put((Object)json);
        }
        return array;
    }

    static {
        valueFinder = new HashMap<Integer, TimeGranularity>();
        nameFinder = new HashMap<String, TimeGranularity>();
        for (TimeGranularity item : TimeGranularity.values()) {
            valueFinder.put(item.value(), item);
            nameFinder.put(item.name(), item);
            nameFinder.put(item.title, item);
        }
        nameFinder.put("\u5b63", QUARTER);
        nameFinder.put("SEASON", QUARTER);
        nameFinder.put("\u6708", MONTH);
    }
}

