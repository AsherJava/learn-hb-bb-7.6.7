/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonCreator
 *  com.fasterxml.jackson.annotation.JsonFormat
 *  com.fasterxml.jackson.annotation.JsonFormat$Shape
 *  com.fasterxml.jackson.annotation.JsonProperty
 */
package nr.midstore.core.definition.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.HashMap;

@JsonFormat(shape=JsonFormat.Shape.OBJECT)
public enum EnumStructType {
    ENUMSTRUCT_LIST(0, "\u5217\u8868"),
    ENUMSTRUCT_TREE(1, "\u6811");

    private final int value;
    private final String title;
    private static final HashMap<Integer, EnumStructType> MAP;
    private static final HashMap<String, EnumStructType> TITLE_MAP;

    private EnumStructType(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int getValue() {
        return this.value;
    }

    public String getTitle() {
        return this.title;
    }

    public static EnumStructType valueOf(int value) {
        return MAP.get(value);
    }

    public static EnumStructType titleOf(String title) {
        return TITLE_MAP.get(title);
    }

    @JsonCreator
    public static EnumStructType forValues(@JsonProperty(value="value") Integer value, @JsonProperty(value="title") String title) {
        if (value == null && title == null) {
            return null;
        }
        if (value != null && title != null) {
            EnumStructType compareStatusType = MAP.get(value);
            EnumStructType byTitle = TITLE_MAP.get(title);
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

    public static EnumStructType[] interestType(int kind) {
        if (kind < 0) {
            return new EnumStructType[0];
        }
        ArrayList<EnumStructType> values = new ArrayList<EnumStructType>(EnumStructType.values().length);
        for (EnumStructType value : EnumStructType.values()) {
            if ((value.getValue() & kind) == 0) continue;
            values.add(value);
        }
        return values.toArray(new EnumStructType[0]);
    }

    static {
        EnumStructType[] values = EnumStructType.values();
        MAP = new HashMap(values.length);
        TITLE_MAP = new HashMap(values.length);
        for (EnumStructType value : values) {
            MAP.put(value.value, value);
            TITLE_MAP.put(value.title, value);
        }
    }
}

