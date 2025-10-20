/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 *  com.jiuqi.gcreport.unionrule.dto.UnionGroupRuleDTO
 */
package com.jiuqi.gcreport.unionrule.util;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.unionrule.base.UnionRuleManager;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import com.jiuqi.gcreport.unionrule.dto.UnionGroupRuleDTO;
import com.jiuqi.gcreport.unionrule.entity.UnionRuleEO;
import com.jiuqi.gcreport.unionrule.util.UnionRuleUtils;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;

public class UnionRuleConverter {
    public static AbstractUnionRule convert(UnionRuleEO unionRuleEO) {
        if (!Objects.equals(1, unionRuleEO.getLeafFlag())) {
            UnionGroupRuleDTO rule = new UnionGroupRuleDTO();
            BeanUtils.copyProperties((Object)unionRuleEO, rule);
            rule.setLeafFlag(Boolean.valueOf(Objects.equals(unionRuleEO.getLeafFlag(), 1)));
            rule.setStartFlag(Boolean.valueOf(Objects.equals(unionRuleEO.getStartFlag(), 1)));
            rule.setInitTypeFlag(Boolean.valueOf(Objects.equals(unionRuleEO.getInitTypeFlag(), 1)));
            rule.setEnableToleranceFlag(Boolean.valueOf(Objects.equals(unionRuleEO.getEnableToleranceFlag(), 1)));
            rule.setDataType(unionRuleEO.getRuleType());
            return rule;
        }
        UnionRuleManager ruleManager = UnionRuleUtils.getManagerByRuleTypeCode(unionRuleEO.getRuleType());
        AbstractUnionRule abstractUnionRule = ruleManager.getRuleHandler().convertEO2DTO(unionRuleEO);
        abstractUnionRule.setLeafFlag(Boolean.valueOf(Objects.equals(unionRuleEO.getLeafFlag(), 1)));
        abstractUnionRule.setStartFlag(Boolean.valueOf(Objects.equals(unionRuleEO.getStartFlag(), 1)));
        abstractUnionRule.setInitTypeFlag(Boolean.valueOf(Objects.equals(unionRuleEO.getInitTypeFlag(), 1)));
        abstractUnionRule.setEnableToleranceFlag(Boolean.valueOf(Objects.equals(unionRuleEO.getEnableToleranceFlag(), 1)));
        String applyGcUnits = unionRuleEO.getApplyGcUnits();
        if (!StringUtils.isEmpty((String)applyGcUnits)) {
            abstractUnionRule.setApplyGcUnits(Arrays.stream(applyGcUnits.split(",")).collect(Collectors.toList()));
        }
        return abstractUnionRule;
    }

    public static UnionRuleEO convertUnionRuleDTOToEO(AbstractUnionRule abstractUnionRule) {
        if (!Boolean.TRUE.equals(abstractUnionRule.getLeafFlag())) {
            UnionRuleEO ruleEO = new UnionRuleEO();
            BeanUtils.copyProperties(abstractUnionRule, (Object)ruleEO);
            ruleEO.setLeafFlag(Boolean.TRUE.equals(abstractUnionRule.getLeafFlag()) ? 1 : 0);
            ruleEO.setStartFlag(Boolean.TRUE.equals(abstractUnionRule.getStartFlag()) ? 1 : 0);
            ruleEO.setInitTypeFlag(Boolean.TRUE.equals(abstractUnionRule.getInitTypeFlag()) ? 1 : 0);
            ruleEO.setEnableToleranceFlag(Boolean.TRUE.equals(abstractUnionRule.getEnableToleranceFlag()) ? 1 : 0);
            return ruleEO;
        }
        UnionRuleManager ruleManager = UnionRuleUtils.getManagerByRuleTypeCode(abstractUnionRule.getRuleType());
        UnionRuleEO unionRuleEO = ruleManager.getRuleHandler().convertDTO2EO(abstractUnionRule);
        unionRuleEO.setLeafFlag(Boolean.TRUE.equals(abstractUnionRule.getLeafFlag()) ? 1 : 0);
        unionRuleEO.setStartFlag(Boolean.TRUE.equals(abstractUnionRule.getStartFlag()) ? 1 : 0);
        unionRuleEO.setInitTypeFlag(Boolean.TRUE.equals(abstractUnionRule.getInitTypeFlag()) ? 1 : 0);
        unionRuleEO.setEnableToleranceFlag(Boolean.TRUE.equals(abstractUnionRule.getEnableToleranceFlag()) ? 1 : 0);
        List applyGcUnits = abstractUnionRule.getApplyGcUnits();
        if (!CollectionUtils.isEmpty((Collection)applyGcUnits)) {
            unionRuleEO.setApplyGcUnits(applyGcUnits.stream().collect(Collectors.joining(",")));
        }
        return unionRuleEO;
    }
}

