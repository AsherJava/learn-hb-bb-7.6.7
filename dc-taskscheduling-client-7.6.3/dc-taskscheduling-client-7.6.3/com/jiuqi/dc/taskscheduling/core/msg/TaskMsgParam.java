/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.taskscheduling.core.msg;

public class TaskMsgParam {
    private String dimCode;
    private String param;

    public TaskMsgParam() {
    }

    public TaskMsgParam(String dimCode, String param) {
        this.dimCode = dimCode;
        this.param = param;
    }

    public String getDimCode() {
        return this.dimCode;
    }

    public void setDimCode(String dimCode) {
        this.dimCode = dimCode;
    }

    public String getParam() {
        return this.param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String toString() {
        return "TaskMsgParam [dimCode=" + this.dimCode + ", param=" + this.param + "]";
    }
}

