/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.bde.plugin.common.cache.memcache.memtable.service.impl;

import com.jiuqi.bde.plugin.common.cache.memcache.memtable.dao.MemoryTableDataDao;
import com.jiuqi.bde.plugin.common.cache.memcache.memtable.domain.MemoryTableDataDO;
import com.jiuqi.bde.plugin.common.cache.memcache.memtable.service.MemoryTableDataService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemoryTableDataServiceImpl
implements MemoryTableDataService {
    @Autowired
    private MemoryTableDataDao dao;

    @Override
    @Transactional(rollbackFor={Exception.class}, propagation=Propagation.REQUIRES_NEW)
    public int insert(MemoryTableDataDO data) {
        return this.dao.insert(data);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public boolean acquireLock(MemoryTableDataDO data) {
        return this.dao.lockByValidTime(data) > 0;
    }

    @Override
    public boolean forceAcquireLock(MemoryTableDataDO data) {
        return this.dao.lockRow(data) > 0;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public int updateCacheTime(MemoryTableDataDO data) {
        return this.dao.updateCacheTime(data);
    }

    @Override
    public List<MemoryTableDataDO> listInvalidCaches() {
        return this.dao.selectInvalidCaches();
    }

    @Override
    public int deleteCacheData(MemoryTableDataDO data) {
        return this.dao.deleteCacheData(data);
    }

    @Override
    public boolean existsCacheTableData(String memTableName) {
        return this.dao.existsCacheTableData(memTableName);
    }

    @Override
    public void truncateCacheData(String memTableName) {
        this.dao.truncateCacheData(memTableName);
    }

    @Override
    public int countDataByTableName(String tableName, Long timeStamp) {
        return this.dao.countDataByTableName(tableName, timeStamp);
    }
}

