/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.examine.task;

import com.jiuqi.nr.examine.common.ErrorType;
import com.jiuqi.nr.examine.common.ExamineEnvironment;
import com.jiuqi.nr.examine.common.ParaType;

public class RestorTask {
    private ParaType paraType;
    private String[] keys;
    private ErrorType errorType;
    private ExamineEnvironment env;

    public RestorTask() {
    }

    public RestorTask(RestorTask task) {
        this.paraType = task.getParaType();
        this.keys = task.getKeys();
        this.env = task.getEnv();
        this.errorType = task.getErrorType();
    }

    public ParaType getParaType() {
        return this.paraType;
    }

    public void setParaType(ParaType paraType) {
        this.paraType = paraType;
    }

    public String[] getKeys() {
        return this.keys;
    }

    public void setKeys(String[] keys) {
        this.keys = keys;
    }

    public ExamineEnvironment getEnv() {
        return this.env;
    }

    public void setEnv(ExamineEnvironment env) {
        this.env = env;
    }

    public ErrorType getErrorType() {
        return this.errorType;
    }

    public void setErrorType(ErrorType errorType) {
        this.errorType = errorType;
    }
}

