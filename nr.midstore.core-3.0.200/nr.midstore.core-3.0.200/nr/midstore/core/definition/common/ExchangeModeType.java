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
public enum ExchangeModeType {
    EXCHANGE_GET(0, "\u6570\u636e\u63a5\u6536"),
    EXCHANGE_POST(1, "\u6570\u636e\u63d0\u4f9b");

    private final int value;
    private final String title;
    private static final HashMap<Integer, ExchangeModeType> MAP;
    private static final HashMap<String, ExchangeModeType> TITLE_MAP;

    private ExchangeModeType(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int getValue() {
        return this.value;
    }

    public String getTitle() {
        return this.title;
    }

    public static ExchangeModeType valueOf(int value) {
        return MAP.get(value);
    }

    public static ExchangeModeType titleOf(String title) {
        return TITLE_MAP.get(title);
    }

    @JsonCreator
    public static ExchangeModeType forValues(@JsonProperty(value="value") Integer value, @JsonProperty(value="title") String title) {
        if (value == null && title == null) {
            return null;
        }
        if (value != null && title != null) {
            ExchangeModeType compareStatusType = MAP.get(value);
            ExchangeModeType byTitle = TITLE_MAP.get(title);
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

    public static ExchangeModeType[] interestType(int kind) {
        if (kind < 0) {
            return new ExchangeModeType[0];
        }
        ArrayList<ExchangeModeType> values = new ArrayList<ExchangeModeType>(ExchangeModeType.values().length);
        for (ExchangeModeType value : ExchangeModeType.values()) {
            if ((value.getValue() & kind) == 0) continue;
            values.add(value);
        }
        return values.toArray(new ExchangeModeType[0]);
    }

    static {
        ExchangeModeType[] values = ExchangeModeType.values();
        MAP = new HashMap(values.length);
        TITLE_MAP = new HashMap(values.length);
        for (ExchangeModeType value : values) {
            MAP.put(value.value, value);
            TITLE_MAP.put(value.title, value);
        }
    }
}

