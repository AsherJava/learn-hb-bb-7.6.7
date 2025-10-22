/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO
 */
package com.jiuqi.gcreport.inputdata.inputdata.dao.impl;

import com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO;
import com.jiuqi.gcreport.inputdata.inputdata.dao.RealTimeOffsetRelLogDao;
import com.jiuqi.gcreport.inputdata.inputdata.entity.RealTimeOffsetRelLogEO;
import org.springframework.stereotype.Repository;

@Repository
public class RealTimeOffsetRelLogDaoImpl
extends GcDbSqlGenericDAO<RealTimeOffsetRelLogEO, String>
implements RealTimeOffsetRelLogDao {
    public RealTimeOffsetRelLogDaoImpl() {
        super(RealTimeOffsetRelLogEO.class);
    }
}

