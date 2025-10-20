/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO
 */
package com.jiuqi.gcreport.aidocaudit.dao;

import com.jiuqi.gcreport.aidocaudit.dto.AuditParamDTO;
import com.jiuqi.gcreport.aidocaudit.eo.AidocauditResultEO;
import com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO;
import java.util.List;

public interface IAidocauditResultDao
extends IDbSqlGenericDAO<AidocauditResultEO, String> {
    public List<AidocauditResultEO> queryByTempIdAndBusiness(String var1, String var2, String var3, String var4);

    public List<AidocauditResultEO> queryByOrgIds(String var1, String var2, String var3, List<String> var4);

    public List<AidocauditResultEO> queryByOrgIdsLimit(String var1, String var2, String var3, List<String> var4, int var5);

    public List<AidocauditResultEO> getUnitDetailByPage(AuditParamDTO var1);

    public List<AidocauditResultEO> queryByRuleIdsAndOrgIds(String var1, List<String> var2, String var3, List<String> var4);
}

