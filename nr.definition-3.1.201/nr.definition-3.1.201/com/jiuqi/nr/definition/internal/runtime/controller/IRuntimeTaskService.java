/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.internal.runtime.controller;

import com.jiuqi.nr.definition.common.TaskType;
import com.jiuqi.nr.definition.facade.TaskDefine;
import java.util.List;
import java.util.stream.Collectors;

public interface IRuntimeTaskService {
    public List<TaskDefine> getAllTaskDefines();

    default public List<TaskDefine> getAllTaskDefinesByType(TaskType type) {
        return this.getAllTaskDefines().stream().filter(t -> type == t.getTaskType()).collect(Collectors.toList());
    }

    public TaskDefine queryTaskDefine(String var1);

    public TaskDefine queryTaskDefineByCode(String var1);

    public TaskDefine queryTaskDefineByFilePrefix(String var1);

    public List<TaskDefine> queryTaskDefinesInGroup(String var1);

    public List<TaskDefine> queryTaskDefinesByDataScheme(String var1);

    public List<TaskDefine> queryTaskDefinesByCaliber(String var1);

    public void refreshTaskCache();
}

