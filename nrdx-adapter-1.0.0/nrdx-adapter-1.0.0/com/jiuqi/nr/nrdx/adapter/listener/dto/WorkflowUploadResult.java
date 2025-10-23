/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.nr.nrdx.adapter.listener.dto;

import com.jiuqi.util.StringUtils;
import java.util.HashSet;
import java.util.Set;

public class WorkflowUploadResult {
    private boolean uploadSuccess;
    private String failedReason;
    private final Set<String> successUnits = new HashSet<String>();
    private final Set<String> failedUnits = new HashSet<String>();

    public boolean isUploadSuccess() {
        return this.uploadSuccess;
    }

    public void setUploadSuccess(boolean uploadSuccess) {
        this.uploadSuccess = uploadSuccess;
    }

    public String getFailedReason() {
        return this.failedReason;
    }

    public void setFailedReason(String failedReason) {
        this.failedReason = failedReason;
    }

    public Set<String> getFailedUnits() {
        return this.failedUnits;
    }

    public Set<String> getSuccessUnits() {
        return this.successUnits;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder("\u4e0a\u62a5");
        builder.append(this.uploadSuccess ? "\u6210\u529f" : "\u5931\u8d25").append("\uff01");
        if (!this.uploadSuccess && StringUtils.isNotEmpty((String)this.failedReason)) {
            builder.append("\u5931\u8d25\u539f\u56e0\u4e3a\uff1a").append(this.failedReason).append("\u3002");
        }
        if (!this.successUnits.isEmpty()) {
            builder.append("\u4e0a\u62a5\u6210\u529f\u7684\u5355\u4f4d\u6709\uff1a");
            this.successUnits.forEach(e -> builder.append((String)e).append(","));
            builder.deleteCharAt(builder.length() - 1);
            builder.append("\u3002");
        }
        if (!this.failedUnits.isEmpty()) {
            builder.append("\u4e0a\u62a5\u5931\u8d25\u7684\u5355\u4f4d\u6709\uff1a");
            this.failedUnits.forEach(e -> builder.append((String)e).append(","));
            builder.deleteCharAt(builder.length() - 1);
            builder.append("\u3002");
        }
        return builder.toString();
    }
}

