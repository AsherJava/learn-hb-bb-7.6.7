/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.task.api.status.ReleaseStatus
 */
package com.jiuqi.nr.task.dto;

import com.jiuqi.nr.task.api.status.ReleaseStatus;
import java.util.Date;

public class FormSchemeStatusDTO {
    private ReleaseStatus status;
    private Date publishTime;
    private String message;

    public ReleaseStatus getStatus() {
        return this.status;
    }

    public void setStatus(ReleaseStatus status) {
        this.status = status;
    }

    public Date getPublishTime() {
        return this.publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

