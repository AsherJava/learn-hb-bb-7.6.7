/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.var.VariableManager
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.definition.internal.impl.RunTimeEntityViewDefineImpl
 *  com.jiuqi.nr.context.cxt.DsContext
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 */
package com.jiuqi.nr.dataservice.core.dimension.provider;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.var.VariableManager;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.definition.internal.impl.RunTimeEntityViewDefineImpl;
import com.jiuqi.nr.context.cxt.DsContext;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.dataservice.core.dimension.DimensionContext;
import com.jiuqi.nr.dataservice.core.dimension.DimensionEnvironment;
import com.jiuqi.nr.dataservice.core.dimension.FixedDimensionValue;
import com.jiuqi.nr.dataservice.core.dimension.VariableDimensionValue;
import com.jiuqi.nr.dataservice.core.dimension.provider.DimensionProviderData;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DimensionProviderUtil {
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;

    public ExecutorContext getExecutorContext() {
        ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
        ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(this.runTimeViewController, this.dataDefinitionRuntimeController, this.entityViewRunTimeController, null);
        executorContext.setEnv((IFmlExecEnvironment)environment);
        executorContext.setJQReportModel(true);
        DsContext dsContext = DsContextHolder.getDsContext();
        List variables = dsContext.getVariables();
        if (variables != null) {
            VariableManager variableManager = executorContext.getVariableManager();
            variables.forEach(v -> variableManager.add(v));
        }
        return executorContext;
    }

    public EntityViewDefine getEntityViewDefine(DimensionContext context, VariableDimensionValue variableDimensionValue, DimensionProviderData dimensionProviderData) {
        EntityViewDefine entityViewDefine = null;
        if (context != null) {
            entityViewDefine = context.getDimViewDefine(variableDimensionValue.getName());
        }
        if (entityViewDefine != null && StringUtils.isNotEmpty((String)dimensionProviderData.getFilter())) {
            RunTimeEntityViewDefineImpl entityViewDefine1 = new RunTimeEntityViewDefineImpl();
            entityViewDefine1.setEntityId(entityViewDefine.getEntityId());
            entityViewDefine1.setFilterRowByAuthority(entityViewDefine.getFilterRowByAuthority());
            entityViewDefine1.setRowFilterExpression("(" + dimensionProviderData.getFilter() + ")" + (StringUtils.isEmpty((String)entityViewDefine.getRowFilterExpression()) ? "" : " and (" + entityViewDefine.getRowFilterExpression() + ")"));
            entityViewDefine = entityViewDefine1;
        }
        if (entityViewDefine == null || !entityViewDefine.getEntityId().equalsIgnoreCase(variableDimensionValue.getEntityID())) {
            entityViewDefine = DimensionEnvironment.getEntityViewRunTimeController().buildEntityView(variableDimensionValue.getEntityID(), dimensionProviderData.getFilter());
        }
        return entityViewDefine;
    }

    public EntityViewDefine getEntityViewDefine(DimensionContext context, FixedDimensionValue dwDimensionValue, DimensionProviderData dimensionProviderData) {
        EntityViewDefine entityViewDefine = null;
        if (context != null) {
            entityViewDefine = context.getDimViewDefine(dwDimensionValue.getName());
        }
        if (entityViewDefine != null && StringUtils.isNotEmpty((String)dimensionProviderData.getFilter())) {
            RunTimeEntityViewDefineImpl entityViewDefine1 = new RunTimeEntityViewDefineImpl();
            entityViewDefine1.setEntityId(entityViewDefine.getEntityId());
            entityViewDefine1.setFilterRowByAuthority(entityViewDefine.getFilterRowByAuthority());
            entityViewDefine1.setRowFilterExpression("(" + dimensionProviderData.getFilter() + ")" + (StringUtils.isEmpty((String)entityViewDefine.getRowFilterExpression()) ? "" : " and (" + entityViewDefine.getRowFilterExpression() + ")"));
            entityViewDefine = entityViewDefine1;
        }
        if (entityViewDefine == null || !entityViewDefine.getEntityId().equalsIgnoreCase(dwDimensionValue.getEntityID())) {
            entityViewDefine = DimensionEnvironment.getEntityViewRunTimeController().buildEntityView(dwDimensionValue.getEntityID(), dimensionProviderData.getFilter());
        }
        return entityViewDefine;
    }
}

