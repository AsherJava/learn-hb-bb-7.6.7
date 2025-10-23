/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.core.settings.entity;

import com.jiuqi.nr.workflow2.engine.core.settings.entity.FillInDaysConfig;
import com.jiuqi.nr.workflow2.engine.core.settings.enumeration.StartTimeStrategy;

public interface FillInStartTimeConfig {
    public boolean isEnable();

    public StartTimeStrategy getType();

    public FillInDaysConfig getFillInDaysConfig();
}

