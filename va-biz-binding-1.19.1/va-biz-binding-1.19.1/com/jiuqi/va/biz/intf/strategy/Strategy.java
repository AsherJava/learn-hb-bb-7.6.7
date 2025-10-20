/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.intf.strategy;

import com.jiuqi.va.biz.intf.value.NamedElement;
import java.util.Set;

public interface Strategy
extends NamedElement {
    @Override
    public String getName();

    public String getTitle();

    default public String getOrder() {
        return "";
    }

    public String getStrategyModule();

    public Set<String> execute(Object var1);
}

