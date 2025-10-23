/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dynamic.temptable.domain;

import com.jiuqi.nr.dynamic.temptable.common.DynamicTempTableStatusEnum;
import java.util.Date;

public class DynamicTempTableInfo {
    private String tableName;
    private int columnCount;
    private DynamicTempTableStatusEnum status;
    private String acquireUser;
    private Date acquireTime;
    private Date lastUseTime;
    private Date createTime;

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public int getColumnCount() {
        return this.columnCount;
    }

    public void setColumnCount(int columnCount) {
        this.columnCount = columnCount;
    }

    public DynamicTempTableStatusEnum getStatus() {
        return this.status;
    }

    public void setStatus(DynamicTempTableStatusEnum status) {
        this.status = status;
    }

    public String getAcquireUser() {
        return this.acquireUser;
    }

    public void setAcquireUser(String acquireUser) {
        this.acquireUser = acquireUser;
    }

    public Date getAcquireTime() {
        return this.acquireTime;
    }

    public void setAcquireTime(Date acquireTime) {
        this.acquireTime = acquireTime;
    }

    public Date getLastUseTime() {
        return this.lastUseTime;
    }

    public void setLastUseTime(Date lastUseTime) {
        this.lastUseTime = lastUseTime;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}

