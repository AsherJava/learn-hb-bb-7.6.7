/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO
 */
package com.jiuqi.gcreport.samecontrol.dao;

import com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO;
import com.jiuqi.gcreport.samecontrol.entity.SameCtrlChgSettingSchemeMappingEO;
import java.util.List;

public interface SameCtrlChgSettingSchemeMappingDao
extends IDbSqlGenericDAO<SameCtrlChgSettingSchemeMappingEO, String> {
    public List<SameCtrlChgSettingSchemeMappingEO> listSchemeMappingByTaskAndShcemeId(String var1, String var2);

    public int deleteSchemeMappingByIds(List<String> var1);
}

