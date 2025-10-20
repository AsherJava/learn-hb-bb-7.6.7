/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.subject.impl.subject.enums.OrientEnum
 *  com.jiuqi.va.domain.common.PageVO
 */
package com.jiuqi.dc.query.client.detail.util;

import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.subject.impl.subject.enums.OrientEnum;
import com.jiuqi.dc.query.client.detail.data.DetailQueryContext;
import com.jiuqi.dc.query.client.detail.data.DetailQueryDataContent;
import com.jiuqi.dc.query.client.detail.data.DetailQueryPreData;
import com.jiuqi.dc.query.client.detail.data.DetailRowDataVO;
import com.jiuqi.dc.query.client.detail.enums.DetailExtraRowDigestEnum;
import com.jiuqi.dc.query.client.detail.enums.DetailTableColumnEnum;
import com.jiuqi.va.domain.common.PageVO;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DetailQueryDataManageUtils {
    public static PageVO<LinkedHashMap<String, Object>> detailQueryDataManage(DetailQueryDataContent detailQueryDataContent) {
        Assert.isNotNull((Object)detailQueryDataContent.getDetailQueryContext());
        Assert.isNotNull((Object)detailQueryDataContent.getDetailQueryPreData());
        Assert.isNotNull(detailQueryDataContent.getDetailRowData());
        DetailQueryContext context = detailQueryDataContent.getDetailQueryContext();
        ArrayList result = new ArrayList();
        DetailQueryPreData preData = detailQueryDataContent.getDetailQueryPreData();
        BigDecimal wBalance = preData.getWnc() == null ? BigDecimal.ZERO : preData.getWnc();
        BigDecimal balance = preData.getNc();
        if (context.getPageNum() == 1) {
            String ncOrient = balance.compareTo(BigDecimal.ZERO) == 0 ? "\u5e73" : OrientEnum.fromCode((Integer)balance.compareTo(BigDecimal.ZERO)).getName();
            result.add(DetailQueryDataManageUtils.getPreRowData(context, null, null, null, null, null, wBalance.abs(), balance.abs(), ncOrient, DetailExtraRowDigestEnum.NC.getName()));
        }
        if (context.getOrgn().booleanValue()) {
            wBalance = wBalance.add(preData.getWjc().subtract(preData.getWdc()));
        }
        balance = balance.add(preData.getCj().subtract(preData.getCd()));
        if (context.getPageNum() == 1) {
            String cOrient = balance.compareTo(BigDecimal.ZERO) == 0 ? "\u5e73" : OrientEnum.fromCode((Integer)balance.compareTo(BigDecimal.ZERO)).getName();
            result.add(DetailQueryDataManageUtils.getPreRowData(context, context.getStartPeriod(), preData.getWjc(), preData.getCj(), preData.getWdc(), preData.getCd(), wBalance.abs(), balance.abs(), cOrient, DetailExtraRowDigestEnum.CLJ.getName()));
            result.add(DetailQueryDataManageUtils.getPreRowData(context, context.getStartPeriod(), null, null, null, null, wBalance.abs(), balance.abs(), cOrient, DetailExtraRowDigestEnum.CYE.getName()));
        }
        LinkedHashMap<Integer, List<DetailRowDataVO>> detailDataMap = detailQueryDataContent.getDetailRowData();
        BigDecimal wbnDebitLJ = preData.getWjc();
        BigDecimal bnDebitLJ = preData.getCj();
        BigDecimal wbnCreditLJ = preData.getWdc();
        BigDecimal bnCreditLJ = preData.getCd();
        int preSize = (context.getPageNum() - 1) * context.getPageSize();
        int afterSize = context.getPageNum() * context.getPageSize();
        int resultCount = 3;
        block0: for (Map.Entry<Integer, List<DetailRowDataVO>> entry : detailDataMap.entrySet()) {
            String yeOrient;
            BigDecimal bqDebitLJ = BigDecimal.ZERO;
            BigDecimal wbqDebitLJ = BigDecimal.ZERO;
            BigDecimal bqCreditLJ = BigDecimal.ZERO;
            BigDecimal wbqCreditLJ = BigDecimal.ZERO;
            if (!CollectionUtils.isEmpty((Collection)detailDataMap.get(entry.getKey()))) {
                for (DetailRowDataVO detailRowDataVO : entry.getValue()) {
                    if (context.getPagination().booleanValue() && resultCount < preSize) {
                        bqDebitLJ = bqDebitLJ.add(detailRowDataVO.getJf());
                        bqCreditLJ = bqCreditLJ.add(detailRowDataVO.getDf());
                        balance = balance.add(detailRowDataVO.getJf().subtract(detailRowDataVO.getDf()));
                        if (context.getOrgn().booleanValue()) {
                            wbqDebitLJ = wbqDebitLJ.add(detailRowDataVO.getWjf());
                            wbqCreditLJ = wbqCreditLJ.add(detailRowDataVO.getWdf());
                            wBalance = wBalance.add(detailRowDataVO.getWjf().subtract(detailRowDataVO.getWdf()));
                        }
                        ++resultCount;
                        continue;
                    }
                    LinkedHashMap<String, Object> balanceRow = new LinkedHashMap<String, Object>();
                    balanceRow.put(DetailTableColumnEnum.SRCTYPE.getCode(), detailRowDataVO.getSrcType());
                    balanceRow.put(DetailTableColumnEnum.VCHRID.getCode(), detailRowDataVO.getVchrId());
                    if (context.getReportFlag().booleanValue()) {
                        balanceRow.put(DetailTableColumnEnum.UNITCODE.getCode(), detailRowDataVO.getUnitCode());
                        balanceRow.put(DetailTableColumnEnum.UNITNAME.getCode(), detailRowDataVO.getUnitName());
                    }
                    balanceRow.put(DetailTableColumnEnum.ACCTYEAR.getCode(), detailRowDataVO.getAcctYear());
                    balanceRow.put(DetailTableColumnEnum.ACCTMONTH.getCode(), entry.getKey());
                    balanceRow.put(DetailTableColumnEnum.ACCTDAY.getCode(), detailRowDataVO.getAcctDay());
                    balanceRow.put(DetailTableColumnEnum.VOUCHERWORD.getCode(), detailRowDataVO.getVoucherWord());
                    if (context.getCfFlag()) {
                        balanceRow.put(DetailTableColumnEnum.CFITEMCODE.getCode(), detailRowDataVO.getCfItemCode());
                        balanceRow.put(DetailTableColumnEnum.CFITEMNAME.getCode(), detailRowDataVO.getCfItemName());
                    } else {
                        balanceRow.put(DetailTableColumnEnum.SUBJECTCODE.getCode(), detailRowDataVO.getSubjectCode());
                        balanceRow.put(DetailTableColumnEnum.SUBJECTNAME.getCode(), detailRowDataVO.getSubjectName());
                    }
                    balanceRow.put(DetailTableColumnEnum.DIGEST.getCode(), detailRowDataVO.getDigest());
                    balanceRow.put("AFFECTPERIODSTART", detailRowDataVO.getAffectPeriodStart());
                    balanceRow.put("AFFECTPERIODEND", detailRowDataVO.getAffectPeriodEnd());
                    balanceRow.put("PERIODTYPE", detailRowDataVO.getPeriodType());
                    for (int j = 0; j < context.getDimensionData().length; ++j) {
                        balanceRow.put(String.valueOf(context.getDimensionData()[j]), "#".equals(detailRowDataVO.getDimensionData()[j]) ? null : detailRowDataVO.getDimensionData()[j]);
                    }
                    balanceRow.put(DetailTableColumnEnum.CURRENCYCODE.getCode(), detailRowDataVO.getCurrencyCode());
                    balance = balance.add(detailRowDataVO.getJf().subtract(detailRowDataVO.getDf()));
                    String orient = balance.compareTo(BigDecimal.ZERO) == 0 ? "\u5e73" : OrientEnum.fromCode((Integer)balance.compareTo(BigDecimal.ZERO)).getName();
                    balanceRow.put(DetailTableColumnEnum.YEORIENT.getCode(), orient);
                    balanceRow.put(DetailTableColumnEnum.JF.getCode(), detailRowDataVO.getJf());
                    balanceRow.put(DetailTableColumnEnum.DF.getCode(), detailRowDataVO.getDf());
                    balanceRow.put(DetailTableColumnEnum.YE.getCode(), balance.abs());
                    if (context.getOrgn().booleanValue()) {
                        wBalance = wBalance.add(detailRowDataVO.getWjf().subtract(detailRowDataVO.getWdf()));
                        balanceRow.put(DetailTableColumnEnum.WJF.getCode(), detailRowDataVO.getWjf());
                        balanceRow.put(DetailTableColumnEnum.WDF.getCode(), detailRowDataVO.getWdf());
                        balanceRow.put(DetailTableColumnEnum.WYE.getCode(), wBalance.abs());
                        wbqDebitLJ = wbqDebitLJ.add(detailRowDataVO.getWjf());
                        wbqCreditLJ = wbqCreditLJ.add(detailRowDataVO.getWdf());
                    }
                    result.add(balanceRow);
                    if (context.getPagination().booleanValue() && ++resultCount == afterSize) break block0;
                    bqDebitLJ = bqDebitLJ.add(detailRowDataVO.getJf());
                    bqCreditLJ = bqCreditLJ.add(detailRowDataVO.getDf());
                }
            }
            String string = yeOrient = balance.compareTo(BigDecimal.ZERO) == 0 ? "\u5e73" : OrientEnum.fromCode((Integer)balance.compareTo(BigDecimal.ZERO)).getName();
            if (resultCount >= preSize && resultCount <= afterSize || !context.getPagination().booleanValue()) {
                result.add(DetailQueryDataManageUtils.getPreRowData(context, entry.getKey(), wbqDebitLJ, bqDebitLJ, wbqCreditLJ, bqCreditLJ, wBalance.abs(), balance.abs(), yeOrient, DetailExtraRowDigestEnum.BQ.getName()));
            }
            if (context.getPagination().booleanValue() && ++resultCount == afterSize) break;
            bnDebitLJ = bnDebitLJ.add(bqDebitLJ);
            bnCreditLJ = bnCreditLJ.add(bqCreditLJ);
            if (context.getOrgn().booleanValue()) {
                wbnDebitLJ = wbnDebitLJ.add(wbqDebitLJ);
                wbnCreditLJ = wbnCreditLJ.add(wbqCreditLJ);
            }
            if (resultCount >= preSize && resultCount <= afterSize || !context.getPagination().booleanValue()) {
                result.add(DetailQueryDataManageUtils.getPreRowData(context, entry.getKey(), wbnDebitLJ, bnDebitLJ, wbnCreditLJ, bnCreditLJ, wBalance.abs(), balance.abs(), yeOrient, DetailExtraRowDigestEnum.BNLJ.getName()));
            }
            if (!context.getPagination().booleanValue() || ++resultCount != afterSize) continue;
            break;
        }
        PageVO pageVO = new PageVO();
        if (resultCount < preSize && context.getPagination().booleanValue()) {
            pageVO.setTotal(0);
            return pageVO;
        }
        pageVO.setRows(result);
        int detailDataCount = 0;
        for (Map.Entry<Integer, List<DetailRowDataVO>> entry : detailDataMap.entrySet()) {
            detailDataCount += detailDataMap.get(entry.getKey()).size();
        }
        pageVO.setTotal(3 + detailDataMap.size() * 2 + detailDataCount);
        return pageVO;
    }

    private static LinkedHashMap<String, Object> getPreRowData(DetailQueryContext context, Integer month, BigDecimal wjf, BigDecimal jf, BigDecimal wdf, BigDecimal df, BigDecimal wye, BigDecimal ye, String yeOrient, String digest) {
        LinkedHashMap<String, Object> balanceRow = new LinkedHashMap<String, Object>();
        balanceRow.put(DetailTableColumnEnum.ACCTYEAR.getCode(), context.getAcctYear());
        balanceRow.put(DetailTableColumnEnum.ACCTMONTH.getCode(), month);
        balanceRow.put(DetailTableColumnEnum.ACCTDAY.getCode(), null);
        balanceRow.put(DetailTableColumnEnum.VOUCHERWORD.getCode(), null);
        balanceRow.put(DetailTableColumnEnum.SUBJECTCODE.getCode(), null);
        balanceRow.put(DetailTableColumnEnum.SUBJECTNAME.getCode(), null);
        balanceRow.put(DetailTableColumnEnum.DIGEST.getCode(), digest);
        for (int i = 0; i < context.getDimensionData().length; ++i) {
            balanceRow.put(String.valueOf(context.getDimensionData()[i]), null);
        }
        balanceRow.put(DetailTableColumnEnum.CURRENCYCODE.getCode(), null);
        balanceRow.put(DetailTableColumnEnum.JF.getCode(), jf);
        balanceRow.put(DetailTableColumnEnum.DF.getCode(), df);
        balanceRow.put(DetailTableColumnEnum.YE.getCode(), ye);
        balanceRow.put(DetailTableColumnEnum.YEORIENT.getCode(), yeOrient);
        if (context.getOrgn().booleanValue()) {
            balanceRow.put(DetailTableColumnEnum.WJF.getCode(), wjf);
            balanceRow.put(DetailTableColumnEnum.WDF.getCode(), wdf);
            balanceRow.put(DetailTableColumnEnum.WYE.getCode(), wye);
        }
        return balanceRow;
    }
}

