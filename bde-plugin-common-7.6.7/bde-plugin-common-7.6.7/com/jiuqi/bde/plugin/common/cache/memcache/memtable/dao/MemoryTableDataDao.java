/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.plugin.common.cache.memcache.memtable.dao;

import com.jiuqi.bde.plugin.common.cache.memcache.memtable.domain.MemoryTableDataDO;
import java.util.List;

public interface MemoryTableDataDao {
    public int insert(MemoryTableDataDO var1);

    public int lockByValidTime(MemoryTableDataDO var1);

    public int lockRow(MemoryTableDataDO var1);

    public int updateCacheTime(MemoryTableDataDO var1);

    public List<MemoryTableDataDO> selectInvalidCaches();

    public int deleteCacheData(MemoryTableDataDO var1);

    public boolean existsCacheTableData(String var1);

    public void truncateCacheData(String var1);

    public int countDataByTableName(String var1, Long var2);
}

