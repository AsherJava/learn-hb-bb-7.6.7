/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.basedata.api.vo.BaseDataVO
 *  com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool
 *  com.jiuqi.gcreport.unionrule.base.RuleHandler
 *  com.jiuqi.gcreport.unionrule.base.RuleManagerFactory
 *  com.jiuqi.gcreport.unionrule.base.RuleType
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 *  com.jiuqi.gcreport.unionrule.dto.FixedAssetsRuleDTO
 *  com.jiuqi.gcreport.unionrule.entity.UnionRuleEO
 *  com.jiuqi.gcreport.unionrule.enums.FixedAssetsTypeEnum
 *  com.jiuqi.gcreport.unionrule.enums.OffsetTypeEnum
 *  com.jiuqi.gcreport.unionrule.enums.ToleranceTypeEnum
 *  com.jiuqi.gcreport.unionrule.vo.ImportMessageVO
 *  com.jiuqi.gcreport.unionrule.vo.UnionRuleVO
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 */
package com.jiuqi.gcreport.asset.calculate.rule.base.fixedAssets;

import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.asset.calculate.rule.base.fixedAssets.FixedAssetsRuleJsonStringEO2VOHelper;
import com.jiuqi.gcreport.asset.calculate.rule.base.fixedAssets.FixedAssetsRuleJsonStringVO2EOHelper;
import com.jiuqi.gcreport.basedata.api.vo.BaseDataVO;
import com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool;
import com.jiuqi.gcreport.unionrule.base.RuleHandler;
import com.jiuqi.gcreport.unionrule.base.RuleManagerFactory;
import com.jiuqi.gcreport.unionrule.base.RuleType;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import com.jiuqi.gcreport.unionrule.dto.FixedAssetsRuleDTO;
import com.jiuqi.gcreport.unionrule.entity.UnionRuleEO;
import com.jiuqi.gcreport.unionrule.enums.FixedAssetsTypeEnum;
import com.jiuqi.gcreport.unionrule.enums.OffsetTypeEnum;
import com.jiuqi.gcreport.unionrule.enums.ToleranceTypeEnum;
import com.jiuqi.gcreport.unionrule.vo.ImportMessageVO;
import com.jiuqi.gcreport.unionrule.vo.UnionRuleVO;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class FixedAssetsRuleHandler
extends RuleHandler {
    public String convertJsonStringWhenVO2EO(UnionRuleVO unionRuleVO) {
        return FixedAssetsRuleJsonStringVO2EOHelper.newInstance(unionRuleVO).convertJsonStringWhenVO2EO();
    }

    public String convertJsonStringWhenEO2VO(UnionRuleEO unionRule) {
        return FixedAssetsRuleJsonStringEO2VOHelper.newInstance(unionRule.getJsonString(), unionRule.getReportSystem()).convertJsonStringWhenEO2VO();
    }

    public AbstractUnionRule convertEO2DTO(UnionRuleEO unionRule) {
        AbstractUnionRule rule = (AbstractUnionRule)JsonUtils.readValue((String)unionRule.getJsonString(), FixedAssetsRuleDTO.class);
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
        ruleEO.setRuleType(ruleType);
        FixedAssetsRuleDTO fixedAssetsRuleDTO = new FixedAssetsRuleDTO();
        BeanUtils.copyProperties(rule, fixedAssetsRuleDTO);
        map.put("scrappedFlag", fixedAssetsRuleDTO.getScrappedFlag());
        map.put("debitItemList", fixedAssetsRuleDTO.getDebitItemList());
        map.put("creditItemList", fixedAssetsRuleDTO.getCreditItemList());
        ruleEO.setJsonString(JsonUtils.writeValueAsString(map));
        return ruleEO;
    }

    public AbstractUnionRule convertMapToDTO(Map<String, Object> importJsonListMap) {
        return (AbstractUnionRule)JsonUtils.readValue((String)JsonUtils.writeValueAsString(importJsonListMap), FixedAssetsRuleDTO.class);
    }

    protected void checkSubjectCode(AbstractUnionRule unionRuleDTO, Collection<String> subjectCodes, Set<ImportMessageVO> resultList, String reportSystemId) {
        FixedAssetsRuleDTO fixedAssetsRuleDTO = (FixedAssetsRuleDTO)unionRuleDTO;
        if (!CollectionUtils.isEmpty(fixedAssetsRuleDTO.getDebitItemList())) {
            fixedAssetsRuleDTO.getDebitItemList().forEach(debitItemList -> {
                BaseDataVO baseDataVO;
                if (FixedAssetsTypeEnum.CUSTOMIZE.equals((Object)debitItemList.getType()) && Objects.isNull(baseDataVO = GcBaseDataCenterTool.getInstance().convertBaseDataVO(GcBaseDataCenterTool.getInstance().queryBasedataByObjCode("MD_GCSUBJECT", GcBaseDataCenterTool.combiningObjectCode((String)debitItemList.getSubjectCode(), (String[])new String[]{reportSystemId}))))) {
                    resultList.add(new ImportMessageVO().setERROR(unionRuleDTO.getTitle(), "\u79d1\u76ee" + debitItemList.getSubjectCode() + "\u5728\u5f53\u524d\u4f53\u7cfb\u4e2d\u4e0d\u5b58\u5728\u3002"));
                }
            });
        }
        if (!CollectionUtils.isEmpty(fixedAssetsRuleDTO.getCreditItemList())) {
            fixedAssetsRuleDTO.getCreditItemList().forEach(creditItemList -> {
                BaseDataVO baseDataVO;
                if (FixedAssetsTypeEnum.CUSTOMIZE.equals((Object)creditItemList.getType()) && Objects.isNull(baseDataVO = GcBaseDataCenterTool.getInstance().convertBaseDataVO(GcBaseDataCenterTool.getInstance().queryBasedataByObjCode("MD_GCSUBJECT", GcBaseDataCenterTool.combiningObjectCode((String)creditItemList.getSubjectCode(), (String[])new String[]{reportSystemId}))))) {
                    resultList.add(new ImportMessageVO().setERROR(unionRuleDTO.getTitle(), "\u79d1\u76ee" + creditItemList.getSubjectCode() + "\u5728\u5f53\u524d\u4f53\u7cfb\u4e2d\u4e0d\u5b58\u5728\u3002"));
                }
            });
        }
    }

    public List<String> getFormulaFetchDataSourceTable(String reportSystemId) {
        return Arrays.asList("GC_COMBINEDASSETBILL");
    }
}

