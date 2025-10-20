/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO
 */
package com.jiuqi.gcreport.offsetitem.init.dao;

import com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO;
import com.jiuqi.gcreport.offsetitem.init.entity.GcOffsetInitLogInfoEO;
import java.util.List;

public interface GcOffsetInitLogInfoDao
extends IDbSqlGenericDAO<GcOffsetInitLogInfoEO, String> {
    public List<GcOffsetInitLogInfoEO> queryLogInfoByCurrDimension(String var1, String var2);
}

