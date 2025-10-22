/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.common;

import java.util.Arrays;
import java.util.Optional;

public enum EntityRowType {
    NORMAL(0, "\u666e\u901a\u8282\u70b9"),
    TEMP_ENTITY_FOR_GATHER(1, "\u6c47\u603b\u865a\u62df\u8282\u70b9");

    private int value;
    private String title;

    private EntityRowType(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int getValue() {
        return this.value;
    }

    public String getTitle() {
        return this.title;
    }

    public static EntityRowType getEntityRowTypeByValue(int value) {
        Optional<EntityRowType> authority = Arrays.stream(EntityRowType.values()).filter(o -> o.getValue() == value).findFirst();
        return authority.orElse(NORMAL);
    }
}

