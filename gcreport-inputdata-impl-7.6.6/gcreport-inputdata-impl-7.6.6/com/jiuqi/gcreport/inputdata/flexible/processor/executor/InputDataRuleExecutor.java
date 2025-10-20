/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.calculate.env.GcCalcEnvContext
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 */
package com.jiuqi.gcreport.inputdata.flexible.processor.executor;

import com.jiuqi.gcreport.calculate.env.GcCalcEnvContext;
import com.jiuqi.gcreport.inputdata.inputdata.entity.InputDataEO;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface InputDataRuleExecutor {
    public Map<String, Set<String>> realTimeOffset(AbstractUnionRule var1, List<InputDataEO> var2, boolean var3, boolean var4);

    public Map<String, Set<String>> manualBatchOffset(AbstractUnionRule var1, AbstractUnionRule var2, List<InputDataEO> var3);

    public void calMerge(AbstractUnionRule var1, GcCalcEnvContext var2);
}

