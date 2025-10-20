/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.unionrule.vo.UnionRuleVO
 */
package com.jiuqi.gcreport.unionrule.base;

import com.jiuqi.gcreport.unionrule.base.RuleHandler;
import com.jiuqi.gcreport.unionrule.base.UnionRuleExportProcessor;
import com.jiuqi.gcreport.unionrule.vo.UnionRuleVO;

public abstract class UnionRuleManager {
    public abstract RuleHandler getRuleHandler();

    public UnionRuleExportProcessor getUnionRuleExportProcessor(UnionRuleVO data, String reportSystemId, int order) {
        return new UnionRuleExportProcessor(data, reportSystemId, order);
    }
}

