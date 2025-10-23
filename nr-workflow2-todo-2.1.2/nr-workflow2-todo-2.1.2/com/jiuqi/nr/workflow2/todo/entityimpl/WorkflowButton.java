/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.todo.entityimpl;

public class WorkflowButton {
    private String code;
    private String title;
    private boolean showBatch;
    private Object actionParam;

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isShowBatch() {
        return this.showBatch;
    }

    public void setShowBatch(boolean showBatch) {
        this.showBatch = showBatch;
    }

    public Object getActionParam() {
        return this.actionParam;
    }

    public void setActionParam(Object actionParam) {
        this.actionParam = actionParam;
    }
}

