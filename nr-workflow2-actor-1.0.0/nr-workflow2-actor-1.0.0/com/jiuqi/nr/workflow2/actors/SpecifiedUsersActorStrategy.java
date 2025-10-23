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
import com.jiuqi.nr.workflow2.actors.executor.AuthorityEexcutor;
import com.jiuqi.nr.workflow2.actors.executor.ExecutorCollection;
import com.jiuqi.nr.workflow2.actors.executor.GivenRoleExecutor;
import com.jiuqi.nr.workflow2.actors.executor.GivenUserExecutor;
import com.jiuqi.nr.workflow2.actors.settings.SpecifiedUserSettings;
import com.jiuqi.nr.workflow2.engine.core.actor.IActorStrategyExecutor;
import com.jiuqi.nr.workflow2.engine.core.actor.IActorStrategyExecutorFactory;

public class SpecifiedUsersActorStrategy
implements IActorStrategyExecutorFactory {
    public static final String ID = "jiuqi.nr.givenuser_givenrole";
    public static final String TITLE = "\u6307\u5b9a\u7684\u7528\u6237\u6216\u89d2\u8272";
    public static final short ORDER = 16;
    private final RoleService roleService;
    private AuthorityEexcutor authExecutor;

    public void setAuthorityEexcutor(AuthorityEexcutor authExecutor) {
        this.authExecutor = authExecutor;
    }

    public SpecifiedUsersActorStrategy(RoleService roleService) {
        this.roleService = roleService;
    }

    public String getActorStrategyDefinitionId() {
        return ID;
    }

    public IActorStrategyExecutor createExecutor(String settings) {
        SpecifiedUserSettings strategySettings = SpecifiedUserSettings.parseFromString(settings);
        GivenRoleExecutor givenRoleExecutor = new GivenRoleExecutor();
        givenRoleExecutor.setRoleService(this.roleService);
        givenRoleExecutor.setRoleIds(strategySettings.getRole());
        GivenUserExecutor givenUserExecutor = new GivenUserExecutor();
        givenUserExecutor.setUserIds(strategySettings.getUser());
        ExecutorCollection userOrRoleExecutor = new ExecutorCollection(givenUserExecutor, givenRoleExecutor, ExecutorCollection.MergeMode.UNION);
        if (this.authExecutor == null) {
            return userOrRoleExecutor;
        }
        return new ExecutorCollection(userOrRoleExecutor, this.authExecutor, ExecutorCollection.MergeMode.OVERLAP);
    }
}

