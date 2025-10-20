/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO
 */
package com.jiuqi.gcreport.efdcdatacheck.impl.dao;

import com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO;
import com.jiuqi.gcreport.efdcdatacheck.impl.entity.DataCheckConfigEO;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface DataCheckConfigDAO
extends IDbSqlGenericDAO<DataCheckConfigEO, String> {
    public void deleteByTaskId(String var1);

    public List<DataCheckConfigEO> queryBySchemeId(String var1);

    public List<DataCheckConfigEO> queryDataCheckConfig(String var1, String var2);

    public void save(String var1, List<DataCheckConfigEO> var2);
}

