/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.multcheck2.web.result;

import com.jiuqi.nr.multcheck2.common.MultcheckSchemeError;
import com.jiuqi.nr.multcheck2.web.vo.MCLabel;
import java.io.Serializable;
import java.util.List;

public class MCExecuteResult
implements Serializable {
    private MultcheckSchemeError error;
    private String errorMsg;
    private List<MCLabel> errorList;
    private String task;
    private String runId;
    private boolean singleOrgRes;
    private boolean cancel = false;
    private String cancelMsg;

    public MultcheckSchemeError getError() {
        return this.error;
    }

    public void setError(MultcheckSchemeError error) {
        this.error = error;
        this.errorMsg = error.getMessage();
    }

    public String getErrorMsg() {
        return this.errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public List<MCLabel> getErrorList() {
        return this.errorList;
    }

    public void setErrorList(List<MCLabel> errorList) {
        this.errorList = errorList;
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

    public boolean isSingleOrgRes() {
        return this.singleOrgRes;
    }

    public void setSingleOrgRes(boolean singleOrgRes) {
        this.singleOrgRes = singleOrgRes;
    }

    public boolean isCancel() {
        return this.cancel;
    }

    public void setCancel(boolean cancel) {
        this.cancel = cancel;
    }

    public String getCancelMsg() {
        return this.cancelMsg;
    }

    public void setCancelMsg(String cancelMsg) {
        this.cancelMsg = cancelMsg;
    }
}

