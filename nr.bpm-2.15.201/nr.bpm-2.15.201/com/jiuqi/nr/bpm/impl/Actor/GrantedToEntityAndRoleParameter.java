/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.impl.Actor;

import com.jiuqi.nr.bpm.Actor.ActorStrategyParameter;
import java.util.Set;

public class GrantedToEntityAndRoleParameter
implements ActorStrategyParameter {
    private static final long serialVersionUID = 7060398752713703526L;
    private Set<String> roleIdSet;

    public Set<String> getRoleIdSet() {
        return this.roleIdSet;
    }

    public void setRoleIdSet(Set<String> roleIdSet) {
        this.roleIdSet = roleIdSet;
    }
}

