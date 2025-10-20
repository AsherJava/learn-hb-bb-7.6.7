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

public final class QuarterShowPattern
extends Enum<QuarterShowPattern> {
    public static final /* enum */ QuarterShowPattern PATTERN1;
    public static final /* enum */ QuarterShowPattern PATTERN2;
    public static final /* enum */ QuarterShowPattern PATTERN3;
    public static final /* enum */ QuarterShowPattern PATTERN4;
    public static final /* enum */ QuarterShowPattern PATTERN5;
    public static final /* enum */ QuarterShowPattern PATTERN6;
    public static final /* enum */ QuarterShowPattern PATTERN7;
    private static final Map<String, QuarterShowPattern> finder;
    private String key;
    private String title;
    private TimeParser p;
    public static final String TAG_VALUE = "value";
    public static final String TAG_TITLE = "title";
    private static final /* synthetic */ QuarterShowPattern[] $VALUES;

    public static QuarterShowPattern[] values() {
        return (QuarterShowPattern[])$VALUES.clone();
    }

    public static QuarterShowPattern valueOf(String name) {
        return Enum.valueOf(QuarterShowPattern.class, name);
    }

    private QuarterShowPattern(String key, String title, TimeParser p) {
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

    public String getShowText(int quarter) {
        return this.p.parse(quarter);
    }

    public static QuarterShowPattern find(String key) {
        QuarterShowPattern pattern = finder.get(key);
        if (pattern == null) {
            return PATTERN1;
        }
        return pattern;
    }

    public static JSONArray toJSON() throws JSONException {
        QuarterShowPattern[] types;
        JSONArray array = new JSONArray();
        for (QuarterShowPattern type : types = QuarterShowPattern.values()) {
            JSONObject json = new JSONObject();
            json.put(TAG_VALUE, (Object)type.key);
            json.put(TAG_TITLE, (Object)type.title);
            array.put((Object)json);
        }
        return array;
    }

    static {
        QuarterShowPattern[] values;
        PATTERN1 = new QuarterShowPattern("Q\u5b63\u5ea6", "1\u5b63\u5ea6", new TimeParser(){
            private String[] values = new String[]{"1\u5b63\u5ea6", "2\u5b63\u5ea6", "3\u5b63\u5ea6", "4\u5b63\u5ea6"};

            @Override
            public String parse(Object obj) {
                Integer i = (Integer)obj;
                if (i > 0 && i <= 4) {
                    return this.values[i - 1];
                }
                return String.valueOf(i);
            }
        });
        PATTERN2 = new QuarterShowPattern("\u7b2cQ\u5b63\u5ea6", "\u7b2c1\u5b63\u5ea6", new TimeParser(){
            private String[] values = new String[]{"\u7b2c1\u5b63\u5ea6", "\u7b2c2\u5b63\u5ea6", "\u7b2c3\u5b63\u5ea6", "\u7b2c4\u5b63\u5ea6"};

            @Override
            public String parse(Object obj) {
                Integer i = (Integer)obj;
                if (i > 0 && i <= 4) {
                    return this.values[i - 1];
                }
                return String.valueOf(i);
            }
        });
        PATTERN3 = new QuarterShowPattern("QQQ", "\u4e00\u5b63\u5ea6", new TimeParser(){
            private String[] values = new String[]{"\u4e00\u5b63\u5ea6", "\u4e8c\u5b63\u5ea6", "\u4e09\u5b63\u5ea6", "\u56db\u5b63\u5ea6"};

            @Override
            public String parse(Object obj) {
                Integer i = (Integer)obj;
                if (i > 0 && i <= 4) {
                    return this.values[i - 1];
                }
                return String.valueOf(i);
            }
        });
        PATTERN4 = new QuarterShowPattern("\u7b2cQQQ", "\u7b2c\u4e00\u5b63\u5ea6", new TimeParser(){
            private String[] values = new String[]{"\u7b2c\u4e00\u5b63\u5ea6", "\u7b2c\u4e8c\u5b63\u5ea6", "\u7b2c\u4e09\u5b63\u5ea6", "\u7b2c\u56db\u5b63\u5ea6"};

            @Override
            public String parse(Object obj) {
                Integer i = (Integer)obj;
                if (i > 0 && i <= 4) {
                    return this.values[i - 1];
                }
                return String.valueOf(i);
            }
        });
        PATTERN5 = new QuarterShowPattern("yyyy\u5e74QQQ", "2014\u5e74\u4e00\u5b63\u5ea6", new TimeParser(){
            private String[] values = new String[]{"2014\u5e74\u4e00\u5b63\u5ea6", "2014\u5e74\u4e8c\u5b63\u5ea6", "2014\u5e74\u4e09\u5b63\u5ea6", "2014\u5e74\u56db\u5b63\u5ea6"};

            @Override
            public String parse(Object obj) {
                Integer i = (Integer)obj;
                if (i > 0 && i <= 4) {
                    return this.values[i - 1];
                }
                return String.valueOf(i);
            }
        });
        PATTERN6 = new QuarterShowPattern("yyyy\u5e74Q\u5b63\u5ea6", "2014\u5e741\u5b63\u5ea6", new TimeParser(){
            private String[] values = new String[]{"2014\u5e741\u5b63\u5ea6", "2014\u5e742\u5b63\u5ea6", "2014\u5e743\u5b63\u5ea6", "2014\u5e744\u5b63\u5ea6"};

            @Override
            public String parse(Object obj) {
                Integer i = (Integer)obj;
                if (i > 0 && i <= 4) {
                    return this.values[i - 1];
                }
                return String.valueOf(i);
            }
        });
        PATTERN7 = new QuarterShowPattern("yyyy\u5e74\u7b2cQ\u5b63\u5ea6", "2014\u5e74\u7b2c1\u5b63\u5ea6", new TimeParser(){
            private String[] values = new String[]{"2014\u5e74\u7b2c1\u5b63\u5ea6", "2014\u5e74\u7b2c2\u5b63\u5ea6", "2014\u5e74\u7b2c3\u5b63\u5ea6", "2014\u5e74\u7b2c4\u5b63\u5ea6"};

            @Override
            public String parse(Object obj) {
                Integer i = (Integer)obj;
                if (i > 0 && i <= 4) {
                    return this.values[i - 1];
                }
                return String.valueOf(i);
            }
        });
        $VALUES = new QuarterShowPattern[]{PATTERN1, PATTERN2, PATTERN3, PATTERN4, PATTERN5, PATTERN6, PATTERN7};
        finder = new HashMap<String, QuarterShowPattern>();
        for (QuarterShowPattern pattern : values = QuarterShowPattern.values()) {
            finder.put(pattern.getKey(), pattern);
        }
    }
}

