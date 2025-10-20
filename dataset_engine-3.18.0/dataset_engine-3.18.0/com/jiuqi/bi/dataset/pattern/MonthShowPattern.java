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

public final class MonthShowPattern
extends Enum<MonthShowPattern> {
    public static final /* enum */ MonthShowPattern PATTERN1;
    public static final /* enum */ MonthShowPattern PATTERN2;
    public static final /* enum */ MonthShowPattern PATTERN3;
    public static final /* enum */ MonthShowPattern PATTERN4;
    public static final /* enum */ MonthShowPattern PATTERN5;
    public static final /* enum */ MonthShowPattern PATTERN6;
    public static final /* enum */ MonthShowPattern PATTERN7;
    public static final /* enum */ MonthShowPattern PATTERN8;
    public static final /* enum */ MonthShowPattern PATTERN9;
    public static final /* enum */ MonthShowPattern PATTERN10;
    private static final Map<String, MonthShowPattern> finder;
    private String key;
    private String title;
    private TimeParser p;
    public static final String TAG_VALUE = "value";
    public static final String TAG_TITLE = "title";
    private static final /* synthetic */ MonthShowPattern[] $VALUES;

    public static MonthShowPattern[] values() {
        return (MonthShowPattern[])$VALUES.clone();
    }

    public static MonthShowPattern valueOf(String name) {
        return Enum.valueOf(MonthShowPattern.class, name);
    }

    private MonthShowPattern(String key, String title, TimeParser p) {
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

    public String getShowText(int month) {
        return this.p.parse(month);
    }

    public static MonthShowPattern find(String key) {
        MonthShowPattern pattern = finder.get(key);
        if (pattern == null) {
            return PATTERN1;
        }
        return pattern;
    }

    public static JSONArray toJSON() throws JSONException {
        MonthShowPattern[] types;
        JSONArray array = new JSONArray();
        for (MonthShowPattern type : types = MonthShowPattern.values()) {
            JSONObject json = new JSONObject();
            json.put(TAG_VALUE, (Object)type.key);
            json.put(TAG_TITLE, (Object)type.title);
            array.put((Object)json);
        }
        return array;
    }

    static {
        MonthShowPattern[] values;
        PATTERN1 = new MonthShowPattern("yyyy\u5e74MM\u6708", "2014\u5e7401\u6708", null);
        PATTERN2 = new MonthShowPattern("yyyy-MM", "2014-01", null);
        PATTERN3 = new MonthShowPattern("yyyy/MM", "2014/01", null);
        PATTERN4 = new MonthShowPattern("yyyy\u5e74M\u6708", "2014\u5e741\u6708", new TimeParser(){

            @Override
            public String parse(Object obj) {
                Integer i = (Integer)obj;
                return "2014\u5e74" + i + "\u6708";
            }
        });
        PATTERN5 = new MonthShowPattern("yyyy\u5e74MMM", "2014\u5e74\u4e00\u6708", new TimeParser(){
            private String[] values = new String[]{"2014\u5e74\u4e00\u6708", "2014\u5e74\u4e8c\u6708", "2014\u5e74\u4e09\u6708", "2014\u5e74\u56db\u6708", "2014\u5e74\u4e94\u6708", "2014\u5e74\u516d\u6708", "2014\u5e74\u4e03\u6708", "2014\u5e74\u516b\u6708", "2014\u5e74\u4e5d\u6708", "2014\u5e74\u5341\u6708", "2014\u5e74\u5341\u4e00\u6708", "2014\u5e74\u5341\u4e8c\u6708"};

            @Override
            public String parse(Object obj) {
                Integer i = (Integer)obj;
                if (i > 0 && i <= 12) {
                    return this.values[i - 1];
                }
                return String.valueOf(i);
            }
        });
        PATTERN6 = new MonthShowPattern("M", "1", new TimeParser(){

            @Override
            public String parse(Object obj) {
                Integer i = (Integer)obj;
                return String.valueOf(i);
            }
        });
        PATTERN7 = new MonthShowPattern("M\u6708", "1\u6708", new TimeParser(){

            @Override
            public String parse(Object obj) {
                Integer i = (Integer)obj;
                return i + "\u6708";
            }
        });
        PATTERN8 = new MonthShowPattern("MMM", "\u4e00\u6708", new TimeParser(){
            private String[] values = new String[]{"\u4e00\u6708", "\u4e8c\u6708", "\u4e09\u6708", "\u56db\u6708", "\u4e94\u6708", "\u516d\u6708", "\u4e03\u6708", "\u516b\u6708", "\u4e5d\u6708", "\u5341\u6708", "\u5341\u4e00\u6708", "\u5341\u4e8c\u6708"};

            @Override
            public String parse(Object obj) {
                Integer i = (Integer)obj;
                if (i > 0 && i <= 12) {
                    return this.values[i - 1];
                }
                return String.valueOf(i);
            }
        });
        PATTERN9 = new MonthShowPattern("MM", "01", new TimeParser(){

            @Override
            public String parse(Object obj) {
                Integer i = (Integer)obj;
                if (i < 10) {
                    return "0" + String.valueOf(obj);
                }
                return String.valueOf(obj);
            }
        });
        PATTERN10 = new MonthShowPattern("MM\u6708", "01\u6708", new TimeParser(){

            @Override
            public String parse(Object obj) {
                Integer i = (Integer)obj;
                if (i < 10) {
                    return "0" + String.valueOf(obj) + "\u6708";
                }
                return String.valueOf(obj) + "\u6708";
            }
        });
        $VALUES = new MonthShowPattern[]{PATTERN1, PATTERN2, PATTERN3, PATTERN4, PATTERN5, PATTERN6, PATTERN7, PATTERN8, PATTERN9, PATTERN10};
        finder = new HashMap<String, MonthShowPattern>();
        for (MonthShowPattern pattern : values = MonthShowPattern.values()) {
            finder.put(pattern.getKey(), pattern);
        }
    }
}

