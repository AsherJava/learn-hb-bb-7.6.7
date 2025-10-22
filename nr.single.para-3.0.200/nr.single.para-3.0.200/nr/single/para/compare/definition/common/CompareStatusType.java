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
public enum CompareStatusType {
    STATUS_BEFORCONFIRD(1, "\u672a\u5bfc\u5165"),
    SCHEME_CONFIRDING(2, "\u786e\u8ba4\u4e2d"),
    SCHEME_CONFIRDED(3, "\u786e\u8ba4\u5b8c\u6210"),
    SCHEME_IMPORTING(4, "\u5bfc\u5165\u4e2d"),
    SCHEME_IMPORTED(5, "\u5bfc\u5165\u6210\u529f"),
    SCHEME_IMPORTFAIL(6, "\u5bfc\u5165\u5931\u8d25"),
    SCHEME_DELETEING(7, "\u51c6\u5907\u5220\u9664"),
    SCHEME_DELETEED(8, "\u6807\u8bb0\u5220\u9664");

    private final int value;
    private final String title;
    private static final HashMap<Integer, CompareStatusType> MAP;
    private static final HashMap<String, CompareStatusType> TITLE_MAP;

    private CompareStatusType(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int getValue() {
        return this.value;
    }

    public String getTitle() {
        return this.title;
    }

    public static CompareStatusType valueOf(int value) {
        return MAP.get(value);
    }

    public static CompareStatusType titleOf(String title) {
        return TITLE_MAP.get(title);
    }

    @JsonCreator
    public static CompareStatusType forValues(@JsonProperty(value="value") Integer value, @JsonProperty(value="title") String title) {
        if (value == null && title == null) {
            return null;
        }
        if (value != null && title != null) {
            CompareStatusType compareStatusType = MAP.get(value);
            CompareStatusType byTitle = TITLE_MAP.get(title);
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

    public static CompareStatusType[] interestType(int kind) {
        if (kind < 0) {
            return new CompareStatusType[0];
        }
        ArrayList<CompareStatusType> values = new ArrayList<CompareStatusType>(CompareStatusType.values().length);
        for (CompareStatusType value : CompareStatusType.values()) {
            if ((value.getValue() & kind) == 0) continue;
            values.add(value);
        }
        return values.toArray(new CompareStatusType[0]);
    }

    static {
        CompareStatusType[] values = CompareStatusType.values();
        MAP = new HashMap(values.length);
        TITLE_MAP = new HashMap(values.length);
        for (CompareStatusType value : values) {
            MAP.put(value.value, value);
            TITLE_MAP.put(value.title, value);
        }
    }
}

