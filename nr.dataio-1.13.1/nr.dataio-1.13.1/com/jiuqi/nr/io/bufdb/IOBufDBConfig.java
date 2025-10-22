/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.io.bufdb;

import com.jiuqi.nr.io.common.ExtConstants;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="jiuqi.nr.bufdb")
public class IOBufDBConfig {
    private long maxCacheBytes;
    private int maxCachePercent;
    private int maxTableCache = -1;
    private int minLiveTime = 300000;
    private int maxTimeOut = -1;
    private String dbPath;

    public long getMaxCacheBytes() {
        if (this.maxCacheBytes <= 0L) {
            this.maxCacheBytes = 0L;
        }
        return this.maxCacheBytes;
    }

    public void setMaxCacheBytes(long maxCacheBytes) {
        this.maxCacheBytes = maxCacheBytes;
    }

    public int getMaxCachePercent() {
        if (this.maxCachePercent <= 0) {
            this.maxCachePercent = 30;
        }
        return this.maxCachePercent;
    }

    public void setMaxCachePercent(int maxCachePercent) {
        this.maxCachePercent = maxCachePercent;
    }

    public int getMaxTableCache() {
        return this.maxTableCache;
    }

    public void setMaxTableCache(int maxTableCache) {
        this.maxTableCache = maxTableCache;
    }

    public int getMinLiveTime() {
        return this.minLiveTime;
    }

    public void setMinLiveTime(int minLiveTime) {
        this.minLiveTime = minLiveTime;
    }

    public int getMaxTimeOut() {
        return this.maxTimeOut;
    }

    public void setMaxTimeOut(int maxTimeOut) {
        this.maxTimeOut = maxTimeOut;
    }

    public String getDbPath() {
        if (this.dbPath == null) {
            this.dbPath = ExtConstants.ROOTPATH + "/" + "bufDb/";
        }
        return this.dbPath;
    }

    public void setDbPath(String dbPath) {
        this.dbPath = dbPath;
    }
}

