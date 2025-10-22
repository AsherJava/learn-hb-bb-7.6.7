/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO
 */
package com.jiuqi.gcreport.inputdata.inputdata.dao.impl;

import com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO;
import com.jiuqi.gcreport.inputdata.inputdata.dao.RealTimeOffsetLogDao;
import com.jiuqi.gcreport.inputdata.inputdata.entity.RealTimeOffsetLogEO;
import org.springframework.stereotype.Repository;

@Repository
public class RealTimeOffsetLogDaoImpl
extends GcDbSqlGenericDAO<RealTimeOffsetLogEO, String>
implements RealTimeOffsetLogDao {
    public RealTimeOffsetLogDaoImpl() {
        super(RealTimeOffsetLogEO.class);
    }
}

