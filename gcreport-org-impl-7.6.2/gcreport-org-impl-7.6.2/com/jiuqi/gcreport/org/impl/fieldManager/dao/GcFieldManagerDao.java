/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO
 */
package com.jiuqi.gcreport.org.impl.fieldManager.dao;

import com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO;
import com.jiuqi.gcreport.org.impl.fieldManager.entity.GcOrgFieldEO;
import java.util.List;

public interface GcFieldManagerDao
extends IDbSqlGenericDAO<GcOrgFieldEO, String> {
    public List<GcOrgFieldEO> queryListByTableName(String var1);

    public int deleteByIds(List<String> var1);

    public void save(List<GcOrgFieldEO> var1);
}

