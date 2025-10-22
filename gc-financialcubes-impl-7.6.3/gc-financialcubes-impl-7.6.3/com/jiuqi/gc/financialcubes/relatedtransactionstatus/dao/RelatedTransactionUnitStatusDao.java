/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gc.financial.financialstatus.vo.FinancialStatusParam
 *  com.jiuqi.gc.financialcubes.financialstatus.entity.FinancialUnitStatusEO
 *  com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO
 */
package com.jiuqi.gc.financialcubes.relatedtransactionstatus.dao;

import com.jiuqi.gc.financial.financialstatus.vo.FinancialStatusParam;
import com.jiuqi.gc.financialcubes.financialstatus.entity.FinancialUnitStatusEO;
import com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO;
import java.util.Date;
import java.util.List;

public interface RelatedTransactionUnitStatusDao
extends IDbSqlGenericDAO<FinancialUnitStatusEO, String> {
    public List<FinancialUnitStatusEO> selectByUnitCode(FinancialStatusParam var1, String var2, String var3, String var4);

    public List<FinancialUnitStatusEO> listCloseUnit(FinancialStatusParam var1, String var2, Date var3);

    public List<FinancialUnitStatusEO> listOpenUnit(FinancialStatusParam var1, String var2, Date var3);

    public List<FinancialUnitStatusEO> selectUnitCodesByDataTime(FinancialStatusParam var1, String var2, String var3, String var4);
}

