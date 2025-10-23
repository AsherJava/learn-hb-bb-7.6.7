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
import com.jiuqi.nr.workflow2.actors.settings.SpecifiedUserSettings;
import com.jiuqi.nr.workflow2.engine.core.actor.IActorStrategyExecutor;
import com.jiuqi.nr.workflow2.engine.core.actor.IActorStrategyExecutorFactory;

public class CanAuditAndGivenRoleActorStrategy
implements IActorStrategyExecutorFactory {
    public static final String ID = "jiuqi.nr.canaudit_givenrole";
    public static final String TITLE = "\u6709\u5ba1\u6279\u6743\u9650\u4e14\u5c5e\u4e8e\u6307\u5b9a\u89d2\u8272\u7684\u4eba";
    public static final short ORDER = 6;
    private final RoleService roleService;
    private final AuthorityEexcutor authorityExecutor;

    public CanAuditAndGivenRoleActorStrategy(AuthorityEexcutor authorityExecutor, RoleService roleService) {
        this.roleService = roleService;
        this.authorityExecutor = authorityExecutor;
    }

    public String getActorStrategyDefinitionId() {
        return ID;
    }

    public IActorStrategyExecutor createExecutor(String settings) {
        GivenRoleExecutor givenRoleExecutor = new GivenRoleExecutor();
        givenRoleExecutor.setRoleService(this.roleService);
        givenRoleExecutor.setRoleIds(SpecifiedUserSettings.parseFromString(settings).getRole());
        return new ExecutorCollection(givenRoleExecutor, this.authorityExecutor, ExecutorCollection.MergeMode.OVERLAP);
    }
}

