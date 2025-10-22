/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.impl.Actor;

import com.jiuqi.nr.bpm.Actor.ActorStrategyParameter;
import java.util.Set;

public class SpecifiedUsersParameter
implements ActorStrategyParameter {
    private static final long serialVersionUID = 4806819257914685706L;
    private Set<String> userIdSet;

    public Set<String> getUserIdSet() {
        return this.userIdSet;
    }

    public void setUserIdSet(Set<String> userIdSet) {
        this.userIdSet = userIdSet;
    }
}

