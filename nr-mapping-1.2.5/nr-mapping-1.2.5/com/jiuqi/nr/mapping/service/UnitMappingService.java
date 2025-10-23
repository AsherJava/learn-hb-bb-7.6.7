/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.mapping.service;

import com.jiuqi.nr.mapping.bean.UnitMapping;
import com.jiuqi.nr.mapping.bean.UnitRule;
import java.util.List;

public interface UnitMappingService {
    public List<UnitMapping> findByMS(String var1);

    public void saveByMS(String var1, List<UnitMapping> var2);

    public void clearByMS(String var1);

    public List<UnitRule> findRuleByMS(String var1);

    public void clearRuleByMS(String var1);

    public void saveRule(String var1, List<UnitRule> var2);
}

