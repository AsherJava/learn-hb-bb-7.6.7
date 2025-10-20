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

public enum BooleanShowPattern {
    PATTERN1("\u662f/\u5426", "\u662f/\u5426"),
    PATTERN2("true/false", "true/false");

    private static final Map<String, BooleanShowPattern> finder;
    private String key;
    private String title;
    public static final String TAG_VALUE = "value";
    public static final String TAG_TITLE = "title";

    private BooleanShowPattern(String key, String title) {
        this.key = key;
        this.title = title;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public static JSONArray toJSON() throws JSONException {
        BooleanShowPattern[] types;
        JSONArray array = new JSONArray();
        for (BooleanShowPattern type : types = BooleanShowPattern.values()) {
            JSONObject json = new JSONObject();
            json.put(TAG_VALUE, (Object)type.key);
            json.put(TAG_TITLE, (Object)type.title);
            array.put((Object)json);
        }
        return array;
    }

    static {
        finder = new HashMap<String, BooleanShowPattern>();
        for (BooleanShowPattern p : BooleanShowPattern.values()) {
            finder.put(p.getKey(), p);
        }
    }
}

