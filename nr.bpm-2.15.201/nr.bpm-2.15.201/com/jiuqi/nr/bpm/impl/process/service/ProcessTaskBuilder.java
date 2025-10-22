/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 */
package com.jiuqi.nr.bpm.impl.process.service;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.bpm.businesskey.BusinessKey;
import com.jiuqi.nr.bpm.common.Task;
import com.jiuqi.nr.bpm.common.UserTask;
import com.jiuqi.nr.bpm.impl.process.consts.ProcessType;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import java.util.List;
import java.util.Optional;

public interface ProcessTaskBuilder {
    public List<Task> queryTaskByBusinessKey(BusinessKey var1);

    public List<Task> queryTasks(DimensionValueSet var1, String var2, FormSchemeDefine var3);

    public Optional<UserTask> queryUserTask(String var1, String var2);

    public Optional<Task> queryTaskById(String var1, BusinessKey var2);

    public ProcessType getProcessType();

    public String nextUserTaskId(String var1, String var2, String var3);

    public boolean canStartProcess(String var1);
}

