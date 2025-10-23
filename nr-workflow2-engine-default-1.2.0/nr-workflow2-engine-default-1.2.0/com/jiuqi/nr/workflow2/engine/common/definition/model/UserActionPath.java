/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.workflow2.engine.core.process.defintion.IProcessStatus
 *  com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserAction
 *  com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserActionPath
 *  com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserTask
 */
package com.jiuqi.nr.workflow2.engine.common.definition.model;

import com.jiuqi.nr.workflow2.engine.core.process.defintion.IProcessStatus;
import com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserAction;
import com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserActionPath;
import com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserTask;

public class UserActionPath
implements IUserActionPath {
    private IUserAction action;
    private IUserTask destUserTask;
    private IProcessStatus destStatus;

    public UserActionPath(IUserAction action, IUserTask destUserTask, IProcessStatus destStatus) {
        this.action = action;
        this.destUserTask = destUserTask;
        this.destStatus = destStatus;
    }

    public IUserAction getUserAction() {
        return this.action;
    }

    public IUserTask getDestUserTask() {
        return this.destUserTask;
    }

    public IProcessStatus getDestStatus() {
        return this.destStatus;
    }
}

