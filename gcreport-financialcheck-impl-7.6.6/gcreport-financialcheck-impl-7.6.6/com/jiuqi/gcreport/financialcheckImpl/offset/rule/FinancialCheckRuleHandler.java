/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.basedata.api.vo.BaseDataVO
 *  com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool
 *  com.jiuqi.gcreport.consolidatedsystem.service.ConsolidatedSystemService
 *  com.jiuqi.gcreport.unionrule.base.RuleHandler
 *  com.jiuqi.gcreport.unionrule.base.RuleManagerFactory
 *  com.jiuqi.gcreport.unionrule.base.RuleType
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 *  com.jiuqi.gcreport.unionrule.dto.FinancialCheckFetchConfig
 *  com.jiuqi.gcreport.unionrule.dto.FinancialCheckRuleDTO
 *  com.jiuqi.gcreport.unionrule.entity.UnionRuleEO
 *  com.jiuqi.gcreport.unionrule.enums.OffsetTypeEnum
 *  com.jiuqi.gcreport.unionrule.enums.ToleranceTypeEnum
 *  com.jiuqi.gcreport.unionrule.vo.ImportMessageVO
 *  com.jiuqi.gcreport.unionrule.vo.UnionRuleVO
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 */
package com.jiuqi.gcreport.financialcheckImpl.offset.rule;

