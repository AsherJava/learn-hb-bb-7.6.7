/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.impl.strategy;

import com.jiuqi.va.biz.impl.value.NamedManagerImpl;
import com.jiuqi.va.biz.intf.strategy.Strategy;
import com.jiuqi.va.biz.intf.strategy.StrategyManager;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class StrategyManagerImpl
extends NamedManagerImpl<Strategy>
implements StrategyManager {
    @Override
    public List<Strategy> getStrategyList(String strategyModule) {
        List<Strategy> strategys = this.stream().filter(o -> o.getStrategyModule().equalsIgnoreCase(strategyModule)).collect(Collectors.toList());
        strategys.sort(Comparator.comparing(Strategy::getOrder));
        return strategys;
    }
}

