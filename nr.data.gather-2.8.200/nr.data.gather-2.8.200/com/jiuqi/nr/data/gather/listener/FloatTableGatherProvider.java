/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.data.engine.gather.FloatTableGatherSetting
 */
package com.jiuqi.nr.data.gather.listener;

import com.jiuqi.nr.data.engine.gather.FloatTableGatherSetting;
import java.util.List;

public interface FloatTableGatherProvider {
    public boolean isEnable();

    public double getOrder();

    public List<FloatTableGatherSetting> getFloatTableGatherParam();
}

