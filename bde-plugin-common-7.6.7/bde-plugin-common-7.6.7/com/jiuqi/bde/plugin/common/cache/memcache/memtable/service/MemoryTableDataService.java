/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.plugin.common.cache.memcache.memtable.service;

import com.jiuqi.bde.plugin.common.cache.memcache.memtable.domain.MemoryTableDataDO;
import java.util.List;

public interface MemoryTableDataService {
    public int insert(MemoryTableDataDO var1);

    public boolean acquireLock(MemoryTableDataDO var1);

    public boolean forceAcquireLock(MemoryTableDataDO var1);

    public int updateCacheTime(MemoryTableDataDO var1);

    public List<MemoryTableDataDO> listInvalidCaches();

    public int deleteCacheData(MemoryTableDataDO var1);

    public boolean existsCacheTableData(String var1);

    public void truncateCacheData(String var1);

    public int countDataByTableName(String var1, Long var2);
}

