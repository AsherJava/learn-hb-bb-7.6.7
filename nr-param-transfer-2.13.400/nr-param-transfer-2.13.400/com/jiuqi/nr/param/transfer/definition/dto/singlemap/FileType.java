/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonCreator
 *  com.fasterxml.jackson.annotation.JsonValue
 */
package com.jiuqi.nr.param.transfer.definition.dto.singlemap;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.HashMap;

public enum FileType {
    ENTITY(1),
    FORMULA(2),
    JIO(4),
    ZB(8);

    private final int value;
    private static final HashMap<Integer, FileType> MAP;

    private FileType(int value) {
        this.value = value;
    }

    @JsonValue
    public int getValue() {
        return this.value;
    }

    @JsonCreator
    public static FileType valueOf(int value) {
        return MAP.get(value);
    }

    static {
        FileType[] values = FileType.values();
        MAP = new HashMap(values.length);
        for (FileType value : values) {
            MAP.put(value.value, value);
        }
    }
}

