/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.financialcheckcore.item.entity.GcRelatedItemEO
 */
package com.jiuqi.gcreport.financialcheckImpl.check.checkutil;

import com.jiuqi.gcreport.financialcheckImpl.check.checkutil.FinancialCheckDataProvider;
import com.jiuqi.gcreport.financialcheckcore.item.entity.GcRelatedItemEO;
import java.util.ArrayList;
import java.util.List;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class GroupedCheckDataProvider {
    private int acctYear;
    private int acctPeriod;
    private int minCheckPeriod;
    private List<GcRelatedItemEO> voucherItems;
    private MultiValueMap<String, GcRelatedItemEO> voucherItemGroupByLocalOrg;
    private boolean checkPeriodBaseVoucherPeriod = false;

    static GroupedCheckDataProvider iniByOther(FinancialCheckDataProvider financialCheckDataProvider) {
        GroupedCheckDataProvider financialCheckDataProviderNew = new GroupedCheckDataProvider();
        financialCheckDataProviderNew.setAcctYear(financialCheckDataProvider.getAcctYear());
        financialCheckDataProviderNew.setAcctPeriod(financialCheckDataProvider.getAcctPeriod());
        financialCheckDataProviderNew.voucherItems = new ArrayList<GcRelatedItemEO>();
        financialCheckDataProviderNew.voucherItemGroupByLocalOrg = new LinkedMultiValueMap<String, GcRelatedItemEO>();
        financialCheckDataProviderNew.setCheckPeriodBaseVoucherPeriod(financialCheckDataProvider.getCheckPeriodBaseVoucherPeriod());
        return financialCheckDataProviderNew;
    }

    public int getAcctYear() {
        return this.acctYear;
    }

    public void setAcctYear(int acctYear) {
        this.acctYear = acctYear;
    }

    public int getAcctPeriod() {
        return this.acctPeriod;
    }

    public void setAcctPeriod(int acctPeriod) {
        this.acctPeriod = acctPeriod;
    }

    List<GcRelatedItemEO> getVoucherItems() {
        return this.voucherItems;
    }

    void addVoucherItem(GcRelatedItemEO voucherItem) {
        this.voucherItems.add(voucherItem);
        this.voucherItemGroupByLocalOrg.add(voucherItem.getUnitId(), voucherItem);
    }

    MultiValueMap<String, GcRelatedItemEO> getVoucherItemGroupByLocalOrg() {
        return this.voucherItemGroupByLocalOrg;
    }

    void removeVoucherItem(GcRelatedItemEO voucherItem) {
        List sameGroupItems = (List)this.voucherItemGroupByLocalOrg.get(voucherItem.getUnitId());
        if (CollectionUtils.isEmpty(sameGroupItems)) {
            return;
        }
        sameGroupItems.remove(voucherItem);
        if (CollectionUtils.isEmpty(sameGroupItems)) {
            this.voucherItemGroupByLocalOrg.remove(voucherItem.getUnitId());
        }
        this.voucherItems.remove(voucherItem);
    }

    public boolean getCheckPeriodBaseVoucherPeriod() {
        return this.checkPeriodBaseVoucherPeriod;
    }

    public void setCheckPeriodBaseVoucherPeriod(boolean checkPeriodBaseVoucherPeriod) {
        this.checkPeriodBaseVoucherPeriod = checkPeriodBaseVoucherPeriod;
    }

    public int getMinCheckPeriod() {
        return this.minCheckPeriod;
    }

    public void setMinCheckPeriod(int minCheckPeriod) {
        this.minCheckPeriod = minCheckPeriod;
    }
}

