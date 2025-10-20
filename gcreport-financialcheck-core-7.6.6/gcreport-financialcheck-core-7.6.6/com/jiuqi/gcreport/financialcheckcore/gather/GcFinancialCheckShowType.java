/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.financialcheckcore.gather;

import com.jiuqi.gcreport.financialcheckcore.gather.GcFinancialCheckAction;
import java.util.List;

public interface GcFinancialCheckShowType {
    public String getCode();

    public String getTitle();

    public List<GcFinancialCheckAction> actions();
}

