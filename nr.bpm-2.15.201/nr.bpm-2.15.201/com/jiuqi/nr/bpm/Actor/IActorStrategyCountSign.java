/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.Actor;

import com.jiuqi.nr.bpm.Actor.Actor;
import com.jiuqi.nr.bpm.businesskey.BusinessKeyInfo;
import com.jiuqi.nr.bpm.common.Task;
import com.jiuqi.nr.bpm.common.UserTask;
import com.jiuqi.nr.bpm.impl.Actor.GrantedToEntityAndRoleParameter;
import java.util.Set;

public interface IActorStrategyCountSign {
    default public String getActorStrategyType() {
        return this.getClass().getName();
    }

    public Set<String> getCountSignGroupNum(BusinessKeyInfo var1, GrantedToEntityAndRoleParameter var2, UserTask var3);

    public boolean isGroupActor(BusinessKeyInfo var1, Actor var2, Task var3, String var4);
}

