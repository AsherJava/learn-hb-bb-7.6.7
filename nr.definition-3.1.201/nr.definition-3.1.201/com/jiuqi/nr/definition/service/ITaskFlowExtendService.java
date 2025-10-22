/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.service;

import com.jiuqi.nr.definition.facade.TaskFlowsDefine;
import com.jiuqi.nr.definition.internal.impl.FillDateType;
import com.jiuqi.nr.definition.internal.impl.FillInAutomaticallyDue;

public interface ITaskFlowExtendService {
    public TaskFlowsDefine getFlowsSetting(String var1, TaskFlowsDefine var2);

    public FillInAutomaticallyDue getFillInAutomaticallyDue(String var1, FillInAutomaticallyDue var2);

    public FillDateType getFillingDateType(String var1, FillDateType var2);

    public int getFillingDateDays(String var1, int var2);
}

