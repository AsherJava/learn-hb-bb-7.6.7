/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.service.para;

import com.jiuqi.nr.workflow2.service.para.IProcessExecutePara;
import com.jiuqi.nr.workflow2.service.para.ProcessRunPara;

public class ProcessExecutePara
extends ProcessRunPara
implements IProcessExecutePara {
    private String taskId;
    private String actionCode;
    private String userTaskCode;

    @Override
    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    @Override
    public String getActionCode() {
        return this.actionCode;
    }

    public void setActionCode(String actionCode) {
        this.actionCode = actionCode;
    }

    @Override
    public String getUserTaskCode() {
        return this.userTaskCode;
    }

    @Override
    public void setUserTaskCode(String userTaskCode) {
        this.userTaskCode = userTaskCode;
    }
}

