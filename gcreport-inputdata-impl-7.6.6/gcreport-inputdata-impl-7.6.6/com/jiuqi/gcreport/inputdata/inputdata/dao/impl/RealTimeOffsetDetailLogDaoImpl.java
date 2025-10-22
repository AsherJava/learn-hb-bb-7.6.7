/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO
 */
package com.jiuqi.gcreport.inputdata.inputdata.dao.impl;

import com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO;
import com.jiuqi.gcreport.inputdata.inputdata.dao.RealTimeOffsetDetailLogDao;
import com.jiuqi.gcreport.inputdata.inputdata.entity.RealTimeOffsetDetailLogEO;
import org.springframework.stereotype.Repository;

@Repository
public class RealTimeOffsetDetailLogDaoImpl
extends GcDbSqlGenericDAO<RealTimeOffsetDetailLogEO, String>
implements RealTimeOffsetDetailLogDao {
    public RealTimeOffsetDetailLogDaoImpl() {
        super(RealTimeOffsetDetailLogEO.class);
    }
}

