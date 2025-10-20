/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.common.util.JsonUtils
 *  com.jiuqi.gcreport.unionrule.base.RuleHandler
 *  com.jiuqi.gcreport.unionrule.base.RuleManagerFactory
 *  com.jiuqi.gcreport.unionrule.base.RuleType
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 *  com.jiuqi.gcreport.unionrule.dto.FloatLineRuleDTO
 *  com.jiuqi.gcreport.unionrule.entity.UnionRuleEO
 *  com.jiuqi.gcreport.unionrule.enums.OffsetTypeEnum
 *  com.jiuqi.gcreport.unionrule.enums.ToleranceTypeEnum
 *  com.jiuqi.gcreport.unionrule.vo.ImportMessageVO
 *  com.jiuqi.gcreport.unionrule.vo.UnionRuleVO
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 */
package com.jiuqi.gcreport.calculate.rule.floatline;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.common.util.JsonUtils;
import com.jiuqi.gcreport.unionrule.base.RuleHandler;
import com.jiuqi.gcreport.unionrule.base.RuleManagerFactory;
import com.jiuqi.gcreport.unionrule.base.RuleType;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import com.jiuqi.gcreport.unionrule.dto.FloatLineRuleDTO;
import com.jiuqi.gcreport.unionrule.entity.UnionRuleEO;
import com.jiuqi.gcreport.unionrule.enums.OffsetTypeEnum;
import com.jiuqi.gcreport.unionrule.enums.ToleranceTypeEnum;
import com.jiuqi.gcreport.unionrule.vo.ImportMessageVO;
import com.jiuqi.gcreport.unionrule.vo.UnionRuleVO;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class FloatLineRuleHandler
extends RuleHandler {
    public String convertJsonStringWhenVO2EO(UnionRuleVO unionRuleVO) {
        this.checkFloatRule(unionRuleVO);
        return unionRuleVO.getJsonString();
    }

    private void checkFloatRule(UnionRuleVO unionRuleVO) {
        Map ruleJson = (Map)JsonUtils.readValue((String)unionRuleVO.getJsonString(), (TypeReference)new TypeReference<Map<String, Object>>(){});
        List debitItems = (List)JsonUtils.readValue((String)JsonUtils.writeValueAsString(ruleJson.get("debitItemList")), (TypeReference)new TypeReference<List<Map<String, Object>>>(){});
        List creditItems = (List)JsonUtils.readValue((String)JsonUtils.writeValueAsString(ruleJson.get("creditItemList")), (TypeReference)new TypeReference<List<Map<String, Object>>>(){});
        for (int debitIndex = 0; debitIndex < debitItems.size(); ++debitIndex) {
            if (Objects.isNull(((Map)debitItems.get(debitIndex)).get("unit"))) {
                throw new BusinessRuntimeException("\u501f\u65b9\u7b2c" + (debitIndex + 1) + "\u884c\u201c\u501f\u65b9\u5355\u4f4d\u201d\u4e0d\u80fd\u4e3a\u7a7a\u3002");
            }
            if (Objects.isNull(((Map)debitItems.get(debitIndex)).get("subject"))) {
                throw new BusinessRuntimeException("\u501f\u65b9\u7b2c" + (debitIndex + 1) + "\u884c\u201c\u501f\u65b9\u79d1\u76ee\u201d\u4e0d\u80fd\u4e3a\u7a7a\u3002");
            }
            if (!Objects.isNull(((Map)debitItems.get(debitIndex)).get("amt"))) continue;
            throw new BusinessRuntimeException("\u501f\u65b9\u7b2c" + (debitIndex + 1) + "\u884c\u201c\u501f\u65b9\u91d1\u989d\u201d\u4e0d\u80fd\u4e3a\u7a7a\u3002");
        }
        for (int creditIndex = 0; creditIndex < creditItems.size(); ++creditIndex) {
            if (Objects.isNull(((Map)creditItems.get(creditIndex)).get("unit"))) {
                throw new BusinessRuntimeException("\u8d37\u65b9\u7b2c" + (creditIndex + 1) + "\u884c\u201c\u8d37\u65b9\u5355\u4f4d\u201d\u4e0d\u80fd\u4e3a\u7a7a\u3002");
            }
            if (Objects.isNull(((Map)creditItems.get(creditIndex)).get("subject"))) {
                throw new BusinessRuntimeException("\u8d37\u65b9\u7b2c" + (creditIndex + 1) + "\u884c\u201c\u8d37\u65b9\u79d1\u76ee\u201d\u4e0d\u80fd\u4e3a\u7a7a\u3002");
            }
            if (!Objects.isNull(((Map)creditItems.get(creditIndex)).get("amt"))) continue;
            throw new BusinessRuntimeException("\u8d37\u65b9\u7b2c" + (creditIndex + 1) + "\u884c\u201c\u8d37\u65b9\u91d1\u989d\u201d\u4e0d\u80fd\u4e3a\u7a7a\u3002");
        }
    }

    public String convertJsonStringWhenEO2VO(UnionRuleEO unionRule) {
        return unionRule.getJsonString();
    }

    public AbstractUnionRule convertEO2DTO(UnionRuleEO unionRule) {
        AbstractUnionRule rule = (AbstractUnionRule)com.jiuqi.common.base.util.JsonUtils.readValue((String)unionRule.getJsonString(), FloatLineRuleDTO.class);
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
        FloatLineRuleDTO floatLineRuleDTO = new FloatLineRuleDTO();
        BeanUtils.copyProperties(rule, floatLineRuleDTO);
        map.put("proportionOffsetDiffFlag", floatLineRuleDTO.getProportionOffsetDiffFlag());
        map.put("floatLineDataRegion", floatLineRuleDTO.getFloatLineDataRegion());
        map.put("debitItemList", floatLineRuleDTO.getDebitItemList());
        map.put("creditItemList", floatLineRuleDTO.getCreditItemList());
        map.put("fetchUnit", floatLineRuleDTO.getFetchUnit());
        ruleEO.setJsonString(com.jiuqi.common.base.util.JsonUtils.writeValueAsString(map));
        return ruleEO;
    }

    public AbstractUnionRule convertMapToDTO(Map<String, Object> importJsonListMap) {
        return (AbstractUnionRule)com.jiuqi.common.base.util.JsonUtils.readValue((String)com.jiuqi.common.base.util.JsonUtils.writeValueAsString(importJsonListMap), FloatLineRuleDTO.class);
    }

    public Set<ImportMessageVO> checkRuleSubjectCode(AbstractUnionRule unionRuleDTO, String reportSystemId) {
        HashSet<ImportMessageVO> resultList = new HashSet<ImportMessageVO>();
        FloatLineRuleDTO floatLineRuleDTO = (FloatLineRuleDTO)unionRuleDTO;
        if (!CollectionUtils.isEmpty(floatLineRuleDTO.getDebitItemList())) {
            floatLineRuleDTO.getDebitItemList().forEach(debitItemList -> {
                if (Objects.isNull(debitItemList.getUnit())) {
                    resultList.add(new ImportMessageVO().setERROR(unionRuleDTO.getTitle(), "\u501f\u65b9\u5355\u4f4d\u4e0d\u80fd\u4e3a\u7a7a\u3002"));
                }
                if (Objects.isNull(debitItemList.getSubject())) {
                    resultList.add(new ImportMessageVO().setERROR(unionRuleDTO.getTitle(), "\u501f\u65b9\u79d1\u76ee\u4e0d\u80fd\u4e3a\u7a7a\u3002"));
                }
                if (Objects.isNull(debitItemList.getAmt())) {
                    resultList.add(new ImportMessageVO().setERROR(unionRuleDTO.getTitle(), "\u501f\u65b9\u91d1\u989d\u4e0d\u80fd\u4e3a\u7a7a\u3002"));
                }
            });
        }
        if (!CollectionUtils.isEmpty(floatLineRuleDTO.getCreditItemList())) {
            floatLineRuleDTO.getCreditItemList().forEach(creditItemList -> {
                if (Objects.isNull(creditItemList.getUnit())) {
                    resultList.add(new ImportMessageVO().setERROR(unionRuleDTO.getTitle(), "\u8d37\u65b9\u5355\u4f4d\u4e0d\u80fd\u4e3a\u7a7a\u3002"));
                }
                if (Objects.isNull(creditItemList.getSubject())) {
                    resultList.add(new ImportMessageVO().setERROR(unionRuleDTO.getTitle(), "\u8d37\u65b9\u79d1\u76ee\u4e0d\u80fd\u4e3a\u7a7a\u3002"));
                }
                if (Objects.isNull(creditItemList.getAmt())) {
                    resultList.add(new ImportMessageVO().setERROR(unionRuleDTO.getTitle(), "\u8d37\u65b9\u91d1\u989d\u4e0d\u80fd\u4e3a\u7a7a\u3002"));
                }
            });
        }
        return resultList;
    }
}

