/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO
 */
package com.jiuqi.gcreport.archive.dao;

import com.jiuqi.gcreport.archive.entity.ArchivePluginEO;
import com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO;

public interface ArchivePluginDao
extends IDbSqlGenericDAO<ArchivePluginEO, String> {
    public String getPluginCode();

    public void save(String var1);
}

