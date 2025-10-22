/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO
 */
package com.jiuqi.gcreport.efdcdatacheck.impl.dao;

import com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO;
import com.jiuqi.gcreport.efdcdatacheck.impl.entity.EfdcCheckReportLogEO;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.stereotype.Repository;

@Repository
public interface EfdcCheckReportDAO
extends IDbSqlGenericDAO<EfdcCheckReportLogEO, String> {
    public List<EfdcCheckReportLogEO> findAllByConditions(Map<String, Object> var1);

    public List<EfdcCheckReportLogEO> findAllByPageAndConditions(Integer var1, Integer var2, Map<String, Object> var3);

    public List<EfdcCheckReportLogEO> findCheckReportByRecids(Set<String> var1);

    public void batchDeleteByRecids(Set<String> var1);
}

