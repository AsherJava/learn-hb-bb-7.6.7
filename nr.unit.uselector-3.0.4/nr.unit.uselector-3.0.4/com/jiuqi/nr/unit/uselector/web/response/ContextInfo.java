/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.unit.uselector.web.response;

import java.util.Map;

public class ContextInfo {
    private String taskId;
    private String selectorKey;
    private boolean isMainDimQuery;
    private boolean canChangeTaskAtFormulaEditor;
    private boolean isQuerySourceAtFormulaEditor;
    private String actionOperateMode;
    private Map<String, Object> params;

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getSelectorKey() {
        return this.selectorKey;
    }

    public void setSelectorKey(String selectorKey) {
        this.selectorKey = selectorKey;
    }

    public boolean isMainDimQuery() {
        return this.isMainDimQuery;
    }

    public void setMainDimQuery(boolean mainDimQuery) {
        this.isMainDimQuery = mainDimQuery;
    }

    public boolean isCanChangeTaskAtFormulaEditor() {
        return this.canChangeTaskAtFormulaEditor;
    }

    public void setCanChangeTaskAtFormulaEditor(boolean canChangeTaskAtFormulaEditor) {
        this.canChangeTaskAtFormulaEditor = canChangeTaskAtFormulaEditor;
    }

    public boolean isQuerySourceAtFormulaEditor() {
        return this.isQuerySourceAtFormulaEditor;
    }

    public void setQuerySourceAtFormulaEditor(boolean querySourceAtFormulaEditor) {
        this.isQuerySourceAtFormulaEditor = querySourceAtFormulaEditor;
    }

    public String getActionOperateMode() {
        return this.actionOperateMode;
    }

    public void setActionOperateMode(String actionOperateMode) {
        this.actionOperateMode = actionOperateMode;
    }

    public Map<String, Object> getParams() {
        return this.params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }
}

