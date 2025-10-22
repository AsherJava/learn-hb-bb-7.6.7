/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.internal.runtime.controller;

import com.jiuqi.nr.definition.facade.TaskGroupDefine;
import java.util.List;

public interface IRuntimeTaskGroupService {
    public TaskGroupDefine queryTaskGroup(String var1);

    public List<TaskGroupDefine> getChildTaskGroups(String var1, boolean var2);

    public List<TaskGroupDefine> getAllTaskGroupDefines();

    public List<TaskGroupDefine> getAllGroupsFromTask(String var1);
}

