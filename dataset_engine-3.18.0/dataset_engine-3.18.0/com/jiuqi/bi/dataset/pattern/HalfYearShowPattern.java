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

public enum HalfYearShowPattern {
    PATTERN1("BBB", "\u4e0a\u534a\u5e74/\u4e0b\u534a\u5e74", new TimeParser(){

        @Override
        public String parse(Object obj) {
            Integer i = (Integer)obj;
            return i == 1 ? "\u4e0a\u534a\u5e74" : "\u4e0b\u534a\u5e74";
        }
    }),
    PATTERN2("yyyy\u5e74BBB", "2014\u5e74\u4e0a\u534a\u5e74/\u4e0b\u534a\u5e74", new TimeParser(){

        @Override
        public String parse(Object obj) {
            Integer i = (Integer)obj;
            return i == 1 ? "2014\u5e74\u4e0a\u534a\u5e74" : "2014\u5e74\u4e0b\u534a\u5e74";
        }
    });

    private static final Map<String, HalfYearShowPattern> finder;
    private String key;
    private String title;
    private TimeParser p;
    public static final String TAG_VALUE = "value";
    public static final String TAG_TITLE = "title";

    private HalfYearShowPattern(String key, String title, TimeParser p) {
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

    public String getShowText(int halfYear) {
        return this.p.parse(halfYear);
    }

    public static HalfYearShowPattern find(String key) {
        HalfYearShowPattern p = finder.get(key);
        if (p == null) {
            return PATTERN1;
        }
        return p;
    }

    public static JSONArray toJSON() throws JSONException {
        HalfYearShowPattern[] types;
        JSONArray array = new JSONArray();
        for (HalfYearShowPattern type : types = HalfYearShowPattern.values()) {
            JSONObject json = new JSONObject();
            json.put(TAG_VALUE, (Object)type.key);
            json.put(TAG_TITLE, (Object)type.title);
            array.put((Object)json);
        }
        return array;
    }

    static {
        finder = new HashMap<String, HalfYearShowPattern>();
        for (HalfYearShowPattern p : HalfYearShowPattern.values()) {
            finder.put(p.getKey(), p);
        }
    }
}

