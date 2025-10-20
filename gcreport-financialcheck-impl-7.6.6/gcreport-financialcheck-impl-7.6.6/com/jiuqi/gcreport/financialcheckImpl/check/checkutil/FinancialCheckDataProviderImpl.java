/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.expimp.progress.common.ProgressData
 *  com.jiuqi.gcreport.financialcheckcore.item.entity.GcRelatedItemEO
 */
package com.jiuqi.gcreport.financialcheckImpl.check.checkutil;

import com.jiuqi.common.expimp.progress.common.ProgressData;
import com.jiuqi.gcreport.financialcheckImpl.check.checkutil.FinancialCheckDataProvider;
import com.jiuqi.gcreport.financialcheckcore.item.entity.GcRelatedItemEO;
import java.util.List;
import org.springframework.util.CollectionUtils;

public class FinancialCheckDataProviderImpl
implements FinancialCheckDataProvider {
    private int acctYear;
    private int acctPeriod;
    private List<GcRelatedItemEO> voucherItems;
    private ProgressData<String> checkProgress;
    private boolean checkPeriodBaseVoucherPeriod = false;

    @Override
    public int getAcctYear() {
        return this.acctYear;
    }

    public void setAcctYear(int acctYear) {
        this.acctYear = acctYear;
    }

    @Override
    public int getAcctPeriod() {
        return this.acctPeriod;
    }

    public void setAcctPeriod(int acctPeriod) {
        this.acctPeriod = acctPeriod;
    }

    @Override
    public List<GcRelatedItemEO> getVoucherItems() {
        return this.voucherItems;
    }

    public void setVoucherItems(List<GcRelatedItemEO> voucherItems) {
        if (!CollectionUtils.isEmpty(voucherItems)) {
            voucherItems.forEach(GcRelatedItemEO::initAmtInfo);
        }
        this.voucherItems = voucherItems;
    }

    @Override
    public String getOrgVer() {
        return String.format("%04d", this.acctYear) + String.format("%02d", this.acctPeriod) + "15";
    }

    @Override
    public ProgressData<String> getCheckProgress() {
        return this.checkProgress;
    }

    public void setCheckProgress(ProgressData<String> checkProgress) {
        this.checkProgress = checkProgress;
    }

    @Override
    public boolean getCheckPeriodBaseVoucherPeriod() {
        return this.checkPeriodBaseVoucherPeriod;
    }

    public void setCheckPeriodBaseVoucherPeriod(boolean checkPeriodBaseVoucherPeriod) {
        this.checkPeriodBaseVoucherPeriod = checkPeriodBaseVoucherPeriod;
    }
}

