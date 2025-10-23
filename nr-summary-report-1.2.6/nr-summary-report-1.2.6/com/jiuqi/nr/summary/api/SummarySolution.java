/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.summary.api;

import com.jiuqi.nr.summary.api.BasicGetter;
import com.jiuqi.nr.summary.api.BasicSetter;
import com.jiuqi.nr.summary.api.OrderSetter;
import com.jiuqi.nr.summary.api.Ordered;

public interface SummarySolution
extends BasicGetter,
BasicSetter,
Ordered,
OrderSetter {
    public String getGroup();

    public String getMainTask();

    public String getTaskConfigData();

    public String getTargetDimension();
}

