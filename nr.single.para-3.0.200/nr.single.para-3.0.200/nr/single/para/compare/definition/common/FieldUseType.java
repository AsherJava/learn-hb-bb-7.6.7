/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonCreator
 *  com.fasterxml.jackson.annotation.JsonFormat
 *  com.fasterxml.jackson.annotation.JsonFormat$Shape
 *  com.fasterxml.jackson.annotation.JsonProperty
 */
package nr.single.para.compare.definition.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.HashMap;

@JsonFormat(shape=JsonFormat.Shape.OBJECT)
public enum FieldUseType {
    USE_BBLX(1, "\u62a5\u8868\u7c7b\u578b"),
    USE_QYDM(2, "\u4f01\u4e1a\u4ee3\u7801"),
    USE_QYMC(3, "\u4f01\u4e1a\u540d\u79f0"),
    USE_SJQYDM(4, "\u4e0a\u7ea7\u4f01\u4e1a\u4ee3\u7801"),
    USE_JTZB(5, "\u96c6\u56e2\u603b\u90e8\u4ee3\u7801"),
    USE_SNDM(6, "\u4e0a\u5e74\u4ee3\u7801"),
    USE_XBYS(7, "\u65b0\u62a5\u56e0\u7d20"),
    USE_TREELEVEL(8, "\u6811\u5f62\u7ed3\u6784\u7ea7\u6b21"),
    USE_ZSHS(9, "\u76f4\u5c5e\u4e0b\u7ea7\u6237\u6570"),
    USE_SQ(9, "\u65f6\u671f"),
    USE_OTHER(10, "");

    private final int value;
    private final String title;
    private static final HashMap<Integer, FieldUseType> MAP;
    private static final HashMap<String, FieldUseType> TITLE_MAP;

    private FieldUseType(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int getValue() {
        return this.value;
    }

    public String getTitle() {
        return this.title;
    }

    public static FieldUseType valueOf(int value) {
        return MAP.get(value);
    }

    public static FieldUseType titleOf(String title) {
        return TITLE_MAP.get(title);
    }

    @JsonCreator
    public static FieldUseType forValues(@JsonProperty(value="value") Integer value, @JsonProperty(value="title") String title) {
        if (value == null && title == null) {
            return null;
        }
        if (value != null && title != null) {
            FieldUseType compareStatusType = MAP.get(value);
            FieldUseType byTitle = TITLE_MAP.get(title);
            if (byTitle == null) {
                return null;
            }
            return byTitle.equals((Object)compareStatusType) ? compareStatusType : null;
        }
        if (value != null) {
            return MAP.get(value);
        }
        return TITLE_MAP.get(title);
    }

    public static FieldUseType[] interestType(int kind) {
        if (kind < 0) {
            return new FieldUseType[0];
        }
        ArrayList<FieldUseType> values = new ArrayList<FieldUseType>(FieldUseType.values().length);
        for (FieldUseType value : FieldUseType.values()) {
            if ((value.getValue() & kind) == 0) continue;
            values.add(value);
        }
        return values.toArray(new FieldUseType[0]);
    }

    static {
        FieldUseType[] values = FieldUseType.values();
        MAP = new HashMap(values.length);
        TITLE_MAP = new HashMap(values.length);
        for (FieldUseType value : values) {
            MAP.put(value.value, value);
            TITLE_MAP.put(value.title, value);
        }
    }
}

