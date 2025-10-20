/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.intf.autotask;

import com.jiuqi.va.biz.intf.autotask.AutoTask;
import com.jiuqi.va.biz.intf.value.NamedContainer;
import java.util.List;

public interface AutoTaskManager
extends NamedContainer<AutoTask> {
    public List<AutoTask> getAutoTaskList(String var1);
}

