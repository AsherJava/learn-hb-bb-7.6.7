/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserAction
 */
package com.jiuqi.nr.workflow2.service.result;

import com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserAction;
import com.jiuqi.nr.workflow2.service.enumeration.ProcessExecuteStatus;

public interface IProcessExecuteResult {
    public String getExecuteMessage();

    public ProcessExecuteStatus getExecuteStatus();

    public IUserAction getUserAction();
}

