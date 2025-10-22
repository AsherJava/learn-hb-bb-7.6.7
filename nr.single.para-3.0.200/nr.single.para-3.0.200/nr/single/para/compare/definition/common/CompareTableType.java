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
public enum CompareTableType {
    TABLE_ORG(1, "\u7ec4\u7ec7\u673a\u6784\u7c7b\u578b"),
    TABLE_FIX(2, "\u6307\u6807\u8868"),
    TABLE_MDINFO(3, "\u5355\u4f4d\u4fe1\u606f\u8868");

    private final int value;
    private final String title;
    private static final HashMap<Integer, CompareTableType> MAP;
    private static final HashMap<String, CompareTableType> TITLE_MAP;

    private CompareTableType(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int getValue() {
        return this.value;
    }

    public String getTitle() {
        return this.title;
    }

    public static CompareTableType valueOf(int value) {
        return MAP.get(value);
    }

    public static CompareTableType titleOf(String title) {
        return TITLE_MAP.get(title);
    }

    @JsonCreator
    public static CompareTableType forValues(@JsonProperty(value="value") Integer value, @JsonProperty(value="title") String title) {
        if (value == null && title == null) {
            return null;
        }
        if (value != null && title != null) {
            CompareTableType compareStatusType = MAP.get(value);
            CompareTableType byTitle = TITLE_MAP.get(title);
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

    public static CompareTableType[] interestType(int kind) {
        if (kind < 0) {
            return new CompareTableType[0];
        }
        ArrayList<CompareTableType> values = new ArrayList<CompareTableType>(CompareTableType.values().length);
        for (CompareTableType value : CompareTableType.values()) {
            if ((value.getValue() & kind) == 0) continue;
            values.add(value);
        }
        return values.toArray(new CompareTableType[0]);
    }

    static {
        CompareTableType[] values = CompareTableType.values();
        MAP = new HashMap(values.length);
        TITLE_MAP = new HashMap(values.length);
        for (CompareTableType value : values) {
            MAP.put(value.value, value);
            TITLE_MAP.put(value.title, value);
        }
    }
}

