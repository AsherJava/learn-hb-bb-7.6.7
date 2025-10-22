/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bsp.contentcheckrules.common.ContentCheckResult
 *  com.jiuqi.bsp.contentcheckrules.service.ContentCheckByGroupService
 */
package com.jiuqi.nr.datacheck.dataanalyze;

import com.jiuqi.bsp.contentcheckrules.common.ContentCheckResult;
import com.jiuqi.bsp.contentcheckrules.service.ContentCheckByGroupService;
import com.jiuqi.nr.datacheck.dataanalyze.AnalyzeRuleGroupParam;
import com.jiuqi.nr.datacheck.dataanalyze.AnalyzeRuleGroupProvider;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class AnalyzeErrorCheckService {
    private AnalyzeRuleGroupProvider ruleGroupProvider;

    @Autowired
    public AnalyzeErrorCheckService(AnalyzeRuleGroupProvider ruleGroupProvider) {
        this.ruleGroupProvider = ruleGroupProvider;
    }

    public String getRuleGroup(AnalyzeRuleGroupParam ruleParam) {
        return this.ruleGroupProvider.getRuleGroup(ruleParam);
    }

    public ContentCheckResult checkContent(String content, String ruleGroup, ContentCheckByGroupService checkService) {
        if (!StringUtils.hasText(ruleGroup) || "CONFIRM-TRUE".equals(content)) {
            ContentCheckResult res = new ContentCheckResult(true, null);
            return res;
        }
        if (!StringUtils.hasText(content)) {
            ContentCheckResult res = new ContentCheckResult(false, Arrays.asList("\u8bf4\u660e\u5185\u5bb9\u4e3a\u7a7a\uff01"));
            return res;
        }
        return checkService.check(content, ruleGroup);
    }
}

