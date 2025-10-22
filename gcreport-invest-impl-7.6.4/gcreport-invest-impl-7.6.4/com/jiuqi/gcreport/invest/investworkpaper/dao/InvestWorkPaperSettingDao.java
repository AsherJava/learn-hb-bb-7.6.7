/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO
 */
package com.jiuqi.gcreport.invest.investworkpaper.dao;

import com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO;
import com.jiuqi.gcreport.invest.investworkpaper.entity.InvestWorkPaperSettingEO;

public interface InvestWorkPaperSettingDao
extends IDbSqlGenericDAO<InvestWorkPaperSettingEO, String> {
    public InvestWorkPaperSettingEO getInvestWorkPaperSetting(String var1, String var2, String var3, String var4);
}

