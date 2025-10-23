/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonCreator
 *  com.fasterxml.jackson.annotation.JsonFormat
 *  com.fasterxml.jackson.annotation.JsonFormat$Shape
 *  com.fasterxml.jackson.annotation.JsonProperty
 *  com.jiuqi.nvwa.definition.common.AggrType
 */
package com.jiuqi.nr.datascheme.api.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jiuqi.nvwa.definition.common.AggrType;
import java.io.Serializable;
import java.util.HashMap;

@JsonFormat(shape=JsonFormat.Shape.OBJECT)
public enum DataFieldGatherType implements Serializable
{
    NONE(AggrType.NONE, "\u4e0d\u6c47\u603b"),
    SUM(AggrType.SUM, "\u6c42\u548c"),
    COUNT(AggrType.COUNT, "\u8ba1\u6570"),
    AVERAGE(AggrType.AVERAGE, "\u5e73\u5747"),
    MIN(AggrType.MIN, "\u6700\u5c0f"),
    MAX(AggrType.MAX, "\u6700\u5927"),
    DISTINCT_COUNT(AggrType.DISTINCT_COUNT, "\u53bb\u91cd\u8ba1\u6570");

    private final String title;
    private final AggrType aggrType;
    private static final HashMap<Integer, DataFieldGatherType> MAP;
    private static final HashMap<String, DataFieldGatherType> TITLE_MAP;

    private DataFieldGatherType(AggrType aggrType, String title) {
        this.aggrType = aggrType;
        this.title = title;
    }

    public int getValue() {
        return this.aggrType.getValue();
    }

    public String getTitle() {
        return this.title;
    }

    public static DataFieldGatherType valueOf(int value) {
        return MAP.get(value);
    }

    public static DataFieldGatherType titleOf(String title) {
        return TITLE_MAP.get(title);
    }

    @JsonCreator
    public static DataFieldGatherType forValues(@JsonProperty(value="value") Integer value, @JsonProperty(value="title") String title) {
        if (value == null && title == null) {
            return null;
        }
        if (value != null && title != null) {
            DataFieldGatherType dataFieldType = MAP.get(value);
            DataFieldGatherType byTitle = TITLE_MAP.get(title);
            if (byTitle == null) {
                return null;
            }
            return byTitle.equals(dataFieldType) ? dataFieldType : null;
        }
        if (value != null) {
            return MAP.get(value);
        }
        return TITLE_MAP.get(title);
    }

    static {
        DataFieldGatherType[] values = DataFieldGatherType.values();
        MAP = new HashMap(values.length);
        TITLE_MAP = new HashMap(values.length);
        for (DataFieldGatherType value : values) {
            MAP.put(value.aggrType.getValue(), value);
            TITLE_MAP.put(value.title, value);
        }
    }
}

