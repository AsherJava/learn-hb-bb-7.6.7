/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.transmission.data.service;

import com.jiuqi.nr.transmission.data.domain.SyncEntityLastHistoryDO;
import com.jiuqi.nr.transmission.data.dto.SyncEntityLastHistoryDTO;
import java.util.List;

public interface ISyncEntityLastHistoryService {
    public boolean add(SyncEntityLastHistoryDO var1);

    public int[] betchAdd(List<SyncEntityLastHistoryDO> var1);

    public int delete(SyncEntityLastHistoryDO var1);

    public int[] betchdeletes(List<SyncEntityLastHistoryDO> var1);

    public SyncEntityLastHistoryDTO get(SyncEntityLastHistoryDO var1);

    public List<SyncEntityLastHistoryDTO> list(SyncEntityLastHistoryDO var1);

    public List<SyncEntityLastHistoryDTO> lists(SyncEntityLastHistoryDO var1, List<String> var2);

    public int[] batchUpdateWithForm(List<SyncEntityLastHistoryDO> var1);

    public int[] batchUpdateWithEntity(List<SyncEntityLastHistoryDO> var1);
}

