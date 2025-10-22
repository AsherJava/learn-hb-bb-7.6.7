/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.billcore.offsetcheck.dto.BillOffsetCheckInfoDTO
 *  com.jiuqi.gcreport.billcore.offsetcheck.handle.BuildBaseOffsetCheckInfoHandler
 *  com.jiuqi.gcreport.billcore.offsetcheck.handle.CommonBuildOffsetCheckInfoHandler
 *  com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO
 *  com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 *  com.jiuqi.gcreport.unionrule.dto.PublicValueAdjustmentRuleDTO
 *  com.jiuqi.gcreport.unionrule.dto.PublicValueAdjustmentRuleDTO$Item
 *  com.jiuqi.gcreport.unionrule.enums.RuleTypeEnum
 */
package com.jiuqi.gcreport.invest.datatrace;

import com.jiuqi.gcreport.billcore.offsetcheck.dto.BillOffsetCheckInfoDTO;
import com.jiuqi.gcreport.billcore.offsetcheck.handle.BuildBaseOffsetCheckInfoHandler;
import com.jiuqi.gcreport.billcore.offsetcheck.handle.CommonBuildOffsetCheckInfoHandler;
import com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO;
import com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import com.jiuqi.gcreport.unionrule.dto.PublicValueAdjustmentRuleDTO;
import com.jiuqi.gcreport.unionrule.enums.RuleTypeEnum;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class FvchBuildOffsetCheckInfoHandler
extends CommonBuildOffsetCheckInfoHandler
implements BuildBaseOffsetCheckInfoHandler {
    public static final String SUBJECTCODE = "SUBJECTCODE";

    public String getTitle() {
        return "\u6839\u636e\u516c\u5141\u89c4\u5219 \u6784\u5efa\u62b5\u9500\u68c0\u67e5\u57fa\u672c\u4fe1\u606f";
    }

    public List<BillOffsetCheckInfoDTO> buildBaseOffsetCheckInfo(AbstractUnionRule unionRule, Map<String, ConsolidatedSubjectEO> subjectCode2DataMap, Map<String, Map<String, Object>> subjectCode2OriginOffsetItem, Map<String, GcOffSetVchrItemDTO> subjectCode2PreCalcOffSetItem) {
        BillOffsetCheckInfoDTO dataTraceCheckInfoDTO;
        PublicValueAdjustmentRuleDTO adjustmentRuleDTO = (PublicValueAdjustmentRuleDTO)unionRule;
        List adjustmentRuleDebitItemList = adjustmentRuleDTO.getDebitItemList();
        List adjustmentRuleCreditItemList = adjustmentRuleDTO.getCreditItemList();
        ArrayList<BillOffsetCheckInfoDTO> dataTraceCheckInfoDTOsOfOneRule = new ArrayList<BillOffsetCheckInfoDTO>();
        for (PublicValueAdjustmentRuleDTO.Item debitItem : adjustmentRuleDebitItemList) {
            dataTraceCheckInfoDTO = this.buildBlankDataTraceDTO(subjectCode2DataMap, unionRule, debitItem.getSubjectCode(), debitItem.getFetchFormula());
            this.appendBuildDataTraceDTO(dataTraceCheckInfoDTO, unionRule, subjectCode2OriginOffsetItem, subjectCode2PreCalcOffSetItem);
            dataTraceCheckInfoDTOsOfOneRule.add(dataTraceCheckInfoDTO);
        }
        for (PublicValueAdjustmentRuleDTO.Item creditItem : adjustmentRuleCreditItemList) {
            dataTraceCheckInfoDTO = this.buildBlankDataTraceDTO(subjectCode2DataMap, unionRule, creditItem.getSubjectCode(), creditItem.getFetchFormula());
            this.appendBuildDataTraceDTO(dataTraceCheckInfoDTO, unionRule, subjectCode2OriginOffsetItem, subjectCode2PreCalcOffSetItem);
            dataTraceCheckInfoDTOsOfOneRule.add(dataTraceCheckInfoDTO);
        }
        return dataTraceCheckInfoDTOsOfOneRule;
    }

    public List<BillOffsetCheckInfoDTO> buildBaseOffsetCheckInfo(AbstractUnionRule unionRule, List<Map<String, Object>> originOffsetItemList, Map<String, ConsolidatedSubjectEO> subjectCode2DataMap) {
        BillOffsetCheckInfoDTO dataTraceCheckInfoDTO;
        Map subjectCode2OriginOffsetItem = new LinkedHashMap();
        if (!CollectionUtils.isEmpty(originOffsetItemList)) {
            subjectCode2OriginOffsetItem = originOffsetItemList.stream().collect(Collectors.toMap(item -> (String)item.get(SUBJECTCODE), item -> item, (v1, v2) -> v1, LinkedHashMap::new));
        }
        PublicValueAdjustmentRuleDTO adjustmentRuleDTO = (PublicValueAdjustmentRuleDTO)unionRule;
        List adjustmentRuleDebitItemList = adjustmentRuleDTO.getDebitItemList();
        List adjustmentRuleCreditItemList = adjustmentRuleDTO.getCreditItemList();
        ArrayList<BillOffsetCheckInfoDTO> dataTraceCheckInfoDTOsOfOneRule = new ArrayList<BillOffsetCheckInfoDTO>();
        for (PublicValueAdjustmentRuleDTO.Item debitItem : adjustmentRuleDebitItemList) {
            dataTraceCheckInfoDTO = this.buildBlankDataTraceDTO(subjectCode2DataMap, unionRule, debitItem.getSubjectCode(), debitItem.getFetchFormula());
            this.appendBuildDataTraceDTO(dataTraceCheckInfoDTO, unionRule, subjectCode2OriginOffsetItem, null);
            dataTraceCheckInfoDTOsOfOneRule.add(dataTraceCheckInfoDTO);
        }
        for (PublicValueAdjustmentRuleDTO.Item creditItem : adjustmentRuleCreditItemList) {
            dataTraceCheckInfoDTO = this.buildBlankDataTraceDTO(subjectCode2DataMap, unionRule, creditItem.getSubjectCode(), creditItem.getFetchFormula());
            this.appendBuildDataTraceDTO(dataTraceCheckInfoDTO, unionRule, subjectCode2OriginOffsetItem, null);
            dataTraceCheckInfoDTOsOfOneRule.add(dataTraceCheckInfoDTO);
        }
        return dataTraceCheckInfoDTOsOfOneRule;
    }

    public boolean isMatch(String ruleType) {
        return RuleTypeEnum.PUBLIC_VALUE_ADJUSTMENT.getCode().equals(ruleType);
    }
}

