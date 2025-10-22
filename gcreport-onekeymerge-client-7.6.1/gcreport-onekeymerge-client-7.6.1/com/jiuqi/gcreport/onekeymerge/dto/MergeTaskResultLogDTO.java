/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.temp.dto.Message
 */
package com.jiuqi.gcreport.onekeymerge.dto;

import com.jiuqi.gcreport.temp.dto.Message;
import java.util.List;

public class MergeTaskResultLogDTO {
    private List<Message> mergeTaskInfos;
    private List<Message> executingLogs;
    private List<Message> errorLogs;
    private String taskProcessState;

    public List<Message> getMergeTaskInfos() {
        return this.mergeTaskInfos;
    }

    public void setMergeTaskInfos(List<Message> mergeTaskInfos) {
        this.mergeTaskInfos = mergeTaskInfos;
    }

    public String getTaskProcessState() {
        return this.taskProcessState;
    }

    public void setTaskProcessState(String taskProcessState) {
        this.taskProcessState = taskProcessState;
    }

    public List<Message> getExecutingLogs() {
        return this.executingLogs;
    }

    public void setExecutingLogs(List<Message> executingLogs) {
        this.executingLogs = executingLogs;
    }

    public List<Message> getErrorLogs() {
        return this.errorLogs;
    }

    public void setErrorLogs(List<Message> errorLogs) {
        this.errorLogs = errorLogs;
    }
}

