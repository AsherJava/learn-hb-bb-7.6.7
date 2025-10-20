/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gc.inspector.domain;

import com.jiuqi.gc.inspector.common.TaskStateEnum;
import java.util.Map;

public class InspectResultVO {
    private TaskStateEnum taskState;
    private String message;
    private Map<String, Object> result;

    public InspectResultVO() {
    }

    public InspectResultVO(TaskStateEnum taskState, Map<String, Object> result) {
        this.taskState = taskState;
        this.result = result;
    }

    public InspectResultVO(TaskStateEnum taskState, String message, Map<String, Object> result) {
        this.taskState = taskState;
        this.message = message;
        this.result = result;
    }

    public static InspectResultVO exceptionResult(String message) {
        return new InspectResultVO(TaskStateEnum.EXCEPTION, message, null);
    }

    public static InspectResultVO unSupportResult(String message) {
        return new InspectResultVO(TaskStateEnum.UNSUPPORT, message, null);
    }

    public static InspectResultVO unSupportResult() {
        return new InspectResultVO(TaskStateEnum.UNSUPPORT, "\u4e0d\u652f\u6301\u7684\u68c0\u67e5\u9879", null);
    }

    public static InspectResultVO successResult(String message, Map<String, Object> result) {
        return new InspectResultVO(TaskStateEnum.SUCCESS, message, null);
    }

    public static InspectResultVO successResult() {
        return new InspectResultVO(TaskStateEnum.SUCCESS, "\u64cd\u4f5c\u6210\u529f", null);
    }

    public TaskStateEnum getTaskState() {
        return this.taskState;
    }

    public void setTaskState(TaskStateEnum taskState) {
        this.taskState = taskState;
    }

    public Map<String, Object> getResult() {
        return this.result;
    }

    public void setResult(Map<String, Object> result) {
        this.result = result;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

