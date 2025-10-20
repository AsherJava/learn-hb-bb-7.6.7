/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.OrientEnum
 *  com.jiuqi.gcreport.financialcheckcore.check.enums.CheckAmtAccessModeEnum
 *  com.jiuqi.gcreport.financialcheckcore.check.enums.VchrSrcWayEnum
 *  com.jiuqi.gcreport.financialcheckcore.item.entity.GcRelatedItemEO
 *  com.jiuqi.gcreport.financialcheckcore.scheme.entity.FinancialCheckSchemeEO
 *  com.jiuqi.gcreport.financialcheckcore.scheme.enums.CheckToleranceEnum
 */
package com.jiuqi.gcreport.financialcheckImpl.check.checkutil;

import com.jiuqi.gcreport.common.OrientEnum;
import com.jiuqi.gcreport.financialcheckcore.check.enums.CheckAmtAccessModeEnum;
import com.jiuqi.gcreport.financialcheckcore.check.enums.VchrSrcWayEnum;
import com.jiuqi.gcreport.financialcheckcore.item.entity.GcRelatedItemEO;
import com.jiuqi.gcreport.financialcheckcore.scheme.entity.FinancialCheckSchemeEO;
import com.jiuqi.gcreport.financialcheckcore.scheme.enums.CheckToleranceEnum;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import org.springframework.util.CollectionUtils;

class AmtCheck {
    private static final double ERRORRANGE = 0.001;
    private FinancialCheckSchemeEO scheme;

    AmtCheck(FinancialCheckSchemeEO scheme) {
        this.scheme = scheme;
        if (Objects.isNull(scheme.getToleranceAmount())) {
            scheme.setToleranceAmount(Double.valueOf(0.0));
        }
    }

    boolean checkAndDistributionAmt(Collection<GcRelatedItemEO> records) {
        if (CollectionUtils.isEmpty(records)) {
            return false;
        }
        double debitSum = 0.0;
        double creditSum = 0.0;
        for (GcRelatedItemEO record : records) {
            if (VchrSrcWayEnum.DATASYNC_CF.name().equals(record.getInputWay())) continue;
            if (record.getAmtOrient() == 1) {
                debitSum += record.getDebitOrig().doubleValue();
                continue;
            }
            creditSum += record.getCreditOrig().doubleValue();
        }
        if (!this.checkAmtWithoutOrient(debitSum, creditSum)) {
            return false;
        }
        this.distributionAmt(records, BigDecimal.valueOf(debitSum).subtract(BigDecimal.valueOf(creditSum)));
        return true;
    }

    boolean checkAndDistributionAmt(Collection<GcRelatedItemEO> localVoucherItem, Collection<GcRelatedItemEO> oppVoucherItem) {
        if (CollectionUtils.isEmpty(localVoucherItem) && CollectionUtils.isEmpty(oppVoucherItem)) {
            return false;
        }
        double debitSum = 0.0;
        double creditSum = 0.0;
        for (GcRelatedItemEO record : localVoucherItem) {
            if (record.getAmtOrient() == 1) {
                debitSum += record.getDebitOrig().doubleValue();
                continue;
            }
            creditSum += record.getCreditOrig().doubleValue();
        }
        for (GcRelatedItemEO record : oppVoucherItem) {
            if (record.getAmtOrient() == 1) {
                debitSum += record.getDebitOrig().doubleValue();
                continue;
            }
            creditSum += record.getCreditOrig().doubleValue();
        }
        if (!this.checkAmtWithoutOrient(debitSum, creditSum)) {
            return false;
        }
        ArrayList<GcRelatedItemEO> allVoucherItems = new ArrayList<GcRelatedItemEO>();
        allVoucherItems.addAll(localVoucherItem);
        allVoucherItems.addAll(oppVoucherItem);
        this.distributionAmt(allVoucherItems, BigDecimal.valueOf(debitSum).subtract(BigDecimal.valueOf(creditSum)));
        return true;
    }

