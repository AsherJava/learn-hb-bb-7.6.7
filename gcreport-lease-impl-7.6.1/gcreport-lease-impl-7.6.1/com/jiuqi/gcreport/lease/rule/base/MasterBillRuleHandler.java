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
 *  com.jiuqi.gcreport.unionrule.dto.LeaseRuleDTO
 *  com.jiuqi.gcreport.unionrule.entity.UnionRuleEO
 *  com.jiuqi.gcreport.unionrule.enums.LeaseFetchTypeEnum
 *  com.jiuqi.gcreport.unionrule.enums.OffsetTypeEnum
 *  com.jiuqi.gcreport.unionrule.enums.ToleranceTypeEnum
 *  com.jiuqi.gcreport.unionrule.vo.ImportMessageVO
 *  com.jiuqi.gcreport.unionrule.vo.UnionRuleVO
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 */
package com.jiuqi.gcreport.lease.rule.base;

import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.lease.rule.base.MasterBillRuleJsonStringEO2VOHelper;
import com.jiuqi.gcreport.lease.rule.base.MasterBillRuleJsonStringVO2EOHelper;
import com.jiuqi.gcreport.unionrule.base.RuleHandler;
import com.jiuqi.gcreport.unionrule.base.RuleManagerFactory;
import com.jiuqi.gcreport.unionrule.base.RuleType;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import com.jiuqi.gcreport.unionrule.dto.LeaseRuleDTO;
import com.jiuqi.gcreport.unionrule.entity.UnionRuleEO;
import com.jiuqi.gcreport.unionrule.enums.LeaseFetchTypeEnum;
import com.jiuqi.gcreport.unionrule.enums.OffsetTypeEnum;
import com.jiuqi.gcreport.unionrule.enums.ToleranceTypeEnum;
import com.jiuqi.gcreport.unionrule.vo.ImportMessageVO;
import com.jiuqi.gcreport.unionrule.vo.UnionRuleVO;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class MasterBillRuleHandler
extends RuleHandler {
    public String convertJsonStringWhenVO2EO(UnionRuleVO unionRuleVO) {
        return MasterBillRuleJsonStringVO2EOHelper.newInstance(unionRuleVO).convertJsonStringWhenVO2EO();
    }

    public String convertJsonStringWhenEO2VO(UnionRuleEO unionRule) {
        return MasterBillRuleJsonStringEO2VOHelper.newInstance(unionRule.getJsonString(), unionRule.getReportSystem()).convertJsonStringWhenEO2VO();
    }

    public AbstractUnionRule convertEO2DTO(UnionRuleEO unionRule) {
        AbstractUnionRule rule = (AbstractUnionRule)JsonUtils.readValue((String)unionRule.getJsonString(), LeaseRuleDTO.class);
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
        LeaseRuleDTO leaseRule = new LeaseRuleDTO();
        BeanUtils.copyProperties(rule, leaseRule);
        map.put("billDefineId", leaseRule.getBillDefineId());
        map.put("debitItemList", leaseRule.getDebitItemList());
        map.put("creditItemList", leaseRule.getCreditItemList());
        ruleEO.setJsonString(JsonUtils.writeValueAsString(map));
        return ruleEO;
    }

    public AbstractUnionRule convertMapToDTO(Map<String, Object> importJsonListMap) {
        return (AbstractUnionRule)JsonUtils.readValue((String)JsonUtils.writeValueAsString(importJsonListMap), LeaseRuleDTO.class);
    }

    public List<String> getFormulaFetchDataSourceTable(String reportSystemId) {
        return Collections.emptyList();
    }

    public boolean filterRuleByParams(UnionRuleEO rule, Map<String, Object> params) {
        if (params.containsKey("billDefineCode") && !StringUtils.isEmpty((String)((String)params.get("billDefineCode")))) {
            String billDefineIdCondi = "billDefineId\":\"" + (String)params.get("billDefineCode") + "";
            if (StringUtils.isEmpty((String)rule.getJsonString()) || rule.getJsonString().indexOf(billDefineIdCondi) == -1) {
                return false;
            }
        }
        return true;
    }

    public Set<ImportMessageVO> checkRuleSubjectCode(AbstractUnionRule unionRuleDTO, String reportSystemId) {
        HashSet<ImportMessageVO> resultList = new HashSet<ImportMessageVO>();
        LeaseRuleDTO leaseRule = (LeaseRuleDTO)unionRuleDTO;
        HashSet subjectCodes = new HashSet();
        if (!CollectionUtils.isEmpty(leaseRule.getDebitItemList())) {
            leaseRule.getDebitItemList().stream().filter(item -> LeaseFetchTypeEnum.CUSTOMIZE.equals((Object)item.getType())).forEach(item -> subjectCodes.add(item.getSubjectCode()));
        }
        if (!CollectionUtils.isEmpty(leaseRule.getCreditItemList())) {
            leaseRule.getCreditItemList().stream().filter(item -> LeaseFetchTypeEnum.CUSTOMIZE.equals((Object)item.getType())).forEach(item -> subjectCodes.add(item.getSubjectCode()));
        }
        this.checkSubjectCode(unionRuleDTO, subjectCodes, resultList, reportSystemId);
        return resultList;
    }
}

