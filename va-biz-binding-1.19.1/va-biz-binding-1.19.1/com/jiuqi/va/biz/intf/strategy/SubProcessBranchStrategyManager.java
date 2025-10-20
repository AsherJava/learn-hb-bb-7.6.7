/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.intf.strategy;

import com.jiuqi.va.biz.intf.strategy.SubProcessBranchStrategy;
import com.jiuqi.va.biz.intf.value.NamedContainer;
import java.util.List;

public interface SubProcessBranchStrategyManager
extends NamedContainer<SubProcessBranchStrategy> {
    public List<SubProcessBranchStrategy> getStrategyList(String var1);
}

