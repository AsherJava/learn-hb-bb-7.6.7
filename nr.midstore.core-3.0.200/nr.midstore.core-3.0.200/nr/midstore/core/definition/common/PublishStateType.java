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
public enum PublishStateType {
    PUBLISHSTATE_NONE(0, "\u672a\u53d1\u5e03"),
    PUBLISHSTATE_SUCCESS(1, "\u53d1\u5e03\u6210\u529f"),
    PUBLISHSTATE_FAIL(2, "\u53d1\u5e03\u5931\u8d25");

    private final int value;
    private final String title;
    private static final HashMap<Integer, PublishStateType> MAP;
    private static final HashMap<String, PublishStateType> TITLE_MAP;

    private PublishStateType(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int getValue() {
        return this.value;
    }

    public String getTitle() {
        return this.title;
    }

    public static PublishStateType valueOf(int value) {
        return MAP.get(value);
    }

    public static PublishStateType titleOf(String title) {
        return TITLE_MAP.get(title);
    }

    @JsonCreator
    public static PublishStateType forValues(@JsonProperty(value="value") Integer value, @JsonProperty(value="title") String title) {
        if (value == null && title == null) {
            return null;
        }
        if (value != null && title != null) {
            PublishStateType compareStatusType = MAP.get(value);
            PublishStateType byTitle = TITLE_MAP.get(title);
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

    public static PublishStateType[] interestType(int kind) {
        if (kind < 0) {
            return new PublishStateType[0];
        }
        ArrayList<PublishStateType> values = new ArrayList<PublishStateType>(PublishStateType.values().length);
        for (PublishStateType value : PublishStateType.values()) {
            if ((value.getValue() & kind) == 0) continue;
            values.add(value);
        }
        return values.toArray(new PublishStateType[0]);
    }

    static {
        PublishStateType[] values = PublishStateType.values();
        MAP = new HashMap(values.length);
        TITLE_MAP = new HashMap(values.length);
        for (PublishStateType value : values) {
            MAP.put(value.value, value);
            TITLE_MAP.put(value.title, value);
        }
    }
}

