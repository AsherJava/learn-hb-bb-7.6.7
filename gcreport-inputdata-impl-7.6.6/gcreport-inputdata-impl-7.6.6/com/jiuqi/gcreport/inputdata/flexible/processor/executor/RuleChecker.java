/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 */
package com.jiuqi.gcreport.inputdata.flexible.processor.executor;

import com.jiuqi.gcreport.inputdata.inputdata.entity.InputDataEO;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import java.util.List;

public interface RuleChecker {
    public String canOffset(List<InputDataEO> var1, boolean var2, AbstractUnionRule var3, String var4);
}

