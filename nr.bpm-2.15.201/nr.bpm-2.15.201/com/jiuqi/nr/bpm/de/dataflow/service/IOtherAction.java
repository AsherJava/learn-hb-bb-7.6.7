/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.de.dataflow.service;

import com.jiuqi.nr.bpm.businesskey.BusinessKey;
import com.jiuqi.nr.bpm.common.Task;
import com.jiuqi.nr.bpm.de.dataflow.bean.WorkflowAction;
import java.util.List;

public interface IOtherAction {
    public boolean enabled(List<Task> var1, BusinessKey var2);

    public WorkflowAction otherAction(List<Task> var1, BusinessKey var2);
}

