/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.user.User
 *  com.jiuqi.np.user.service.SystemUserService
 *  com.jiuqi.nr.workflow2.engine.core.actor.IActor
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObjectSet
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.BusinessObjectSet
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.RuntimeBusinessKey
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.RuntimeBusinessKeyCollection
 */
package com.jiuqi.nr.workflow2.actors.executor;

import com.jiuqi.np.user.User;
import com.jiuqi.np.user.service.SystemUserService;
import com.jiuqi.nr.workflow2.actors.executor.ExecutorBase;
import com.jiuqi.nr.workflow2.engine.core.actor.IActor;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObjectSet;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.BusinessObjectSet;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.RuntimeBusinessKey;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.RuntimeBusinessKeyCollection;
import java.util.Set;
import java.util.stream.Collectors;

public class SystemUserExecutor
extends ExecutorBase {
    private final SystemUserService systemUserService;

    public SystemUserExecutor(SystemUserService systemUserService) {
        this.systemUserService = systemUserService;
    }

    public boolean isMatch(IActor actor, RuntimeBusinessKey businessKey) {
        return this.systemUserService.get(actor.getUserId()) != null;
    }

    public Set<String> getMatchUsers(RuntimeBusinessKey businessKey) {
        return this.systemUserService.getAllUsers().stream().map(User::getId).collect(Collectors.toSet());
    }

    public IBusinessObjectSet getMatchBusinessKeys(IActor actor, RuntimeBusinessKeyCollection businessKeys) {
        if (this.systemUserService.get(actor.getUserId()) != null) {
            BusinessObjectSet matchedBusinessObjects = new BusinessObjectSet();
            for (IBusinessObject businessObject : businessKeys.getBusinessKeys().getBusinessObjects()) {
                matchedBusinessObjects.add((Object)businessObject);
            }
            return matchedBusinessObjects;
        }
        return new BusinessObjectSet();
    }

    @Override
    boolean isActive() {
        return true;
    }

    @Override
    boolean isDillwithBusinessKey() {
        return false;
    }
}

