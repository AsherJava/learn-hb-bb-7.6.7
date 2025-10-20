/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.bi.dataset.pattern;

import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class WeekShowPattern
extends Enum<WeekShowPattern> {
    public static final /* enum */ WeekShowPattern PATTERN1;
    public static final /* enum */ WeekShowPattern PATTERN2;
    public static final /* enum */ WeekShowPattern PATTERN3;
    public static final /* enum */ WeekShowPattern PATTERN4;
    public static final /* enum */ WeekShowPattern PATTERN5;
    private static final Map<String, WeekShowPattern> finder;
    private String key;
    private String title;
    public static final String TAG_VALUE = "value";
    public static final String TAG_TITLE = "title";
    private static final /* synthetic */ WeekShowPattern[] $VALUES;

    public static WeekShowPattern[] values() {
        return (WeekShowPattern[])$VALUES.clone();
    }

    public static WeekShowPattern valueOf(String name) {
        return Enum.valueOf(WeekShowPattern.class, name);
    }

    private WeekShowPattern(String key, String title) {
        this.key = key;
        this.title = title;
    }

    public String getKey() {
        return this.key;
    }

    public String getTitle() {
        return this.title;
    }

    public String getShowText(int year, int week) {
        return null;
    }

    public static WeekShowPattern find(String key) {
        WeekShowPattern pattern = finder.get(key);
        if (pattern == null) {
            return PATTERN1;
        }
        return pattern;
    }

    public static JSONArray toJSON() throws JSONException {
        WeekShowPattern[] types;
        JSONArray array = new JSONArray();
        for (WeekShowPattern type : types = WeekShowPattern.values()) {
            JSONObject json = new JSONObject();
            json.put(TAG_VALUE, (Object)type.key);
            json.put(TAG_TITLE, (Object)type.title);
            array.put((Object)json);
        }
        return array;
    }

    static {
        WeekShowPattern[] values;
        PATTERN1 = new WeekShowPattern("\u7b2cw\u5468", "\u7b2c1\u5468");
        PATTERN2 = new WeekShowPattern("yyyy\u5e74\u7b2cw\u5468\uff08M/d~N/e\uff09", "2020\u5e74\u7b2c1\u5468\uff081/6~1/12\uff09");
        PATTERN3 = new WeekShowPattern("yyyy\u5e74\u7b2cww\u5468\uff08MM/dd~NN/ee\uff09", "2020\u5e74\u7b2c01\u5468\uff0801/06~01/12\uff09");
        PATTERN4 = new WeekShowPattern("yyyy\u5e74\u7b2cw\u5468", "2020\u5e74\u7b2c1\u5468");
        PATTERN5 = new WeekShowPattern("yyyy\u5e74\u7b2cww\u5468", "2020\u5e74\u7b2c01\u5468");
        $VALUES = new WeekShowPattern[]{PATTERN1, PATTERN2, PATTERN3, PATTERN4, PATTERN5};
        finder = new HashMap<String, WeekShowPattern>();
        for (WeekShowPattern pattern : values = WeekShowPattern.values()) {
            finder.put(pattern.getKey(), pattern);
        }
    }
}

