/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.plugin.common.cache.memcache.memtable.domain.MemoryTableDataDO
 *  com.jiuqi.bde.plugin.common.cache.memcache.memtable.service.MemoryTableDataService
 *  com.jiuqi.bi.core.jobs.defaultlog.Logger
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.bde.plugin.external.service.impl;

import com.jiuqi.bde.plugin.common.cache.memcache.memtable.domain.MemoryTableDataDO;
import com.jiuqi.bde.plugin.common.cache.memcache.memtable.service.MemoryTableDataService;
import com.jiuqi.bde.plugin.external.service.CleanMemCacheTableService;
import com.jiuqi.bi.core.jobs.defaultlog.Logger;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CleanMemCacheTableServiceImpl
implements CleanMemCacheTableService {
    @Autowired
    private MemoryTableDataService memoryTableDataService;

    @Override
    public void doClean(Logger logger) {
        Map<String, List<MemoryTableDataDO>> invalidCacheMap = this.memoryTableDataService.listInvalidCaches().stream().collect(Collectors.groupingBy(MemoryTableDataDO::getTableName));
        for (Map.Entry<String, List<MemoryTableDataDO>> invalidCacheEntry : invalidCacheMap.entrySet()) {
            logger.info(String.format("\u5f00\u59cb\u70ed\u70b9\u6570\u636e\u8868\u3010%1$s\u3011\u7f13\u5b58\u6e05\u7406\r\n", invalidCacheEntry.getKey()));
            try {
                for (MemoryTableDataDO memoryTableData : invalidCacheEntry.getValue()) {
                    try {
                        ((CleanMemCacheTableService)ApplicationContextRegister.getBean(CleanMemCacheTableService.class)).doCleanCache(memoryTableData);
                    }
                    catch (Exception e) {
                        logger.info(String.format("\u70ed\u70b9\u6570\u636e\u8868\u3010%1$s\u3011\u7ec4\u5408\u6807\u8bc6\u3010%2$s\u3011\u7f13\u5b58\u6e05\u7406\u51fa\u73b0\u9519\u8bef\uff1a%3$s,\u81ea\u52a8\u8df3\u8fc7\r\n", memoryTableData.getTableName(), memoryTableData.getBizCombId(), e.getMessage()));
                    }
                }
                boolean exeTruncate = ((CleanMemCacheTableService)ApplicationContextRegister.getBean(CleanMemCacheTableService.class)).tryTruncateTable(invalidCacheEntry.getKey());
                if (exeTruncate) {
                    logger.info(String.format("\u70ed\u70b9\u6570\u636e\u8868\u3010%1$s\u3011\u5b8c\u6210TRUNCATE\u64cd\u4f5c\uff0c\u964d\u4f4e\u6c34\u4f4d\u7ebf\r\n", invalidCacheEntry.getKey()));
                } else {
                    logger.info(String.format("\u70ed\u70b9\u6570\u636e\u8868\u3010%1$s\u3011\u5b58\u5728\u751f\u6548\u6570\u636e\uff0c\u8df3\u8fc7TRUNCATE\u64cd\u4f5c\r\n", invalidCacheEntry.getKey()));
                }
            }
            catch (Exception e) {
                logger.info(String.format("\u70ed\u70b9\u6570\u636e\u8868\u3010%1$s\u3011\u7f13\u5b58\u6e05\u7406\u51fa\u73b0\u9519\u8bef\uff1a%2$s\r\n,\u81ea\u52a8\u8df3\u8fc7", invalidCacheEntry.getKey(), e.getMessage()));
            }
            logger.info(String.format("\u5b8c\u6210\u70ed\u70b9\u6570\u636e\u8868\u3010%1$s\u3011\u7f13\u5b58\u6e05\u7406\r\n", invalidCacheEntry.getKey()));
        }
    }

    @Override
    @Transactional(rollbackFor={Exception.class}, propagation=Propagation.REQUIRES_NEW)
    public int doCleanCache(MemoryTableDataDO memoryTableData) {
        this.memoryTableDataService.forceAcquireLock(memoryTableData);
        return this.memoryTableDataService.deleteCacheData(memoryTableData);
    }

    @Override
    @Transactional(rollbackFor={Exception.class}, propagation=Propagation.REQUIRES_NEW)
    public boolean tryTruncateTable(String tableName) {
        boolean existsCacheTableData = this.memoryTableDataService.existsCacheTableData(tableName);
        if (!existsCacheTableData) {
            this.memoryTableDataService.truncateCacheData(tableName);
            return true;
        }
        return false;
    }
}

