/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.impl.strategy;

import com.jiuqi.va.biz.impl.value.NamedManagerImpl;
import com.jiuqi.va.biz.intf.strategy.SubProcessBranchStrategy;
import com.jiuqi.va.biz.intf.strategy.SubProcessBranchStrategyManager;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class SubProcessBranchStrategyManagerImpl
extends NamedManagerImpl<SubProcessBranchStrategy>
implements SubProcessBranchStrategyManager {
    @Override
    public List<SubProcessBranchStrategy> getStrategyList(String strategyModule) {
        return this.stream().filter(o -> o.getStrategyModule().equalsIgnoreCase(strategyModule)).sorted(Comparator.comparing(SubProcessBranchStrategy::getOrder)).collect(Collectors.toList());
    }
}

