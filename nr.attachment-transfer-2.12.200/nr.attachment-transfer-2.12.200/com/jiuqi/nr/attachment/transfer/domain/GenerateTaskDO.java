/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.attachment.transfer.domain;

import java.util.Date;

public class GenerateTaskDO {
    private String key;
    private String serverId;
    private String paramConfig;
    private int status;
    private Date createTime;
    private Date updateTime;
    private long total;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getServerId() {
        return this.serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getParamConfig() {
        return this.paramConfig;
    }

    public void setParamConfig(String paramConfig) {
        this.paramConfig = paramConfig;
    }

    public long getTotal() {
        return this.total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}

