/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.impl.autotask;

import com.jiuqi.va.biz.impl.value.NamedManagerImpl;
import com.jiuqi.va.biz.intf.autotask.AutoTask;
import com.jiuqi.va.biz.intf.autotask.AutoTaskManager;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class AutoTaskManagerImpl
extends NamedManagerImpl<AutoTask>
implements AutoTaskManager {
    @Override
    public List<AutoTask> getAutoTaskList(String autoTaskModule) {
        return this.stream().filter(o -> o.getAutoTaskModule().equalsIgnoreCase(autoTaskModule)).collect(Collectors.toList());
    }
}

