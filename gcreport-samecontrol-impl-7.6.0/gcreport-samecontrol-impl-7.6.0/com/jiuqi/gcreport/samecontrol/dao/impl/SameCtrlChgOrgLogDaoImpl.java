/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO
 */
package com.jiuqi.gcreport.samecontrol.dao.impl;

import com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO;
import com.jiuqi.gcreport.samecontrol.dao.SameCtrlChgOrgLogDao;
import com.jiuqi.gcreport.samecontrol.entity.SameCtrlChgOrgLogEO;
import org.springframework.stereotype.Repository;

@Repository
public class SameCtrlChgOrgLogDaoImpl
extends GcDbSqlGenericDAO<SameCtrlChgOrgLogEO, String>
implements SameCtrlChgOrgLogDao {
    public SameCtrlChgOrgLogDaoImpl() {
        super(SameCtrlChgOrgLogEO.class);
    }
}

