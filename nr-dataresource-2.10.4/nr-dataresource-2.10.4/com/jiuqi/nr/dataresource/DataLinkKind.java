/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonCreator
 *  com.fasterxml.jackson.annotation.JsonProperty
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 */
package com.jiuqi.nr.dataresource;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import java.util.ArrayList;
import java.util.HashMap;

public enum DataLinkKind {
    FIELD_ZB_LINK(DataFieldKind.FIELD_ZB.getValue(), "\u6307\u6807\u94fe\u63a5"),
    FIELD_LINK(DataFieldKind.FIELD.getValue(), "\u5b57\u6bb5\u94fe\u63a5");

    private final int value;
    private final String title;
    private static final HashMap<Integer, DataLinkKind> MAP;
    private static final HashMap<String, DataLinkKind> TITLE_MAP;

    private DataLinkKind(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int getValue() {
        return this.value;
    }

    public String getTitle() {
        return this.title;
    }

    public static DataLinkKind valueOf(int value) {
        return MAP.get(value);
    }

    public static DataLinkKind titleOf(String title) {
        return TITLE_MAP.get(title);
    }

    @JsonCreator
    public static DataLinkKind forValues(@JsonProperty(value="value") Integer value, @JsonProperty(value="title") String title) {
        if (value == null && title == null) {
            return null;
        }
        if (value != null && title != null) {
            DataLinkKind groupKind = MAP.get(value);
            DataLinkKind byTitle = TITLE_MAP.get(title);
            if (byTitle == null) {
                return null;
            }
            return byTitle.equals((Object)groupKind) ? groupKind : null;
        }
        if (value != null) {
            return MAP.get(value);
        }
        return TITLE_MAP.get(title);
    }

    public static DataLinkKind[] interestType(int kind) {
        if (kind < 0) {
            return new DataLinkKind[0];
        }
        ArrayList<DataLinkKind> values = new ArrayList<DataLinkKind>(DataLinkKind.values().length);
        for (DataLinkKind value : DataLinkKind.values()) {
            if ((value.getValue() & kind) == 0) continue;
            values.add(value);
        }
        return values.toArray(new DataLinkKind[0]);
    }

    static {
        DataLinkKind[] values = DataLinkKind.values();
        MAP = new HashMap(values.length);
        TITLE_MAP = new HashMap(values.length);
        for (DataLinkKind value : values) {
            MAP.put(value.value, value);
            TITLE_MAP.put(value.title, value);
        }
    }
}

