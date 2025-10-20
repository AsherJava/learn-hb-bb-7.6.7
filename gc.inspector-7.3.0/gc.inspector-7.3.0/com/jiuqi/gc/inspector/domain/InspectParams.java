/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gc.inspector.domain;

import com.jiuqi.gc.inspector.common.TaskTypeEnum;
import java.util.Map;

public class InspectParams {
    private TaskTypeEnum taskType;
    private String code;
    private Map<String, Object> params;

    public TaskTypeEnum getTaskType() {
        return this.taskType;
    }

    public void setTaskType(TaskTypeEnum taskType) {
        this.taskType = taskType;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Map<String, Object> getParams() {
        return this.params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }
}

