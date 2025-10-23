/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.core.process.defintion;

import com.jiuqi.nr.workflow2.engine.core.process.defintion.IProcessStatus;
import com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserTask;
import java.util.List;

public interface IProcessDefinition {
    public String getId();

    public String getTitle();

    public String getDescription();

    public String getProcessEngineId();

    public List<IUserTask> getUserTasks();

    public List<IProcessStatus> getStatus();
}

