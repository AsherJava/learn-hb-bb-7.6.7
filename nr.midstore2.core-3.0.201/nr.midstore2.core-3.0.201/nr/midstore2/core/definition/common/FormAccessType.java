/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonCreator
 *  com.fasterxml.jackson.annotation.JsonFormat
 *  com.fasterxml.jackson.annotation.JsonFormat$Shape
 *  com.fasterxml.jackson.annotation.JsonProperty
 */
package nr.midstore2.core.definition.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.HashMap;

@JsonFormat(shape=JsonFormat.Shape.OBJECT)
public enum FormAccessType {
    FORMACCESS_VISIBLE(0, "\u8868\u5355\u53ef\u89c1"),
    FORMACCESS_READ(1, "\u8868\u5355\u6570\u636e\u53ef\u8bfb"),
    FORMACCESS_WRITE(2, "\u8868\u5355\u6570\u636e\u53ef\u5199");

    private final int value;
    private final String title;
    private static final HashMap<Integer, FormAccessType> MAP;
    private static final HashMap<String, FormAccessType> TITLE_MAP;

    private FormAccessType(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int getValue() {
        return this.value;
    }

    public String getTitle() {
        return this.title;
    }

    public static FormAccessType valueOf(int value) {
        return MAP.get(value);
    }

    public static FormAccessType titleOf(String title) {
        return TITLE_MAP.get(title);
    }

    @JsonCreator
    public static FormAccessType forValues(@JsonProperty(value="value") Integer value, @JsonProperty(value="title") String title) {
        if (value == null && title == null) {
            return null;
        }
        if (value != null && title != null) {
            FormAccessType compareStatusType = MAP.get(value);
            FormAccessType byTitle = TITLE_MAP.get(title);
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

    public static FormAccessType[] interestType(int kind) {
        if (kind < 0) {
            return new FormAccessType[0];
        }
        ArrayList<FormAccessType> values = new ArrayList<FormAccessType>(FormAccessType.values().length);
        for (FormAccessType value : FormAccessType.values()) {
            if ((value.getValue() & kind) == 0) continue;
            values.add(value);
        }
        return values.toArray(new FormAccessType[0]);
    }

    static {
        FormAccessType[] values = FormAccessType.values();
        MAP = new HashMap(values.length);
        TITLE_MAP = new HashMap(values.length);
        for (FormAccessType value : values) {
            MAP.put(value.value, value);
            TITLE_MAP.put(value.title, value);
        }
    }
}

