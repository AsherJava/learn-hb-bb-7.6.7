/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.impl.countersign.group;

import com.jiuqi.nr.bpm.Actor.Actor;
import com.jiuqi.nr.bpm.Actor.ActorStrategyProvider;
import com.jiuqi.nr.bpm.businesskey.BusinessKey;
import com.jiuqi.nr.bpm.common.Task;
import com.jiuqi.nr.bpm.common.UserTask;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowNodeSet;
import java.util.Set;

public interface IQueryGroupCount {
    public Set<String> getActors(WorkFlowNodeSet var1, UserTask var2, BusinessKey var3);

    public boolean isGroupActor(UserTask var1, BusinessKey var2, Actor var3, ActorStrategyProvider var4, Task var5, String var6);
}

