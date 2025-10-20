/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.org.OrgDO
 */
package com.jiuqi.dc.integration.execute.impl.basedatasync.dao;

import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.org.OrgDO;
import java.util.List;
import java.util.Map;

public interface BaseDataSyncDao {
    public int insertOrgData(String var1, List<OrgDO> var2, List<String> var3);

    public int insertBaseData(String var1, List<BaseDataDO> var2, List<String> var3);

    public int deleteBaseData(String var1, List<String> var2);

    public void truncateTable(String var1);

    public Map<String, String> prepareTempOrgData(String var1, List<OrgDO> var2, List<String> var3);

    public Map<String, String> prepareTempTableData(String var1, List<BaseDataDO> var2, List<String> var3);

    public int insertTableData(String var1, Map<String, String> var2);

    public int updateTableData(String var1, Map<String, String> var2);

    public void clearTempData(String var1);
}

