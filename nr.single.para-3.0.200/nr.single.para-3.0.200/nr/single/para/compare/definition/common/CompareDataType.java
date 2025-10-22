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
public enum CompareDataType {
    DATA_FMDMFIELD(1, "\u5c01\u9762\u4ee3\u7801\u6307\u6807"),
    DATA_ENUM(2, "\u679a\u4e3e\u5b57\u5178"),
    DATA_ENUMITEM(3, "\u679a\u4e3e\u9879"),
    DATA_FORM(4, "\u62a5\u8868"),
    DATA_FORMDATA(5, "\u62a5\u8868\u8868\u6837"),
    DATA_FIELD(6, "\u6307\u6807"),
    DATA_FORMULA(7, "\u516c\u5f0f"),
    DATA_FORMULA_SCHEME(14, "\u516c\u5f0f\u65b9\u6848"),
    DATA_PRINTTITEM(8, "\u6253\u5370\u6a21\u677f"),
    DATA_TASKLINK(9, "\u5173\u8054\u4efb\u52a1"),
    DATA_PRINTTSCHEME(10, "\u6253\u5370\u65b9\u6848"),
    DATA_REGION(11, "\u6570\u636e\u533a\u57df"),
    DATA_PUBLISH(12, "\u53d1\u5e03\u4fe1\u606f"),
    DATA_EXCEPTION(13, "\u5f02\u5e38\u4fe1\u606f");

    private final int value;
    private final String title;
    private static final HashMap<Integer, CompareDataType> MAP;
    private static final HashMap<String, CompareDataType> TITLE_MAP;

    private CompareDataType(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int getValue() {
        return this.value;
    }

    public String getTitle() {
        return this.title;
    }

    public static CompareDataType valueOf(int value) {
        return MAP.get(value);
    }

    public static CompareDataType titleOf(String title) {
        return TITLE_MAP.get(title);
    }

    @JsonCreator
    public static CompareDataType forValues(@JsonProperty(value="value") Integer value, @JsonProperty(value="title") String title) {
        if (value == null && title == null) {
            return null;
        }
        if (value != null && title != null) {
            CompareDataType compareStatusType = MAP.get(value);
            CompareDataType byTitle = TITLE_MAP.get(title);
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

    public static CompareDataType[] interestType(int kind) {
        if (kind < 0) {
            return new CompareDataType[0];
        }
        ArrayList<CompareDataType> values = new ArrayList<CompareDataType>(CompareDataType.values().length);
        for (CompareDataType value : CompareDataType.values()) {
            if ((value.getValue() & kind) == 0) continue;
            values.add(value);
        }
        return values.toArray(new CompareDataType[0]);
    }

    static {
        CompareDataType[] values = CompareDataType.values();
        MAP = new HashMap(values.length);
        TITLE_MAP = new HashMap(values.length);
        for (CompareDataType value : values) {
            MAP.put(value.value, value);
            TITLE_MAP.put(value.title, value);
        }
    }
}

