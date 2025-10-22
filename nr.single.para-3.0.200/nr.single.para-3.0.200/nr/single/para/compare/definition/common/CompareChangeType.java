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
public enum CompareChangeType {
    CHANGE_DEFAULT(0, "\u9ed8\u8ba4"),
    CHANGE_NOFLAG(1, "\u6807\u8bc6\u4e0d\u540c"),
    CHANGE_NOEXIST(2, "\u7f51\u62a5\u65e0"),
    CHANGE_FLAGTITLESAME(3, "\u6807\u8bc6\u540d\u79f0\u76f8\u540c"),
    CHANGE_FLAGSAMENOTITLE(4, "\u6807\u8bc6\u76f8\u540c\u540d\u79f0\u4e0d\u540c"),
    CHANGE_TITLESAMENOFLAG(5, "\u540d\u79f0\u76f8\u540c\u6807\u8bc6\u4e0d\u540c"),
    CHANGE_FORMFIELDSAME(6, "\u8868\u6837\u6307\u6807\u76f8\u540c"),
    CHANGE_FORMSAMENOFIELD(7, "\u8868\u6837\u76f8\u540c\u6307\u6807\u4e0d\u540c"),
    CHANGE_FIELDSAMENOFORM(8, "\u6307\u6807\u76f8\u540c\u8868\u6837\u4e0d\u540c"),
    CHANGE_FORMFIELDNOSAME(9, "\u6307\u6807\u8868\u6837\u4e0d\u76f8\u540c"),
    CHANGE_FIELDATTRCHANGE(10, "\u6307\u6807\u5c5e\u6027\u53d8\u5316"),
    CHANGE_PRINTATTRCHANGE(11, "\u6253\u5370\u6a21\u677f\u53d8\u5316"),
    CHANGE_PRINTATTRNOCHANGE(12, "\u6253\u5370\u6a21\u677f\u76f8\u540c"),
    CHANGE_SAME(13, "\u76f8\u540c"),
    CHANGE_NOSAME(14, "\u4e0d\u76f8\u540c"),
    CHANGE_FLAGSAME(15, "\u6807\u8bc6\u76f8\u540c"),
    CHANGE_FORMNOEXIST_FIELDSAME(20, "\u8868\u6837\u65e0\u6307\u6807\u76f8\u540c"),
    CHANGE_FORMNOEXIST_FIELDNOSAME(21, "\u8868\u6837\u65e0\u6307\u6807\u4e0d\u540c");

    private final int value;
    private final String title;
    private static final HashMap<Integer, CompareChangeType> MAP;
    private static final HashMap<String, CompareChangeType> TITLE_MAP;

    private CompareChangeType(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int getValue() {
        return this.value;
    }

    public String getTitle() {
        return this.title;
    }

    public static CompareChangeType valueOf(int value) {
        return MAP.get(value);
    }

    public static CompareChangeType titleOf(String title) {
        return TITLE_MAP.get(title);
    }

    @JsonCreator
    public static CompareChangeType forValues(@JsonProperty(value="value") Integer value, @JsonProperty(value="title") String title) {
        if (value == null && title == null) {
            return null;
        }
        if (value != null && title != null) {
            CompareChangeType compareStatusType = MAP.get(value);
            CompareChangeType byTitle = TITLE_MAP.get(title);
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

    public static CompareChangeType[] interestType(int kind) {
        if (kind < 0) {
            return new CompareChangeType[0];
        }
        ArrayList<CompareChangeType> values = new ArrayList<CompareChangeType>(CompareChangeType.values().length);
        for (CompareChangeType value : CompareChangeType.values()) {
            if ((value.getValue() & kind) == 0) continue;
            values.add(value);
        }
        return values.toArray(new CompareChangeType[0]);
    }

    static {
        CompareChangeType[] values = CompareChangeType.values();
        MAP = new HashMap(values.length);
        TITLE_MAP = new HashMap(values.length);
        for (CompareChangeType value : values) {
            MAP.put(value.value, value);
            TITLE_MAP.put(value.title, value);
        }
    }
}

