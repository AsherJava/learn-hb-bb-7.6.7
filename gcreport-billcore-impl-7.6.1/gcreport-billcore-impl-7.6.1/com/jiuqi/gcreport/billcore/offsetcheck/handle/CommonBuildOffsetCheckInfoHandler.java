/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.NumberUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.billcore.offsetcheck.dto.BillOffsetCheckInfoDTO
 *  com.jiuqi.gcreport.common.OrientEnum
 *  com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO
 *  com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 */
package com.jiuqi.gcreport.billcore.offsetcheck.handle;

import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.NumberUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.billcore.offsetcheck.dto.BillOffsetCheckInfoDTO;
import com.jiuqi.gcreport.common.OrientEnum;
import com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO;
import com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import java.math.BigDecimal;
import java.util.Map;

public class CommonBuildOffsetCheckInfoHandler {
    public static final String SUBJECTCODE = "SUBJECTCODE";

    protected BillOffsetCheckInfoDTO buildBlankDataTraceDTO(Map<String, ConsolidatedSubjectEO> subjectCode2DataMap, AbstractUnionRule rule, String subjectCode, String fetchFormula) {
        if (StringUtils.isEmpty((String)subjectCode) || rule == null) {
            return null;
        }
        BillOffsetCheckInfoDTO dataTraceCheckInfoDTO = new BillOffsetCheckInfoDTO(rule.getId(), subjectCode, fetchFormula);
        dataTraceCheckInfoDTO.setRuleTitle(rule.getLocalizedName());
        ConsolidatedSubjectEO consolidatedSubjectEO = subjectCode2DataMap.get(subjectCode);
        if (consolidatedSubjectEO != null) {
            dataTraceCheckInfoDTO.setSubjectTitle(consolidatedSubjectEO.getTitle());
        }
        return dataTraceCheckInfoDTO;
    }

    protected void appendBuildDataTraceDTO(BillOffsetCheckInfoDTO dataTraceCheckInfoDTO, AbstractUnionRule rule, Map<String, Map<String, Object>> subjectCode2OriginOffsetItem, Map<String, GcOffSetVchrItemDTO> subjectCode2PreCalcOffSetItem) {
        GcOffSetVchrItemDTO preCalcOffSetItem;
        Map<String, Object> originOffsetItem;
        String subjectCode = dataTraceCheckInfoDTO.getSubjectCode();
        dataTraceCheckInfoDTO.setRuleType(rule.getRuleType());
        dataTraceCheckInfoDTO.setRuleTitle(rule.getLocalizedName());
        dataTraceCheckInfoDTO.setRuleApplyConditon(rule.getRuleCondition());
        dataTraceCheckInfoDTO.setRuleTypeTitle(rule.getRuleType());
        dataTraceCheckInfoDTO.setRuleTypeTitle(rule.getRuleTypeDescription());
        BigDecimal offsetDebit = BigDecimal.ZERO;
        BigDecimal offsetCredit = BigDecimal.ZERO;
        if (subjectCode2OriginOffsetItem != null && subjectCode2OriginOffsetItem.size() > 0 && (originOffsetItem = subjectCode2OriginOffsetItem.get(subjectCode)) != null && originOffsetItem.size() > 0) {
            offsetDebit = ConverterUtils.getAsBigDecimal((Object)originOffsetItem.get("OFFSETDEBIT"), (BigDecimal)BigDecimal.ZERO);
            offsetCredit = ConverterUtils.getAsBigDecimal((Object)originOffsetItem.get("OFFSETCREDIT"), (BigDecimal)BigDecimal.ZERO);
            Integer amtOrient = (Integer)originOffsetItem.get("SUBJECTORIENT");
            if (offsetDebit.compareTo(BigDecimal.ZERO) == 0) {
                amtOrient = OrientEnum.C.getValue();
            }
            if (offsetCredit.compareTo(BigDecimal.ZERO) == 0) {
                amtOrient = OrientEnum.D.getValue();
            }
            if (amtOrient == OrientEnum.D.getValue()) {
                dataTraceCheckInfoDTO.setOffsetDebitInfo(NumberUtils.doubleToString((double)offsetDebit.doubleValue()));
            } else {
                dataTraceCheckInfoDTO.setOffsetCreditInfo(NumberUtils.doubleToString((double)offsetCredit.doubleValue()));
            }
        }
        BigDecimal checkOffsetDebit = BigDecimal.ZERO;
        BigDecimal checkOffsetCredit = BigDecimal.ZERO;
        if (subjectCode2PreCalcOffSetItem != null && subjectCode2PreCalcOffSetItem.size() > 0 && (preCalcOffSetItem = subjectCode2PreCalcOffSetItem.get(subjectCode)) != null) {
            checkOffsetDebit = ConverterUtils.getAsBigDecimal((Object)preCalcOffSetItem.getOffSetDebit(), (BigDecimal)BigDecimal.ZERO);
            checkOffsetCredit = ConverterUtils.getAsBigDecimal((Object)preCalcOffSetItem.getOffSetCredit(), (BigDecimal)BigDecimal.ZERO);
            if (preCalcOffSetItem.getOrient() == OrientEnum.D) {
                dataTraceCheckInfoDTO.setCheckOffsetDebitInfo(NumberUtils.doubleToString((double)checkOffsetDebit.doubleValue()));
            } else {
                dataTraceCheckInfoDTO.setCheckOffsetCreditInfo(NumberUtils.doubleToString((double)checkOffsetCredit.doubleValue()));
            }
        }
        if (offsetDebit.compareTo(BigDecimal.ZERO) == 0 && offsetCredit.compareTo(BigDecimal.ZERO) == 0 && checkOffsetDebit.compareTo(BigDecimal.ZERO) == 0 && checkOffsetCredit.compareTo(BigDecimal.ZERO) == 0) {
            dataTraceCheckInfoDTO.setRowAmtAllNull(true);
        } else {
            BigDecimal checkCreditDiff;
            BigDecimal checkDebitDiff = NumberUtils.sub((BigDecimal)checkOffsetDebit, (BigDecimal)offsetDebit);
            if (checkDebitDiff.compareTo(BigDecimal.ZERO) != 0) {
                dataTraceCheckInfoDTO.setCheckDebitDiffInfo(NumberUtils.doubleToString((double)NumberUtils.sub((BigDecimal)checkOffsetDebit, (BigDecimal)offsetDebit).doubleValue(), (int)2));
            }
            if ((checkCreditDiff = NumberUtils.sub((BigDecimal)checkOffsetCredit, (BigDecimal)offsetCredit)).compareTo(BigDecimal.ZERO) != 0) {
                dataTraceCheckInfoDTO.setCheckCreditDiffInfo(NumberUtils.doubleToString((double)NumberUtils.sub((BigDecimal)checkOffsetCredit, (BigDecimal)offsetCredit).doubleValue(), (int)2));
            }
        }
    }
}

