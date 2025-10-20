/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.datatrace.vo.GcDataTraceCondi
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 */
package com.jiuqi.gcreport.billcore.offsetcheck.ruleconditoncheck;

import com.jiuqi.gcreport.billcore.offsetcheck.dto.OffsetCheckResultDTO;
import com.jiuqi.gcreport.datatrace.vo.GcDataTraceCondi;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;

public interface RuleCondtionCheck {
    public String getRuleContionType();

    public String getRuleContionCode();

    public String getRuleContionTitle();

    public OffsetCheckResultDTO check(AbstractUnionRule var1, GcDataTraceCondi var2, boolean var3);
}

