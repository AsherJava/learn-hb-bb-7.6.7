/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserAction
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IProcessTask
 */
package com.jiuqi.nr.workflow2.form.reject.ext.engine;

import com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserAction;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IProcessTask;
import java.util.ArrayList;
import java.util.List;

public class FormRejectProcessTask
implements IProcessTask {
    public static final String taskId = "com.jiuqi.nr.workflow2.form.reject.ext.engine.FormRejectProcessTask";
    private final List<IUserAction> actions = new ArrayList<IUserAction>();

    public String getId() {
        return taskId;
    }

    public List<IUserAction> getActions() {
        return this.actions;
    }

    public void appendAction(IUserAction action) {
        this.actions.add(action);
    }
}

