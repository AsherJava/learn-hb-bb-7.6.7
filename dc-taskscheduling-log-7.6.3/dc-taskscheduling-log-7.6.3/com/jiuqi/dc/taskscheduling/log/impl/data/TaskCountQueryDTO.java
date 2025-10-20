/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.taskscheduling.log.impl.data;

import com.jiuqi.dc.taskscheduling.log.impl.enums.DataHandleState;
import java.io.Serializable;
import java.util.Collection;

public class TaskCountQueryDTO
implements Serializable {
    private static final long serialVersionUID = 0L;
    private String runnerId;
    private String taskName;
    private Collection<String> dimCodes;
    private Collection<String> instanceIdList;
    private Collection<DataHandleState> dataHandleStates;

    public TaskCountQueryDTO() {
    }

    public TaskCountQueryDTO(String runnerId) {
        this.runnerId = runnerId;
    }

    public TaskCountQueryDTO(String runnerId, Collection<String> dimCodes, Collection<DataHandleState> dataHandleStates) {
        this.runnerId = runnerId;
        this.dimCodes = dimCodes;
        this.dataHandleStates = dataHandleStates;
    }

    public TaskCountQueryDTO(String runnerId, String taskName, Collection<String> dimCodes, Collection<DataHandleState> dataHandleStates) {
        this.runnerId = runnerId;
        this.taskName = taskName;
        this.dimCodes = dimCodes;
        this.dataHandleStates = dataHandleStates;
    }

    public String getRunnerId() {
        return this.runnerId;
    }

    public void setRunnerId(String runnerId) {
        this.runnerId = runnerId;
    }

    public Collection<DataHandleState> getDataHandleStates() {
        return this.dataHandleStates;
    }

    public void setDataHandleStates(Collection<DataHandleState> dataHandleState) {
        this.dataHandleStates = dataHandleState;
    }

    public String getTaskName() {
        return this.taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Collection<String> getDimCodes() {
        return this.dimCodes;
    }

    public void setDimCodes(Collection<String> dimCode) {
        this.dimCodes = dimCode;
    }

    public Collection<String> getInstanceIdList() {
        return this.instanceIdList;
    }

    public void setInstanceIdList(Collection<String> instanceIdList) {
        this.instanceIdList = instanceIdList;
    }
}

