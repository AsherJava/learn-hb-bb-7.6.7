/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.todo.entity;

import java.util.List;

public interface TodoInfo {
    public String getTaskId();

    public String getBusinessKey();

    public String getBusinessTitle();

    public String getWorkflowNodeTask();

    public List<String> getParticipants();

    public String getWorkflowNode();

    public String getWorkflowInstance();

    public String getFormSchemeKey();

    public String getPeriod();

    public String getRemark();
}

