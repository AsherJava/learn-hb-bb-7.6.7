/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.unionrule.base.RuleHandler
 *  com.jiuqi.gcreport.unionrule.base.RuleType
 *  com.jiuqi.gcreport.unionrule.base.UnionRuleExportProcessor
 *  com.jiuqi.gcreport.unionrule.base.UnionRuleManager
 *  com.jiuqi.gcreport.unionrule.vo.UnionRuleVO
 */
package com.jiuqi.gcreport.lease.rule.base;

import com.jiuqi.gcreport.lease.rule.base.MasterBillRuleExportProcessor;
import com.jiuqi.gcreport.lease.rule.base.MasterBillRuleHandler;
import com.jiuqi.gcreport.unionrule.base.RuleHandler;
import com.jiuqi.gcreport.unionrule.base.RuleType;
import com.jiuqi.gcreport.unionrule.base.UnionRuleExportProcessor;
import com.jiuqi.gcreport.unionrule.base.UnionRuleManager;
import com.jiuqi.gcreport.unionrule.vo.UnionRuleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RuleType(code="LEASE", name="\u5355\u636e\u89c4\u5219", order=4.0)
public class MasterBillRuleManager
extends UnionRuleManager {
    @Autowired
    MasterBillRuleHandler handler;

    public RuleHandler getRuleHandler() {
        return this.handler;
    }

    public UnionRuleExportProcessor getUnionRuleExportProcessor(UnionRuleVO data, String reportSystemId, int order) {
        return new MasterBillRuleExportProcessor(data, reportSystemId, order);
    }
}

