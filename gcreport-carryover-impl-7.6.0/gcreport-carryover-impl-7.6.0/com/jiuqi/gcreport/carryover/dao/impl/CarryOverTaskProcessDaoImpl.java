/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO
 */
package com.jiuqi.gcreport.carryover.dao.impl;

import com.jiuqi.gcreport.carryover.dao.CarryOverTaskProcessDao;
import com.jiuqi.gcreport.carryover.entity.CarryOverTaskProcessEO;
import com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO;
import org.springframework.stereotype.Repository;

@Repository
public class CarryOverTaskProcessDaoImpl
extends GcDbSqlGenericDAO<CarryOverTaskProcessEO, String>
implements CarryOverTaskProcessDao {
    public CarryOverTaskProcessDaoImpl() {
        super(CarryOverTaskProcessEO.class);
    }

    @Override
    public void updateProcess(long finishedCount, double process, String groupId) {
        String sql = "update GC_CARRYOVER_PROCESS\n set PROCESS = PROCESS + ?, \nFINISHEDTASKCOUNT = FINISHEDTASKCOUNT + ?\nwhere ID = ?";
        this.execute(sql, new Object[]{process, finishedCount, groupId});
    }
}

