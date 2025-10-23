/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.access.api.response;

import com.jiuqi.nr.data.access.api.response.BatchLockUnitFormsInfo;
import java.util.List;

public class BatchLockReturnInfo {
    private List<BatchLockUnitFormsInfo> highLockUnitForms;
    private List<BatchLockUnitFormsInfo> noAuthUnitForms;
    private boolean isLock;
    private String noAuthReason;

    public List<BatchLockUnitFormsInfo> getHighLockUnitForms() {
        return this.highLockUnitForms;
    }

    public void setHighLockUnitForms(List<BatchLockUnitFormsInfo> highLockUnitForms) {
        this.highLockUnitForms = highLockUnitForms;
    }

    public List<BatchLockUnitFormsInfo> getNoAuthUnitForms() {
        return this.noAuthUnitForms;
    }

    public void setNoAuthUnitForms(List<BatchLockUnitFormsInfo> noAuthUnitForms) {
        this.noAuthUnitForms = noAuthUnitForms;
    }

    public boolean isLock() {
        return this.isLock;
    }

    public void setLock(boolean lock) {
        this.isLock = lock;
    }

    public String getNoAuthReason() {
        return this.noAuthReason;
    }

    public void setNoAuthReason(String noAuthReason) {
        this.noAuthReason = noAuthReason;
    }
}

