/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.impl.process.consts;

public enum TaskEnum {
    TASK_UPLOAD("tsk_upload", "\u4e0a\u62a5"),
    TASK_SUBMIT("tsk_submit", "\u9001\u5ba1"),
    TASK_AUDIT("tsk_audit", "\u786e\u8ba4"),
    TASK_AFTER_CONFIRM("tsk_audit_after_confirm", "\u786e\u8ba4\u540e\u7f6e\u8282\u70b9"),
    TASK_START("StartEvent", "\u5f00\u59cb"),
    TASK_END("EndEvent", "\u7ed3\u675f"),
    TASK_DEFAULT("", "");

    private String taskId;
    private String name;

    private TaskEnum(String taskId, String name) {
        this.taskId = taskId;
        this.name = name;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

