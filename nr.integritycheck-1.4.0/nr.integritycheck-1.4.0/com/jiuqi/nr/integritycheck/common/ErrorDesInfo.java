/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.integritycheck.common;

import java.io.Serializable;
import org.springframework.util.StringUtils;

public class ErrorDesInfo
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String recid;
    private String description;
    private String createTime;
    private String creator;
    private String updateTime;
    private String updater;

    public String getRecid() {
        return this.recid;
    }

    public void setRecid(String recid) {
        this.recid = recid;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreator() {
        return this.creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdater() {
        return this.updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater;
    }

    public boolean isNotNull() {
        return StringUtils.hasLength(this.description);
    }
}

