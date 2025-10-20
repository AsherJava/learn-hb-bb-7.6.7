/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.designer.service.impl;

import com.jiuqi.nr.designer.service.impl.ProgressInfo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Progress
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String taskID;
    private List<ProgressInfo> infos = new ArrayList<ProgressInfo>();
    private boolean over = false;
    private String operation;

    public String getOperation() {
        return this.operation;
    }

    public void setOperation(String operation) {
        if (this.operation == null) {
            this.operation = operation;
        } else if (this.operation != null && this.operation.equals(operation)) {
            this.operation = operation;
        }
    }

    public Progress() {
    }

    public Progress(String taskID) {
        this();
        this.taskID = taskID;
    }

    public String getTaskID() {
        return this.taskID;
    }

    public void setTaskID(String taskID) {
        this.taskID = taskID;
    }

    public List<ProgressInfo> getInfos() {
        return this.infos;
    }

    public void addInfos(ProgressInfo info) {
        this.infos.add(info);
    }

    public boolean isOver() {
        return this.over;
    }

    public void setOver(boolean over) {
        this.over = over;
    }
}

