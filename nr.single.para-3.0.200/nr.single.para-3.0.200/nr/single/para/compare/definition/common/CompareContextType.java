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
public enum CompareContextType {
    CONTEXT_CODE(1, "\u6309\u6807\u8bc6\u5339\u914d"),
    CONTEXT_TITLE(2, "\u6309\u540d\u79f0\u5339\u914d"),
    CONTEXT_INIMAP(3, "\u6309INI\u6620\u5c04\u5339\u914d"),
    CONTEXT_NOMAP(4, "\u4e0d\u5339\u914d"),
    CONTEXT_GLOBALCODE(5, "\u6309\u5168\u5c40\u6807\u8bc6\u5339\u914d"),
    CONTEXT_CODEFIRST_TITLE(6, "\u6309\u5148\u6807\u8bc6\u540e\u540d\u79f0\u5339\u914d"),
    CONTEXT_TITLEFIRST_CODE(7, "\u6309\u5148\u540d\u79f0\u540e\u6807\u8bc6\u5339\u914d");

    private final int value;
    private final String title;
    private static final HashMap<Integer, CompareContextType> MAP;
    private static final HashMap<String, CompareContextType> TITLE_MAP;

    private CompareContextType(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int getValue() {
        return this.value;
    }

    public String getTitle() {
        return this.title;
    }

    public static CompareContextType valueOf(int value) {
        return MAP.get(value);
    }

    public static CompareContextType titleOf(String title) {
        return TITLE_MAP.get(title);
    }

    @JsonCreator
    public static CompareContextType forValues(@JsonProperty(value="value") Integer value, @JsonProperty(value="title") String title) {
        if (value == null && title == null) {
            return null;
        }
        if (value != null && title != null) {
            CompareContextType compareStatusType = MAP.get(value);
            CompareContextType byTitle = TITLE_MAP.get(title);
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

    public static CompareContextType[] interestType(int kind) {
        if (kind < 0) {
            return new CompareContextType[0];
        }
        ArrayList<CompareContextType> values = new ArrayList<CompareContextType>(CompareContextType.values().length);
        for (CompareContextType value : CompareContextType.values()) {
            if ((value.getValue() & kind) == 0) continue;
            values.add(value);
        }
        return values.toArray(new CompareContextType[0]);
    }

    static {
        CompareContextType[] values = CompareContextType.values();
        MAP = new HashMap(values.length);
        TITLE_MAP = new HashMap(values.length);
        for (CompareContextType value : values) {
            MAP.put(value.value, value);
            TITLE_MAP.put(value.title, value);
        }
    }
}

