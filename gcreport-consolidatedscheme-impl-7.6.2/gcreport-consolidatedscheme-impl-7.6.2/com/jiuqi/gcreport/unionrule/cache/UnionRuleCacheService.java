/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 *  javax.validation.constraints.NotNull
 */
package com.jiuqi.gcreport.unionrule.cache;

import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import java.util.List;
import javax.validation.constraints.NotNull;

public interface UnionRuleCacheService {
    public List<AbstractUnionRule> getRulesBySystemId(@NotNull String var1);

    public void clearCache(String var1);
}

