/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.logic.monitor;

import com.jiuqi.nr.data.logic.monitor.base.BatchSerialBaseMonitor;

public class CheckBatchSerialMonitor
extends BatchSerialBaseMonitor {
    private boolean delResult;
    private boolean canceled;

    public boolean isCancel() {
        if (this.canceled) {
            return true;
        }
        boolean forceCancel = this.mainMonitor.isCancel();
        boolean normalCancel = super.getErrorMonitor().isCancel();
        if (forceCancel || normalCancel) {
            this.delResult = forceCancel;
            this.canceled = true;
            return true;
        }
        return false;
    }

    public boolean delResult() {
        return this.delResult;
    }
}

