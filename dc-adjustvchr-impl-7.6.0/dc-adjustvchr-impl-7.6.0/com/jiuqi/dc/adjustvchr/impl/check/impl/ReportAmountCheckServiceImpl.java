/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.adjustvchr.client.dto.AdjustVoucherQueryDTO
 *  com.jiuqi.dc.adjustvchr.client.vo.AdjustVchrSysOptionVO
 *  com.jiuqi.dc.base.common.utils.DataCenterUtil
 */
package com.jiuqi.dc.adjustvchr.impl.check.impl;

import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.adjustvchr.client.dto.AdjustVoucherQueryDTO;
import com.jiuqi.dc.adjustvchr.client.vo.AdjustVchrSysOptionVO;
import com.jiuqi.dc.adjustvchr.impl.check.AdjustVchrImportCheckService;
import com.jiuqi.dc.adjustvchr.impl.impexp.entity.AdjustVchrItemImportVO;
import com.jiuqi.dc.base.common.utils.DataCenterUtil;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
public class ReportAmountCheckServiceImpl
implements AdjustVchrImportCheckService {
    private static final Map<String, String> convertCurrencyMap = new HashMap<String, String>();
    private static final String EMPTY_AMOUNT_MSG = "\u7b2c%1$s\u884c\u91d1\u989d\u5217\u4e0d\u80fd\u5168\u4e3a\u7a7a\uff0c\u8bf7\u8865\u5145\u5b8c\u6574\u540e\u91cd\u65b0\u5bfc\u5165\u3002\n";
    private static final String FULL_AMOUNT_MSG = "\u7b2c%1$s\u884c\u501f\u8d37\u65b9\u4e0d\u80fd\u90fd\u6709\u503c\uff0c\u8bf7\u786e\u8ba4\u540e\u91cd\u65b0\u5bfc\u5165\u3002\n";

    @Override
    public String checkImportData(int startIndex, List<AdjustVchrItemImportVO> importData, AdjustVoucherQueryDTO importParam, AdjustVchrSysOptionVO optionVO, boolean convertEnable) {
        StringBuilder errorCheckImportMsg = new StringBuilder();
        for (int i = 0; i < importData.size(); ++i) {
            int rowIndex = startIndex + i;
            AdjustVchrItemImportVO itemImportVO = importData.get(i);
            if (!StringUtils.isNull((String)itemImportVO.getDebit()) && !StringUtils.isNull((String)itemImportVO.getCredit())) {
                errorCheckImportMsg.append(String.format(FULL_AMOUNT_MSG, rowIndex));
                continue;
            }
            if (optionVO.isEnableOrgnCurr() && !StringUtils.isNull((String)itemImportVO.getOrgND()) && !StringUtils.isNull((String)itemImportVO.getOrgNC())) {
                errorCheckImportMsg.append(String.format(FULL_AMOUNT_MSG, rowIndex));
                continue;
            }
            if (convertEnable && (!StringUtils.isNull((String)itemImportVO.getCnyDebit()) && !StringUtils.isNull((String)itemImportVO.getCnyCredit()) || !StringUtils.isNull((String)itemImportVO.getHkdDebit()) && !StringUtils.isNull((String)itemImportVO.getHkdCredit()) || !StringUtils.isNull((String)itemImportVO.getUsdDebit()) && !StringUtils.isNull((String)itemImportVO.getUsdCredit()))) {
                errorCheckImportMsg.append(String.format(FULL_AMOUNT_MSG, rowIndex));
                continue;
            }
            BigDecimal countAmount = ReportAmountCheckServiceImpl.checkAmount(convertEnable, optionVO.isEnableOrgnCurr(), itemImportVO);
            if (countAmount.compareTo(BigDecimal.ZERO) != 0) continue;
            errorCheckImportMsg.append(String.format(EMPTY_AMOUNT_MSG, rowIndex));
        }
        return errorCheckImportMsg.toString();
    }

    @Override
    public int checkOrder() {
        return 5;
    }

    private static BigDecimal checkAmount(boolean convertEnable, boolean enableOrgEnable, AdjustVchrItemImportVO importVO) {
        String unitCode = String.valueOf(importVO.getUnit());
        BigDecimal countAmount = BigDecimal.ZERO;
        BigDecimal debit = StringUtils.isNull((String)importVO.getDebit()) ? BigDecimal.ZERO : new BigDecimal(importVO.getDebit());
        BigDecimal credit = StringUtils.isNull((String)importVO.getCredit()) ? BigDecimal.ZERO : new BigDecimal(importVO.getCredit());
        countAmount = countAmount.add(debit);
        countAmount = countAmount.add(credit);
        if (enableOrgEnable) {
            BigDecimal orgnd = StringUtils.isNull((String)importVO.getOrgND()) ? BigDecimal.ZERO : new BigDecimal(importVO.getOrgND());
            BigDecimal orgnc = StringUtils.isNull((String)importVO.getOrgNC()) ? BigDecimal.ZERO : new BigDecimal(importVO.getOrgNC());
            countAmount = countAmount.add(orgnd);
            countAmount = countAmount.add(orgnc);
        }
        List amountList = DataCenterUtil.getRepCurrCode((String)unitCode);
        if (!convertEnable || amountList.isEmpty()) {
            return countAmount;
        }
        for (String amount : amountList) {
            BigDecimal converCredit;
            BigDecimal converDebit;
            if (ObjectUtils.isEmpty(convertCurrencyMap.get(amount))) continue;
            if ("\u4eba\u6c11\u5e01".equals(amount)) {
                converDebit = StringUtils.isNull((String)importVO.getCnyDebit()) ? BigDecimal.ZERO : new BigDecimal(importVO.getCnyDebit());
                countAmount = countAmount.add(converDebit);
                converCredit = StringUtils.isNull((String)importVO.getCnyCredit()) ? BigDecimal.ZERO : new BigDecimal(importVO.getCnyCredit());
                countAmount = countAmount.add(converCredit);
                continue;
            }
            if ("\u6e2f\u5e01".equals(amount)) {
                converDebit = StringUtils.isNull((String)importVO.getHkdDebit()) ? BigDecimal.ZERO : new BigDecimal(importVO.getHkdDebit());
                countAmount = countAmount.add(converDebit);
                converCredit = StringUtils.isNull((String)importVO.getHkdCredit()) ? BigDecimal.ZERO : new BigDecimal(importVO.getHkdCredit());
                countAmount = countAmount.add(converCredit);
                continue;
            }
            converDebit = StringUtils.isNull((String)importVO.getUsdDebit()) ? BigDecimal.ZERO : new BigDecimal(importVO.getUsdDebit());
            countAmount = countAmount.add(converDebit);
            converCredit = StringUtils.isNull((String)importVO.getUsdCredit()) ? BigDecimal.ZERO : new BigDecimal(importVO.getUsdCredit());
            countAmount = countAmount.add(converCredit);
        }
        return countAmount;
    }

    static {
        convertCurrencyMap.put("CNY", "\u4eba\u6c11\u5e01");
        convertCurrencyMap.put("HKD", "\u6e2f\u5e01");
        convertCurrencyMap.put("USD", "\u7f8e\u5143");
    }
}

