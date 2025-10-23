/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.authz2.service.IdentityService
 *  com.jiuqi.np.authz2.service.OrgIdentityService
 *  com.jiuqi.np.authz2.service.RoleService
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.user.service.SystemUserService
 *  com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider
 *  com.jiuqi.nr.entity.service.IEntityAuthorityService
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.workflow2.engine.core.actor.ActorStrategyRegisteration
 *  com.jiuqi.nr.workflow2.engine.core.actor.IActorStrategyExecutorFactory
 */
package com.jiuqi.nr.workflow2.actor.spring;

import com.jiuqi.np.authz2.service.IdentityService;
import com.jiuqi.np.authz2.service.OrgIdentityService;
import com.jiuqi.np.authz2.service.RoleService;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.user.service.SystemUserService;
import com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider;
import com.jiuqi.nr.entity.service.IEntityAuthorityService;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.workflow2.actors.CanAuditActorStrategy;
import com.jiuqi.nr.workflow2.actors.CanAuditAndGivenRoleActorStrategy;
import com.jiuqi.nr.workflow2.actors.CanSubmitActorStrategy;
import com.jiuqi.nr.workflow2.actors.CanSubmitAndGivenRoleActorStrategy;
import com.jiuqi.nr.workflow2.actors.CanUploadActorStrategy;
import com.jiuqi.nr.workflow2.actors.CanUploadAndGivenRoleActorStrategy;
import com.jiuqi.nr.workflow2.actors.CurrentUnitActorStrategy;
import com.jiuqi.nr.workflow2.actors.CurrentUnitAndGivenRoleActorStrategy;
import com.jiuqi.nr.workflow2.actors.CurrentUnitCanSubmitActorStrategy;
import com.jiuqi.nr.workflow2.actors.CurrentUnitCanUploadActorStrategy;
import com.jiuqi.nr.workflow2.actors.ParentUnitActorStrategy;
import com.jiuqi.nr.workflow2.actors.ParentUnitAndGivenRoleActorStrategy;
import com.jiuqi.nr.workflow2.actors.ParentUnitCanAuditActorStrategy;
import com.jiuqi.nr.workflow2.actors.ParentsUnitActorStrategy;
import com.jiuqi.nr.workflow2.actors.ParentsUnitAndGivenRoleActorStrategy;
import com.jiuqi.nr.workflow2.actors.SpecifiedUsersActorStrategy;
import com.jiuqi.nr.workflow2.actors.SystemUserActorStrategy;
import com.jiuqi.nr.workflow2.actors.executor.AuthorityEexcutor;
import com.jiuqi.nr.workflow2.actors.executor.CurrentUnitEexcutor;
import com.jiuqi.nr.workflow2.engine.core.actor.ActorStrategyRegisteration;
import com.jiuqi.nr.workflow2.engine.core.actor.IActorStrategyExecutorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class ActorAutoConfiguration {
    @Autowired
    private IPeriodEntityAdapter periodAdapter;
    @Autowired
    private IEntityAuthorityService entityAuthorityService;
    @Autowired
    private DefinitionAuthorityProvider definitionAuthorityProvider;
    @Autowired
    private RoleService roleService;
    @Autowired
    private SystemUserService systemUserService;
    @Autowired
    private OrgIdentityService orgIdentityService;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionController;
    @Autowired
    private IEntityDataService entityDataService;
    @Autowired
    private IEntityViewRunTimeController entityViewController;
    @Autowired
    private IdentityService identityService;
    @Autowired
    private IEntityMetaService entityMetaService;
    private AuthorityEexcutor canSubmitExecutor;
    private AuthorityEexcutor canReportExecutor;
    private AuthorityEexcutor canAuditExecutor;
    private CurrentUnitEexcutor currentUnitEexcutor;
    private CurrentUnitEexcutor parentUnitEexcutor;
    private CurrentUnitEexcutor parentsUnitEexcutor;

    private AuthorityEexcutor getCansubmitExecutor() {
        if (this.canSubmitExecutor == null) {
            this.canSubmitExecutor = new AuthorityEexcutor();
            this.canSubmitExecutor.setDefinitionAuthorityProvider(this.definitionAuthorityProvider);
            this.canSubmitExecutor.setEntityAuthorityService(this.entityAuthorityService);
            this.canSubmitExecutor.setPeriodEntityAdapter(this.periodAdapter);
            this.canSubmitExecutor.setAuthority(AuthorityEexcutor.AuthorityItem.SUBMIT);
        }
        return this.canSubmitExecutor;
    }

    private AuthorityEexcutor getCanReportExecutor() {
        if (this.canReportExecutor == null) {
            this.canReportExecutor = new AuthorityEexcutor();
            this.canReportExecutor.setDefinitionAuthorityProvider(this.definitionAuthorityProvider);
            this.canReportExecutor.setEntityAuthorityService(this.entityAuthorityService);
            this.canReportExecutor.setPeriodEntityAdapter(this.periodAdapter);
            this.canReportExecutor.setAuthority(AuthorityEexcutor.AuthorityItem.REPORT);
        }
        return this.canReportExecutor;
    }

    private AuthorityEexcutor getCanAuditExecutor() {
        if (this.canAuditExecutor == null) {
            this.canAuditExecutor = new AuthorityEexcutor();
            this.canAuditExecutor.setDefinitionAuthorityProvider(this.definitionAuthorityProvider);
            this.canAuditExecutor.setEntityAuthorityService(this.entityAuthorityService);
            this.canAuditExecutor.setPeriodEntityAdapter(this.periodAdapter);
            this.canAuditExecutor.setAuthority(AuthorityEexcutor.AuthorityItem.AUDIT);
        }
        return this.canAuditExecutor;
    }

    private CurrentUnitEexcutor getCurrentUnitEexcutor() {
        if (this.currentUnitEexcutor == null) {
            this.currentUnitEexcutor = new CurrentUnitEexcutor();
            this.currentUnitEexcutor.setUnitLevel(CurrentUnitEexcutor.UnitLevel.SELF);
            this.currentUnitEexcutor.setOrgIdentityService(this.orgIdentityService);
            this.currentUnitEexcutor.setIdentityService(this.identityService);
            this.currentUnitEexcutor.setEntityMetaService(this.entityMetaService);
        }
        return this.currentUnitEexcutor;
    }

    private CurrentUnitEexcutor getParentUnitEexcutor() {
        if (this.parentUnitEexcutor == null) {
            this.parentUnitEexcutor = new CurrentUnitEexcutor();
            this.parentUnitEexcutor.setUnitLevel(CurrentUnitEexcutor.UnitLevel.PARENT);
            this.parentUnitEexcutor.setOrgIdentityService(this.orgIdentityService);
            this.parentUnitEexcutor.setDataDefinitionController(this.dataDefinitionController);
            this.parentUnitEexcutor.setEntityViewController(this.entityViewController);
            this.parentUnitEexcutor.setEntityDataService(this.entityDataService);
            this.parentUnitEexcutor.setIdentityService(this.identityService);
            this.parentUnitEexcutor.setEntityMetaService(this.entityMetaService);
        }
        return this.parentUnitEexcutor;
    }

    private CurrentUnitEexcutor getParentsUnitEexcutor() {
        if (this.parentsUnitEexcutor == null) {
            this.parentsUnitEexcutor = new CurrentUnitEexcutor();
            this.parentsUnitEexcutor.setUnitLevel(CurrentUnitEexcutor.UnitLevel.PARENTS);
            this.parentsUnitEexcutor.setOrgIdentityService(this.orgIdentityService);
            this.parentsUnitEexcutor.setDataDefinitionController(this.dataDefinitionController);
            this.parentsUnitEexcutor.setEntityViewController(this.entityViewController);
            this.parentsUnitEexcutor.setEntityDataService(this.entityDataService);
            this.parentsUnitEexcutor.setIdentityService(this.identityService);
            this.parentsUnitEexcutor.setEntityMetaService(this.entityMetaService);
        }
        return this.parentsUnitEexcutor;
    }

    @Bean(value={"com.jiuqi.nr.workflow2.actor.CanAuditActorStrategy"})
    public ActorStrategyRegisteration getCanAuditActorStrategy() {
        ActorStrategyRegisteration registeration = new ActorStrategyRegisteration("jiuqi.nr.canaudit", "\u6709\u5ba1\u6279\u6743\u9650\u7684\u4eba");
        registeration.order((short)3);
        registeration.executorFactory((IActorStrategyExecutorFactory)new CanAuditActorStrategy(this.getCanAuditExecutor()));
        return registeration;
    }

    @Bean(value={"com.jiuqi.nr.workflow2.actor.CanAuditAndGivenRoleActorStrategy"})
    public ActorStrategyRegisteration getCanAuditAndGivenRoleActorStrategy() {
        ActorStrategyRegisteration registeration = new ActorStrategyRegisteration("jiuqi.nr.canaudit_givenrole", "\u6709\u5ba1\u6279\u6743\u9650\u4e14\u5c5e\u4e8e\u6307\u5b9a\u89d2\u8272\u7684\u4eba");
        registeration.order((short)6);
        registeration.executorFactory((IActorStrategyExecutorFactory)new CanAuditAndGivenRoleActorStrategy(this.getCanAuditExecutor(), this.roleService));
        return registeration;
    }

    @Bean(value={"com.jiuqi.nr.workflow2.actor.CanSubmitActorStrategy"})
    public ActorStrategyRegisteration getCanSubmitActorStrategy() {
        ActorStrategyRegisteration registeration = new ActorStrategyRegisteration("jiuqi.nr.cansubmit", "\u6709\u9001\u5ba1\u6743\u9650\u7684\u4eba");
        registeration.order((short)1);
        registeration.executorFactory((IActorStrategyExecutorFactory)new CanSubmitActorStrategy(this.getCansubmitExecutor()));
        return registeration;
    }

    @Bean(value={"com.jiuqi.nr.workflow2.actor.CanSubmitAndGivenRoleActorStrategy"})
    public ActorStrategyRegisteration getCanSubmitAndGivenRoleActorStrategy() {
        ActorStrategyRegisteration registeration = new ActorStrategyRegisteration("jiuqi.nr.cansubmit_givenrole", "\u6709\u9001\u5ba1\u6743\u9650\u4e14\u5c5e\u4e8e\u6307\u5b9a\u89d2\u8272\u7684\u4eba");
        registeration.order((short)4);
        registeration.executorFactory((IActorStrategyExecutorFactory)new CanSubmitAndGivenRoleActorStrategy(this.getCansubmitExecutor(), this.roleService));
        return registeration;
    }

    @Bean(value={"com.jiuqi.nr.workflow2.actor.CanUploadActorStrategy"})
    public ActorStrategyRegisteration getCanUploadActorStrategy() {
        ActorStrategyRegisteration registeration = new ActorStrategyRegisteration("jiuqi.nr.canreport", "\u6709\u4e0a\u62a5\u6743\u9650\u7684\u4eba");
        registeration.order((short)2);
        registeration.executorFactory((IActorStrategyExecutorFactory)new CanUploadActorStrategy(this.getCanReportExecutor()));
        return registeration;
    }

    @Bean(value={"com.jiuqi.nr.workflow2.actor.CanUploadAndGivenRoleActorStrategy"})
    public ActorStrategyRegisteration getCanUploadAndGivenRoleActorStrategy() {
        ActorStrategyRegisteration registeration = new ActorStrategyRegisteration("jiuqi.nr.canreport_givenrole", "\u6709\u4e0a\u62a5\u6743\u9650\u4e14\u5c5e\u4e8e\u6307\u5b9a\u89d2\u8272\u7684\u4eba");
        registeration.order((short)5);
        registeration.executorFactory((IActorStrategyExecutorFactory)new CanUploadAndGivenRoleActorStrategy(this.getCanReportExecutor(), this.roleService));
        return registeration;
    }

    @Bean(value={"com.jiuqi.nr.workflow2.actor.CurrentUnitActorStrategy"})
    public ActorStrategyRegisteration getCurrentUnitActorStrategy() {
        ActorStrategyRegisteration registeration = new ActorStrategyRegisteration("jiuqi.nr.curunit", "\u5f53\u524d\u5355\u4f4d\u4e0b\u7684\u7528\u6237");
        registeration.order((short)7);
        registeration.executorFactory((IActorStrategyExecutorFactory)new CurrentUnitActorStrategy(this.getCurrentUnitEexcutor()));
        return registeration;
    }

    @Bean(value={"com.jiuqi.nr.workflow2.actor.CurrentUnitCanSubmitActorStrategy"})
    public ActorStrategyRegisteration getCurrentUnitCanSubmitActorStrategy() {
        ActorStrategyRegisteration registeration = new ActorStrategyRegisteration("jiuqi.nr.curunit_cansubmit", "\u5f53\u524d\u5355\u4f4d\u4e0b\u6709\u9001\u5ba1\u6743\u9650\u7684\u4eba");
        registeration.order((short)8);
        registeration.executorFactory((IActorStrategyExecutorFactory)new CurrentUnitCanSubmitActorStrategy(this.getCurrentUnitEexcutor(), this.getCansubmitExecutor()));
        return registeration;
    }

    @Bean(value={"com.jiuqi.nr.workflow2.actor.CurrentUnitCanUploadActorStrategy"})
    public ActorStrategyRegisteration getCurrentUnitCanReportActorStrategy() {
        ActorStrategyRegisteration registeration = new ActorStrategyRegisteration("jiuqi.nr.curunit_canreport", "\u5f53\u524d\u5355\u4f4d\u4e0b\u6709\u4e0a\u62a5\u6743\u9650\u7684\u4eba");
        registeration.order((short)9);
        registeration.executorFactory((IActorStrategyExecutorFactory)new CurrentUnitCanUploadActorStrategy(this.getCurrentUnitEexcutor(), this.getCanReportExecutor()));
        return registeration;
    }

    @Bean(value={"com.jiuqi.nr.workflow2.actor.CurrentUnitAndGivenRoleActorStrategy"})
    public ActorStrategyRegisteration getCurrentUnitAndGivenRoleActorStrategy() {
        ActorStrategyRegisteration registeration = new ActorStrategyRegisteration("jiuqi.nr.curunit_givenrole", "\u5f53\u524d\u5355\u4f4d\u4e0b\u6307\u5b9a\u89d2\u8272\u7684\u7528\u6237");
        registeration.order((short)10);
        registeration.executorFactory((IActorStrategyExecutorFactory)new CurrentUnitAndGivenRoleActorStrategy(this.roleService, this.getCurrentUnitEexcutor()));
        return registeration;
    }

    @Bean(value={"com.jiuqi.nr.workflow2.actor.ParentsUnitActorStrategy"})
    public ActorStrategyRegisteration getParentsUnitActorStrategy() {
        ActorStrategyRegisteration registeration = new ActorStrategyRegisteration("jiuqi.nr.parentsunit", "\u6240\u6709\u4e0a\u7ea7\u5355\u4f4d\u4e0b\u7684\u7528\u6237");
        registeration.order((short)14);
        registeration.executorFactory((IActorStrategyExecutorFactory)new ParentsUnitActorStrategy(this.getParentsUnitEexcutor()));
        return registeration;
    }

    @Bean(value={"com.jiuqi.nr.workflow2.actor.ParentsUnitAndGivenRoleActorStrategy"})
    public ActorStrategyRegisteration getParentsUnitAndGivenRoleActorStrategy() {
        ActorStrategyRegisteration registeration = new ActorStrategyRegisteration("jiuqi.nr.parentsunit_givenrole", "\u6240\u6709\u4e0a\u7ea7\u5355\u4f4d\u4e0b\u6307\u5b9a\u89d2\u8272\u7684\u7528\u6237");
        registeration.order((short)15);
        registeration.executorFactory((IActorStrategyExecutorFactory)new ParentsUnitAndGivenRoleActorStrategy(this.roleService, this.getParentsUnitEexcutor()));
        return registeration;
    }

    @Bean(value={"com.jiuqi.nr.workflow2.actor.ParentUnitActorStrategy"})
    public ActorStrategyRegisteration getParentUnitActorStrategy() {
        ActorStrategyRegisteration registeration = new ActorStrategyRegisteration("jiuqi.nr.parentunit", "\u76f4\u63a5\u4e0a\u7ea7\u5355\u4f4d\u4e0b\u7684\u7528\u6237");
        registeration.order((short)11);
        registeration.executorFactory((IActorStrategyExecutorFactory)new ParentUnitActorStrategy(this.getParentUnitEexcutor()));
        return registeration;
    }

    @Bean(value={"com.jiuqi.nr.workflow2.actor.ParentUnitAndGivenRoleActorStrategy"})
    public ActorStrategyRegisteration getParentUnitAndGivenRoleActorStrategy() {
        ActorStrategyRegisteration registeration = new ActorStrategyRegisteration("jiuqi.nr.parentunit_givenrole", "\u76f4\u63a5\u4e0a\u7ea7\u5355\u4f4d\u4e0b\u6307\u5b9a\u89d2\u8272\u7684\u7528\u6237");
        registeration.order((short)13);
        registeration.executorFactory((IActorStrategyExecutorFactory)new ParentUnitAndGivenRoleActorStrategy(this.roleService, this.getParentUnitEexcutor()));
        return registeration;
    }

    @Bean(value={"com.jiuqi.nr.workflow2.actor.ParentUnitCanAuditActorStrategy"})
    public ActorStrategyRegisteration getParentUnitCanAuditActorStrategy() {
        ActorStrategyRegisteration registeration = new ActorStrategyRegisteration("jiuqi.nr.parentunit_canaudit", "\u76f4\u63a5\u4e0a\u7ea7\u5355\u4f4d\u4e0b\u6709\u5ba1\u6279\u6743\u9650\u7684\u4eba");
        registeration.order((short)12);
        registeration.executorFactory((IActorStrategyExecutorFactory)new ParentUnitCanAuditActorStrategy(this.getParentUnitEexcutor(), this.getCanAuditExecutor()));
        return registeration;
    }

    @Bean(value={"com.jiuqi.nr.workflow2.actor.SpecifiedUsersActorStrategy"})
    public ActorStrategyRegisteration getSpecifiedUsersActorStrategy() {
        AuthorityEexcutor canReadExecutor = new AuthorityEexcutor();
        canReadExecutor.setDefinitionAuthorityProvider(this.definitionAuthorityProvider);
        canReadExecutor.setEntityAuthorityService(this.entityAuthorityService);
        canReadExecutor.setPeriodEntityAdapter(this.periodAdapter);
        canReadExecutor.setAuthority(AuthorityEexcutor.AuthorityItem.READ);
        SpecifiedUsersActorStrategy specifiedUsersActorStrategy = new SpecifiedUsersActorStrategy(this.roleService);
        specifiedUsersActorStrategy.setAuthorityEexcutor(canReadExecutor);
        ActorStrategyRegisteration registeration = new ActorStrategyRegisteration("jiuqi.nr.givenuser_givenrole", "\u6307\u5b9a\u7684\u7528\u6237\u6216\u89d2\u8272");
        registeration.order((short)16);
        registeration.executorFactory((IActorStrategyExecutorFactory)specifiedUsersActorStrategy);
        return registeration;
    }

    @Bean(value={"com.jiuqi.nr.workflow2.actor.SystemUserActorStrategy"})
    public ActorStrategyRegisteration getSystemUserActorStrategy() {
        ActorStrategyRegisteration registeration = new ActorStrategyRegisteration("jiuqi.nr.systemuser", "\u7cfb\u7edf\u7ba1\u7406\u5458");
        registeration.order((short)255);
        registeration.executorFactory((IActorStrategyExecutorFactory)new SystemUserActorStrategy(this.systemUserService));
        return registeration;
    }
}

