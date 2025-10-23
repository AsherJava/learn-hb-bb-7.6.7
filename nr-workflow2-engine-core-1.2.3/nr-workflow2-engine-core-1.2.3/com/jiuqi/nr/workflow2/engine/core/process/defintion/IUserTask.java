/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.core.process.defintion;

import com.jiuqi.nr.workflow2.engine.core.process.defintion.IActorStrategy;
import com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserActionPath;
import java.util.List;

public interface IUserTask {
    public static final String START_TASK_CODE = "tsk_start";
    public static final String END_TASK_CODE = "tsk_end";

    public String getCode();

    public String getTitle();

    public String getAlias();

    public List<IUserActionPath> getActionPaths();

    public List<IActorStrategy> getActionExecutors();

    public boolean enableSendTodo();

    public List<IActorStrategy> getTodoReceivers();
}

