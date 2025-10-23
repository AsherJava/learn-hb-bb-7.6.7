/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.workflow2.engine.core.actor.IActor
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObjectSet
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.BusinessObjectSet
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.RuntimeBusinessKey
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.RuntimeBusinessKeyCollection
 */
package com.jiuqi.nr.workflow2.actors.executor;

import com.jiuqi.nr.workflow2.actors.executor.ExecutorBase;
import com.jiuqi.nr.workflow2.engine.core.actor.IActor;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObjectSet;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.BusinessObjectSet;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.RuntimeBusinessKey;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.RuntimeBusinessKeyCollection;
import java.util.Collections;
import java.util.Set;

public class GivenUserExecutor
extends ExecutorBase {
    private Set<String> userIds;

    public void setUserIds(Set<String> userIds) {
        this.userIds = userIds;
    }

    @Override
    public boolean isActive() {
        return this.userIds != null && !this.userIds.isEmpty();
    }

    public boolean isMatch(IActor actor, RuntimeBusinessKey rtBusinessKey) {
        if (actor.getIdentityId() == null) {
            return false;
        }
        return !this.isActive() || this.userIds.contains(actor.getUserId());
    }

    public Set<String> getMatchUsers(RuntimeBusinessKey rtBusinessKey) {
        if (this.isActive()) {
            return this.userIds;
        }
        return Collections.emptySet();
    }

    public IBusinessObjectSet getMatchBusinessKeys(IActor actor, RuntimeBusinessKeyCollection businessKeys) {
        BusinessObjectSet set = new BusinessObjectSet();
        if (!this.isActive() || this.userIds.contains(actor.getUserId())) {
            for (IBusinessObject businessObject : businessKeys.getBusinessKeys().getBusinessObjects()) {
                set.add((Object)businessObject);
            }
        }
        return set;
    }

    @Override
    boolean isDillwithBusinessKey() {
        return false;
    }
}

