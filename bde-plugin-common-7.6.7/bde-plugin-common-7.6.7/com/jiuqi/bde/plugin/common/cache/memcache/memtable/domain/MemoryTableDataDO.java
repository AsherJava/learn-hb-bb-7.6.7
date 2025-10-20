/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.plugin.common.cache.memcache.memtable.domain;

import java.io.Serializable;

public class MemoryTableDataDO
implements Serializable {
    private static final long serialVersionUID = 6610377463486194373L;
    public static final String TABLE_NAME = "BDE_MEMORY_TABLEDATA";
    private String tableName;
    private String bizCombId;
    private Long cacheTime;
    private String remark;

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getBizCombId() {
        return this.bizCombId;
    }

    public void setBizCombId(String bizCombId) {
        this.bizCombId = bizCombId;
    }

    public Long getCacheTime() {
        return this.cacheTime;
    }

    public void setCacheTime(Long cacheTime) {
        this.cacheTime = cacheTime;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}

