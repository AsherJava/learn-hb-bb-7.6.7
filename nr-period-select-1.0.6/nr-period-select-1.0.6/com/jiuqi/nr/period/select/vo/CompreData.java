/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.period.select.vo;

import com.jiuqi.nr.period.select.vo.PeriodRangeData;
import com.jiuqi.nr.period.select.vo.TaskData;
import java.util.ArrayList;
import java.util.List;

public class CompreData {
    private List<PeriodRangeData> schemePeriod = new ArrayList<PeriodRangeData>();
    private TaskData taskData;
    private String period;

    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public List<PeriodRangeData> getSchemePeriod() {
        return this.schemePeriod;
    }

    public void setSchemePeriod(List<PeriodRangeData> schemePeriod) {
        this.schemePeriod = schemePeriod;
    }

    public TaskData getTaskData() {
        return this.taskData;
    }

    public void setTaskData(TaskData taskData) {
        this.taskData = taskData;
    }
}

