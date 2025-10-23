/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKey
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IProcessInstance
 */
package com.jiuqi.nr.workflow2.engine.dflt.process.runtime;

import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKey;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IProcessInstance;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.repository.ProcessInstanceDO;
import java.util.Calendar;

public class ProcessInstance
implements IProcessInstance {
    private final ProcessInstanceDO instance;

    public ProcessInstance(ProcessInstanceDO instance) {
        this.instance = instance;
    }

    public String getProcessDefinitionId() {
        return this.instance.getProcessDefinitionId();
    }

    public String getId() {
        return this.instance.getId();
    }

    public IBusinessKey getBusinessKey() {
        return this.instance.getBusinessKey();
    }

    public String getCurrentUserTask() {
        return this.instance.getCurNode();
    }

    public Calendar getStartTime() {
        return this.instance.getStartTime();
    }

    public Calendar getLastOperateTime() {
        return this.instance.getUpdateTime();
    }

    public String getProcessEngineId() {
        return "jiuqi.nr.default";
    }

    public String getStartUser() {
        return this.instance.getStartUser();
    }
}

