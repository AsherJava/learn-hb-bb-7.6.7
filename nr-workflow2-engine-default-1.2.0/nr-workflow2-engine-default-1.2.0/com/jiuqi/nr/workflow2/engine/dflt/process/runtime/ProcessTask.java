/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserAction
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IProcessTask
 */
package com.jiuqi.nr.workflow2.engine.dflt.process.runtime;

import com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserAction;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IProcessTask;
import java.util.List;

public class ProcessTask
implements IProcessTask {
    private final String taskId;
    private final List<IUserAction> actions;

    public ProcessTask(String taskId, List<IUserAction> actions) {
        this.taskId = taskId;
        this.actions = actions;
    }

    public String getId() {
        return this.taskId;
    }

    public List<IUserAction> getActions() {
        return this.actions;
    }
}

