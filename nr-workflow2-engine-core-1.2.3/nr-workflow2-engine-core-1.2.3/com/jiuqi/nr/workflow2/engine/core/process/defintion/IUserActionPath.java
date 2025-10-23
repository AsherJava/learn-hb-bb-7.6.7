/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.core.process.defintion;

import com.jiuqi.nr.workflow2.engine.core.process.defintion.IProcessStatus;
import com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserAction;
import com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserTask;

public interface IUserActionPath {
    public IUserAction getUserAction();

    public IUserTask getDestUserTask();

    public IProcessStatus getDestStatus();
}

