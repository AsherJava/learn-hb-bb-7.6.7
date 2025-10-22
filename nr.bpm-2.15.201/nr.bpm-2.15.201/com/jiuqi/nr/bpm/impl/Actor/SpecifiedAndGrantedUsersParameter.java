/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.impl.Actor;

import com.jiuqi.nr.bpm.Actor.ActorStrategyParameter;
import java.util.Set;

public class SpecifiedAndGrantedUsersParameter
implements ActorStrategyParameter {
    private static final long serialVersionUID = 1L;
    private Set<String> userIdSet;
    private Set<String> roleIdSet;

    public Set<String> getUserIdSet() {
        return this.userIdSet;
    }

    public void setUserIdSet(Set<String> userIdSet) {
        this.userIdSet = userIdSet;
    }

    public Set<String> getRoleIdSet() {
        return this.roleIdSet;
    }

    public void setRoleIdSet(Set<String> roleIdSet) {
        this.roleIdSet = roleIdSet;
    }
}

