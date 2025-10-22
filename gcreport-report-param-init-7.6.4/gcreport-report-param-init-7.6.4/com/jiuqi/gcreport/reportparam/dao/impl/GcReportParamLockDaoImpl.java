/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO
 */
package com.jiuqi.gcreport.reportparam.dao.impl;

import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO;
import com.jiuqi.gcreport.reportparam.dao.GcReportParamLockDao;
import com.jiuqi.gcreport.reportparam.eo.GcReportParamLockEO;
import java.util.Date;
import org.springframework.stereotype.Repository;

@Repository
public class GcReportParamLockDaoImpl
extends GcDbSqlGenericDAO<GcReportParamLockEO, String>
implements GcReportParamLockDao {
    public GcReportParamLockDaoImpl() {
        super(GcReportParamLockEO.class);
    }

    @Override
    public int updateLocked(String userName, Date lockTime) {
        String sql = "UPDATE GC_REPORTPARAMLOCK SET LOCKUSER = ?,LOCKTIME = ?,LOCKED = 1 WHERE ID = ?";
        return this.execute(sql, new Object[]{userName, new Date(), UUIDUtils.emptyUUIDStr()});
    }

    @Override
    public void unLock() {
        String sql = "UPDATE GC_REPORTPARAMLOCK SET LOCKED = 0 WHERE ID = ?";
        this.execute(sql, new Object[]{UUIDUtils.emptyUUIDStr()});
    }
}

