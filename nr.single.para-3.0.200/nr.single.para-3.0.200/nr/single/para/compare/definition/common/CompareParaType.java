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
public enum CompareParaType {
    PARA_FORMSCHEME_HAS(1, "\u62a5\u8868\u65b9\u6848\u5b58\u5728"),
    PARA_FORMSCHEME_NEW(2, "\u4efb\u52a1\u5b58\u5728\uff0c\u62a5\u8868\u65b9\u6848\u521b\u5efa"),
    PARA_TASK_NEW(3, "\u6570\u636e\u65b9\u6848\u5b58\u5728\uff0c\u4efb\u52a1\u3001\u62a5\u8868\u65b9\u6848\u521b\u5efa"),
    DATA_DATASCHEME_NEW(4, "\u6570\u636e\u65b9\u6848\u3001\u4efb\u52a1\u3001\u62a5\u8868\u65b9\u6848\u521b\u5efa"),
    DATA_ENTITY_NEW(5, "\u6570\u636e\u65b9\u6848\u3001\u4efb\u52a1\u3001\u62a5\u8868\u65b9\u6848\u3001\u4e3b\u4f53\u521b\u5efa");

    private final int value;
    private final String title;
    private static final HashMap<Integer, CompareParaType> MAP;
    private static final HashMap<String, CompareParaType> TITLE_MAP;

    private CompareParaType(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int getValue() {
        return this.value;
    }

    public String getTitle() {
        return this.title;
    }

    public static CompareParaType valueOf(int value) {
        return MAP.get(value);
    }

    public static CompareParaType titleOf(String title) {
        return TITLE_MAP.get(title);
    }

    @JsonCreator
    public static CompareParaType forValues(@JsonProperty(value="value") Integer value, @JsonProperty(value="title") String title) {
        if (value == null && title == null) {
            return null;
        }
        if (value != null && title != null) {
            CompareParaType compareStatusType = MAP.get(value);
            CompareParaType byTitle = TITLE_MAP.get(title);
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

    public static CompareParaType[] interestType(int kind) {
        if (kind < 0) {
            return new CompareParaType[0];
        }
        ArrayList<CompareParaType> values = new ArrayList<CompareParaType>(CompareParaType.values().length);
        for (CompareParaType value : CompareParaType.values()) {
            if ((value.getValue() & kind) == 0) continue;
            values.add(value);
        }
        return values.toArray(new CompareParaType[0]);
    }

    static {
        CompareParaType[] values = CompareParaType.values();
        MAP = new HashMap(values.length);
        TITLE_MAP = new HashMap(values.length);
        for (CompareParaType value : values) {
            MAP.put(value.value, value);
            TITLE_MAP.put(value.title, value);
        }
    }
}

