/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.task.entity.GcTaskBaseArguments
 */
package com.jiuqi.gcreport.datatrace.vo;

import com.jiuqi.gcreport.common.task.entity.GcTaskBaseArguments;
import java.io.Serializable;

public class OffsetAmtTraceConditionVO
implements Serializable {
    private String offsetItemId;
    private String mrecid;
    private GcTaskBaseArguments taskArg;

    public OffsetAmtTraceConditionVO(String offsetItemId, GcTaskBaseArguments taskArg) {
        this.offsetItemId = offsetItemId;
        this.taskArg = taskArg;
    }

    public OffsetAmtTraceConditionVO() {
    }

    public String getOffsetItemId() {
        return this.offsetItemId;
    }

    public void setOffsetItemId(String offsetItemId) {
        this.offsetItemId = offsetItemId;
    }

    public GcTaskBaseArguments getTaskArg() {
        return this.taskArg;
    }

    public void setTaskArg(GcTaskBaseArguments taskArg) {
        this.taskArg = taskArg;
    }

    public String getMrecid() {
        return this.mrecid;
    }

    public void setMrecid(String mrecid) {
        this.mrecid = mrecid;
    }

    public String toString() {
        return "OffsetAmtTraceConditionVO{offsetItemId='" + this.offsetItemId + '\'' + ", mrecid='" + this.mrecid + '\'' + ", taskArg=" + this.taskArg + '}';
    }
}

