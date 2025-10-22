/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.batch.gather.gzw.web.app.func.para;

import java.util.HashMap;
import java.util.Map;

public class OpenDataEntryFuncPara {
    private String taskId;
    private Map<String, Object> variableMap = new HashMap<String, Object>();

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public Map<String, Object> getVariableMap() {
        return this.variableMap;
    }

    public void setVariableMap(Map<String, Object> variableMap) {
        this.variableMap = variableMap;
    }
}

