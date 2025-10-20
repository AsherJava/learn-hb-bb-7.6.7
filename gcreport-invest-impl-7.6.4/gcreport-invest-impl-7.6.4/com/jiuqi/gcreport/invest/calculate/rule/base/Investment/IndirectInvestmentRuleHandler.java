/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.unionrule.base.RuleHandler
 *  com.jiuqi.gcreport.unionrule.base.RuleManagerFactory
 *  com.jiuqi.gcreport.unionrule.base.RuleType
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 *  com.jiuqi.gcreport.unionrule.dto.InDirectInvestmentDTO
 *  com.jiuqi.gcreport.unionrule.entity.UnionRuleEO
 *  com.jiuqi.gcreport.unionrule.enums.OffsetTypeEnum
 *  com.jiuqi.gcreport.unionrule.enums.ToleranceTypeEnum
 *  com.jiuqi.gcreport.unionrule.vo.UnionRuleVO
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 */
package com.jiuqi.gcreport.invest.calculate.rule.base.Investment;

import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.invest.calculate.rule.base.Investment.InvestmentRuleJsonStringEO2VOHelper;
import com.jiuqi.gcreport.invest.calculate.rule.base.Investment.InvestmentRuleJsonStringVO2EOHelper;
import com.jiuqi.gcreport.unionrule.base.RuleHandler;
import com.jiuqi.gcreport.unionrule.base.RuleManagerFactory;
import com.jiuqi.gcreport.unionrule.base.RuleType;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import com.jiuqi.gcreport.unionrule.dto.InDirectInvestmentDTO;
import com.jiuqi.gcreport.unionrule.entity.UnionRuleEO;
import com.jiuqi.gcreport.unionrule.enums.OffsetTypeEnum;
import com.jiuqi.gcreport.unionrule.enums.ToleranceTypeEnum;
import com.jiuqi.gcreport.unionrule.vo.UnionRuleVO;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class IndirectInvestmentRuleHandler
extends RuleHandler {
    public String convertJsonStringWhenVO2EO(UnionRuleVO unionRuleVO) {
        return InvestmentRuleJsonStringVO2EOHelper.newInstance(unionRuleVO).convertJsonStringWhenVO2EO();
    }

    public String convertJsonStringWhenEO2VO(UnionRuleEO unionRule) {
        return InvestmentRuleJsonStringEO2VOHelper.newInstance(unionRule.getJsonString(), unionRule.getReportSystem()).convertJsonStringWhenEO2VO();
    }

    public AbstractUnionRule convertEO2DTO(UnionRuleEO unionRule) {
        AbstractUnionRule rule = (AbstractUnionRule)JsonUtils.readValue((String)unionRule.getJsonString(), InDirectInvestmentDTO.class);
        BeanUtils.copyProperties(unionRule, rule);
        RuleType ruleType = ((RuleManagerFactory)SpringBeanUtils.getBean(RuleManagerFactory.class)).getRuleType(unionRule.getRuleType());
        rule.setRuleType(ruleType.code());
        rule.setRuleTypeDescription(ruleType.name());
        if (!StringUtils.isEmpty((String)unionRule.getOffsetType())) {
            rule.setOffsetType(OffsetTypeEnum.codeOf((String)unionRule.getOffsetType()));
        }
        if (!StringUtils.isEmpty((String)unionRule.getToleranceType())) {
            rule.setToleranceType(ToleranceTypeEnum.codeOf((String)unionRule.getToleranceType()));
        }
        return rule;
    }

    public UnionRuleEO convertDTO2EO(AbstractUnionRule rule) {
        UnionRuleEO ruleEO = new UnionRuleEO();
        HashMap<String, Object> map = new HashMap<String, Object>(8);
        String ruleType = rule.getRuleType();
        ruleEO.setRuleType(ruleType);
        InDirectInvestmentDTO inDirectInvestmentDTO = new InDirectInvestmentDTO();
        BeanUtils.copyProperties(rule, inDirectInvestmentDTO);
        map.put("initialMerge", inDirectInvestmentDTO.getInitialMerge());
        map.put("goingConcern", inDirectInvestmentDTO.getGoingConcern());
        map.put("dealWith", inDirectInvestmentDTO.getDealWith());
        map.put("equityLawAdjustFlag", inDirectInvestmentDTO.getEquityLawAdjustFlag());
        map.put("debitItemList", inDirectInvestmentDTO.getDebitItemList());
        map.put("creditItemList", inDirectInvestmentDTO.getCreditItemList());
        ruleEO.setJsonString(JsonUtils.writeValueAsString(map));
        return ruleEO;
    }

    public AbstractUnionRule convertMapToDTO(Map<String, Object> importJsonListMap) {
        return (AbstractUnionRule)JsonUtils.readValue((String)JsonUtils.writeValueAsString(importJsonListMap), InDirectInvestmentDTO.class);
    }

    public List<String> getFormulaFetchDataSourceTable(String reportSystemId) {
        return Arrays.asList("GC_INVESTBILL", "GC_INVESTBILLITEM");
    }
}

