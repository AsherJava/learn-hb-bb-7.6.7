/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.schedule.dao.impl;

import com.jiuqi.nr.workflow2.schedule.dao.WFSTriggerEntity;
import com.jiuqi.nr.workflow2.schedule.enumeration.WFSTriggerStatus;
import java.util.Date;

public class WFSTriggerEntityImpl
implements WFSTriggerEntity {
    private String taskKey;
    private String period;
    private Date planedStartTime;
    private Date planedEndTime;
    private Date actualTime;
    private WFSTriggerStatus status;
    private int execCount;

    @Override
    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    @Override
    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    @Override
    public Date getPlanedStartTime() {
        return this.planedStartTime;
    }

    public void setPlanedStartTime(Date planedStartTime) {
        this.planedStartTime = planedStartTime;
    }

    @Override
    public Date getPlanedEndTime() {
        return this.planedEndTime;
    }

    public void setPlanedEndTime(Date planedEndTime) {
        this.planedEndTime = planedEndTime;
    }

    @Override
    public Date getActualTime() {
        return this.actualTime;
    }

    public void setActualTime(Date actualTime) {
        this.actualTime = actualTime;
    }

    @Override
    public WFSTriggerStatus getStatus() {
        return this.status;
    }

    public void setStatus(WFSTriggerStatus status) {
        this.status = status;
    }

    @Override
    public int getExecCount() {
        return this.execCount;
    }

    public void setExecCount(int execCount) {
        this.execCount = execCount;
    }
}

