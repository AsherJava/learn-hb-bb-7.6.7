/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.transmission.data.dao;

import com.jiuqi.nr.transmission.data.domain.SyncHistoryDO;
import java.util.List;

public interface ISyncHistoryDao {
    public int add(SyncHistoryDO var1);

    public int delete(String var1);

    public int deletes(String var1);

    public int update(SyncHistoryDO var1);

    public int updateSyncSchemeParam(SyncHistoryDO var1);

    public int updateResult(SyncHistoryDO var1);

    public int updateField(String var1, String var2, String var3);

    public SyncHistoryDO get(String var1);

    public List<SyncHistoryDO> getUnComplete();

    public List<SyncHistoryDO> list(String var1);

    public List<SyncHistoryDO> listImport();

    public List<SyncHistoryDO> listByGroup(List<String> var1);
}

