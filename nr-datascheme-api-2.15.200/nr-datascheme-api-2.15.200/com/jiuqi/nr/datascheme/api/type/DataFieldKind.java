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
public enum DataFieldKind implements Serializable
{
    FIELD_ZB(1, "\u6307\u6807"),
    FIELD(4, "\u5b57\u6bb5"),
    TABLE_FIELD_DIM(8, "\u8868\u5185\u7ef4\u5ea6"),
    PUBLIC_FIELD_DIM(16, "\u516c\u5171\u7ef4\u5ea6"),
    BUILT_IN_FIELD(32, "\u5185\u7f6e\u5b57\u6bb5");

    private final int value;
    private final String title;
    private static final HashMap<Integer, DataFieldKind> MAP;
    private static final HashMap<String, DataFieldKind> TITLE_MAP;
    public static int TABLE;
    public static int DETAIL;
    public static int FMDM_TABLE;

    private DataFieldKind(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int getValue() {
        return this.value;
    }

    public String getTitle() {
        return this.title;
    }

    public static DataFieldKind valueOf(int value) {
        return MAP.get(value);
    }

    public static DataFieldKind titleOf(String title) {
        return TITLE_MAP.get(title);
    }

    public static DataFieldKind[] interestType(int type) {
        if (type < 0) {
            return new DataFieldKind[0];
        }
        ArrayList<DataFieldKind> values = new ArrayList<DataFieldKind>(DataFieldKind.values().length);
        for (DataFieldKind value : DataFieldKind.values()) {
            if ((value.getValue() & type) == 0) continue;
            values.add(value);
        }
        return values.toArray(new DataFieldKind[0]);
    }

    @JsonCreator
    public static DataFieldKind forValues(@JsonProperty(value="value") Integer value, @JsonProperty(value="title") String title) {
        if (value == null && title == null) {
            return null;
        }
        if (value != null && title != null) {
            DataFieldKind dataFieldKind = MAP.get(value);
            DataFieldKind byTitle = TITLE_MAP.get(title);
            if (byTitle == null) {
                return null;
            }
            return byTitle.equals(dataFieldKind) ? dataFieldKind : null;
        }
        if (value != null) {
            return MAP.get(value);
        }
        return TITLE_MAP.get(title);
    }

    static {
        DataFieldKind[] values = DataFieldKind.values();
        MAP = new HashMap(values.length);
        TITLE_MAP = new HashMap(values.length);
        for (DataFieldKind value : values) {
            MAP.put(value.value, value);
            TITLE_MAP.put(value.title, value);
        }
        TABLE = FIELD_ZB.getValue() | BUILT_IN_FIELD.getValue() | PUBLIC_FIELD_DIM.getValue();
        DETAIL = FIELD.getValue() | TABLE_FIELD_DIM.getValue() | PUBLIC_FIELD_DIM.getValue() | BUILT_IN_FIELD.getValue();
        FMDM_TABLE = BUILT_IN_FIELD.getValue();
    }
}

