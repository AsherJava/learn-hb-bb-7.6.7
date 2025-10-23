/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.core;

import com.jiuqi.nr.workflow2.engine.core.process.defintion.IProcessDefinition;
import com.jiuqi.nr.workflow2.engine.core.process.defintion.IProcessStatus;
import com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserAction;
import com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserActionEvent;
import com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserTask;
import java.util.List;

public interface IProcessDefinitionService {
    public IProcessDefinition queryProcess(String var1);

    public List<IUserTask> queryAllUserTasks(String var1);

    public IUserTask queryUserTask(String var1, String var2);

    public IUserTask getUserTaskOrDefault(String var1, String var2);

    public List<IUserAction> queryAllActions(String var1, String var2);

    public IUserAction queryAction(String var1, String var2, String var3);

    public IUserAction getActionOrDefault(String var1, String var2, String var3);

    public List<IUserActionEvent> queryAllPreviousEvents(String var1, String var2, String var3);

    public List<IUserActionEvent> queryAllPostEvents(String var1, String var2, String var3);

    public List<IProcessStatus> queryAllStatus(String var1);

    public List<IProcessStatus> queryUserTaskStatus(String var1, String var2);

    public IProcessStatus queryStatus(String var1, String var2);
}

