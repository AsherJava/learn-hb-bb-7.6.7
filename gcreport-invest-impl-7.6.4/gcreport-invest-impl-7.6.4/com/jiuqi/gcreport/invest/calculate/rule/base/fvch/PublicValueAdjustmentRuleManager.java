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
package com.jiuqi.gcreport.invest.calculate.rule.base.fvch;

import com.jiuqi.gcreport.invest.calculate.rule.base.fvch.PublicValueAdjustmentRuleExportProcessor;
import com.jiuqi.gcreport.invest.calculate.rule.base.fvch.PublicValueAdjustmentRuleHandler;
import com.jiuqi.gcreport.unionrule.base.RuleHandler;
import com.jiuqi.gcreport.unionrule.base.RuleType;
import com.jiuqi.gcreport.unionrule.base.UnionRuleExportProcessor;
import com.jiuqi.gcreport.unionrule.base.UnionRuleManager;
import com.jiuqi.gcreport.unionrule.vo.UnionRuleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RuleType(code="PUBLIC_VALUE_ADJUSTMENT", name="\u516c\u5141\u4ef7\u503c\u8c03\u6574\u89c4\u5219", order=6.0)
public class PublicValueAdjustmentRuleManager
extends UnionRuleManager {
    @Autowired
    PublicValueAdjustmentRuleHandler handler;

    public RuleHandler getRuleHandler() {
        return this.handler;
    }

    public UnionRuleExportProcessor getUnionRuleExportProcessor(UnionRuleVO data, String reportSystemId, int order) {
        return new PublicValueAdjustmentRuleExportProcessor(data, reportSystemId, order);
    }
}

