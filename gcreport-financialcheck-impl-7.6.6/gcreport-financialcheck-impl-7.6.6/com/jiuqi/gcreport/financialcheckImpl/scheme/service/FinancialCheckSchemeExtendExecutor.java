/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.financialcheckapi.scheme.vo.FinancialCheckSchemeVO
 *  com.jiuqi.gcreport.financialcheckcore.scheme.entity.FinancialCheckSchemeEO
 */
package com.jiuqi.gcreport.financialcheckImpl.scheme.service;

import com.jiuqi.gcreport.financialcheckapi.scheme.vo.FinancialCheckSchemeVO;
import com.jiuqi.gcreport.financialcheckcore.scheme.entity.FinancialCheckSchemeEO;

public interface FinancialCheckSchemeExtendExecutor {
    public void save(FinancialCheckSchemeVO var1, FinancialCheckSchemeEO var2);

    default public void afterDelete(String id) {
    }

    public FinancialCheckSchemeVO convertEO2VO(FinancialCheckSchemeEO var1);
}

