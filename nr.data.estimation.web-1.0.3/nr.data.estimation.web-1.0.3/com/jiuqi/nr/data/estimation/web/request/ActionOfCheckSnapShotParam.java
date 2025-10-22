/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.snapshot.message.DataRange
 */
package com.jiuqi.nr.data.estimation.web.request;

import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.snapshot.message.DataRange;

public class ActionOfCheckSnapShotParam {
    private JtableContext context;
    private DataRange dataRange;
    private String periodCode;
    private String snapshotId;

    public String getSnapshotId() {
        return this.snapshotId;
    }

    public void setSnapshotId(String snapshotId) {
        this.snapshotId = snapshotId;
    }

    public JtableContext getContext() {
        return this.context;
    }

    public void setContext(JtableContext context) {
        this.context = context;
    }

    public DataRange getDataRange() {
        return this.dataRange;
    }

    public void setDataRange(DataRange dataRange) {
        this.dataRange = dataRange;
    }

    public String getPeriodCode() {
        return this.periodCode;
    }

    public void setPeriodCode(String periodCode) {
        this.periodCode = periodCode;
    }
}

