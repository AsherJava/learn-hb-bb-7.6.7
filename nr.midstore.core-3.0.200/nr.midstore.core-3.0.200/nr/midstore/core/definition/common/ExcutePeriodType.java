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
public enum ExcutePeriodType {
    EXCUTEPERIOD_CURRENT(0, "\u5f53\u524d"),
    EXCUTEPERIOD_LAST(1, "\u4e0a\u4e00\u671f"),
    EXCUTEPERIOD_APPOINT(2, "\u6307\u5b9a");

    private final int value;
    private final String title;
    private static final HashMap<Integer, ExcutePeriodType> MAP;
    private static final HashMap<String, ExcutePeriodType> TITLE_MAP;

    private ExcutePeriodType(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int getValue() {
        return this.value;
    }

    public String getTitle() {
        return this.title;
    }

    public static ExcutePeriodType valueOf(int value) {
        return MAP.get(value);
    }

    public static ExcutePeriodType titleOf(String title) {
        return TITLE_MAP.get(title);
    }

    @JsonCreator
    public static ExcutePeriodType forValues(@JsonProperty(value="value") Integer value, @JsonProperty(value="title") String title) {
        if (value == null && title == null) {
            return null;
        }
        if (value != null && title != null) {
            ExcutePeriodType compareStatusType = MAP.get(value);
            ExcutePeriodType byTitle = TITLE_MAP.get(title);
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

    public static ExcutePeriodType[] interestType(int kind) {
        if (kind < 0) {
            return new ExcutePeriodType[0];
        }
        ArrayList<ExcutePeriodType> values = new ArrayList<ExcutePeriodType>(ExcutePeriodType.values().length);
        for (ExcutePeriodType value : ExcutePeriodType.values()) {
            if ((value.getValue() & kind) == 0) continue;
            values.add(value);
        }
        return values.toArray(new ExcutePeriodType[0]);
    }

    static {
        ExcutePeriodType[] values = ExcutePeriodType.values();
        MAP = new HashMap(values.length);
        TITLE_MAP = new HashMap(values.length);
        for (ExcutePeriodType value : values) {
            MAP.put(value.value, value);
            TITLE_MAP.put(value.title, value);
        }
    }
}

