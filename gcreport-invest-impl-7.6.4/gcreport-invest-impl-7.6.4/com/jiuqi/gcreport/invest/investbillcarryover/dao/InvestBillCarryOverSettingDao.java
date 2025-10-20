/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO
 */
package com.jiuqi.gcreport.invest.investbillcarryover.dao;

import com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO;
import com.jiuqi.gcreport.invest.investbillcarryover.entity.InvestBillCarryOverSettingEO;
import java.util.List;

public interface InvestBillCarryOverSettingDao
extends IDbSqlGenericDAO<InvestBillCarryOverSettingEO, String> {
    public List<InvestBillCarryOverSettingEO> listInvestBillCarryOverSetting(String var1);
}

