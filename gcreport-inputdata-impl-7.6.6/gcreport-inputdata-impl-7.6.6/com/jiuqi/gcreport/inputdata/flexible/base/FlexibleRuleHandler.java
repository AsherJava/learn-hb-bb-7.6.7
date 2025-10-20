/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.basedata.api.vo.BaseDataVO
 *  com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool
 *  com.jiuqi.gcreport.common.util.JsonUtils
 *  com.jiuqi.gcreport.consolidatedsystem.service.ConsolidatedSystemService
 *  com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService
 *  com.jiuqi.gcreport.consolidatedsystem.vo.ConsolidatedSystemVO
 *  com.jiuqi.gcreport.unionrule.base.RuleHandler
 *  com.jiuqi.gcreport.unionrule.base.RuleManagerFactory
 *  com.jiuqi.gcreport.unionrule.base.RuleType
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 *  com.jiuqi.gcreport.unionrule.dto.FlexibleFetchConfig
 *  com.jiuqi.gcreport.unionrule.dto.FlexibleRuleDTO
 *  com.jiuqi.gcreport.unionrule.entity.UnionRuleEO
 *  com.jiuqi.gcreport.unionrule.enums.OffsetTypeEnum
 *  com.jiuqi.gcreport.unionrule.enums.ToleranceTypeEnum
 *  com.jiuqi.gcreport.unionrule.util.UnionRuleConverter
 *  com.jiuqi.gcreport.unionrule.vo.ImportMessageVO
 *  com.jiuqi.gcreport.unionrule.vo.UnionRuleVO
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 */
package com.jiuqi.gcreport.inputdata.flexible.base;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.basedata.api.vo.BaseDataVO;
import com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool;
import com.jiuqi.gcreport.common.util.JsonUtils;
import com.jiuqi.gcreport.consolidatedsystem.service.ConsolidatedSystemService;
import com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService;
import com.jiuqi.gcreport.consolidatedsystem.vo.ConsolidatedSystemVO;
import com.jiuqi.gcreport.inputdata.flexible.base.FlexibleRuleJsonStringEO2VOHelper;
import com.jiuqi.gcreport.inputdata.flexible.base.FlexibleRuleJsonStringVO2EOHelper;
import com.jiuqi.gcreport.inputdata.util.InputDataNameProvider;
import com.jiuqi.gcreport.unionrule.base.RuleHandler;
import com.jiuqi.gcreport.unionrule.base.RuleManagerFactory;
import com.jiuqi.gcreport.unionrule.base.RuleType;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import com.jiuqi.gcreport.unionrule.dto.FlexibleFetchConfig;
import com.jiuqi.gcreport.unionrule.dto.FlexibleRuleDTO;
import com.jiuqi.gcreport.unionrule.entity.UnionRuleEO;
import com.jiuqi.gcreport.unionrule.enums.OffsetTypeEnum;
import com.jiuqi.gcreport.unionrule.enums.ToleranceTypeEnum;
import com.jiuqi.gcreport.unionrule.util.UnionRuleConverter;
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
public class FlexibleRuleHandler
extends RuleHandler {
    @Autowired
    ConsolidatedSystemService systemService;
    @Autowired
    private InputDataNameProvider inputDataNameProvider;
    @Autowired
    private ConsolidatedSubjectService consolidatedSubjectService;
    private static ObjectMapper mapper = com.jiuqi.common.base.util.JsonUtils.newObjectMapperEnumNull();

    public String convertJsonStringWhenVO2EO(UnionRuleVO unionRuleVO) {
        return FlexibleRuleJsonStringVO2EOHelper.newInstance(unionRuleVO).convertJsonStringWhenVO2EO();
    }

    public String convertJsonStringWhenEO2VO(UnionRuleEO unionRule) {
        return FlexibleRuleJsonStringEO2VOHelper.newInstance(unionRule.getJsonString(), unionRule.getReportSystem()).convertJsonStringWhenEO2VO();
    }

    public AbstractUnionRule convertEO2DTO(UnionRuleEO unionRule) {
        FlexibleRuleDTO rule;
        String jsonString = unionRule.getJsonString();
        try {
            rule = (FlexibleRuleDTO)mapper.readValue(jsonString, FlexibleRuleDTO.class);
        }
        catch (JsonProcessingException e) {
            throw new BusinessRuntimeException((Throwable)e);
        }
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
        rule.setSrcDebitAllChildrenCodes(this.consolidatedSubjectService.listAllChildrenCodesContainsSelf(rule.getSrcDebitSubjectCodeList(), rule.getReportSystem()));
        rule.setSrcCreditAllChildrenCodes(this.consolidatedSubjectService.listAllChildrenCodesContainsSelf(rule.getSrcCreditSubjectCodeList(), rule.getReportSystem()));
        return rule;
    }

    public UnionRuleEO convertDTO2EO(AbstractUnionRule rule) {
        UnionRuleEO ruleEO = new UnionRuleEO();
        HashMap<String, Object> map = new HashMap<String, Object>();
        String ruleType = rule.getRuleType();
        FlexibleRuleDTO flexibleRuleDTO = new FlexibleRuleDTO();
        BeanUtils.copyProperties(rule, flexibleRuleDTO);
        map.put("offsetGroupingField", flexibleRuleDTO.getOffsetGroupingField());
        map.put("realTimeOffsetFlag", flexibleRuleDTO.getRealTimeOffsetFlag());
        map.put("reconciliationOffsetFlag", flexibleRuleDTO.getReconciliationOffsetFlag());
        map.put("oneToOneOffsetFlag", flexibleRuleDTO.getOneToOneOffsetFlag());
        map.put("unilateralOffsetFlag", flexibleRuleDTO.getUnilateralOffsetFlag());
        map.put("proportionOffsetDiffFlag", flexibleRuleDTO.getProportionOffsetDiffFlag());
        map.put("generatePHSFlag", flexibleRuleDTO.getGeneratePHSFlag());
        map.put("checkOffsetFlag", flexibleRuleDTO.getCheckOffsetFlag());
        map.put("unCheckOffsetFlag", flexibleRuleDTO.getUnCheckOffsetFlag());
        map.put("corporateOffsetFlag", flexibleRuleDTO.getCorporateOffsetFlag());
        map.put("debitItemList", flexibleRuleDTO.getDebitItemList());
        map.put("creditItemList", flexibleRuleDTO.getCreditItemList());
        map.put("fetchConfigList", flexibleRuleDTO.getFetchConfigList());
        ruleEO.setJsonString(com.jiuqi.common.base.util.JsonUtils.writeValueAsString(map));
        ruleEO.setRuleType(ruleType);
        return ruleEO;
    }

    public AbstractUnionRule convertMapToDTO(Map<String, Object> importJsonListMap) {
        return (AbstractUnionRule)com.jiuqi.common.base.util.JsonUtils.readValue((String)JsonUtils.writeValueAsString(importJsonListMap), FlexibleRuleDTO.class);
    }

    public String reorganizeJsonStringWhenCopy(String jsonString) {
        FlexibleRuleDTO flexibleRuleDTO;
        try {
            flexibleRuleDTO = (FlexibleRuleDTO)mapper.readValue(jsonString, FlexibleRuleDTO.class);
        }
        catch (JsonProcessingException e2) {
            throw new BusinessRuntimeException((Throwable)e2);
        }
        flexibleRuleDTO.getFetchConfigList().forEach(e -> e.setFetchSetGroupId(UUIDUtils.newUUIDStr()));
        HashMap map = Maps.newHashMap();
        map.put("offsetGroupingField", flexibleRuleDTO.getOffsetGroupingField());
        map.put("realTimeOffsetFlag", flexibleRuleDTO.getRealTimeOffsetFlag());
        map.put("reconciliationOffsetFlag", flexibleRuleDTO.getReconciliationOffsetFlag());
        map.put("oneToOneOffsetFlag", flexibleRuleDTO.getOneToOneOffsetFlag());
        map.put("unilateralOffsetFlag", flexibleRuleDTO.getUnilateralOffsetFlag());
        map.put("proportionOffsetDiffFlag", flexibleRuleDTO.getProportionOffsetDiffFlag());
        map.put("generatePHSFlag", flexibleRuleDTO.getGeneratePHSFlag());
        map.put("checkOffsetFlag", flexibleRuleDTO.getCheckOffsetFlag());
        map.put("unCheckOffsetFlag", flexibleRuleDTO.getUnCheckOffsetFlag());
        map.put("corporateOffsetFlag", flexibleRuleDTO.getCorporateOffsetFlag());
        map.put("fetchConfigList", flexibleRuleDTO.getFetchConfigList());
        map.put("debitItemList", flexibleRuleDTO.getDebitItemList());
        map.put("creditItemList", flexibleRuleDTO.getCreditItemList());
        return com.jiuqi.common.base.util.JsonUtils.writeValueAsString((Object)map);
    }

    public Set<ImportMessageVO> checkRuleSubjectCode(AbstractUnionRule unionRuleDTO, String reportSystemId) {
        HashSet<String> deleteSubjectCodes;
        HashSet<ImportMessageVO> resultList = new HashSet<ImportMessageVO>();
        FlexibleRuleDTO flexibleRuleDTO = (FlexibleRuleDTO)unionRuleDTO;
        if (!CollectionUtils.isEmpty(flexibleRuleDTO.getFetchConfigList())) {
            this.checkFetchConfigSubjectCode(unionRuleDTO, flexibleRuleDTO.getFetchConfigList(), resultList, reportSystemId);
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

    private void checkFetchConfigSubjectCode(AbstractUnionRule unionRuleDTO, List<FlexibleFetchConfig> fetchConfigList, Set<ImportMessageVO> resultList, String reportSystemId) {
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
        ConsolidatedSystemVO consolidatedSystem = this.systemService.getConsolidatedSystemVO(reportSystemId);
        String tableName = this.inputDataNameProvider.getTableNameByDataSchemeKey(consolidatedSystem.getDataSchemeKey());
        if (StringUtils.isEmpty((String)tableName)) {
            throw new BusinessRuntimeException("\u83b7\u53d6\u5185\u90e8\u8868\u8868\u540d\u4e3a\u7a7a\uff0creportSystemId:" + reportSystemId);
        }
        return Arrays.asList(tableName);
    }

    public List<UnionRuleVO> getUnionRuleChildNodes(UnionRuleEO parent) {
        FlexibleRuleDTO rule = (FlexibleRuleDTO)UnionRuleConverter.convert((UnionRuleEO)parent);
        ArrayList flexChildren = Lists.newArrayList();
        if (rule.getFetchConfigList().size() <= 1) {
            return flexChildren;
        }
        int num = 0;
        for (FlexibleFetchConfig config : rule.getFetchConfigList()) {
            if (StringUtils.isEmpty((String)config.getDescription())) {
                ++num;
            }
            UnionRuleVO newRuleVO = new UnionRuleVO();
            newRuleVO.setId(config.getFetchSetGroupId());
            String des = StringUtils.isEmpty((String)config.getDescription()) ? parent.getTitle() + num : config.getDescription();
            newRuleVO.setTitle(des);
            flexChildren.add(newRuleVO);
        }
        return flexChildren;
    }

    public boolean filterRuleByParams(UnionRuleEO rule, Map<String, Object> params) {
        if (params.isEmpty()) {
            return true;
        }
        if (params.containsKey("checkOffsetFlag")) {
            boolean checkOffsetFlag = Boolean.valueOf(String.valueOf(params.get("checkOffsetFlag")));
            FlexibleRuleDTO flexibleRuleDTO = (FlexibleRuleDTO)UnionRuleConverter.convert((UnionRuleEO)rule);
            return checkOffsetFlag && flexibleRuleDTO.getCheckOffsetFlag() != false;
        }
        return true;
    }
}

