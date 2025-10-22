/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.entity.service.IEntityAuthorityService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 */
package com.jiuqi.nr.bpm.impl.Actor;

import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.bpm.Actor.Actor;
import com.jiuqi.nr.bpm.Actor.ActorStrategy;
import com.jiuqi.nr.bpm.Actor.ActorStrategyParameter;
import com.jiuqi.nr.bpm.businesskey.BusinessKey;
import com.jiuqi.nr.bpm.businesskey.BusinessKeyInfo;
import com.jiuqi.nr.bpm.common.Task;
import com.jiuqi.nr.bpm.common.UserTask;
import com.jiuqi.nr.bpm.de.dataflow.util.DimensionUtil;
import com.jiuqi.nr.bpm.impl.common.EntityQueryVersion;
import com.jiuqi.nr.bpm.impl.common.NrParameterUtils;
import com.jiuqi.nr.bpm.impl.common.NrProcessAuthorityProvider;
import com.jiuqi.nr.bpm.service.BpmIEntityUpgrader;
import com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.entity.service.IEntityAuthorityService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

abstract class AuthorityActorStrategyBase
implements ActorStrategy<ActorStrategyParameter.Useless> {
    private static final Logger logger = LoggerFactory.getLogger(AuthorityActorStrategyBase.class);
    private final String title;
    @Autowired
    protected IEntityAuthorityService entityAuthorityService;
    @Autowired
    protected DefinitionAuthorityProvider definitionAuthorityProvider;
    @Autowired
    protected NrParameterUtils nrParameterUtils;
    @Autowired
    protected NrProcessAuthorityProvider nrProcessAuthorityProvider;
    @Autowired
    private BpmIEntityUpgrader bpmIEntityUpgrader;
    @Autowired
    private IPeriodEntityAdapter periodEntityAdapter;
    @Autowired
    protected DimensionUtil dimensionUtil;

    protected AuthorityActorStrategyBase(String title) {
        this.title = title;
    }

    protected abstract Set<String> getCanOperateIdentityKeys(EntityViewDefine var1, String var2, Date var3, Date var4, BusinessKeyInfo var5);

    protected abstract boolean canOperateEntity(EntityViewDefine var1, String var2, Date var3, Date var4, BusinessKeyInfo var5);

    protected abstract Map<BusinessKey, Boolean> canBatchOperateEntity(EntityViewDefine var1, String var2, Date var3, Date var4, List<BusinessKey> var5);

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public String serializeParameter(ActorStrategyParameter.Useless parameter) {
        return null;
    }

    @Override
    public ActorStrategyParameter.Useless readParameter(String parameterJson) throws Exception {
        return null;
    }

    @Override
    public Set<String> getActors(BusinessKeyInfo businessKey, ActorStrategyParameter.Useless strategyParameter, Task task) {
        String entityView = this.bpmIEntityUpgrader.queryEntityView(businessKey);
        if (!StringUtils.hasLength(entityView)) {
            return Collections.emptySet();
        }
        EntityViewDefine entityViewDefine = this.nrParameterUtils.getEntityView(entityView);
        TableModelDefine tableModel = this.nrParameterUtils.getFirstEntityTable(businessKey);
        String entityKeyData = businessKey.getMasterEntityInfo().getMasterEntityKey(tableModel.getName());
        FormSchemeDefine formScheme = this.nrParameterUtils.getFormScheme(businessKey.getFormSchemeKey());
        String dateTime = formScheme.getDateTime();
        EntityQueryVersion queryPeriod = EntityQueryVersion.parseFromPeriod(businessKey.getPeriod(), businessKey.getFormSchemeKey(), dateTime);
        return this.getCanOperateIdentityKeys(entityViewDefine, entityKeyData, queryPeriod.getQueryVersionStartDate(), queryPeriod.getQueryVersionDate(), businessKey);
    }

    @Override
    public Set<String> getActors(BusinessKeyInfo businessKey, ActorStrategyParameter.Useless strategyParameter, UserTask userTask) {
        return this.getActors(businessKey, strategyParameter, (Task)null);
    }

    @Override
    public boolean isUserMatch(BusinessKeyInfo businessKey, ActorStrategyParameter.Useless parameter, Actor actor, Task task) {
        if (actor.getIdentityId() == null) {
            return false;
        }
        String viewKey = this.bpmIEntityUpgrader.queryEntityView(businessKey);
        if (!StringUtils.hasLength(viewKey)) {
            return false;
        }
        EntityViewDefine entityViewDefine = this.nrParameterUtils.getEntityView(viewKey);
        TableModelDefine tableModel = this.nrParameterUtils.getFirstEntityTable(businessKey);
        String entityKeyData = businessKey.getMasterEntityInfo().getMasterEntityKey(tableModel.getName());
        FormSchemeDefine formScheme = this.nrParameterUtils.getFormScheme(businessKey.getFormSchemeKey());
        EntityQueryVersion queryPeriod = EntityQueryVersion.parseFromPeriod(businessKey.getPeriod(), businessKey.getFormSchemeKey(), formScheme.getDateTime());
        return this.canOperateEntity(entityViewDefine, entityKeyData, queryPeriod.getQueryVersionStartDate(), queryPeriod.getQueryVersionDate(), businessKey);
    }

    @Override
    public Map<BusinessKey, Boolean> isBatchUserMatch(List<BusinessKey> businessKeys, ActorStrategyParameter.Useless parameter, Actor actor, Task task) {
        if (actor.getIdentityId() == null) {
            return new HashMap<BusinessKey, Boolean>();
        }
        if (businessKeys == null || businessKeys.size() == 0) {
            return new HashMap<BusinessKey, Boolean>();
        }
        BusinessKey businessKey = (BusinessKey)businessKeys.stream().findFirst().get();
        String viewKey = this.bpmIEntityUpgrader.queryEntityView(businessKey);
        if (!StringUtils.hasLength(viewKey)) {
            return new HashMap<BusinessKey, Boolean>();
        }
        EntityViewDefine entityViewDefine = this.nrParameterUtils.getEntityView(viewKey);
        TableModelDefine tableModel = this.nrParameterUtils.getFirstEntityTable(businessKey);
        String entityKeyData = businessKey.getMasterEntityInfo().getMasterEntityKey(tableModel.getName());
        FormSchemeDefine formScheme = this.nrParameterUtils.getFormScheme(businessKey.getFormSchemeKey());
        EntityQueryVersion queryPeriod = EntityQueryVersion.parseFromPeriod(businessKey.getPeriod(), businessKey.getFormSchemeKey(), formScheme.getDateTime());
        return this.canBatchOperateEntity(entityViewDefine, entityKeyData, queryPeriod.getQueryVersionStartDate(), queryPeriod.getQueryVersionDate(), businessKeys);
    }
}

