/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.plugin.common.cache.memcache.memtable.domain.MemoryTableDataDO
 *  com.jiuqi.bi.core.jobs.defaultlog.Logger
 */
package com.jiuqi.bde.plugin.external.service;

import com.jiuqi.bde.plugin.common.cache.memcache.memtable.domain.MemoryTableDataDO;
import com.jiuqi.bi.core.jobs.defaultlog.Logger;

public interface CleanMemCacheTableService {
    public void doClean(Logger var1);

    public int doCleanCache(MemoryTableDataDO var1);

    public boolean tryTruncateTable(String var1);
}

