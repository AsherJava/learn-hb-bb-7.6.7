/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.bpm.businesskey.BusinessKey
 *  com.jiuqi.nr.bpm.businesskey.BusinessKeySet
 *  com.jiuqi.nr.bpm.common.Task
 *  com.jiuqi.nr.bpm.common.TaskContext
 *  com.jiuqi.nr.bpm.service.RunTimeService
 */
package com.jiuqi.gcreport.inputdata.dataentryext.listener.upload.impl;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.bpm.businesskey.BusinessKey;
import com.jiuqi.nr.bpm.businesskey.BusinessKeySet;
import com.jiuqi.nr.bpm.common.Task;
import com.jiuqi.nr.bpm.common.TaskContext;
import com.jiuqi.nr.bpm.service.RunTimeService;
import java.util.List;

public class GcUploaderImpl {
    private TaskContext taskContext;
    private RunTimeService runTimeService;
    private List<Task> tasks;
    private BusinessKey businessKey;
    private BusinessKeySet businessKeySet;
    private DimensionValueSet dimensionValue;

    public GcUploaderImpl(TaskContext taskContext, RunTimeService runTimeService, List<Task> tasks, BusinessKey businessKey, BusinessKeySet businessKeySet, DimensionValueSet dimensionValue) {
        this.setRunTimeService(runTimeService);
        this.setTaskContext(taskContext);
        this.setTasks(tasks);
        this.setBusinessKey(businessKey);
        this.setBusinessKeySet(businessKeySet);
        this.setDimensionValue(dimensionValue);
    }

    public void setRunTimeService(RunTimeService runTimeService) {
        this.runTimeService = runTimeService;
    }

    public RunTimeService getRunTimeService() {
        return this.runTimeService;
    }

    public void setTaskContext(TaskContext taskContext) {
        this.taskContext = taskContext;
    }

    public TaskContext getTaskContext() {
        return this.taskContext;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public List<Task> getTasks() {
        return this.tasks;
    }

    public BusinessKey getBusinessKey() {
        return this.businessKey;
    }

    public void setBusinessKey(BusinessKey businessKey) {
        this.businessKey = businessKey;
    }

    public BusinessKeySet getBusinessKeySet() {
        return this.businessKeySet;
    }

    public void setBusinessKeySet(BusinessKeySet businessKeySet) {
        this.businessKeySet = businessKeySet;
    }

    public DimensionValueSet getDimensionValue() {
        return this.dimensionValue;
    }

    public void setDimensionValue(DimensionValueSet dimensionValue) {
        this.dimensionValue = dimensionValue;
    }
}

