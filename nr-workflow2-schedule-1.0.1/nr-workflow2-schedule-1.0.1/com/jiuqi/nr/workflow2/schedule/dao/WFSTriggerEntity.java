/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.schedule.dao;

import com.jiuqi.nr.workflow2.schedule.enumeration.WFSTriggerStatus;
import java.util.Date;

public interface WFSTriggerEntity {
    public String getTaskKey();

    public String getPeriod();

    public Date getPlanedStartTime();

    public Date getPlanedEndTime();

    public Date getActualTime();

    public WFSTriggerStatus getStatus();

    public int getExecCount();
}

