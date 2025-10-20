/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.gc.financial.financialstatus.vo.FinancialStatusParam
 *  com.jiuqi.gc.financial.financialstatus.vo.FinancialUnitStatusVO
 */
package com.jiuqi.gc.financialcubes.relatedtransactionstatus.service;

import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.gc.financial.financialstatus.vo.FinancialStatusParam;
import com.jiuqi.gc.financial.financialstatus.vo.FinancialUnitStatusVO;

public interface RelatedTransactionUnitStatusService {
    public PageInfo<FinancialUnitStatusVO> listCloseUnit(FinancialStatusParam var1);

    public PageInfo<FinancialUnitStatusVO> listOpenUnit(FinancialStatusParam var1);

    public String openUnit(FinancialStatusParam var1);

    public String closeUnit(FinancialStatusParam var1);
}

