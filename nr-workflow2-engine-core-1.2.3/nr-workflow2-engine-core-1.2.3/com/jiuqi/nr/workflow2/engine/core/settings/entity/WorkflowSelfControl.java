/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.core.settings.entity;

import com.jiuqi.nr.workflow2.engine.core.settings.entity.FillInDaysConfig;
import com.jiuqi.nr.workflow2.engine.core.settings.enumeration.StartTimeStrategy;
import java.time.LocalTime;

public interface WorkflowSelfControl {
    public boolean isEnable();

    public StartTimeStrategy getType();

    public FillInDaysConfig getFillInDaysConfig();

    public LocalTime getBootTime();
}

