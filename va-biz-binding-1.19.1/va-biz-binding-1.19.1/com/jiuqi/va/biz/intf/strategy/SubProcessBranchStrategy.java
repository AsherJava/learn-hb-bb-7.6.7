/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.intf.strategy;

import com.jiuqi.va.biz.intf.value.NamedElement;
import java.util.Map;

public interface SubProcessBranchStrategy
extends NamedElement {
    @Override
    public String getName();

    public String getTitle();

    default public String getOrder() {
        return "";
    }

    public String getStrategyModule();

    public Map<String, Object> execute(Object var1);

    default public boolean checkRetract(Object params) {
        return false;
    }

    default public String getRetractMq() {
        return null;
    }
}

