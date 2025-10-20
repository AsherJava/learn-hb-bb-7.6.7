/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.calculate.service.AssetsBillDepreItemCalcCache
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.nvwa.cellbook.constant.StringUtils
 */
package com.jiuqi.gcreport.asset.calculate.rule.processor.impl;

import com.jiuqi.gcreport.calculate.service.AssetsBillDepreItemCalcCache;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.nvwa.cellbook.constant.StringUtils;
import java.lang.ref.SoftReference;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class AssetsBillDepreItemCalcCacheImpl
implements AssetsBillDepreItemCalcCache {
    private Logger logger = LoggerFactory.getLogger(AssetsBillDepreItemCalcCacheImpl.class);
    private final ReadWriteLock read_write_lock = new ReentrantReadWriteLock();
    private Map<String, SoftReference<Map<String, List<DefaultTableEntity>>>> depreItemCache = new ConcurrentHashMap<String, SoftReference<Map<String, List<DefaultTableEntity>>>>();

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public List<DefaultTableEntity> getCacheData(String sn, String mastId, Callable<List<DefaultTableEntity>> valueLoader) {
        this.checkRemoveNullValue();
        try {
            if (StringUtils.isEmpty((String)sn)) {
                List<DefaultTableEntity> callData = valueLoader.call();
                return callData;
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        this.read_write_lock.readLock().lock();
        try {
            if (this.cacheHasValue(sn, mastId)) {
                List<DefaultTableEntity> e = this.depreItemCache.get(sn).get().get(mastId);
                return e;
            }
        }
        finally {
            this.read_write_lock.readLock().unlock();
        }
        this.read_write_lock.writeLock().lock();
        try {
            if (this.cacheHasValue(sn, mastId)) {
                List<DefaultTableEntity> e = this.depreItemCache.get(sn).get().get(mastId);
                return e;
            }
            List<DefaultTableEntity> callData = valueLoader.call();
            this.putCacheData(sn, mastId, callData);
            List<DefaultTableEntity> list = callData;
            return list;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        finally {
            this.read_write_lock.writeLock().unlock();
        }
    }

    private boolean cacheHasValue(String sn, String mastId) {
        SoftReference<Map<String, List<DefaultTableEntity>>> mapSoftReference = this.depreItemCache.get(sn);
        if (mapSoftReference == null) {
            return false;
        }
        Map<String, List<DefaultTableEntity>> mastId2DepreItemMap = mapSoftReference.get();
        if (mastId2DepreItemMap == null) {
            return false;
        }
        return mastId2DepreItemMap.containsKey(mastId);
    }

    public void putCacheData(String sn, String mastId, List<DefaultTableEntity> data) {
        SoftReference<Map<String, List<DefaultTableEntity>>> mapSoftReference = this.depreItemCache.get(sn);
        if (mapSoftReference == null) {
            mapSoftReference = new SoftReference(new ConcurrentHashMap());
        }
        mapSoftReference.get().put(mastId, data);
        this.depreItemCache.put(sn, mapSoftReference);
    }

    public void removeCacheBySn(String sn) {
        this.read_write_lock.readLock().lock();
        try {
            this.depreItemCache.remove(sn);
        }
        finally {
            this.read_write_lock.readLock().unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void checkRemoveNullValue() {
        this.read_write_lock.readLock().lock();
        try {
            for (String sn : this.depreItemCache.keySet()) {
                SoftReference<Map<String, List<DefaultTableEntity>>> mapSoftReference = this.depreItemCache.get(sn);
                if (mapSoftReference == null) {
                    this.depreItemCache.remove(sn);
                    continue;
                }
                if (mapSoftReference.get() != null) continue;
                this.depreItemCache.remove(sn);
            }
        }
        finally {
            this.read_write_lock.readLock().unlock();
        }
    }
}

