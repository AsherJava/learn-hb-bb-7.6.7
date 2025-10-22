/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO
 */
package com.jiuqi.gcreport.archive.dao;

import com.jiuqi.gcreport.archive.entity.ArchiveConfigEO;
import com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO;
import java.util.List;

public interface ArchiveConfigDao
extends IDbSqlGenericDAO<ArchiveConfigEO, String> {
    public List<ArchiveConfigEO> queryBySchemeId(String var1);

    public void save(String var1, List<ArchiveConfigEO> var2);

    public void deleteByTaskId(String var1);

    public void deleteByTaskIdAndOrgType(String var1, String var2);

    public List<ArchiveConfigEO> queryBySchemeIdAndOrgType(String var1, String var2);
}

