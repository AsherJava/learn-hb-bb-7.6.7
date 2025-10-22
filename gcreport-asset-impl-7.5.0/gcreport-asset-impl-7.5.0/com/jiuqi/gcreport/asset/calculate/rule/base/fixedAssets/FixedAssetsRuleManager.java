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
package com.jiuqi.gcreport.asset.calculate.rule.base.fixedAssets;

import com.jiuqi.gcreport.asset.calculate.rule.base.fixedAssets.FixedAssetsRuleExportProcessor;
import com.jiuqi.gcreport.asset.calculate.rule.base.fixedAssets.FixedAssetsRuleHandler;
import com.jiuqi.gcreport.unionrule.base.RuleHandler;
import com.jiuqi.gcreport.unionrule.base.RuleType;
import com.jiuqi.gcreport.unionrule.base.UnionRuleExportProcessor;
import com.jiuqi.gcreport.unionrule.base.UnionRuleManager;
import com.jiuqi.gcreport.unionrule.vo.UnionRuleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RuleType(code="FIXED_ASSETS", name="\u56fa\u5b9a\u8d44\u4ea7\u89c4\u5219", order=5.0)
public class FixedAssetsRuleManager
extends UnionRuleManager {
    @Autowired
    FixedAssetsRuleHandler handler;

    public RuleHandler getRuleHandler() {
        return this.handler;
    }

    public UnionRuleExportProcessor getUnionRuleExportProcessor(UnionRuleVO data, String reportSystemId, int order) {
        return new FixedAssetsRuleExportProcessor(data, reportSystemId, order);
    }
}

