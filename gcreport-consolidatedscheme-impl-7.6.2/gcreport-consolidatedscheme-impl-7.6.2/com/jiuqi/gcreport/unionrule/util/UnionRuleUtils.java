/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 *  com.jiuqi.gcreport.unionrule.vo.UnionRuleVO
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 */
package com.jiuqi.gcreport.unionrule.util;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.unionrule.base.RuleManagerFactory;
import com.jiuqi.gcreport.unionrule.base.UnionRuleManager;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import com.jiuqi.gcreport.unionrule.service.UnionRuleService;
import com.jiuqi.gcreport.unionrule.vo.UnionRuleVO;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import java.util.List;
import java.util.Objects;
import java.util.Stack;

public final class UnionRuleUtils {
    private UnionRuleUtils() {
    }

    private static UnionRuleService getUnionRuleService() {
        UnionRuleService unionRuleService = (UnionRuleService)SpringContextUtils.getBean(UnionRuleService.class);
        return unionRuleService;
    }

    public static List<AbstractUnionRule> selectRuleListByReportSystemAndRuleTypes(String reportSystemId, List<String> ruleTypes) {
        return UnionRuleUtils.getUnionRuleService().selectRuleListByReportSystemAndRuleTypes(reportSystemId, ruleTypes);
    }

    public static List<AbstractUnionRule> selectRuleListBySchemeId(String schemeId, String periodStr) {
        return UnionRuleUtils.getUnionRuleService().selectRuleListBySchemeIdAndRuleTypes(schemeId, periodStr, null);
    }

    public static Stack<UnionRuleVO> getParentsByChildId(String childId) {
        return UnionRuleUtils.getUnionRuleService().getParentsByChildId(childId);
    }

    public static AbstractUnionRule getAbstractUnionRuleById(String recid) {
        return UnionRuleUtils.getUnionRuleService().selectUnionRuleDTOById(recid);
    }

    public static List<AbstractUnionRule> getAbstractUnionRuleByIdList(List<String> ids) {
        return UnionRuleUtils.getUnionRuleService().selectUnionRuleDTOByIdList(ids);
    }

    public static boolean hasRulesByReportSystemId(String reportSystemId) {
        return UnionRuleUtils.getUnionRuleService().hasRulesByReportSystemId(reportSystemId);
    }

    public static UnionRuleManager getManagerByRuleTypeCode(String type) {
        UnionRuleManager ruleManager = ((RuleManagerFactory)SpringBeanUtils.getBean(RuleManagerFactory.class)).getUnionRuleManager(type);
        if (Objects.isNull(ruleManager)) {
            throw new BusinessRuntimeException(type + "\u672a\u627e\u5230\u5bf9\u5e94\u7684\u5408\u5e76\u89c4\u5219\u7ba1\u7406\u5668\u5b9e\u73b0\u7c7b");
        }
        return ruleManager;
    }
}

