/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 */
package com.jiuqi.nr.datacheck.dataanalyze;

import com.jiuqi.nr.datacheck.dataanalyze.AnalyzeRuleGroupParam;
import com.jiuqi.nr.datacheck.dataanalyze.AnalyzeRuleGroupProvider;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import org.springframework.beans.factory.annotation.Autowired;

public class AnalyzeRuleGroupDefaultImpl
implements AnalyzeRuleGroupProvider {
    @Autowired
    private INvwaSystemOptionService iNvwaSystemOptionService;

    @Override
    public String getRuleGroup(AnalyzeRuleGroupParam param) {
        return this.iNvwaSystemOptionService.get("nr-audit-group", "@nr/check/explain-use-rule-group");
    }
}

