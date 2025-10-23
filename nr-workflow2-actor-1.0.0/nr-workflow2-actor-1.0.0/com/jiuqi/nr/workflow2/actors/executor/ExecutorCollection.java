/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.workflow2.engine.core.actor.IActor
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObjectSet
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.BusinessObjectSet
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.RuntimeBusinessKey
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.RuntimeBusinessKeyCollection
 */
package com.jiuqi.nr.workflow2.actors.executor;

import com.jiuqi.nr.workflow2.actors.executor.ExecutorBase;
import com.jiuqi.nr.workflow2.engine.core.actor.IActor;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObjectSet;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.BusinessObjectSet;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.RuntimeBusinessKey;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.RuntimeBusinessKeyCollection;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ExecutorCollection
extends ExecutorBase {
    private MergeMode mergeMode;
    private Collection<ExecutorBase> executors;

    public ExecutorCollection(ExecutorBase firstExecutor, ExecutorBase SecondExecutor, MergeMode mergeMode) {
        this.executors = Arrays.asList(firstExecutor, SecondExecutor);
        this.mergeMode = mergeMode;
    }

    public boolean isMatch(IActor actor, RuntimeBusinessKey rtBusinessKey) {
        if (actor.getIdentityId() == null) {
            return false;
        }
        if (this.mergeMode == MergeMode.OVERLAP) {
            return this.executors.stream().filter(ExecutorBase::isActive).allMatch(o -> o.isMatch(actor, rtBusinessKey));
        }
        if (this.mergeMode == MergeMode.UNION) {
            return this.executors.stream().filter(ExecutorBase::isActive).anyMatch(o -> o.isMatch(actor, rtBusinessKey));
        }
        return false;
    }

    public Set<String> getMatchUsers(RuntimeBusinessKey rtBusinessKey) {
        Set<String> matchUsers = null;
        if (this.mergeMode == MergeMode.OVERLAP) {
            for (ExecutorBase executor : this.executors) {
                if (!executor.isActive()) continue;
                if (matchUsers == null) {
                    matchUsers = executor.getMatchUsers(rtBusinessKey);
                } else {
                    matchUsers.retainAll(executor.getMatchUsers(rtBusinessKey));
                }
                if (!matchUsers.isEmpty()) continue;
                break;
            }
            return matchUsers == null ? Collections.emptySet() : matchUsers;
        }
        if (this.mergeMode == MergeMode.UNION) {
            matchUsers = new HashSet<String>();
            for (ExecutorBase executor : this.executors) {
                if (!executor.isActive()) continue;
                matchUsers.addAll(executor.getMatchUsers(rtBusinessKey));
            }
            return matchUsers;
        }
        return Collections.emptySet();
    }

    public IBusinessObjectSet getMatchBusinessKeys(IActor actor, RuntimeBusinessKeyCollection businessKeys) {
        BusinessObjectSet canActBusinessObjectSet = null;
        if (this.mergeMode == MergeMode.OVERLAP) {
            for (ExecutorBase executor : this.executors) {
                if (!executor.isActive()) continue;
                IBusinessObjectSet set = executor.getMatchBusinessKeys(actor, businessKeys);
                if (set.isEmpty()) {
                    return new BusinessObjectSet();
                }
                if (canActBusinessObjectSet == null) {
                    canActBusinessObjectSet = set;
                    continue;
                }
                canActBusinessObjectSet.retainAll((Collection)set);
            }
            return canActBusinessObjectSet;
        }
        if (this.mergeMode == MergeMode.UNION) {
            canActBusinessObjectSet = new BusinessObjectSet();
            for (ExecutorBase executor : this.executors) {
                if (!executor.isActive()) continue;
                IBusinessObjectSet set = executor.getMatchBusinessKeys(actor, businessKeys);
                canActBusinessObjectSet.addAll((Collection)set);
            }
            return canActBusinessObjectSet;
        }
        return new BusinessObjectSet();
    }

    @Override
    boolean isActive() {
        return this.executors.stream().anyMatch(ExecutorBase::isActive);
    }

    @Override
    boolean isDillwithBusinessKey() {
        return this.executors.stream().anyMatch(ExecutorBase::isDillwithBusinessKey);
    }

    public static enum MergeMode {
        OVERLAP,
        UNION;

    }
}

