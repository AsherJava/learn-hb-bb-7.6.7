/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.core.settings.entity;

import com.jiuqi.nr.workflow2.engine.core.settings.enumeration.TimeControlType;

public interface FillInDaysConfig {
    public TimeControlType getType();

    public int getDayNum();
}

