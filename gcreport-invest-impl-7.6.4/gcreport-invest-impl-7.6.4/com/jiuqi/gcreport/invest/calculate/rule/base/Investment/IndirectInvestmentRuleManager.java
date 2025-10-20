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
package com.jiuqi.gcreport.invest.calculate.rule.base.Investment;

import com.jiuqi.gcreport.invest.calculate.rule.base.Investment.IndirectInvestmentRuleHandler;
import com.jiuqi.gcreport.invest.calculate.rule.base.Investment.InvestmentRuleExportProcessor;
import com.jiuqi.gcreport.unionrule.base.RuleHandler;
import com.jiuqi.gcreport.unionrule.base.RuleType;
import com.jiuqi.gcreport.unionrule.base.UnionRuleExportProcessor;
import com.jiuqi.gcreport.unionrule.base.UnionRuleManager;
import com.jiuqi.gcreport.unionrule.vo.UnionRuleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RuleType(code="INDIRECT_INVESTMENT", name="\u95f4\u63a5\u6295\u8d44\u89c4\u5219", order=7.0)
public class IndirectInvestmentRuleManager
extends UnionRuleManager {
    @Autowired
    IndirectInvestmentRuleHandler handler;

    public RuleHandler getRuleHandler() {
        return this.handler;
    }

    public UnionRuleExportProcessor getUnionRuleExportProcessor(UnionRuleVO data, String reportSystemId, int order) {
        return new InvestmentRuleExportProcessor(data, reportSystemId, order);
    }
}

