/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.common;

import java.util.Date;

public interface ProcessActivity {
    public String getProcessDefinitionId();

    public String getProcessInstanceId();

    public String getProcessTaskId();

    public String getActivityId();

    public String getActUserId();

    public Date getStartTime();

    public Date getEndTime();

    public boolean isFinish();
}

