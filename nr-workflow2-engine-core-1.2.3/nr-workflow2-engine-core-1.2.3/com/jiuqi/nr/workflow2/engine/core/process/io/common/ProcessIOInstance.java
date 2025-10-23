/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.core.process.io.common;

import com.jiuqi.nr.workflow2.engine.core.process.io.IProcessIOInstance;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject;
import java.util.Calendar;

public class ProcessIOInstance
implements IProcessIOInstance {
    private String id;
    private IBusinessObject businessObject;
    private String currentUserTask;
    private String status;
    private String currentUserTaskCode;
    private Calendar startTime;
    private Calendar updateTime;

    public ProcessIOInstance() {
    }

    public ProcessIOInstance(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public IBusinessObject getBusinessObject() {
        return this.businessObject;
    }

    public void setBusinessObject(IBusinessObject businessObject) {
        this.businessObject = businessObject;
    }

    @Override
    public String getCurrentUserTask() {
        return this.currentUserTask;
    }

    public void setCurrentUserTask(String currentUserTask) {
        this.currentUserTask = currentUserTask;
    }

    @Override
    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String getCurrentUserTaskCode() {
        return this.currentUserTaskCode;
    }

    public void setCurrentUserTaskCode(String currentUserTaskCode) {
        this.currentUserTaskCode = currentUserTaskCode;
    }

    @Override
    public Calendar getStartTime() {
        return this.startTime;
    }

    public void setStartTime(Calendar startTime) {
        this.startTime = startTime;
    }

    @Override
    public Calendar getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Calendar updateTime) {
        this.updateTime = updateTime;
    }
}

