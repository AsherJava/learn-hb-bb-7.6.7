/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.entity.engine.result;

import com.jiuqi.nr.entity.engine.result.CheckFailNodeInfo;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EntityCheckResult {
    private String message;
    private String entityId;
    private Date versionTime;
    private List<CheckFailNodeInfo> failInfos;

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getEntityId() {
        return this.entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public Date getVersionTime() {
        return this.versionTime;
    }

    public void setVersionTime(Date versionTime) {
        this.versionTime = versionTime;
    }

    public List<CheckFailNodeInfo> getFailInfos() {
        if (this.failInfos == null) {
            this.failInfos = new ArrayList<CheckFailNodeInfo>();
        }
        return this.failInfos;
    }

    public void setFailInfos(List<CheckFailNodeInfo> failInfos) {
        this.failInfos = failInfos;
    }

    public void addFailInfos(CheckFailNodeInfo checkFailNodeInfo) {
        if (this.failInfos == null) {
            this.failInfos = new ArrayList<CheckFailNodeInfo>();
        }
        this.failInfos.add(checkFailNodeInfo);
    }
}

