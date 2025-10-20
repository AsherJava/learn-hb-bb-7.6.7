/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.intf.strategy;

import com.jiuqi.va.biz.intf.strategy.Strategy;
import com.jiuqi.va.biz.intf.value.NamedContainer;
import java.util.List;

public interface StrategyManager
extends NamedContainer<Strategy> {
    public List<Strategy> getStrategyList(String var1);
}

