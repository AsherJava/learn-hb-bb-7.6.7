/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 */
package com.jiuqi.nr.workflow2.service.helper;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nr.workflow2.service.helper.IProcessEntityQueryHelper;
import com.jiuqi.nr.workflow2.service.helper.IProcessRuntimeParamHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProcessEntityQueryHelper
implements IProcessEntityQueryHelper {
    @Autowired
    private IEntityDataService dataService;
    @Autowired
    private IRunTimeViewController viewController;
    @Autowired
    private IDataDefinitionRuntimeController tbRtCtl;
    @Autowired
    private IEntityViewRunTimeController viewAdapter;
    @Autowired
    private IProcessRuntimeParamHelper runtimeParamHelper;

    @Override
    public IEntityTable getIEntityTable(String entityId) {
        return null;
    }

    @Override
    public IEntityTable getIEntityTable(String taskKey, String period) throws Exception {
        IEntityQuery entityQuery = this.makeIEntityQuery(taskKey, period);
        ExecutorContext executorContext = this.makeExecuteContext(taskKey, period);
        executorContext.setVarDimensionValueSet(entityQuery.getMasterKeys());
        return this.getIEntityTable(entityQuery, executorContext);
    }

    @Override
    public IEntityTable getIEntityTable(String taskKey, String period, DimensionValueSet dimensionValueSet) throws Exception {
        IEntityQuery entityQuery = this.makeIEntityQuery(taskKey, period);
        entityQuery.setMasterKeys(dimensionValueSet);
        ExecutorContext executorContext = this.makeExecuteContext(taskKey, period);
        executorContext.setVarDimensionValueSet(entityQuery.getMasterKeys());
        return this.getIEntityTable(entityQuery, executorContext);
    }

    @Override
    public IEntityTable getIEntityTable(IEntityQuery entityQuery, ExecutorContext executorContext) throws Exception {
        return entityQuery.executeReader((IContext)executorContext);
    }

    @Override
    public IEntityQuery makeIEntityQuery(String taskKey, String period) {
        DimensionValueSet masterKeys = this.getMasterKeys(taskKey, period);
        IEntityQuery query = this.dataService.newEntityQuery();
        query.sorted(true);
        query.setMasterKeys(masterKeys);
        query.setAuthorityOperations(AuthorityType.Read);
        query.markLeaf();
        query.lazyQuery();
        IEntityDefine entityDefine = this.runtimeParamHelper.getProcessEntityDefinition(taskKey);
        query.setEntityView(this.runtimeParamHelper.buildEntityView(taskKey, period, entityDefine.getId()));
        return query;
    }

    @Override
    public ExecutorContext makeExecuteContext(String taskKey, String period) {
        ExecutorContext executorContext = new ExecutorContext(this.tbRtCtl);
        IPeriodEntity periodEntity = this.runtimeParamHelper.getPeriodEntityDefine(taskKey);
        executorContext.setPeriodView(periodEntity.getKey());
        FormSchemeDefine formScheme = this.runtimeParamHelper.getFormScheme(taskKey, period);
        IFmlExecEnvironment env = executorContext.getEnv();
        if (formScheme != null && null == env) {
            env = new ReportFmlExecEnvironment(this.viewController, this.tbRtCtl, this.viewAdapter, formScheme.getKey());
            executorContext.setEnv(env);
        }
        return executorContext;
    }

    private DimensionValueSet getMasterKeys(String taskKey, String period) {
        DimensionValueSet masterKeys = new DimensionValueSet();
        IPeriodEntity periodEntity = this.runtimeParamHelper.getPeriodEntityDefine(taskKey);
        masterKeys.setValue(periodEntity.getDimensionName(), (Object)period);
        return masterKeys;
    }
}

