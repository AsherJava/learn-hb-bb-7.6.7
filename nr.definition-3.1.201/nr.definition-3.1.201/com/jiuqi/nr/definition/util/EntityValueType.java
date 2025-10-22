/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonCreator
 *  com.fasterxml.jackson.annotation.JsonValue
 */
package com.jiuqi.nr.definition.util;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum EntityValueType {
    CUSTOM_STRING,
    DATA_ITEM,
    DATA_ITEM_CODE;


    @JsonValue
    public int num() {
        return this.ordinal();
    }

    @JsonCreator
    public EntityValueType create(int num) {
        EntityValueType[] values = EntityValueType.values();
        if (num < values.length - 1) {
            return values[num];
        }
        return null;
    }
}

