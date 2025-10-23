/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKey
 */
package com.jiuqi.nr.workflow2.engine.dflt.process.runtime.repository;

import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKey;
import java.util.Calendar;

public class ProcessInstanceDO {
    private String id;
    private IBusinessKey businessKey;
    private String processDefinitionId;
    private Calendar startTime;
    private Calendar updateTime;
    private String startUser;
    private String curTaskId;
    private String curNode;
    private String curStatus;
    private String lastOperationId;

    public IBusinessKey getBusinessKey() {
        return this.businessKey;
    }

    public void setBusinessKey(IBusinessKey businessKey) {
        this.businessKey = businessKey;
    }

    public Calendar getStartTime() {
        return this.startTime;
    }

    public void setStartTime(Calendar startTime) {
        this.startTime = startTime;
    }

    public Calendar getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Calendar updateTime) {
        this.updateTime = updateTime;
    }

    public String getStartUser() {
        return this.startUser;
    }

    public void setStartUser(String startUser) {
        this.startUser = startUser;
    }

    public String getCurTaskId() {
        return this.curTaskId;
    }

    public void setCurTaskId(String curTaskId) {
        this.curTaskId = curTaskId;
    }

    public String getCurNode() {
        return this.curNode;
    }

    public void setCurNode(String curNode) {
        this.curNode = curNode;
    }

    public String getCurStatus() {
        return this.curStatus;
    }

    public void setCurStatus(String curStatus) {
        this.curStatus = curStatus;
    }

    public String getProcessDefinitionId() {
        return this.processDefinitionId;
    }

    public void setProcessDefinitionId(String processDefinitionId) {
        this.processDefinitionId = processDefinitionId;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLastOperationId() {
        return this.lastOperationId;
    }

    public void setLastOperationId(String lastOperationId) {
        this.lastOperationId = lastOperationId;
    }
}

