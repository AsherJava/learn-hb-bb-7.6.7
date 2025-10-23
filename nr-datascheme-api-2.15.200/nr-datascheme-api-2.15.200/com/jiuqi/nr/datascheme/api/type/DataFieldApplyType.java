/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonCreator
 *  com.fasterxml.jackson.annotation.JsonFormat
 *  com.fasterxml.jackson.annotation.JsonFormat$Shape
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.fasterxml.jackson.annotation.JsonProperty
 *  com.jiuqi.nvwa.definition.common.ApplyType
 */
package com.jiuqi.nr.datascheme.api.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jiuqi.nvwa.definition.common.ApplyType;
import java.io.Serializable;
import java.util.HashMap;

@JsonFormat(shape=JsonFormat.Shape.OBJECT)
public enum DataFieldApplyType implements Serializable
{
    NONE(ApplyType.NONE, "\u65e0"),
    PERIODIC(ApplyType.PERIODIC, "\u65f6\u671f\u6570"),
    TIME_POINT(ApplyType.TIME_POINT, "\u65f6\u70b9\u6570"),
    OPENING_BALANCE(ApplyType.OPENING_BALANCE, "\u671f\u521d\u6570"),
    CLOSING_BALANCE(ApplyType.CLOSING_BALANCE, "\u671f\u672b\u6570"),
    SUM(ApplyType.SUM, "\u7d2f\u8ba1\u6570");

    private static final long serialVersionUID = -3107789829156241121L;
    private final String title;
    @JsonIgnore
    private final ApplyType applyType;
    private static final HashMap<Integer, DataFieldApplyType> MAP;
    private static final HashMap<String, DataFieldApplyType> TITLE_MAP;

    private DataFieldApplyType(ApplyType applyType, String title) {
        this.title = title;
        this.applyType = applyType;
    }

    public int getValue() {
        return this.applyType.getValue();
    }

    public String getTitle() {
        return this.title;
    }

    public ApplyType getApplyType() {
        return this.applyType;
    }

    public static DataFieldApplyType valueOf(int value) {
        return MAP.get(value);
    }

    public static DataFieldApplyType titleOf(String title) {
        return TITLE_MAP.get(title);
    }

    @JsonCreator
    public static DataFieldApplyType forValues(@JsonProperty(value="value") Integer value, @JsonProperty(value="title") String title) {
        if (value == null && title == null) {
            return null;
        }
        if (value != null && title != null) {
            DataFieldApplyType applyType = MAP.get(value);
            DataFieldApplyType byTitle = TITLE_MAP.get(title);
            if (byTitle == null) {
                return null;
            }
            return byTitle.equals(applyType) ? applyType : null;
        }
        if (value != null) {
            return MAP.get(value);
        }
        return TITLE_MAP.get(title);
    }

    static {
        DataFieldApplyType[] values = DataFieldApplyType.values();
        MAP = new HashMap(values.length);
        TITLE_MAP = new HashMap(values.length);
        for (DataFieldApplyType value : values) {
            MAP.put(value.getValue(), value);
            TITLE_MAP.put(value.title, value);
        }
    }
}

