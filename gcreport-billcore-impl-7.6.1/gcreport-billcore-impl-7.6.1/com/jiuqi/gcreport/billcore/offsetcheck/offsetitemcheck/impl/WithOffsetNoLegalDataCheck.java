/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.NumberUtils
 *  com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO
 */
package com.jiuqi.gcreport.billcore.offsetcheck.offsetitemcheck.impl;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.NumberUtils;
import com.jiuqi.gcreport.billcore.offsetcheck.dto.OffsetCheckResultDTO;
import com.jiuqi.gcreport.billcore.offsetcheck.enums.CheckStatusEnum;
import com.jiuqi.gcreport.billcore.offsetcheck.enums.OffsetCheckInfoEnum;
import com.jiuqi.gcreport.billcore.offsetcheck.offsetitemcheck.OffsetItemCheck;
import com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class WithOffsetNoLegalDataCheck
implements OffsetItemCheck {
    @Override
    public String getType() {
        return "COMMONCHECK";
    }

    @Override
    public String getTitle() {
        return "\u62b5\u9500\u5206\u5f55\u6709\u6570\u636e\uff0c\u62b5\u9500\u68c0\u67e5\u65e0\u5408\u6cd5\u62b5\u9500\u6570\u636e\u573a\u666f\u6821\u9a8c";
    }

    @Override
    public OffsetCheckResultDTO check(List<Map<String, Object>> originOffsetItems, List<GcOffSetVchrItemDTO> preCalcOffSetItems, String gcDataTraceType) {
        if (!CollectionUtils.isEmpty(originOffsetItems) && !CollectionUtils.isEmpty(preCalcOffSetItems)) {
            BigDecimal preCalcOffSetItemDebitSum = BigDecimal.ZERO;
            BigDecimal preCalcOffSetItemCreditSum = BigDecimal.ZERO;
            boolean isAllZero = true;
            for (GcOffSetVchrItemDTO offSetVchrItemDTO : preCalcOffSetItems) {
                BigDecimal offsetDebit = ConverterUtils.getAsBigDecimal((Object)offSetVchrItemDTO.getOffSetDebit(), (BigDecimal)BigDecimal.ZERO);
                BigDecimal offsetCredit = ConverterUtils.getAsBigDecimal((Object)offSetVchrItemDTO.getOffSetCredit(), (BigDecimal)BigDecimal.ZERO);
                if (isAllZero && (offsetDebit.compareTo(BigDecimal.ZERO) != 0 || offsetCredit.compareTo(BigDecimal.ZERO) != 0)) {
                    isAllZero = false;
                }
                preCalcOffSetItemDebitSum = NumberUtils.sum((BigDecimal)preCalcOffSetItemDebitSum, (BigDecimal)offsetDebit);
                preCalcOffSetItemCreditSum = NumberUtils.sum((BigDecimal)preCalcOffSetItemCreditSum, (BigDecimal)offsetCredit);
            }
            if (isAllZero) {
                return new OffsetCheckResultDTO(OffsetCheckInfoEnum.CALCULATION_RESULT_ZERO_AFTER_CHANGE.getOffsetCheckSceneTypeName(gcDataTraceType), CheckStatusEnum.CHECK_INCONSISTENT.getCode());
            }
            if (preCalcOffSetItemDebitSum.compareTo(preCalcOffSetItemCreditSum) != 0) {
                return new OffsetCheckResultDTO(OffsetCheckInfoEnum.DEBIT_CREDIT_MISMATCH_AFTER_CHANGE.getOffsetCheckSceneTypeName(gcDataTraceType), CheckStatusEnum.CHECK_INCONSISTENT.getCode());
            }
        }
        return null;
    }
}

