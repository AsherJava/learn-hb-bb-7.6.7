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

public enum DayShowPattern {
    PATTERN1("yyyy\u5e74MM\u6708dd\u65e5", "2014\u5e7401\u670801\u65e5", null),
    PATTERN2("yyyy-MM-dd", "2014-01-01", null),
    PATTERN3("yyyy/MM/dd", "2014/01/01", null),
    PATTERN4("yyyy\u5e74M\u6708d\u65e5", "2014\u5e741\u67081\u65e5", new TimeParser(){

        @Override
        public String parse(Object obj) {
            Integer i = (Integer)obj;
            return "2014\u5e741\u6708" + i + "\u65e5";
        }
    }),
    PATTERN5("yyyy-M-d", "2014-1-1", new TimeParser(){

        @Override
        public String parse(Object obj) {
            Integer i = (Integer)obj;
            return "2014-1-" + i;
        }
    }),
    PATTERN6("yyyy/M/dd", "2014/1/1", new TimeParser(){

        @Override
        public String parse(Object obj) {
            Integer i = (Integer)obj;
            return "2014/1/" + i;
        }
    }),
    PATTERN7("d", "1", new TimeParser(){

        @Override
        public String parse(Object obj) {
            return String.valueOf(obj);
        }
    }),
    PATTERN8("d\u65e5", "1\u65e5", new TimeParser(){
        private String[] DAY_STR = new String[]{"1\u65e5", "2\u65e5", "3\u65e5", "4\u65e5", "5\u65e5", "6\u65e5", "7\u65e5", "8\u65e5", "9\u65e5", "10\u65e5", "11\u65e5", "12\u65e5", "13\u65e5", "14\u65e5", "15\u65e5", "16\u65e5", "17\u65e5", "18\u65e5", "19\u65e5", "20\u65e5", "21\u65e5", "22\u65e5", "23\u65e5", "24\u65e5", "25\u65e5", "26\u65e5", "27\u65e5", "28\u65e5", "29\u65e5", "30\u65e5", "31\u65e5"};

        @Override
        public String parse(Object obj) {
            Integer i = (Integer)obj;
            if (i > 0 || i <= 31) {
                return this.DAY_STR[i];
            }
            return i + "\u65e5";
        }
    }),
    PATTERN9("dd", "01", new TimeParser(){

        @Override
        public String parse(Object obj) {
            Integer i = (Integer)obj;
            if (i < 10) {
                return "0" + String.valueOf(obj);
            }
            return String.valueOf(obj);
        }
    }),
    PATTERN10("dd\u65e5", "01\u65e5", new TimeParser(){

        @Override
        public String parse(Object obj) {
            Integer i = (Integer)obj;
            if (i < 10) {
                return "0" + String.valueOf(obj) + "\u65e5";
            }
            return String.valueOf(obj) + "\u65e5";
        }
    });

    private static final Map<String, DayShowPattern> finder;
    private String key;
    private String title;
    private TimeParser p;
    public static final String TAG_VALUE = "value";
    public static final String TAG_TITLE = "title";

    private DayShowPattern(String key, String title, TimeParser p) {
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

    public static DayShowPattern find(String key) {
        DayShowPattern p = finder.get(key);
        if (p == null) {
            return PATTERN1;
        }
        return p;
    }

    public static JSONArray toJSON() throws JSONException {
        DayShowPattern[] types;
        JSONArray array = new JSONArray();
        for (DayShowPattern type : types = DayShowPattern.values()) {
            JSONObject json = new JSONObject();
            json.put(TAG_VALUE, (Object)type.key);
            json.put(TAG_TITLE, (Object)type.title);
            array.put((Object)json);
        }
        return array;
    }

    static {
        finder = new HashMap<String, DayShowPattern>();
        for (DayShowPattern p : DayShowPattern.values()) {
            finder.put(p.getKey(), p);
        }
    }
}

