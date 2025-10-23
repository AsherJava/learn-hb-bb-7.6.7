/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.transmission.data.dao;

import com.jiuqi.nr.transmission.data.domain.SyncEntityLastHistoryDO;
import java.util.List;

public interface ISyncEntityLastHistoryDao {
    public int add(SyncEntityLastHistoryDO var1);

    public int[] betchAdd(List<SyncEntityLastHistoryDO> var1);

    public int delete(String var1);

    public int[] betchdeletes(List<SyncEntityLastHistoryDO> var1);

    public SyncEntityLastHistoryDO get(SyncEntityLastHistoryDO var1);

    public List<SyncEntityLastHistoryDO> list(SyncEntityLastHistoryDO var1);

    public List<SyncEntityLastHistoryDO> lists(SyncEntityLastHistoryDO var1, List<String> var2);

    public int[] batchUpdateWithForm(List<SyncEntityLastHistoryDO> var1);

    public int[] batchUpdateWithEntity(List<SyncEntityLastHistoryDO> var1);
}

