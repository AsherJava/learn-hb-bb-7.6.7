/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO
 */
package com.jiuqi.gcreport.reportparam.dao;

import com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO;
import com.jiuqi.gcreport.reportparam.eo.GcReportParamLockEO;
import java.util.Date;

public interface GcReportParamLockDao
extends IDbSqlGenericDAO<GcReportParamLockEO, String> {
    public int updateLocked(String var1, Date var2);

    public void unLock();
}

