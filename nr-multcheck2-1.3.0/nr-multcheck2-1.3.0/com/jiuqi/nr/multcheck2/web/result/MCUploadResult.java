/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.multcheck2.web.result;

import com.jiuqi.nr.multcheck2.web.vo.MCLabel;
import java.util.ArrayList;
import java.util.List;

public class MCUploadResult {
    String errorMsg;
    List<MCLabel> successList = new ArrayList<MCLabel>();
    List<MCLabel> failedList = new ArrayList<MCLabel>();
    String task;
    String runId;

    public String getErrorMsg() {
        return this.errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public List<MCLabel> getSuccessList() {
        return this.successList;
    }

    public void setSuccessList(List<MCLabel> successList) {
        this.successList = successList;
    }

    public List<MCLabel> getFailedList() {
        return this.failedList;
    }

    public void setFailedList(List<MCLabel> failedList) {
        this.failedList = failedList;
    }

    public String getTask() {
        return this.task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getRunId() {
        return this.runId;
    }

    public void setRunId(String runId) {
        this.runId = runId;
    }
}

