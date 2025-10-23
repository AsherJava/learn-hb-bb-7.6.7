/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.core.process.runtime;

import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKey;
import java.util.Calendar;

public interface IProcessInstance {
    public String getId();

    public IBusinessKey getBusinessKey();

    public String getProcessEngineId();

    public String getProcessDefinitionId();

    public String getCurrentUserTask();

    public Calendar getStartTime();

    public String getStartUser();

    public Calendar getLastOperateTime();
}

