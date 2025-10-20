/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.internal.runtime.controller;

import com.jiuqi.nr.definition.facade.TaskLinkDefine;
import java.util.List;

public interface IRuntimeTaskLinkService {
    public List<TaskLinkDefine> queryTaskLink(String var1);

    public TaskLinkDefine queryTaskLinkByCurrentFormSchemeAndNumber(String var1, String var2);

    public TaskLinkDefine queryTaskLinkByKey(String var1);
}

