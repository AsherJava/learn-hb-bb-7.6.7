/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO
 */
package com.jiuqi.gcreport.reportparam.dao;

import com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO;
import com.jiuqi.gcreport.reportparam.eo.GcReportParamInitEO;
import java.util.List;

public interface GcReportParamInitDao
extends IDbSqlGenericDAO<GcReportParamInitEO, String> {
    public GcReportParamInitEO selectByName(String var1);

    public int queryOrgCount(String var1);

    public List<String> queryAllOrgTypes();
}