    private void distributionAmt(Collection<GcRelatedItemEO> records, BigDecimal debitCreditDiff) {
        int diffDistriOrient;
        if (CollectionUtils.isEmpty(records)) {
            return;
        }
        int diffCmp = debitCreditDiff.compareTo(BigDecimal.ZERO);
        if (diffCmp == 0) {
            this.distributionAmtNotDiff(records);
            return;
        }
        CheckAmtAccessModeEnum amtAccessMode = CheckAmtAccessModeEnum.getEnumByCode((Integer)this.scheme.getCheckAmount());
        if (amtAccessMode == CheckAmtAccessModeEnum.BIGGER) {
            diffDistriOrient = diffCmp > 0 ? -1 : 1;
            debitCreditDiff = diffDistriOrient == 1 ? debitCreditDiff.negate() : debitCreditDiff;
        } else {
            diffDistriOrient = diffCmp > 0 ? 1 : -1;
            debitCreditDiff = diffDistriOrient == 1 ? debitCreditDiff.negate() : debitCreditDiff;
        }
        GcRelatedItemEO maxAmtData = null;
        for (GcRelatedItemEO record : records) {
            if (record.getAmtOrient() == diffDistriOrient && (maxAmtData == null || this.getMaxAmt(record, diffDistriOrient) > this.getMaxAmt(maxAmtData, diffDistriOrient))) {
                maxAmtData = record;
            }
            this.updateChkAmtValues(record);
        }
        if (maxAmtData == null) {
            maxAmtData = records.iterator().next();
        }
        this.updateMaxAmtData(maxAmtData, debitCreditDiff);
    }

    private double getMaxAmt(GcRelatedItemEO record, int diffDistriOrient) {
        return diffDistriOrient == OrientEnum.D.getValue() ? record.getDebitOrig() : record.getCreditOrig();
    }

    private void updateChkAmtValues(GcRelatedItemEO record) {
        if (record.getAmtOrient() == 1) {
            record.setChkAmtD(record.getDebitOrig());
            record.setChkAmtC(Double.valueOf(0.0));
        } else {
            record.setChkAmtD(Double.valueOf(0.0));
            record.setChkAmtC(record.getCreditOrig());
        }
    }

    private void updateMaxAmtData(GcRelatedItemEO maxAmtData, BigDecimal debitCreditDiff) {
        if (OrientEnum.D.getValue().equals(maxAmtData.getAmtOrient())) {
            maxAmtData.setChkAmtD(Double.valueOf(maxAmtData.getAmountAsDebit().add(debitCreditDiff).doubleValue()));
            maxAmtData.setChkAmtC(Double.valueOf(0.0));
        } else {
            maxAmtData.setChkAmtD(Double.valueOf(0.0));
            maxAmtData.setChkAmtC(Double.valueOf(maxAmtData.getAmountAsDebit().negate().add(debitCreditDiff).doubleValue()));
        }
    }

    private void distributionAmtNotDiff(Collection<GcRelatedItemEO> records) {
        for (GcRelatedItemEO record : records) {
            if (record.getAmtOrient() == 1) {
                record.setChkAmtD(record.getDebitOrig());
                record.setChkAmtC(Double.valueOf(0.0));
                continue;
            }
            record.setChkAmtD(Double.valueOf(0.0));
            record.setChkAmtC(record.getCreditOrig());
        }
    }

    private boolean checkAmtWithoutOrient(double amount1, double amount2) {
        boolean tolerance = CheckToleranceEnum.ENABLE.getCode().equals(this.scheme.getToleranceEnable());
        if (Math.abs(amount1 - amount2) < 0.001 || tolerance && this.scheme.getToleranceAmount() < 0.0) {
            return true;
        }
        if (!tolerance) {
            return false;
        }
        return this.rateToleranceRangeChcek(amount1, amount2);
    }

    private boolean rateToleranceRangeChcek(double amount1, double amount2) {
        double drate;
        double diff;
        double smallerAmount;
        double biggerAmount;
        Double toleranceRate = this.scheme.getToleranceRate();
        if (amount1 > amount2) {
            biggerAmount = amount1;
            smallerAmount = amount2;
        } else {
            biggerAmount = amount2;
            smallerAmount = amount1;
        }
        if (Objects.isNull(toleranceRate)) {
            toleranceRate = 100.0;
        }
        return (diff = Math.abs(biggerAmount - smallerAmount * ((drate = toleranceRate / 100.0) + 1.0))) - this.scheme.getToleranceAmount() <= 0.001;
    }
}

