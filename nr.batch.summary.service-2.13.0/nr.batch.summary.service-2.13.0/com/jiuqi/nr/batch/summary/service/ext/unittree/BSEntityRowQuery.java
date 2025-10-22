/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.var.Variable
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.batch.summary.service.ext.unittree;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.var.Variable;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.service.IEntityDataService;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class BSEntityRowQuery {
    @Resource
    private IEntityDataService dataService;
    @Resource
    private IEntityViewRunTimeController viewAdapter;
    @Resource
    private IDataDefinitionRuntimeController tbRtCtl;
    @Resource
    private IRunTimeViewController viewController;

    public IEntityTable makeFullTreeData(IUnitTreeContext contextHelper) {
        IEntityQuery query = this.newIEntityQuery(contextHelper.getEntityDefine().getId(), contextHelper.getRowFilterExpression());
        query.sorted(true);
        this.appendMasterKeys(contextHelper, query.getMasterKeys());
        ExecutorContext executorContext = this.newQueryContext(contextHelper);
        return this.executeFullBuild((IContext)executorContext, query);
    }

    public IEntityTable makeExecuteReader(IUnitTreeContext contextHelper, Map<String, Object> customVariableMap) {
        IEntityQuery query = this.newIEntityQuery(contextHelper.getEntityDefine().getId(), contextHelper.getRowFilterExpression());
        this.appendMasterKeys(contextHelper, query.getMasterKeys());
        ExecutorContext executorContext = this.newQueryContext(contextHelper, customVariableMap);
        return this.executeReader((IContext)executorContext, query);
    }

    private ExecutorContext newQueryContext(IUnitTreeContext contextHelper) {
        ExecutorContext executorContext = new ExecutorContext(this.tbRtCtl);
        executorContext.setPeriodView(contextHelper.getPeriodEntity().getKey());
        return executorContext;
    }

    private ExecutorContext newQueryContext(IUnitTreeContext contextHelper, Map<String, Object> customVariableMap) {
        ExecutorContext executorContext = this.newQueryContext(contextHelper);
        IFmlExecEnvironment env = executorContext.getEnv();
        if (env == null) {
            env = new ReportFmlExecEnvironment(this.viewController, this.tbRtCtl, this.viewAdapter, contextHelper.getFormScheme().getKey());
            executorContext.setEnv(env);
        }
        if (env.getVariableManager() != null) {
            for (Map.Entry<String, Object> entry : customVariableMap.entrySet()) {
                env.getVariableManager().add(new Variable(entry.getKey(), entry.getKey(), 6, entry.getValue()));
            }
        }
        return executorContext;
    }

    private IEntityQuery newIEntityQuery(String entityId, String rowFilter) {
        IEntityQuery query = this.dataService.newEntityQuery();
        query.setEntityView(this.viewAdapter.buildEntityView(entityId, rowFilter));
        query.setMasterKeys(new DimensionValueSet());
        query.setAuthorityOperations(AuthorityType.Read);
        query.sorted(true);
        query.lazyQuery();
        query.markLeaf();
        return query;
    }

    private void appendMasterKeys(IUnitTreeContext contextHelper, DimensionValueSet masterKeys) {
        String mainDimName = contextHelper.getEntityDefine().getDimensionName();
        String periodDimName = contextHelper.getPeriodEntity().getDimensionName();
        String periodValue = contextHelper.getPeriod();
        Map dimValueSet = contextHelper.getDimValueSet();
        for (Map.Entry entry : dimValueSet.entrySet()) {
            if (mainDimName.equals(entry.getKey())) continue;
            if (periodDimName.equals(entry.getKey())) {
                masterKeys.setValue(periodDimName, (Object)periodValue);
                continue;
            }
            masterKeys.setValue(((DimensionValue)entry.getValue()).getName(), (Object)((DimensionValue)entry.getValue()).getValue());
        }
    }

    private IEntityTable executeFullBuild(IContext executorContext, IEntityQuery query) {
        try {
            return query.executeFullBuild(executorContext);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private IEntityTable executeReader(IContext executorContext, IEntityQuery query) {
        try {
            return query.executeReader(executorContext);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

