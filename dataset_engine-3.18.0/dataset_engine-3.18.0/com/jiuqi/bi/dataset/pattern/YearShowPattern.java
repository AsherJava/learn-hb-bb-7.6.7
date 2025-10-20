/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.bi.dataset.pattern;

import com.jiuqi.bi.dataset.pattern.TimeParser;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class YearShowPattern
extends Enum<YearShowPattern> {
    public static final /* enum */ YearShowPattern PATTERN1;
    public static final /* enum */ YearShowPattern PATTERN2;
    public static final /* enum */ YearShowPattern PATTERN3;
    private static final Map<String, YearShowPattern> finder;
    private String key;
    private String title;
    private TimeParser p;
    public static final String TAG_VALUE = "value";
    public static final String TAG_TITLE = "title";
    private static final /* synthetic */ YearShowPattern[] $VALUES;

    public static YearShowPattern[] values() {
        return (YearShowPattern[])$VALUES.clone();
    }

    public static YearShowPattern valueOf(String name) {
        return Enum.valueOf(YearShowPattern.class, name);
    }

    private YearShowPattern(String key, String title, TimeParser p) {
        this.key = key;
        this.title = title;
        this.p = p;
    }

    public String getKey() {
        return this.key;
    }

    public String getTitle() {
        return this.title;
    }

    public String getShowText(int year) {
        return this.p.parse(year);
    }

    public static YearShowPattern find(String key) {
        YearShowPattern pattern = finder.get(key);
        if (pattern == null) {
            return PATTERN1;
        }
        return pattern;
    }

    public static JSONArray toJSON() throws JSONException {
        YearShowPattern[] types;
        JSONArray array = new JSONArray();
        for (YearShowPattern type : types = YearShowPattern.values()) {
            JSONObject json = new JSONObject();
            json.put(TAG_VALUE, (Object)type.key);
            json.put(TAG_TITLE, (Object)type.title);
            array.put((Object)json);
        }
        return array;
    }

    static {
        YearShowPattern[] values;
        PATTERN1 = new YearShowPattern("yyyy\u5e74", "2014\u5e74", new TimeParser(){

            @Override
            public String parse(Object obj) {
                Integer i = (Integer)obj;
                return i + "\u5e74";
            }
        });
        PATTERN2 = new YearShowPattern("yyyy", "2014", new TimeParser(){

            @Override
            public String parse(Object obj) {
                Integer i = (Integer)obj;
                return String.valueOf(i);
            }
        });
        PATTERN3 = new YearShowPattern("yy\u5e74", "14\u5e74", new TimeParser(){

            @Override
            public String parse(Object obj) {
                Integer i = (Integer)obj;
                return i % 100 + "\u5e74";
            }
        });
        $VALUES = new YearShowPattern[]{PATTERN1, PATTERN2, PATTERN3};
        finder = new HashMap<String, YearShowPattern>();
        for (YearShowPattern pattern : values = YearShowPattern.values()) {
            finder.put(pattern.getKey(), pattern);
        }
    }
}

