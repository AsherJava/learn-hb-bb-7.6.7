/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IExpressionEvaluator
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.definition.internal.env.ExectuteFormula
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 */
package com.jiuqi.nr.enumcheck.utils;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IExpressionEvaluator;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.internal.env.ExectuteFormula;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.enumcheck.common.EnumDataCheckParam;
import java.util.List;

public class VariableExectuteFormula
implements ExectuteFormula {
    private IRunTimeViewController runtimeView = (IRunTimeViewController)BeanUtil.getBean(IRunTimeViewController.class);
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController = (IDataDefinitionRuntimeController)BeanUtil.getBean(IDataDefinitionRuntimeController.class);
    private IDataAccessProvider dataAccessProvider = (IDataAccessProvider)BeanUtil.getBean(IDataAccessProvider.class);
    private IEntityViewRunTimeController entityViewRunTimeController = (IEntityViewRunTimeController)BeanUtil.getBean(IEntityViewRunTimeController.class);
    private EnumDataCheckParam enumDataCheckParam;
    private DimensionValueSet dimensionValueSet;

    public VariableExectuteFormula(EnumDataCheckParam enumDataCheckParam, DimensionValueSet dimensionValueSet) {
        this.enumDataCheckParam = enumDataCheckParam;
        this.dimensionValueSet = dimensionValueSet;
    }

    public Object evalFormula(String name, String formulaExpression) {
        if (StringUtils.isNotEmpty((String)formulaExpression)) {
            IExpressionEvaluator expressionEvaluator = this.dataAccessProvider.newExpressionEvaluator();
            ExecutorContext executorContext = this.getExecutorContext();
            try {
                AbstractData result = expressionEvaluator.eval(formulaExpression, executorContext, this.dimensionValueSet);
                return result.getAsObject();
            }
            catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    private ExecutorContext getExecutorContext() {
        ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
        executorContext.setJQReportModel(true);
        DimensionValueSet varDimensionValueSet = new DimensionValueSet();
        for (int i = 0; i < this.dimensionValueSet.size(); ++i) {
            String dimensionName = this.dimensionValueSet.getName(i);
            Object dimensionValue = this.dimensionValueSet.getValue(i);
            if (dimensionValue instanceof List) continue;
            varDimensionValueSet.setValue(dimensionName, dimensionValue);
        }
        executorContext.setVarDimensionValueSet(varDimensionValueSet);
        ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(this.runtimeView, this.dataDefinitionRuntimeController, this.entityViewRunTimeController, this.enumDataCheckParam.getFormSchemeKey(), this.enumDataCheckParam.getVariableMap());
        executorContext.setEnv((IFmlExecEnvironment)environment);
        return executorContext;
    }
}

