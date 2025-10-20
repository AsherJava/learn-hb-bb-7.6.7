/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 */
package com.jiuqi.dc.taskscheduling.api.vo;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.dc.taskscheduling.api.vo.DimTypeVO;
import com.jiuqi.dc.taskscheduling.core.enums.InstanceTypeEnum;
import com.jiuqi.dc.taskscheduling.core.enums.TaskTypeEnum;
import com.jiuqi.dc.taskscheduling.core.intf.ITaskHandler;
import java.util.List;

public class TaskHandlerVO {
    private String name;
    private String title;
    private String preTask;
    private TaskTypeEnum taskType;
    private InstanceTypeEnum instanceType;
    private String module;
    private DimTypeVO dimType;
    private String specialQueueFlag;
    private boolean dimSerialExecute;
    private List<String> applicationNames;

    public TaskHandlerVO() {
    }

    public TaskHandlerVO(String name, String title, String preTask) {
        this.name = name;
        this.title = title;
        this.preTask = preTask;
    }

    public TaskHandlerVO(ITaskHandler taskHandler, String applicationName) {
        this.name = taskHandler.getName();
        this.title = taskHandler.getTitle();
        this.preTask = taskHandler.getPreTask();
        this.taskType = taskHandler.getTaskType();
        this.instanceType = taskHandler.getInstanceType();
        this.module = taskHandler.getModule();
        this.specialQueueFlag = taskHandler.getSpecialQueueFlag();
        this.dimType = new DimTypeVO(taskHandler.getDimType().getName(), taskHandler.getDimType().getTitle());
        this.dimSerialExecute = taskHandler.isDimSerialExecute();
        this.applicationNames = CollectionUtils.newArrayList((Object[])new String[]{applicationName});
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPreTask() {
        return this.preTask;
    }

    public void setPreTask(String preTask) {
        this.preTask = preTask;
    }

    public TaskTypeEnum getTaskType() {
        return this.taskType;
    }

    public void setTaskType(TaskTypeEnum taskType) {
        this.taskType = taskType;
    }

    public InstanceTypeEnum getInstanceType() {
        return this.instanceType;
    }

    public void setInstanceType(InstanceTypeEnum instanceType) {
        this.instanceType = instanceType;
    }

    public String getModule() {
        return this.module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public DimTypeVO getDimType() {
        return this.dimType;
    }

    public void setDimType(DimTypeVO dimType) {
        this.dimType = dimType;
    }

    public boolean isDimSerialExecute() {
        return this.dimSerialExecute;
    }

    public void setDimSerialExecute(boolean dimSerialExecute) {
        this.dimSerialExecute = dimSerialExecute;
    }

    public String getSpecialQueueFlag() {
        return this.specialQueueFlag;
    }

    public void setSpecialQueueFlag(String specialQueueFlag) {
        this.specialQueueFlag = specialQueueFlag;
    }

    public List<String> getApplicationNames() {
        return this.applicationNames;
    }

    public void setApplicationNames(List<String> applicationNames) {
        this.applicationNames = applicationNames;
    }

    public void addApplicationNames(List<String> applicationNames) {
        applicationNames.forEach(applicationName -> {
            if (!this.applicationNames.contains(applicationName)) {
                this.applicationNames.add((String)applicationName);
            }
        });
    }
}

