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

public enum XunShowPattern {
    PATTERN1("TTT", "\u4e0a/\u4e2d/\u4e0b\u65ec", new TimeParser(){

        @Override
        public String parse(Object obj) {
            Integer i = (Integer)obj;
            if (i <= 10) {
                return "\u4e0a\u65ec";
            }
            if (i <= 20) {
                return "\u4e2d\u65ec";
            }
            return "\u4e0b\u65ec";
        }
    }),
    PATTERN2("yyyy\u5e74MM\u6708TTT", "2014\u5e7401\u6708\u4e0a/\u4e2d/\u4e0b\u65ec", new TimeParser(){

        @Override
        public String parse(Object obj) {
            Integer i = (Integer)obj;
            if (i <= 10) {
                return "2014\u5e7401\u6708\u4e0a\u65ec";
            }
            if (i <= 20) {
                return "2014\u5e7401\u6708\u4e2d\u65ec";
            }
            return "2014\u5e7401\u6708\u4e0b\u65ec";
        }
    }),
    PATTERN3("yyyy\u5e74M\u6708TTT", "2014\u5e741\u6708\u4e0a/\u4e2d/\u4e0b\u65ec", new TimeParser(){

        @Override
        public String parse(Object obj) {
            Integer i = (Integer)obj;
            if (i <= 10) {
                return "2014\u5e741\u6708\u4e0a\u65ec";
            }
            if (i <= 20) {
                return "2014\u5e741\u6708\u4e2d\u65ec";
            }
            return "2014\u5e741\u6708\u4e0b\u65ec";
        }
    });

    private static final Map<String, XunShowPattern> finder;
    private String key;
    private String title;
    private TimeParser p;
    public static final String TAG_VALUE = "value";
    public static final String TAG_TITLE = "title";

    private XunShowPattern(String key, String title, TimeParser p) {
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

    public String getShowText(int day) {
        return this.p.parse(day);
    }

    public static XunShowPattern find(String key) {
        XunShowPattern p = finder.get(key);
        if (p == null) {
            return PATTERN1;
        }
        return p;
    }

    public static JSONArray toJSON() throws JSONException {
        XunShowPattern[] types;
        JSONArray array = new JSONArray();
        for (XunShowPattern type : types = XunShowPattern.values()) {
            JSONObject json = new JSONObject();
            json.put(TAG_VALUE, (Object)type.key);
            json.put(TAG_TITLE, (Object)type.title);
            array.put((Object)json);
        }
        return array;
    }

    static {
        finder = new HashMap<String, XunShowPattern>();
        for (XunShowPattern p : XunShowPattern.values()) {
            finder.put(p.getKey(), p);
        }
    }
}

