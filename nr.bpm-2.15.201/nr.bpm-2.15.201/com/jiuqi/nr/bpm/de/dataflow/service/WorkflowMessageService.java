/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.de.dataflow.service;

import com.jiuqi.nr.bpm.common.Task;
import java.util.Set;

public interface WorkflowMessageService {
    public void message(Task var1, Set<String> var2, String var3) throws Exception;
}

