/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO
 */
package com.jiuqi.gcreport.efdcdatacheck.impl.dao;

import com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO;
import com.jiuqi.gcreport.efdcdatacheck.impl.entity.EfdcCheckReportLogShareEO;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface EfdcCheckReportShareDAO
extends IDbSqlGenericDAO<EfdcCheckReportLogShareEO, String> {
    public List<EfdcCheckReportLogShareEO> queryShareEoByUserAndFileKeys(String var1, List<String> var2);
}

