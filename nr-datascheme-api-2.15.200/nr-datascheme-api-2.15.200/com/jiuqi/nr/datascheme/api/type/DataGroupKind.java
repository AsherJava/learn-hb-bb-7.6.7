/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonCreator
 *  com.fasterxml.jackson.annotation.JsonFormat
 *  com.fasterxml.jackson.annotation.JsonFormat$Shape
 *  com.fasterxml.jackson.annotation.JsonProperty
 */
package com.jiuqi.nr.datascheme.api.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

@JsonFormat(shape=JsonFormat.Shape.OBJECT)
public enum DataGroupKind implements Serializable
{
    TABLE_GROUP(1, "\u76ee\u5f55"),
    SCHEME_GROUP(4, "\u65b9\u6848\u5206\u7ec4"),
    QUERY_SCHEME_GROUP(8, "\u67e5\u8be2\u6570\u636e\u65b9\u6848\u5206\u7ec4");

    private final int value;
    private final String title;
    private static final HashMap<Integer, DataGroupKind> MAP;
    private static final HashMap<String, DataGroupKind> TITLE_MAP;

    private DataGroupKind(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int getValue() {
        return this.value;
    }

    public String getTitle() {
        return this.title;
    }

    public static DataGroupKind valueOf(int value) {
        return MAP.get(value);
    }

    public static DataGroupKind titleOf(String title) {
        return TITLE_MAP.get(title);
    }

    @JsonCreator
    public static DataGroupKind forValues(@JsonProperty(value="value") Integer value, @JsonProperty(value="title") String title) {
        if (value == null && title == null) {
            return null;
        }
        if (value != null && title != null) {
            DataGroupKind dataGroupKind = MAP.get(value);
            DataGroupKind byTitle = TITLE_MAP.get(title);
            if (byTitle == null) {
                return null;
            }
            return byTitle.equals(dataGroupKind) ? dataGroupKind : null;
        }
        if (value != null) {
            return MAP.get(value);
        }
        return TITLE_MAP.get(title);
    }

    public static DataGroupKind[] interestType(int kind) {
        if (kind < 0) {
            return new DataGroupKind[0];
        }
        ArrayList<DataGroupKind> values = new ArrayList<DataGroupKind>(DataGroupKind.values().length);
        for (DataGroupKind value : DataGroupKind.values()) {
            if ((value.getValue() & kind) == 0) continue;
            values.add(value);
        }
        return values.toArray(new DataGroupKind[0]);
    }

    static {
        DataGroupKind[] values = DataGroupKind.values();
        MAP = new HashMap(values.length);
        TITLE_MAP = new HashMap(values.length);
        for (DataGroupKind value : values) {
            MAP.put(value.value, value);
            TITLE_MAP.put(value.title, value);
        }
    }
}

