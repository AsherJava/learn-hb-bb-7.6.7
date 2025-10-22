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
package com.jiuqi.gcreport.calculate.rule.floatline;

import com.jiuqi.gcreport.calculate.rule.floatline.FloatLineRuleExportProcessor;
import com.jiuqi.gcreport.calculate.rule.floatline.FloatLineRuleHandler;
import com.jiuqi.gcreport.unionrule.base.RuleHandler;
import com.jiuqi.gcreport.unionrule.base.RuleType;
import com.jiuqi.gcreport.unionrule.base.UnionRuleExportProcessor;
import com.jiuqi.gcreport.unionrule.base.UnionRuleManager;
import com.jiuqi.gcreport.unionrule.vo.UnionRuleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RuleType(code="FLOAT_LINE", name="\u6d6e\u52a8\u884c\u89c4\u5219", order=2.0)
public class FloatLineRuleManager
extends UnionRuleManager {
    @Autowired
    FloatLineRuleHandler handler;

    public RuleHandler getRuleHandler() {
        return this.handler;
    }

    public UnionRuleExportProcessor getUnionRuleExportProcessor(UnionRuleVO data, String reportSystemId, int order) {
        return new FloatLineRuleExportProcessor(data, reportSystemId, order);
    }
}