import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.basedata.api.vo.BaseDataVO;
import com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool;
import com.jiuqi.gcreport.consolidatedsystem.service.ConsolidatedSystemService;
import com.jiuqi.gcreport.financialcheckImpl.offset.rule.FinancialCheckRuleJsonStringEO2VOHelper;
import com.jiuqi.gcreport.financialcheckImpl.offset.rule.FinancialCheckRuleJsonStringVO2EOHelper;
import com.jiuqi.gcreport.unionrule.base.RuleHandler;
import com.jiuqi.gcreport.unionrule.base.RuleManagerFactory;
import com.jiuqi.gcreport.unionrule.base.RuleType;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import com.jiuqi.gcreport.unionrule.dto.FinancialCheckFetchConfig;
import com.jiuqi.gcreport.unionrule.dto.FinancialCheckRuleDTO;
import com.jiuqi.gcreport.unionrule.entity.UnionRuleEO;
import com.jiuqi.gcreport.unionrule.enums.OffsetTypeEnum;
import com.jiuqi.gcreport.unionrule.enums.ToleranceTypeEnum;
import com.jiuqi.gcreport.unionrule.vo.ImportMessageVO;
import com.jiuqi.gcreport.unionrule.vo.UnionRuleVO;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class FinancialCheckRuleHandler
extends RuleHandler {
    @Autowired
    ConsolidatedSystemService systemService;

    public String convertJsonStringWhenVO2EO(UnionRuleVO unionRuleVO) {
        return FinancialCheckRuleJsonStringVO2EOHelper.newInstance(unionRuleVO).convertJsonStringWhenVO2EO();
    }

    public String convertJsonStringWhenEO2VO(UnionRuleEO unionRule) {
        return FinancialCheckRuleJsonStringEO2VOHelper.newInstance(unionRule.getJsonString(), unionRule.getReportSystem()).convertJsonStringWhenEO2VO();
    }

    public AbstractUnionRule convertEO2DTO(UnionRuleEO unionRule) {
        String jsonString = unionRule.getJsonString();
        AbstractUnionRule rule = (AbstractUnionRule)JsonUtils.readValue((String)jsonString, FinancialCheckRuleDTO.class);
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
        HashMap<String, Object> map = new HashMap<String, Object>();
        String ruleType = rule.getRuleType();
        FinancialCheckRuleDTO financialCheckRule = new FinancialCheckRuleDTO();
        BeanUtils.copyProperties(rule, financialCheckRule);
        map.put("fetchConfigList", financialCheckRule.getFetchConfigList());
        map.put("creditItemList", financialCheckRule.getSrcCreditSubjectCodeList());
        map.put("debitItemList", financialCheckRule.getSrcDebitSubjectCodeList());
        map.put("checked", financialCheckRule.isChecked());
        map.put("reconciliationOffsetFlag", financialCheckRule.getReconciliationOffsetFlag());
        map.put("unilateralOffsetFlag", financialCheckRule.getUnilateralOffsetFlag());
        map.put("proportionOffsetDiffFlag", financialCheckRule.getProportionOffsetDiffFlag());
        map.put("delCheckedOffsetFlag", financialCheckRule.getDelCheckedOffsetFlag());
        map.put("offsetGroupingField", financialCheckRule.getOffsetGroupingField());
        ruleEO.setJsonString(JsonUtils.writeValueAsString(map));
        ruleEO.setRuleType(ruleType);
        return ruleEO;
    }

    public Set<ImportMessageVO> checkRuleSubjectCode(AbstractUnionRule unionRuleDTO, String reportSystemId) {
        HashSet<String> deleteSubjectCodes;
        HashSet<ImportMessageVO> resultList = new HashSet<ImportMessageVO>();
        FinancialCheckRuleDTO financialCheckRuleDTO = (FinancialCheckRuleDTO)unionRuleDTO;
        if (!CollectionUtils.isEmpty(financialCheckRuleDTO.getFetchConfigList())) {
            this.checkFinancialCheckFetchConfigSubjectCode(unionRuleDTO, financialCheckRuleDTO.getFetchConfigList(), resultList, reportSystemId);
        }
        if (!CollectionUtils.isEmpty(unionRuleDTO.getSrcDebitSubjectCodeList())) {
            deleteSubjectCodes = new HashSet<String>();
            this.checkFlexibleSubjectCode(unionRuleDTO.getSrcDebitSubjectCodeList(), deleteSubjectCodes, reportSystemId);
            if (!CollectionUtils.isEmpty(deleteSubjectCodes)) {
                resultList.add(new ImportMessageVO().setERROR(unionRuleDTO.getTitle(), "\u501f\u65b9\u6570\u636e\u6e90\u4e2d\u79d1\u76ee" + ((Object)deleteSubjectCodes).toString() + "\u5728\u5f53\u524d\u4f53\u7cfb\u4e2d\u4e0d\u5b58\u5728\u3002"));
            }
        }
        if (!CollectionUtils.isEmpty(unionRuleDTO.getSrcCreditSubjectCodeList())) {
            deleteSubjectCodes = new HashSet();
            this.checkFlexibleSubjectCode(unionRuleDTO.getSrcCreditSubjectCodeList(), deleteSubjectCodes, reportSystemId);
            if (!CollectionUtils.isEmpty(deleteSubjectCodes)) {
                resultList.add(new ImportMessageVO().setERROR(unionRuleDTO.getTitle(), "\u8d37\u65b9\u6570\u636e\u6e90\u4e2d\u79d1\u76ee" + ((Object)deleteSubjectCodes).toString() + "\u5728\u5f53\u524d\u4f53\u7cfb\u4e2d\u4e0d\u5b58\u5728\u3002"));
            }
        }
        return resultList;
    }

    public AbstractUnionRule convertMapToDTO(Map<String, Object> importJsonListMap) {
        return (AbstractUnionRule)JsonUtils.readValue((String)JsonUtils.writeValueAsString(importJsonListMap), FinancialCheckRuleDTO.class);
    }

    private void checkFinancialCheckFetchConfigSubjectCode(AbstractUnionRule unionRuleDTO, List<FinancialCheckFetchConfig> fetchConfigList, Set<ImportMessageVO> resultList, String reportSystemId) {
        fetchConfigList.forEach(fetchConfig -> {
            if (!CollectionUtils.isEmpty(fetchConfig.getCreditConfigList())) {
                ArrayList creditConfigCodes = new ArrayList();
                fetchConfig.getCreditConfigList().forEach(creditConfig -> {
                    if (creditConfig.getSubjectCode().isEmpty()) {
                        resultList.add(new ImportMessageVO().setERROR(unionRuleDTO.getTitle(), "\u79d1\u76ee\u4e0d\u80fd\u4e3a\u7a7a\u3002"));
                    } else {
                        creditConfigCodes.add(creditConfig.getSubjectCode());
                    }
                });
                super.checkSubjectCode(unionRuleDTO, creditConfigCodes, resultList, reportSystemId);
            }
            if (!CollectionUtils.isEmpty(fetchConfig.getDebitConfigList())) {
                ArrayList debitConfigCodes = new ArrayList();
                fetchConfig.getDebitConfigList().forEach(debitConfig -> {
                    if (debitConfig.getSubjectCode().isEmpty()) {
                        resultList.add(new ImportMessageVO().setERROR(unionRuleDTO.getTitle(), "\u79d1\u76ee\u4e0d\u80fd\u4e3a\u7a7a\u3002"));
                    } else {
                        debitConfigCodes.add(debitConfig.getSubjectCode());
                    }
                });
                super.checkSubjectCode(unionRuleDTO, debitConfigCodes, resultList, reportSystemId);
            }
        });
    }

    private void checkFlexibleSubjectCode(List<String> subjectCodes, Set<String> deleteSubjectCodes, String reportSystem) {
        subjectCodes.forEach(debitConfigSubject -> {
            BaseDataVO baseDataVO = GcBaseDataCenterTool.getInstance().convertBaseDataVO(GcBaseDataCenterTool.getInstance().queryBasedataByObjCode("MD_GCSUBJECT", GcBaseDataCenterTool.combiningObjectCode((String)debitConfigSubject, (String[])new String[]{reportSystem})));
            if (org.springframework.util.StringUtils.isEmpty(baseDataVO)) {
                deleteSubjectCodes.add((String)debitConfigSubject);
            }
        });
    }

    public List<String> getFormulaFetchDataSourceTable(String reportSystemId) {
        return Arrays.asList("GC_RELATED_ITEM");
    }
}

