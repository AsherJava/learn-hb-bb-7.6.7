/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.expimp.progress.common.ProgressData
 *  com.jiuqi.gcreport.financialcheckcore.item.entity.GcRelatedItemEO
 */
package com.jiuqi.gcreport.financialcheckImpl.check.checkutil;

import com.jiuqi.common.expimp.progress.common.ProgressData;
import com.jiuqi.gcreport.financialcheckcore.item.entity.GcRelatedItemEO;
import java.util.List;

public interface FinancialCheckDataProvider {
    public int getAcctYear();

    public int getAcctPeriod();

    public List<GcRelatedItemEO> getVoucherItems();

    public String getOrgVer();

    public ProgressData<String> getCheckProgress();

    public boolean getCheckPeriodBaseVoucherPeriod();
}

