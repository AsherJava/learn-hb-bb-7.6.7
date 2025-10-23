/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.core.settings.entity;

import com.jiuqi.nr.workflow2.engine.core.settings.entity.FillInDaysConfig;

public interface FillInEndTimeConfig {
    public boolean isEnable();

    public FillInDaysConfig getFillInDaysConfig();

    public boolean isHierarchicalControl();
}

