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
public enum StorageModeType {
    STOREMODE_DATABASE(0, "\u6570\u636e\u5e93"),
    STOREMODE_FILE(1, "\u6587\u4ef6");

    private final int value;
    private final String title;
    private static final HashMap<Integer, StorageModeType> MAP;
    private static final HashMap<String, StorageModeType> TITLE_MAP;

    private StorageModeType(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int getValue() {
        return this.value;
    }

    public String getTitle() {
        return this.title;
    }

    public static StorageModeType valueOf(int value) {
        return MAP.get(value);
    }

    public static StorageModeType titleOf(String title) {
        return TITLE_MAP.get(title);
    }

    @JsonCreator
    public static StorageModeType forValues(@JsonProperty(value="value") Integer value, @JsonProperty(value="title") String title) {
        if (value == null && title == null) {
            return null;
        }
        if (value != null && title != null) {
            StorageModeType compareStatusType = MAP.get(value);
            StorageModeType byTitle = TITLE_MAP.get(title);
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

    public static StorageModeType[] interestType(int kind) {
        if (kind < 0) {
            return new StorageModeType[0];
        }
        ArrayList<StorageModeType> values = new ArrayList<StorageModeType>(StorageModeType.values().length);
        for (StorageModeType value : StorageModeType.values()) {
            if ((value.getValue() & kind) == 0) continue;
            values.add(value);
        }
        return values.toArray(new StorageModeType[0]);
    }

    static {
        StorageModeType[] values = StorageModeType.values();
        MAP = new HashMap(values.length);
        TITLE_MAP = new HashMap(values.length);
        for (StorageModeType value : values) {
            MAP.put(value.value, value);
            TITLE_MAP.put(value.title, value);
        }
    }
}

