/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.bean;

import com.jiuqi.nr.dataentry.bean.MultCheckLabel;
import java.util.List;

public class MultCheckReturnResult {
    private String errorMsg;
    private List<MultCheckLabel> successList;
    private List<MultCheckLabel> failedList;
    private String runId;
    private String task;

    public List<MultCheckLabel> getSuccessList() {
        return this.successList;
    }

    public void setSuccessList(List<MultCheckLabel> successList) {
        this.successList = successList;
    }

    public List<MultCheckLabel> getFailedList() {
        return this.failedList;
    }

    public void setFailedList(List<MultCheckLabel> failedList) {
        this.failedList = failedList;
    }

    public String getRunId() {
        return this.runId;
    }

    public void setRunId(String runId) {
        this.runId = runId;
    }

    public String getTask() {
        return this.task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getErrorMsg() {
        return this.errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}

