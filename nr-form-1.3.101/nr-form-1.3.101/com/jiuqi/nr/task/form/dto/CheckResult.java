/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.jiuqi.nr.period.util.JacksonUtils
 */
package com.jiuqi.nr.task.form.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jiuqi.nr.period.util.JacksonUtils;
import com.jiuqi.nr.task.form.dto.ErrorData;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CheckResult
implements Serializable {
    private boolean success;
    private List<ErrorData> errorData;

    public CheckResult() {
    }

    public CheckResult(boolean success) {
        this.success = success;
    }

    public CheckResult(boolean success, List<ErrorData> errorData) {
        this.success = success;
        this.errorData = errorData;
    }

    public CheckResult(CheckResult checkResult) {
        this.success = checkResult.isSuccess();
        this.errorData = checkResult.getErrorData();
    }

    public boolean isSuccess() {
        return this.success;
    }

    @JsonIgnore
    public boolean isError() {
        return !this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<ErrorData> getErrorData() {
        return this.errorData;
    }

    public void setErrorData(List<ErrorData> errorData) {
        this.errorData = errorData;
    }

    public static CheckResult successResult() {
        return new CheckResult(true);
    }

    public static CheckResult errorResult(List<ErrorData> errorData) {
        return new CheckResult(false, errorData);
    }

    public static CheckResult errorResult(ErrorData errorData) {
        CheckResult checkResult = new CheckResult(false, new ArrayList<ErrorData>());
        checkResult.addErrorData(errorData);
        return checkResult;
    }

    public void addErrorData(ErrorData errorData) {
        if (this.errorData == null) {
            this.errorData = new ArrayList<ErrorData>();
        }
        if (errorData != null) {
            this.errorData.add(errorData);
            this.success = false;
        }
    }

    public void addErrorData(List<ErrorData> errorData) {
        if (this.errorData == null) {
            this.errorData = new ArrayList<ErrorData>();
        }
        if (errorData != null) {
            this.errorData.addAll(errorData);
            this.success = false;
        }
    }

    public String toString() {
        return JacksonUtils.objectToJson((Object)this);
    }
}

