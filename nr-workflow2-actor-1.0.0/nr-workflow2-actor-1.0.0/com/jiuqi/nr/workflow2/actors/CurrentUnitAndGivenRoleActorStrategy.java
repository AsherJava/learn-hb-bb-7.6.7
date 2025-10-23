/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.authz2.service.RoleService
 *  com.jiuqi.nr.workflow2.engine.core.actor.IActorStrategyExecutor
 *  com.jiuqi.nr.workflow2.engine.core.actor.IActorStrategyExecutorFactory
 */
package com.jiuqi.nr.workflow2.actors;

import com.jiuqi.np.authz2.service.RoleService;
import com.jiuqi.nr.workflow2.actors.executor.CurrentUnitEexcutor;
import com.jiuqi.nr.workflow2.actors.executor.ExecutorCollection;
import com.jiuqi.nr.workflow2.actors.executor.GivenRoleExecutor;
import com.jiuqi.nr.workflow2.actors.settings.SpecifiedUserSettings;
import com.jiuqi.nr.workflow2.engine.core.actor.IActorStrategyExecutor;
import com.jiuqi.nr.workflow2.engine.core.actor.IActorStrategyExecutorFactory;

public class CurrentUnitAndGivenRoleActorStrategy
implements IActorStrategyExecutorFactory {
    public static final String ID = "jiuqi.nr.curunit_givenrole";
    public static final String TITLE = "\u5f53\u524d\u5355\u4f4d\u4e0b\u6307\u5b9a\u89d2\u8272\u7684\u7528\u6237";
    public static final short ORDER = 10;
    private final RoleService roleService;
    private final CurrentUnitEexcutor currentUnitEexcutor;

    public CurrentUnitAndGivenRoleActorStrategy(RoleService roleService, CurrentUnitEexcutor currentUnitEexcutor) {
        this.roleService = roleService;
        this.currentUnitEexcutor = currentUnitEexcutor;
    }

    public String getActorStrategyDefinitionId() {
        return ID;
    }

    public IActorStrategyExecutor createExecutor(String settings) {
        GivenRoleExecutor givenRoleExecutor = new GivenRoleExecutor();
        givenRoleExecutor.setRoleService(this.roleService);
        givenRoleExecutor.setRoleIds(SpecifiedUserSettings.parseFromString(settings).getRole());
        return new ExecutorCollection(givenRoleExecutor, this.currentUnitEexcutor, ExecutorCollection.MergeMode.OVERLAP);
    }
}

