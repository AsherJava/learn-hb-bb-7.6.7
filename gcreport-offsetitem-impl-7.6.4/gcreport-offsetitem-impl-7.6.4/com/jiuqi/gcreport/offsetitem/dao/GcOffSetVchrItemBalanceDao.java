/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO
 *  com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO
 */
package com.jiuqi.gcreport.offsetitem.dao;

import com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO;
import com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO;
import com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemBalanceEO;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface GcOffSetVchrItemBalanceDao
extends IDbSqlGenericDAO<GcOffSetVchrItemBalanceEO, String> {
    public void batchInsertBalanceWhenNotExists(List<GcOffSetVchrItemAdjustEO> var1);

    public void updateCurrentPeriodBalance(boolean var1, GcOffSetVchrItemAdjustEO var2);
}

