/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.var.Variable
 *  com.jiuqi.np.dataengine.var.VariableManager
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 */
package com.jiuqi.nr.datacrud.impl.service.impl;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.var.Variable;
import com.jiuqi.np.dataengine.var.VariableManager;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.datacrud.ParamRelation;
import com.jiuqi.nr.datacrud.spi.IExecutorContextFactory;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

public class DefaultExecutorContextFactory
implements IExecutorContextFactory {
    @Autowired
    protected IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    protected IRunTimeViewController runTimeViewController;
    @Autowired
    protected IEntityViewRunTimeController iEntityViewRunTimeController;

    @Override
    public ExecutorContext getExecutorContext(ParamRelation regionRelation, DimensionValueSet dimensionCombination) {
        return this.getExecutorContext(regionRelation, dimensionCombination, null);
    }

    @Override
    public ExecutorContext getExecutorContext(ParamRelation regionRelation, DimensionCombination dimensionCombination) {
        if (dimensionCombination != null) {
            return this.getExecutorContext(regionRelation, dimensionCombination.toDimensionValueSet(), null);
        }
        return this.getExecutorContext(regionRelation, (DimensionValueSet)null, null);
    }

    @Override
    public ExecutorContext getExecutorContext(ParamRelation regionRelation, DimensionValueSet dimensionCombination, Iterator<Variable> variables) {
        ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
        executorContext.setDefaultGroupName(regionRelation.getDefaultGroupName());
        executorContext.setJQReportModel(true);
        executorContext.setVarDimensionValueSet(dimensionCombination);
        String contextEntityId = DsContextHolder.getDsContext().getContextEntityId();
        if (!StringUtils.isEmpty((String)contextEntityId)) {
            executorContext.setOrgEntityId(contextEntityId);
        }
        ArrayList<Variable> variableList = null;
        if (variables == null) {
            List contextVar = DsContextHolder.getDsContext().getVariables();
            if (contextVar != null) {
                variableList = new ArrayList(contextVar);
            }
        } else {
            variableList = new ArrayList<Variable>();
            while (variables.hasNext()) {
                variableList.add(variables.next());
            }
        }
        if (variableList != null) {
            VariableManager variableManager = executorContext.getVariableManager();
            DefaultExecutorContextFactory.addVar(variableList, variableManager);
        }
        IFmlExecEnvironment iFmlExecEnvironment = this.getIFmlExecEnvironment(executorContext, regionRelation);
        if (variableList != null) {
            VariableManager variableManager = iFmlExecEnvironment.getVariableManager();
            DefaultExecutorContextFactory.addVar(variableList, variableManager);
        }
        executorContext.setEnv(iFmlExecEnvironment);
        executorContext.setAutoDataMasking(false);
        return executorContext;
    }

    private static void addVar(List<Variable> variableList, VariableManager variableManager) {
        for (Variable variable : variableList) {
            Variable findVar = variableManager.find(variable.getVarName());
            if (findVar != null) {
                try {
                    findVar.setVarValue(variable.getVarValue(null));
                    variable = findVar;
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
            variableManager.add(variable);
        }
    }

    protected IFmlExecEnvironment getIFmlExecEnvironment(ExecutorContext executorContext, ParamRelation regionRelation) {
        return regionRelation.getReportFmlExecEnvironment();
    }

    @Override
    public ExecutorContext getExecutorContext(ParamRelation regionRelation, DimensionCombination dimensionCombination, Iterator<Variable> variables) {
        if (dimensionCombination != null) {
            return this.getExecutorContext(regionRelation, dimensionCombination.toDimensionValueSet(), variables);
        }
        return this.getExecutorContext(regionRelation, (DimensionValueSet)null, variables);
    }
}

