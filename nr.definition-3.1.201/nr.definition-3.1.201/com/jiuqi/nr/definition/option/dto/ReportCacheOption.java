/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBField
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBTable
 */
package com.jiuqi.nr.definition.option.dto;

import com.jiuqi.np.definition.internal.anno.DBAnno;

@DBAnno.DBTable(dbTable="NR_PARAM_CACHE_OPTION")
public class ReportCacheOption {
    @DBAnno.DBField(dbField="CO_TASK", isPk=true)
    private String task;
    @DBAnno.DBField(dbField="CO_CYCLE_BEGIN")
    private String cycleBeginDays;
    @DBAnno.DBField(dbField="CO_CYCLE_END")
    private String cycleEndDays;
    @DBAnno.DBField(dbField="CO_TYPE", isPk=true)
    private int currentOptionType;

    public String getTask() {
        return this.task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getCycleBeginDays() {
        return this.cycleBeginDays;
    }

    public void setCycleBeginDays(String cycleBeginDays) {
        this.cycleBeginDays = cycleBeginDays;
    }

    public String getCycleEndDays() {
        return this.cycleEndDays;
    }

    public void setCycleEndDays(String cycleEndDays) {
        this.cycleEndDays = cycleEndDays;
    }

    public int getCurrentOptionType() {
        return this.currentOptionType;
    }

    public void setCurrentOptionType(int currentOptionType) {
        this.currentOptionType = currentOptionType;
    }
}

