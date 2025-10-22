/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.option.spi;

import com.jiuqi.nr.definition.option.core.VisibleType;

public interface TaskOptionPage {
    public String getTitle();

    public Double getOrder();

    default public VisibleType getVisibleType(String taskKey) {
        return VisibleType.DEFAULT;
    }
}

